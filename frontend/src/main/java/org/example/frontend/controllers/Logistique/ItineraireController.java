package org.example.frontend.controllers.Logistique;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

import javafx.scene.control.TableCell;

import org.example.projetjavafx.Model.Itineraire;
import org.example.projetjavafx.ImplementationDAO.ItineraireImpl;
import org.example.projetjavafx.util.HibernateUtil;
import org.example.projetjavafx.DAO.ItineraireDAO;

public class ItineraireController {

    private ItineraireDAO dao = new ItineraireImpl(HibernateUtil.getSessionFactory());

    //Delcartaion du tableau d'affichage
    @FXML
    private TableView<Itineraire> ItTableView;
    @FXML
    private TableColumn<Itineraire, String> ColIT;
    @FXML
    private TableColumn<Itineraire, String> ColDep;
    @FXML
    private TableColumn<Itineraire, String> ColAr;
    @FXML
    private TableColumn<Itineraire, Integer> ColFrais;
    @FXML
    private  TableColumn<Itineraire,Void> ColEdit;
    @FXML
    private TableColumn<Itineraire,Void> ColDelete;

    @FXML
    private VBox FormItineraire;

    private AddFormController AddformController;

    private EditFormController EditfromControler;


    @FXML
    public void initialize() {
        ShowAdd();
        ColIT.setCellValueFactory(new PropertyValueFactory<>("codeit"));
        ColDep.setCellValueFactory(new PropertyValueFactory<>("villedep"));
        ColAr.setCellValueFactory(new PropertyValueFactory<>("villearr"));
        ColFrais.setCellValueFactory(new PropertyValueFactory<>("frais"));
        addButtonToTable();
        chargerTableView();
    }

    // === Chargement dynamique des vues ===
    @FXML
    public void ShowAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/frontend/Composant/Logistique/AddFormIt.fxml"));
            Node formView = loader.load();

            AddformController = loader.getController(); // Important

            // Récupération du bouton depuis le contrôleur du formulaire
            Button validBtn = (Button) formView.lookup("#ValidAddBtn");
            Button AnnulerBtn = (Button) formView.lookup("#AnnulerBtn");
            if (validBtn != null || AnnulerBtn != null) {
                validBtn.setOnAction(this::AddItinéraire);
                AnnulerBtn.setOnAction(this::Refresh);
            }

            FormItineraire.getChildren().clear();
            FormItineraire.getChildren().add(formView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ShowEdit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/frontend/Composant/Logistique/EditFormIt.fxml"));
            Node formView = loader.load();

            EditfromControler = loader.getController();

            Button ValidBtn = (Button) formView.lookup("#ValidBtn");
            Button AnnulerBtn = (Button) formView.lookup("#AnnulerBtn");
            if (ValidBtn != null || AnnulerBtn != null) {
                ValidBtn.setOnAction(this::EditItinéraire);
                AnnulerBtn.setOnAction(this::CloseEditForm);
            }
            FormItineraire.getChildren().clear();
            FormItineraire.getChildren().add(formView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

   @FXML
    public void Refresh(ActionEvent event){
            AddformController.reset();
    }

    // === Bouton ajouter itinéraire ===
    @FXML
    public void AddItinéraire(ActionEvent event){
        String villeDepart = AddformController.getVilleDepart();
        String villeArrive = AddformController.getVilleArrive();
        Integer frais = AddformController.getFrais();

        if(villeDepart == "" || villeArrive == ""){
            AddformController.Warning();
        }
        else{
            boolean response =dao.ajouterItineraire(villeDepart, villeArrive, frais);
            if(response){
                addButtonToTable();
                chargerTableView();
                AddformController.Clean();
            }
            else {
                AddformController.Warning1();
            }
        }
    }

    // === Édition d’itinéraire ===
    @FXML
    public void EditItinéraire(ActionEvent event){
        String Id = EditfromControler.getIdIT();
        String VilleDepart = EditfromControler.getVilleDepart();
        String VilleArrive = EditfromControler.getVilleArrive();
        Integer Frais = EditfromControler.getFrais();

        if(Id == "" || VilleArrive == "" || VilleDepart == ""){
            EditfromControler.Warning();
        }
        else{
            boolean response = dao.modifierItineraire(Id,VilleDepart,VilleArrive,Frais);
            if(response){
                CloseEditForm(event);
                addButtonToTable();
                chargerTableView();
            }else{
                EditfromControler.Warning1();
            }
        }
    }

    @FXML
    public void CloseEditForm(ActionEvent event){
        ShowAdd();
    }

    //==TableView==

    private void addButtonToTable() {
        // Colonne Edit
        ColEdit.setCellFactory(col -> new TableCell<Itineraire, Void>() {
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
                    Itineraire itineraire = getTableView().getItems().get(getIndex());
                    System.out.println("Edit clicked: " + itineraire.getCodeit());
                    // Ouvre le formulaire d'édition, charge l'objet sélectionné dedans
                    ShowEdit();
                    if (EditfromControler != null) {
                        EditfromControler.RemplirForm(itineraire);
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
        ColDelete.setCellFactory(col -> new TableCell<Itineraire, Void>() {
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
                    Itineraire itineraire = getTableView().getItems().get(getIndex());
                    System.out.println("Delete clicked: " + itineraire.getCodeit());

                    // Appel DAO pour supprimer
                    String result = dao.supprimerItineraire(itineraire.getCodeit());
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
        List<Itineraire> list = dao.listerItineraire();
        if (list != null) {
            ItTableView.setItems(FXCollections.observableArrayList(list));
        }
    }
}
