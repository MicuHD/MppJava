package aplicatie.domain;

/**
 * Created by Micu on 3/12/2017.
 */
public class Personal {
    Integer id;
    String nume;
    String username;
    String parola;

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public Personal(Integer id, String nume, String username, String parola) {
        this.id = id;
        this.nume = nume;
        this.username = username;
        this.parola = parola;
    }
    public Personal(String nume, String username, String parola) {
        this.id = -1;
        this.nume = nume;
        this.username = username;
        this.parola = parola;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
