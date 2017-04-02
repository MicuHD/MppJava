import aplicatie.repository.CumparatorJdbcRepository;
import aplicatie.repository.PersonalJdbcRepository;
import aplicatie.repository.SpectacolJdbcRepository;
import aplicatie.service.IServer;
import network.utils.AbstractServer;
import network.utils.ChatRpcConcurrentServer;
import network.utils.ServerException;
import server.ChatServerImpl;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by grigo on 2/25/16.
 */
public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        PersonalJdbcRepository pRepo=new PersonalJdbcRepository(serverProps);
        CumparatorJdbcRepository cRepo=new CumparatorJdbcRepository(serverProps);
        SpectacolJdbcRepository sRepo = new SpectacolJdbcRepository(serverProps);
        IServer chatServerImpl=new ChatServerImpl(pRepo,sRepo,cRepo);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ChatRpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
