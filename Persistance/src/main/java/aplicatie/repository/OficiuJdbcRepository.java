package aplicatie.repository;

import aplicatie.domain.Oficiu;

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
public class OficiuJdbcRepository implements IRepository<Integer,Oficiu> {
    private JdbcUtils dbUtils;

    public OficiuJdbcRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Oficiu")) {
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
    public void save(Oficiu entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("INSERT INTO Oficiu(nume, descriere) VALUES (?,?);")){
            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getDescriere());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Oficiu where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Oficiu entity) {
        delete(integer);
        save(entity);
    }

    @Override
    public Oficiu findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Oficiu where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    String descrirere = result.getString("descriere");
                    Oficiu oficiu = new Oficiu(id, nume,descrirere);
                    return oficiu;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Iterable<Oficiu> findAll() {
        Connection con=dbUtils.getConnection();
        List<Oficiu> oficii=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Oficiu")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    String descrirere = result.getString("descriere");
                    Oficiu oficiu = new Oficiu(id, nume,descrirere);
                    oficii.add(oficiu);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return oficii;
    }



}
