package network.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:20:27 PM
 */
public class CumparatorDTO implements Serializable{
    Integer id;
    String nume;
    Integer bilete;
    Integer show;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getBilete() {
        return bilete;
    }

    public void setBilete(Integer bilete) {
        this.bilete = bilete;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public CumparatorDTO(Integer id, String nume, Integer bilete, Integer show) {
        this.id = id;
        this.nume = nume;
        this.bilete = bilete;
        this.show = show;
    }

    @Override
    public String toString() {
        return "CumparatorDTO{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", bilete=" + bilete +
                ", show=" + show +
                '}';
    }
}
