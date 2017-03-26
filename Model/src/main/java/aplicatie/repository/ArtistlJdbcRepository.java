package aplicatie.repository;

import aplicatie.domain.Artist;

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
public class ArtistlJdbcRepository implements IRepository<Integer,Artist> {
    private JdbcUtils dbUtils;

    public ArtistlJdbcRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Artist")) {
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
    public void save(Artist entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("INSERT INTO Artist(nume) VALUES (?);")){
            preStmt.setString(1,entity.getNume());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Artist where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Artist entity) {
        delete(integer);
        save(entity);
    }

    @Override
    public Artist findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Artist where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("Id");
                    String nume = result.getString("Nume");

                    Artist artist = new Artist(id, nume);
                    return artist;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Iterable<Artist> findAll() {
        Connection con=dbUtils.getConnection();
        List<Artist> artists=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Artist")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("Id");
                    String nume = result.getString("Nume");
                    Artist artist = new Artist(id, nume);
                    artists.add(artist);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return artists;
    }



}
