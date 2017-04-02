package aplicatie;

import aplicatie.View.LoginView;
import aplicatie.repository.CumparatorJdbcRepository;
import aplicatie.repository.PersonalJdbcRepository;
import aplicatie.repository.SpectacolJdbcRepository;
import aplicatie.service.ComandService;
import aplicatie.service.IServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import network.rpcprotocol.ChatServerRpcProxy;

import java.io.IOException;
import java.util.Properties;

import static javafx.application.Application.launch;

/**
 * Created by grigo on 2/25/16.
 */
public class StartRpcClient extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IServer server= (IServer) new ChatServerRpcProxy(serverIP, serverPort);

        //LoginView ctrl=new LoginView(server);


        LoginView serv;

        FXMLLoader loader=new FXMLLoader(getClass().getResource("View/login.fxml"));
        AnchorPane myPane;
        try {
            myPane = (AnchorPane) loader.load();
            serv = loader.getController();
            serv.setService(server);
            Scene myScene = new Scene(myPane);
            primaryStage.setTitle("Login");
            primaryStage.setScene(myScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static ComandService getService(Properties serverProps){

        PersonalJdbcRepository repoP=new PersonalJdbcRepository(serverProps);
        CumparatorJdbcRepository repoC=new CumparatorJdbcRepository(serverProps);
        SpectacolJdbcRepository repoS=new SpectacolJdbcRepository(serverProps);
        ComandService service=new ComandService(repoS,repoC,repoP);
        return service;

    }
}
