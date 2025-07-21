package org.example.frontend.controllers.Colis;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

import org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.ImplementationDAO.EnvoyerImpl;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Voiture;
import org.example.projetjavafx.util.HibernateUtil;

public class EnvoyerController {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());

    @FXML
    private TableView<Envoyer> Table;
    @FXML
    private TableColumn<Envoyer, Integer> id;
    @FXML
    private TableColumn<Envoyer, String> envoyeur;
    @FXML
    private TableColumn<Envoyer, String> email;
    @FXML
    private TableColumn<Envoyer, String> colis;
    @FXML
    private TableColumn<Envoyer, Integer> Frais;
    @FXML
    private TableColumn<Envoyer, String> voiture;
    @FXML
    private TableColumn<Envoyer, String> date;
    @FXML
    private TableColumn<Envoyer, String> recepteur;
    @FXML
    private TableColumn<Envoyer, String> contact;

    @FXML
    private DatePicker Date1;
    @FXML
    private  DatePicker Date2;

    @FXML
    public StackPane FormView;


    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("idenvoi"));
        envoyeur.setCellValueFactory(new PropertyValueFactory<>("nomEnvoyeur"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailEnvoyeur"));
        colis.setCellValueFactory(new PropertyValueFactory<>("colis"));
        recepteur.setCellValueFactory(new PropertyValueFactory<>("nomRecepteur"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactRecepteur"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_envoi"));
        Frais.setCellValueFactory(new PropertyValueFactory<>("frais"));

        // Afficher l’ID de la voiture associée (voiture.getIdvoit())
        voiture.setCellValueFactory(cellData -> {
            Voiture v = cellData.getValue().getVoiture();
            return new SimpleStringProperty(v != null ? String.valueOf(v.getIdvoit()) : "");
        });

        FormView.setVisible(false);
        chargerTableView();
    }

    @FXML
    public void ShowAd(ActionEvent event) {
        FormView.setVisible(true);
        LoadForm("/org/example/frontend/Composant/Colis/AddEnv.fxml");
    }

    @FXML
    public void ShowEdit(ActionEvent event) {
        FormView.setVisible(true);
        LoadForm("/org/example/frontend/Composant/Colis/EditEnv.fxml");
    }

    @FXML
    public void ShowDelete(ActionEvent event) {
        FormView.setVisible(true);
        LoadForm("/org/example/frontend/Composant/Colis/DeleteEnv.fxml");
    }

    private void LoadForm(String fxmlPath) {
        try {
            FormView.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node formView = loader.load();
            Object controller = loader.getController();
            if (controller instanceof EnvoyerDeleteController) {
                ((EnvoyerDeleteController) controller).setMainController(this);
            } else if (controller instanceof  EnvoyerEdditContorller) {
                ((EnvoyerEdditContorller) controller).setMainController(this);
            } else if (controller instanceof  EnvoyeAddControler) {
                ((EnvoyeAddControler) controller).setMainController(this);
            }

            FormView.getChildren().add(formView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerTableView() {
        List<Envoyer> list = dao.listerEnvoi();
        if (list != null && !list.isEmpty()) {
            Table.setItems(FXCollections.observableArrayList(list));
        } else {
            System.out.println("Aucune donnée à afficher.");
        }
    }

    public void TrierTableView(ActionEvent event){
        if(Date1.getValue() == null || Date2.getValue() == null){
            chargerTableView();
        }
        else{
            List<Envoyer> list = dao.trierColis(Date1.getValue(),Date2.getValue());
            Table.setItems((FXCollections.observableArrayList(list)));
        }
    }

    public void Reset(){
        chargerTableView();
    }
}
