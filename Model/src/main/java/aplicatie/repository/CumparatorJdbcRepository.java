package aplicatie.repository;

import aplicatie.domain.Cumparator;

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
public class CumparatorJdbcRepository implements IRepository<Integer,Cumparator> {
    private JdbcUtils dbUtils;

    public CumparatorJdbcRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Cumparator")) {
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
    public void save(Cumparator entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("INSERT INTO Cumparator(nume, bilete,idspectacol) VALUES (?,?,?);")){
            preStmt.setString(1,entity.getNume());
            preStmt.setInt(2,entity.getBilete());
            preStmt.setInt(3,entity.getIdSpectacol());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Cumparator where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Cumparator entity) {
        delete(integer);
        save(entity);
    }

    @Override
    public Cumparator findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Cumparator where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer bilete = result.getInt("bilete");
                    Integer spectacol = result.getInt("idspectacol");
                    Cumparator cumparator = new Cumparator(id,nume,bilete,spectacol);
                    return cumparator;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Iterable<Cumparator> findAll() {
        Connection con=dbUtils.getConnection();
        List<Cumparator> cumparatori=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Cumparator")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer bilete = result.getInt("bilete");
                    Integer spectacol = result.getInt("idspectacol");
                    Cumparator cumparator = new Cumparator(id,nume,bilete,spectacol);
                    cumparatori.add(cumparator);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return cumparatori;
    }



}
