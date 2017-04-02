package aplicatie.View;


import aplicatie.domain.Personal;
import aplicatie.repository.ArtistlJdbcRepository;
import aplicatie.repository.CumparatorJdbcRepository;
import aplicatie.repository.SpectacolJdbcRepository;
import aplicatie.service.ComandService;
import aplicatie.service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by Micu on 3/18/2017.
 */
public class LoginView {
    @FXML
    public Button loginBtn;
    @FXML
    public PasswordField passField;
    @FXML
    public TextField userField;
    @FXML
    public Label label;

    public LoginView(){

    }

    private ComandService service;
    private Properties props;
    public void setService(ComandService service, Properties props){
        this.service=service;
        this.props = props;
    }

    @FXML
    public void handleLogin()
    {
        String passString = passField.getText();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            String hex = (new HexBinaryAdapter()).marshal(md5.digest(passString.getBytes()));
            Personal pers = service.login(userField.getText(),hex.toString());
            System.out.println(pers.getNume());
            if(pers.equals(null)){
                label.setText("Parola sau user invalide!");
            }
            else{
                FXMLLoader loader=new FXMLLoader(getClass().getResource("comandPanel.fxml"));
                AnchorPane myPane = null;
                try {
                    myPane = (AnchorPane) loader.load();
                    ComandPanelView serv = loader.getController();
                    serv.setService(service,props,pers);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene myScene = new Scene(myPane);
                Stage stage = new Stage();
                stage.setTitle("Vanzator: "+pers.getNume());
                stage.setScene(myScene);
                stage.show();

                Stage thisStage = (Stage) loginBtn.getScene().getWindow();
                thisStage.close();

                label.setText("Totul e ok!");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }




    }

}
