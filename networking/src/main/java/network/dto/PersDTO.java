package network.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:20:27 PM
 */
public class PersDTO implements Serializable{
    private String user;
    private String passwd;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public PersDTO(String user) {
        this(user,"");
    }

    public PersDTO(String user, String passwd) {
        this.user = user;
        this.passwd = passwd;
    }



    @Override
    public String toString(){
        return "PersDTO["+user+' '+passwd+"]";
    }
}
