package network.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:20:27 PM
 */
public class CuvantDTO implements Serializable{
    public String getCuvant() {
        return cuvant;
    }

    public void setCuvant(String cuvant) {
        this.cuvant = cuvant;
    }

    private String cuvant;


    public CuvantDTO(String cuvant) {
        this.cuvant = cuvant;
    }

    @Override
    public String toString() {
        return "CuvantDTO{" +
                "cuvant='" + cuvant + '\'' +
                '}';
    }
}
