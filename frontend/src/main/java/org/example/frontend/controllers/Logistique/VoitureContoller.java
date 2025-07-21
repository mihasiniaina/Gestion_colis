package org.example.frontend.controllers.Logistique;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import org.example.projetjavafx.ImplementationDAO.VoitureImpl;
import org.example.projetjavafx.DAO.VoitureDAO;
import org.example.projetjavafx.Model.Itineraire;
import org.example.projetjavafx.util.HibernateUtil;
import org.example.projetjavafx.Model.Voiture;

public class VoitureContoller {

    private VoitureDAO dao = new VoitureImpl(HibernateUtil.getSessionFactory());

    @FXML
    public VBox FormVoiture;

    @FXML
    private TableView<Voiture> VoTable;
    @FXML
    private TableColumn<Voiture,String> MatriculeCo;
    @FXML
    private TableColumn<Voiture,String> DesignCo;
    @FXML
    private TableColumn<Voiture,String> VilleDepCo;
    @FXML
    private TableColumn<Voiture,String>VilleArCo;
    @FXML
    private  TableColumn<Voiture,Void> ColEdit;
    @FXML
    private TableColumn<Voiture,Void>ColDelete;

    private VoAddFormController VoAddcontroller;


    @FXML
    public void initialize() {
        ShowAdd();
    }

    @FXML
    public void ShowAdd() {
        loadForm("/org/example/frontend/Composant/Logistique/AddFormVo.fxml");
    }

    @FXML
    public void ShowEdit() {
        loadForm("/org/example/frontend/Composant/Logistique/EditFormVo.fxml");
    }

    private void loadForm(String fxmlPath) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node formView = loader.load();

            if (fxmlPath.contains("AddFormVo")) {
                VoAddcontroller = loader.getController();
            } else {

            }
            Button ValidBtn = (Button) formView.lookup("#AddBtn");
            Button AnnulerBtn = (Button) formView.lookup(("#AnnulerBtn"));
            if (ValidBtn != null && AnnulerBtn != null) {
                ValidBtn.setOnAction(this::AddVoiture);
                AnnulerBtn.setOnAction(this::Clean);
            }
            FormVoiture.getChildren().clear();
            FormVoiture.getChildren().add(formView);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void AddVoiture(ActionEvent event) {
        String matricule = VoAddcontroller.getMatricule();
        String designation = VoAddcontroller.getDesign();
        String itineraire = VoAddcontroller.getItineraire();

        if (matricule.isEmpty() || designation.isEmpty() || itineraire.isEmpty()) {
            VoAddcontroller.Warning();
        } else {
            boolean reponse = dao.ajouterVoiture(matricule, designation, itineraire);
            if (reponse) {
                VoAddcontroller.reset();
            } else {
                VoAddcontroller.Warning1();
                System.err.println(itineraire);
            }
        }
    }

    public void EdditVoiture(ActionEvent event){

    }


    public void Clean(ActionEvent event) {
        VoAddcontroller.reset();
    }


}
