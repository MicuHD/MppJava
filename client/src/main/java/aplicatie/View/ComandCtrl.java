package aplicatie.View;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.service.IClient;
import aplicatie.service.IServer;
import aplicatie.service.ShowException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by Micu on 3/18/2017.
 */
public class ComandCtrl implements IClient {
    private ObservableList<Spectacol> model;
    private ObservableList<Spectacol> modelC;
    private Personal personal;
    @FXML
    public TextField cautareField;

    @FXML
    public Button logoutBtn;
    @FXML
    public TableView<Spectacol> tableView;
    @FXML
    public TableColumn<Spectacol,String> artistColumn;
    @FXML
    public TableColumn<Spectacol,String> locatieColumn;
    @FXML
    public TableColumn<Spectacol,String> dataColumn;
    @FXML
    public TableColumn<Spectacol,Integer> disponibileColumn;
    @FXML
    public TableColumn<Spectacol,Integer> vanduteColumn;

    @FXML
    public TableView<Spectacol> tableCView;
    @FXML
    public TableColumn<Spectacol,String> artistCColumn;
    @FXML
    public TableColumn<Spectacol,String> locatieCColumn;
    @FXML
    public TableColumn<Spectacol,String> oraCColumn;
    @FXML
    public TableColumn<Spectacol,Integer> disponibileCColumn;

    @FXML
    public Button cautareBtn;

    @FXML
    public TextField numeField;
    @FXML
    Spinner<Integer> spinnerCount;
    @FXML
    public Button vindeBtn;

    private IServer server;
    public ComandCtrl(){

    }

    @FXML
    public void initialize() {
        artistColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("artist"));
        locatieColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("locatie"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("data"));
        disponibileColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, Integer>("disponibile"));
        vanduteColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, Integer>("vandute"));
        artistCColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("artist"));
        locatieCColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("locatie"));
        oraCColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("ora"));
        disponibileCColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, Integer>("disponibile"));
        spinnerCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255,0));



        tableView.setRowFactory(tv -> new TableRow<Spectacol>() {
            @Override
            public void updateItem(Spectacol item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (item.getDisponibile().equals(0)) {
                    setStyle("-fx-background-color: tomato;");
                } else {
                    setStyle("");
                }
            }
        });
        tableCView.setRowFactory(tv -> new TableRow<Spectacol>() {
            @Override
            public void updateItem(Spectacol item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (item.getDisponibile().equals(0)) {
                    setStyle("-fx-background-color: tomato;");
                } else {
                    setStyle("");
                }
            }
        });

    }

    @FXML
    public void handleCautare(){
        try {
            this.modelC = FXCollections.observableArrayList(server.cautare(cautareField.getText()));
        } catch (ShowException e) {
            e.printStackTrace();
        }
        this.tableCView.setItems(modelC);
    }
    public void setService(IServer serv,Personal pers){
        this.server=serv;
        this.personal=pers;
        //Stage thisStage = (Stage)logoutBtn.getScene().getWindow();
        //stage.setTitle("Vanzator: " + pers.getNume());
        try {
            List<Spectacol> specs = server.getSpecacol();
            this.model= FXCollections.observableArrayList(specs);

        } catch (ShowException e) {
            e.printStackTrace();
        }
        tableView.setItems(model);
        //service.addObserver(this);
    }


    @FXML
    public void handleLogout(){
        Stage thisStage = (Stage)logoutBtn.getScene().getWindow();
        try {
            server.logout(personal,this);
            Platform.exit();
            System.exit(0);
            thisStage.close();
            thisStage.close();
        } catch (ShowException e) {
            e.printStackTrace();
        }



    }
    @FXML
    public void handleCumparare(){
        try{
            Spectacol spec = tableView.getSelectionModel().getSelectedItem();
            if(this.numeField.getText().equals("")){
                showErrorMessage("introduceti numele / nr de bilete!");
            }
            else{
                String nume = this.numeField.getText();
                Integer nrbilet = this.spinnerCount.getValue();
                if(nrbilet > spec.getDisponibile() || nrbilet == 0){
                    showErrorMessage("Numarul de bilete cerut este indisponibil");
                }
                else if(spec.equals(null)){
                    showErrorMessage("Alegeti un spectacol");
                }
                else{

                    server.cumparare(new Cumparator(nume,nrbilet,spec.getId()));
                    this.tableCView.setItems(null);
                }


            }
        }catch (NullPointerException err){
            showErrorMessage("Alegeti un spectacol");
        } catch (ShowException e) {
            showErrorMessage("Numarul de bilete cerut este indisponibil");
        }


    }

    void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    public void SoldTickets(Spectacol spec) {
        System.out.println("ID_ul ESTEEE"+spec.getId().toString());
//        int index = model.indexOf(spec);
        for(int i=0;i<model.size();i++){
            if(model.get(i).getId().equals(spec.getId())){
                model.set(i,spec);
                break;
            }
        }
        tableView.setItems(model);
        tableView.refresh();
    }
}

