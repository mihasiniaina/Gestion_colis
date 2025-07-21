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
import javafx.scene.control.cell.PropertyValueFactory;
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
    private VoitureEditController EditfromControler;


    @FXML
    public void initialize() {
        ShowAdd();
        MatriculeCo.setCellValueFactory(new PropertyValueFactory<>("idvoit"));  // ou "matricule" selon ton champ réel
        DesignCo.setCellValueFactory(new PropertyValueFactory<>("design"));
        VilleDepCo.setCellValueFactory(cellData -> {
            Itineraire itineraire = cellData.getValue().getItineraire();
            return javafx.beans.binding.Bindings.createStringBinding(
                    () -> itineraire != null ? itineraire.getVilledep() : ""
            );
        });
        VilleArCo.setCellValueFactory(cellData -> {
            Itineraire itineraire = cellData.getValue().getItineraire();
            return javafx.beans.binding.Bindings.createStringBinding(
                    () -> itineraire != null ? itineraire.getVillearr() : ""
            );
        });

        addButtonToTable();
        chargerTableView();
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
            } else if (fxmlPath.contains("EditFormVo")) {
                EditfromControler = loader.getController();
            }

            Button ValidBtn = (Button) formView.lookup("#AddBtn");
            Button AnnulerBtn = (Button) formView.lookup("#AnnulerBtn");
            if (fxmlPath.contains("AddFormVo")) {
                VoAddcontroller = loader.getController();
                ValidBtn.setOnAction(this::AddVoiture);
                AnnulerBtn.setOnAction(this::Clean);
            } else if (fxmlPath.contains("EditFormVo")) {
                EditfromControler = loader.getController();
                ValidBtn.setOnAction(this::validerModification);
                AnnulerBtn.setOnAction(this::CloseEditForm);
            }


            FormVoiture.getChildren().clear();
            FormVoiture.getChildren().add(formView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void validerModification(ActionEvent event) {
        // Récupérer les données modifiées depuis EditfromControler (formulaire édition)
        String matricule = EditfromControler.getMatricule();
        String designation = EditfromControler.getDesignation();
        String itineraire = EditfromControler.getItineraire();

        if (matricule.isEmpty() || designation.isEmpty() || itineraire.isEmpty()) {
            EditfromControler.Warning();
        } else {
            // Appeler DAO pour modifier la voiture
            boolean reponse = dao.modifierVoiture(matricule, designation, itineraire);
            if (reponse) {
                System.out.println("Modification réussie");
                // Recharger la table pour afficher les modifications
                chargerTableView();
                // Optionnel : reset ou fermer le formulaire édition
                EditfromControler.reset();
            } else {
                EditfromControler.Warning1();
                System.err.println("Erreur modification");
            }
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
                chargerTableView();
            } else {
                VoAddcontroller.Warning1();
                System.err.println(itineraire);
            }
        }
    }

    public void CloseEditForm(ActionEvent event){
         ShowAdd();
    }


    public void Clean(ActionEvent event) {
        VoAddcontroller.resetAddWarning();
        VoAddcontroller.reset();
    }
    private void addButtonToTable() {
        // Colonne Edit
        ColEdit.setCellFactory(col -> new TableCell<Voiture, Void>() {
            private final Button editBtn = new Button();

            {
                ImageView editIcon = new ImageView(new Image(
                        getClass().getResource("/org/example/frontend/Images/icons8-créer-un-nouveau-48.png").toExternalForm()
                ));
                editIcon.setFitWidth(40);
                editIcon.setFitHeight(40);
                editBtn.setGraphic(editIcon);
                editBtn.getStyleClass().add("EditBtn");
                editBtn.setOnAction(e -> {
                   Voiture voiture = getTableView().getItems().get(getIndex());
                    System.out.println("Edit clicked: " + voiture.getIdvoit());
                    // Ouvre le formulaire d'édition, charge l'objet sélectionné dedans
                    ShowEdit();
                    if (EditfromControler != null) {
                        EditfromControler.RemplirForm(voiture);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editBtn);
            }
        });

        // Colonne Delete
        ColDelete.setCellFactory(col -> new TableCell<Voiture, Void>() {
            private final Button deleteBtn = new Button();

            {

                ImageView deleteIcon = new ImageView(new Image(
                        getClass().getResource("/org/example/frontend/Images/icons8-poubelle-64.png").toExternalForm()
                ));
                deleteIcon.setFitWidth(40);
                deleteIcon.setFitHeight(40);
                deleteBtn.setGraphic(deleteIcon);
                deleteBtn.getStyleClass().add("DeleteBtn");
                deleteBtn.setOnAction(e -> {
                    Voiture voiture= getTableView().getItems().get(getIndex());
                    System.out.println("Delete clicked: " + voiture.getIdvoit());

                    // Appel DAO pour supprimer
                    String result = dao.supprimerVoiture(voiture.getIdvoit());
                    if (result != null) {
                        System.out.println("Suppression réussie");
                        // Rafraîchir la table après suppression
                        chargerTableView();
                    } else {
                        System.err.println("Erreur suppression");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    public void chargerTableView() {
        List<Voiture> list = dao.listerVoiture();
        if (list != null) {
            VoTable.setItems(FXCollections.observableArrayList(list));
        }
    }

}
