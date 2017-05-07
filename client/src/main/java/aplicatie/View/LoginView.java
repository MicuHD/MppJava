package aplicatie.View;


import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.service.ShowException;
import aplicatie.service.IClient;
import aplicatie.service.IServer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by Micu on 3/18/2017.
 */
public class LoginView implements IClient{
    @FXML
    public Button loginBtn;
    @FXML
    public PasswordField passField;
    @FXML
    public TextField userField;
    @FXML
    public Label label;
    ComandCtrl serv;
    public LoginView(){

    }

    private IServer service;
    private Properties props;
    public void setService(IServer service){
        this.service=service;
    }

    @FXML
    public void handleLogin()
    {
        String passString = passField.getText();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            String hex = (new HexBinaryAdapter()).marshal(md5.digest(passString.getBytes()));
            //Personal pers = service.login(userField.getText(),hex.toString());
            Personal pers = service.login(new Personal(userField.getText(),hex.toString()),this);
            System.out.println("yeeep");
            System.out.println(pers.getNume());
            if(pers.equals(null)){
                label.setText("Parola sau user invalide!");
            }
            else{
                FXMLLoader loader=new FXMLLoader(getClass().getResource("comandPanel.fxml"));
                AnchorPane myPane = null;
                try {
                    myPane = (AnchorPane) loader.load();
                    serv = loader.getController();
                    serv.setService(service,pers);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene myScene = new Scene(myPane);
                Stage stage = new Stage();
                stage.setTitle("Vanzator: ");
                stage.setScene(myScene);
                stage.show();

                Stage thisStage = (Stage) loginBtn.getScene().getWindow();
                thisStage.close();

                label.setText("Totul e ok!");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ShowException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Server Error or something else");
            alert.setContentText(e.getMessage());
        }


    }

    @Override
    public void SoldTickets(Spectacol spec) {
        System.out.println("This is is wrong #####################");
        serv.SoldTickets(spec);
    }
}
