package network.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:20:27 PM
 */
public class SpectacolDTO implements Serializable{
    Integer id;
    String locatie;
    Integer disponibile;
    Integer vandute;
    String artist;
    String data;
    String ora;

    public SpectacolDTO(Integer id, String locatie, Integer disponibile, Integer vandute, String artist, String data, String ora) {
        this.id = id;
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

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    @Override
    public String toString() {
        return "SpectacolDTO{" +
                "id=" + id +
                ", locatie='" + locatie + '\'' +
                ", disponibile=" + disponibile +
                ", vandute=" + vandute +
                ", artist='" + artist + '\'' +
                ", data='" + data + '\'' +
                ", ora='" + ora + '\'' +
                '}';
    }
}
