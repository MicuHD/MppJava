package aplicatie.repository;

import aplicatie.domain.Spectacol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by grigo on 3/2/17.
 */
public class SpectacolJdbcRepository implements IRepository<Integer,Spectacol> {
    private JdbcUtils dbUtils;

    public SpectacolJdbcRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Spectacol")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Spectacol entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("INSERT INTO Spectacol(locatie,disponibile,vandute,artist,data,ora) " +
                                                            "VALUES (?,?,?,?,?,?);")){
            preStmt.setString(1,entity.getLocatie());
            preStmt.setInt(2,entity.getDisponibile());
            preStmt.setInt(3,entity.getVandute());
            preStmt.setString(4,entity.getArtist());
            preStmt.setString(5,entity.getData());
            preStmt.setString(6,entity.getOra());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Spectacol where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Spectacol entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("UPDATE Spectacol SET Disponibile=?,vandute=? WHERE Id = ?;")){
            preStmt.setInt(1,entity.getDisponibile());
            preStmt.setInt(2,entity.getVandute());
            preStmt.setInt(3,entity.getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public Spectacol findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Spectacol where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String locatie = result.getString("locatie");
                    int disponibile = result.getInt("disponibile");
                    int vandute = result.getInt("vandute");
                    String artist = result.getString("artist");
                    String data = result.getString("data");
                    String ora = result.getString("ora");
                    Spectacol spectacol = new Spectacol(id, locatie,disponibile,vandute,artist,data,ora);
                    return spectacol;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Iterable<Spectacol> findAll() {
        Connection con=dbUtils.getConnection();
        List<Spectacol> spectacols=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Spectacol")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String locatie = result.getString("locatie");
                    int disponibile = result.getInt("disponibile");
                    int vandute = result.getInt("vandute");
                    String artist = result.getString("artist");
                    String data = result.getString("data");
                    String ora = result.getString("ora");
                    Spectacol spectacol = new Spectacol(id, locatie,disponibile,vandute,artist,data,ora);
                    spectacols.add(spectacol);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return spectacols;
    }
}
