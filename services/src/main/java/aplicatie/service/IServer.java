package aplicatie.service;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;

import java.util.List;

/**
 * Created by Micu on 4/2/2017.
 */
public interface IServer{
    List<Spectacol> getSpecacol() throws ShowException;
    //boolean cumparare(Spectacol spec, String nume,Integer nrbilete);
    boolean cumparare(Cumparator cump) throws ShowException;
    List<Spectacol> cautare(String data) throws ShowException;
    Personal login(Personal pers,IClient client) throws ShowException;
    void logout(Personal user,IClient client) throws ShowException;
}
