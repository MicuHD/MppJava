package server;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.repository.CumparatorJdbcRepository;
import aplicatie.repository.PersonalJdbcRepository;
import aplicatie.repository.SpectacolJdbcRepository;
import aplicatie.service.ChatException;
import aplicatie.service.IClient;
import aplicatie.service.IServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 1:39:47 PM
 */
public class ChatServerImpl implements IServer {
    private Map<String, IClient> loggedClients;


    private PersonalJdbcRepository persRepo;
    private SpectacolJdbcRepository specRepo;
    private CumparatorJdbcRepository cumpRepo;



    public ChatServerImpl(PersonalJdbcRepository pRepo, SpectacolJdbcRepository sRepo,CumparatorJdbcRepository cRepo) {

        this.persRepo = pRepo;
        this.specRepo = sRepo;
        this.cumpRepo = cRepo;
        loggedClients=new ConcurrentHashMap<>();
    }


    public synchronized List<Spectacol> getSpecacol() throws ChatException{
        List<Spectacol> specs = (List<Spectacol>) specRepo.findAll();
        System.out.println("get specs list");
        return specs;
    }

    public synchronized boolean cumparare(Cumparator cump) throws ChatException {
        Spectacol spec = specRepo.findOne(cump.getIdSpectacol());
        if(spec.getDisponibile() < cump.getBilete())
            throw new ChatException("Number of tickets not available");
        spec.setDisponibile(spec.getDisponibile()-cump.getBilete());
        spec.setVandute(spec.getVandute()+cump.getBilete());
        specRepo.update(spec.getId(),spec);
        cumpRepo.save(cump);
        spec = specRepo.findOne(cump.getIdSpectacol());
        notifySoldTicket(spec);
        return true;
    }

    public synchronized List<Spectacol> cautare(String data) throws ChatException {
        List<Spectacol> specs = (List<Spectacol>) specRepo.findAll();

        List<Spectacol> spectacols = new ArrayList<>();
        for (Object obj:specs) {
            Spectacol spectacol = (Spectacol)obj;
            if(spectacol.getData().equals(data)){
                spectacols.add(spectacol);
            }
        }
        return spectacols;
    }



    public synchronized Personal login(Personal user, IClient client) throws ChatException {
        Personal userR=persRepo.login(user);
        if (userR!=null){
            if(loggedClients.get(user.getUsername())!=null)
                throw new ChatException("User already logged in.");
            loggedClients.put(user.getUsername(), client);
        }
        else
            throw new ChatException("Authentication failed.");
        return userR;
    }

//
    public synchronized void logout(Personal user,IClient client) throws ChatException {
        IClient localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new ChatException("User "+user.getId()+" is not logged in.");
    }


    private final int defaultThreadsNo=5;
    private void notifySoldTicket(Spectacol spec) throws ChatException {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IClient client :loggedClients.values()){
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ");
                        client.SoldTickets(spec);
                    } catch (ChatException e) {
                        System.out.println("Error notifying friend " + e);
                    }
                });

        }
        executor.shutdown();
    }
}
