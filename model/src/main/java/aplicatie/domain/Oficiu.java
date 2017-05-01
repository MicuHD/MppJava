package aplicatie.domain;

/**
 * Created by Micu on 3/12/2017.
 */
public class Oficiu {
    Integer id;
    String nume;
    String descriere;

    @Override
    public String toString() {
        return "Oficiu{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                '}';
    }

    public Oficiu(String nume, String descriere) {
        this.id = -1;
        this.nume = nume;
        this.descriere = descriere;
    }

    public Oficiu(Integer id, String nume, String descriere) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
    }

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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
