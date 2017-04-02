package aplicatie.service;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.utils.Observable;
import aplicatie.utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micu on 4/2/2017.
 */
public interface ICommandService<T> extends Observable<T>{
    List<Spectacol> getSpecacol();
    boolean cumparare(Spectacol spec, String nume,Integer nrbilete);
    List<Spectacol> cautare(String data);
    Personal login(String username, String password);

}
