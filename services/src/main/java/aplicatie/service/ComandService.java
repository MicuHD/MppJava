package aplicatie.service;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;

import aplicatie.repository.IRepository;
import aplicatie.repository.IUserRepository;
import aplicatie.utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micu on 3/18/2017.
 */
public class ComandService implements IServer {
    IRepository specrepo;
    IRepository cumprepo;
    IUserRepository persrepo;
    public ComandService(IRepository specrepo,IRepository cumparrepo,IUserRepository persrepo){
        this.specrepo = specrepo;
        this.cumprepo = cumparrepo;
        this.persrepo = persrepo;
    }

    public List<Spectacol> getSpecacol(){
        List<Spectacol> spectacols = new ArrayList<>();
        for (Object p:specrepo.findAll()) {
            spectacols.add((Spectacol) p);
        }
        return spectacols;
    }

    public boolean cumparare(Spectacol spec, String nume,Integer nrbilete)
    {
        Spectacol nspec = new Spectacol(spec.getLocatie(),spec.getDisponibile()-nrbilete, //
        spec.getVandute()+nrbilete,spec.getArtist(),spec.getData(),spec.getOra());
        specrepo.update(spec.getId(),nspec);
        cumprepo.save(new Cumparator(nume,nrbilete,spec.getId()));
        return true;
    }


    public List<Spectacol> cautare(String data) {
        List<Spectacol> spectacols = new ArrayList<>();
        for (Object obj:specrepo.findAll()) {
            Spectacol spectacol = (Spectacol)obj;
            if(spectacol.getData().equals(data)){
                spectacols.add(spectacol);
            }
        }
        return spectacols;
    }

    public Personal login(String username, String password){
        Personal pers = new Personal(username,password);
        return (Personal)persrepo.login(pers);
    }

    @Override
    public void login(Personal pers, IClient client) throws ChatException {

    }

    @Override
    public void logout(Personal user, IClient client) throws ChatException {

    }
}
