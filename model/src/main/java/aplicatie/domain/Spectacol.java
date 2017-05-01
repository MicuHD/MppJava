package aplicatie.domain;

/**
 * Created by Micu on 3/12/2017.
 */
public class Spectacol {
    Integer id;
    String locatie;
    Integer disponibile;
    Integer vandute;
    String artist;
    String data;
    String ora;

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Spectacol{" +
                "id=" + id +
                ", locatie='" + locatie + '\'' +
                ", disponibile=" + disponibile +
                ", vandute=" + vandute +
                ", Artist=" + artist +
                ", data='" + data + '\'' +
                '}';
    }

    public Spectacol(Integer id, String locatie, Integer disponibile, Integer vandute, String artist,String data,String ora) {
        this.id = id;
        this.locatie = locatie;
        this.disponibile = disponibile;
        this.vandute = vandute;
        this.artist = artist;
        this.data = data;
        this.ora = ora;
    }
    public Spectacol(String locatie, Integer disponibile, Integer vandute, String artist,String data,String ora) {
        this.id = -1;
        this.locatie = locatie;
        this.disponibile = disponibile;
        this.vandute = vandute;
        this.artist = artist;
        this.data = data;
        this.ora = ora;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Integer getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(Integer disponibile) {
        this.disponibile = disponibile;
    }

    public Integer getVandute() {
        return vandute;
    }

    public void setVandute(Integer vandute) {
        this.vandute = vandute;
    }

    public String getArtist() {
        return artist;
    }

}
