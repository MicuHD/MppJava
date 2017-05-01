package aplicatie.domain;

/**
 * Created by Micu on 3/12/2017.
 */
public class Cumparator {
    Integer id;
    String nume;

    @Override
    public String toString() {
        return "Cumparator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", bilete=" + bilete +
                ", IdSpectacol=" + IdSpectacol +
                '}';
    }

    public Cumparator(String nume, Integer bilete, Integer idSpectacol) {
        this.id = -1;
        this.nume = nume;
        this.bilete = bilete;
        IdSpectacol = idSpectacol;
    }

    public Cumparator(Integer id, String nume, Integer bilete, Integer idSpectacol) {
        this.id = id;
        this.nume = nume;
        this.bilete = bilete;
        IdSpectacol = idSpectacol;
    }

    Integer bilete;
    Integer IdSpectacol;

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

    public Integer getIdSpectacol() {
        return IdSpectacol;
    }

    public void setIdSpectacol(Integer idSpectacol) {
        IdSpectacol = idSpectacol;
    }
}
