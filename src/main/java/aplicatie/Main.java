package aplicatie; /**
 * Created by Micu on 3/18/2017.
 */

import aplicatie.View.LoginView;
import aplicatie.repository.PersonalJdbcRepository;
import aplicatie.service.LoginService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        LoginView serv;

        FXMLLoader loader=new FXMLLoader(getClass().getResource("View/login.fxml"));
        AnchorPane myPane = null;
        try {
            myPane = (AnchorPane) loader.load();
            serv = loader.getController();
            serv.setService(getLoginService(serverProps),serverProps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene myScene = new Scene(myPane);
        primaryStage.setTitle("Login");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }

    static LoginService getLoginService(Properties serverProps){

        PersonalJdbcRepository repo=new PersonalJdbcRepository(serverProps);
        LoginService service=new LoginService(repo);
        return service;

    }
}
