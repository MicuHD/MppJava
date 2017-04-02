package aplicatie.service;

import aplicatie.domain.Personal;
import aplicatie.repository.IRepository;

/**
 * Created by Micu on 3/18/2017.
 */
public class LoginService {
    IRepository repo;
    public LoginService(IRepository repo){
        this.repo = repo;
    }

    public Personal login(String username, String password){

        Iterable<Personal> pers = repo.findAll();
        for (Personal persoana: pers) {
            if(persoana.getUsername().equals(username) && persoana.getParola().equals(password)){
                //System.out.println("ceva merge"+username+" "+password);
                Personal per = persoana;
                per.setParola("");
                return per;
            }
        }
        return null;

    }
}
