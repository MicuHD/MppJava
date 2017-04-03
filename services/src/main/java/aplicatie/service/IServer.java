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
public interface IServer{
    List<Spectacol> getSpecacol() throws ChatException;
    //boolean cumparare(Spectacol spec, String nume,Integer nrbilete);
    boolean cumparare(Cumparator cump) throws ChatException;
    List<Spectacol> cautare(String data) throws ChatException;
    Personal login(Personal pers,IClient client) throws ChatException;
    void logout(Personal user,IClient client) throws ChatException;
}
