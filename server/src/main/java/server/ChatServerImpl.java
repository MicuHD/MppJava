package server;

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

//    public synchronized User[] getLoggedFriends(User user) throws ChatException {
//        Iterable<User> friends=userRepository.getFriendsOf(user);
//        Set<User> result=new TreeSet<User>();
//        System.out.println("Logged friends for: "+user.getId());
//        for (User friend : friends){
//            if (loggedClients.containsKey(friend.getId())){    //the friend is logged in
//                result.add(new User(friend.getId()));
//                System.out.println("+"+friend.getId());
//            }
//        }
//        System.out.println("Size "+result.size());
//        return result.toArray(new User[result.size()]);
//    }


    public synchronized void login(Personal user, IClient client) throws ChatException {
        Personal userR=persRepo.login(user);
        if (userR!=null){
            if(loggedClients.get(user.getUsername())!=null)
                throw new ChatException("User already logged in.");
            loggedClients.put(user.getUsername(), client);
        }
        else
            throw new ChatException("Authentication failed.");
    }

//    @Override
//    public void login(Personal pers, IClient client) throws ChatException {
//
//    }
//
//  /*  private boolean isLogged(User u){
//        return loggedClients.get(u.getId())!=null;
//    }*/
//    private final int defaultThreadsNo=5;
//    private void notifyFriendsLoggedIn(User user) throws ChatException {
//        Iterable<User> friends=userRepository.getFriendsOf(user);
//        System.out.println("Logged "+friends);
//
//        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
//        for(User us :friends){
//            IChatClient chatClient=loggedClients.get(us.getId());
//            if (chatClient!=null)
//                executor.execute(() -> {
//                    try {
//                        System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
//                        chatClient.friendLoggedIn(user);
//                    } catch (ChatException e) {
//                        System.err.println("Error notifying friend " + e);
//                    }
//                });
//        }
//
//        executor.shutdown();
//    }
//
//    private void notifyFriendsLoggedOut(User user) throws ChatException {
//        Iterable<User> friends=userRepository.getFriendsOf(user);
//        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
//
//
//        for(User us :friends){
//            IChatClient chatClient=loggedClients.get(us.getId());
//            if (chatClient!=null)
//                executor.execute(() -> {
//                    try {
//                        System.out.println("Notifying ["+us.getId()+"] friend ["+user.getId()+"] logged out.");
//                        chatClient.friendLoggedOut(user);
//                    } catch (ChatException e) {
//                        System.out.println("Error notifying friend " + e);
//                    }
//                });
//
//        }
//        executor.shutdown();
//    }
//
//    public synchronized void sendMessage(Message message) throws ChatException {
//        String id_receiver=message.getReceiver().getId();
//        IChatClient receiverClient=loggedClients.get(id_receiver);
//        if (receiverClient!=null) {      //the receiver is logged in
//            messageRepository.save(message);
//            receiverClient.messageReceived(message);
//        }
//        else
//            throw new ChatException("User "+id_receiver+" not logged in.");
//    }
//
    public synchronized void logout(Personal user,IClient client) throws ChatException {
        IClient localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new ChatException("User "+user.getId()+" is not logged in.");
    }
//
//    public synchronized User[] getLoggedFriends(User user) throws ChatException {
//       Iterable<User> friends=userRepository.getFriendsOf(user);
//        Set<User> result=new TreeSet<User>();
//        System.out.println("Logged friends for: "+user.getId());
//        for (User friend : friends){
//            if (loggedClients.containsKey(friend.getId())){    //the friend is logged in
//                result.add(new User(friend.getId()));
//                System.out.println("+"+friend.getId());
//            }
//        }
//        System.out.println("Size "+result.size());
//        return result.toArray(new User[result.size()]);
//    }
}
