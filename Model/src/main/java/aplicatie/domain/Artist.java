package aplicatie.domain;

/**
 * Created by Micu on 3/12/2017.
 */
public class Artist {
    String nume;

    @Override
    public String toString() {
        return "Artist{" +
                "nume='" + nume + '\'' +
                ", id=" + id +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    Integer id;

    public Artist(String nume){
        this.nume = nume;
        this.id = -1;
    }

    public Artist(Integer id,String nume){
        this.nume = nume;
        this.id = id;
    }
}
