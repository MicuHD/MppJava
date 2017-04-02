package aplicatie.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by grigo on 3/4/17.
 */
public class JdbcUtils {
    private Properties jdbcProps;

    public JdbcUtils(Properties props){
        jdbcProps=props;
    }
    private  Connection instance=null;
    private Connection getNewConnection(){
        String driver=jdbcProps.getProperty("labmpp.jdbc.driver");
        String url=jdbcProps.getProperty("labmpp.jdbc.url");
        Connection con=null;
        try {
            Class.forName(driver);
//                con=DriverManager.getConnection("Festival");
                con=DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver "+e);
        } catch (SQLException e) {
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }
}
