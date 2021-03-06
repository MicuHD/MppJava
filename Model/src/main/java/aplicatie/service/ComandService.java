package aplicatie.service;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.repository.IRepository;
import aplicatie.utils.Observable;
import aplicatie.utils.Observer;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micu on 3/18/2017.
 */
public class ComandService implements Observable<Spectacol> {
    ArrayList<Observer<Spectacol>> obs = new ArrayList<>();
    IRepository specrepo;
    IRepository cumprepo;
    public ComandService(IRepository specrepo,IRepository cumparrepo){
        this.specrepo = specrepo;
        this.cumprepo = cumparrepo;
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
        notifyObservers();
        return true;
    }

    @Override
    public void addObserver(Observer<Spectacol> o) {
        obs.add(o);
    }

    @Override
    public void removeObserver(Observer<Spectacol> o) {
        obs.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Spectacol> o:obs) {
            o.update(this);
        }
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
}
