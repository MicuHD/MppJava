package aplicatie.repository;

import aplicatie.domain.Personal;

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
public class PersonalJdbcRepository implements IRepository<Integer,Personal> {
    private JdbcUtils dbUtils;

    public PersonalJdbcRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Personal")) {
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
    public void save(Personal entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("INSERT INTO Personal(nume, username,parola) VALUES (?,?);")){
            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getParola());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Personal where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Personal entity) {
        delete(integer);
        save(entity);
    }

    @Override
    public Personal findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Personal where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    Personal pers = new Personal(id, nume,username,parola);
                    return pers;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Iterable<Personal> findAll() {
        Connection con=dbUtils.getConnection();
        List<Personal> pers=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Personal")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    Personal personal = new Personal(id, nume,username,parola);
                    pers.add(personal);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return pers;
    }



}
