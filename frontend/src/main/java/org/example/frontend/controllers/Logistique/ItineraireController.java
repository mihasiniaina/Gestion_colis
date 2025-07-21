package org.example.frontend.controllers.Logistique;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import java.io.IOException;
import java.util.List;

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

    // Méthode pour styliser les boîtes de dialogue
    private void styleAlert(Alert alert) {
        // Appliquer le CSS d'abord pour obtenir les bonnes dimensions
        String css = """
            .dialog-pane {
                -fx-background-color: #f0f0f0;
                -fx-pref-width: 400;
                -fx-pref-height: 200;
            }
            .dialog-pane .header-panel {
                -fx-background-color: #20323D;
            }
            .dialog-pane .header-panel .label {
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-font-size: 18px;
            }
            .dialog-pane .content.label {
                -fx-font-size: 16px;
                -fx-text-fill: #333333;
            }
            .dialog-pane .button-bar .button {
                -fx-background-color: #20323D;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-font-size: 14px;
                -fx-pref-width: 100;
                -fx-pref-height: 35;
            }
            .dialog-pane .button-bar .button:hover {
                -fx-background-color: #2c4450;
            }
            """;

        alert.getDialogPane().getStylesheets().add("data:text/css;base64," +
                java.util.Base64.getEncoder().encodeToString(css.getBytes()));

        // Obtenir la fenêtre de l'alerte après l'application du CSS
        alert.getDialogPane().applyCss();
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

        // Obtenir les dimensions de l'écran
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Centrer horizontalement et positionner légèrement vers le bas
        double dialogWidth = 400; // Largeur définie dans le CSS
        double dialogHeight = 200; // Hauteur définie dans le CSS

        alertStage.setX((screenBounds.getWidth() - dialogWidth) / 2); // Parfaitement centré
        alertStage.setY((screenBounds.getHeight() - dialogHeight) / 2 + 100); // Centré + 100px vers le bas
    }

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
        AddformController.resetWarning();
    }

    // === Bouton ajouter itinéraire ===
    @FXML
    public void AddItinéraire(ActionEvent event) {
        String villeDepart = AddformController.getVilleDepart();
        String villeArrive = AddformController.getVilleArrive();
        Integer frais = AddformController.getFrais();

        if (villeDepart.isEmpty() || villeArrive.isEmpty()) {
            AddformController.Warning();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Ajouter un itinéraire ?");
            alert.setContentText("Voulez-vous vraiment ajouter cet itinéraire ?");

            // Appliquer le style personnalisé
            styleAlert(alert);

            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    boolean success = dao.ajouterItineraire(villeDepart, villeArrive, frais);
                    if (success) {
                        addButtonToTable();
                        chargerTableView();
                        AddformController.Clean();
                    } else {
                        AddformController.Warning1();
                    }
                }
            });
        }
    }

    // === Édition d'itinéraire ===
    @FXML
    public void EditItinéraire(ActionEvent event) {
        String Id = EditfromControler.getIdIT();
        String VilleDepart = EditfromControler.getVilleDepart();
        String VilleArrive = EditfromControler.getVilleArrive();
        Integer Frais = EditfromControler.getFrais();

        if (Id.isEmpty() || VilleArrive.isEmpty() || VilleDepart.isEmpty()) {
            EditfromControler.Warning();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Modifier l'itinéraire ?");
            alert.setContentText("Voulez-vous enregistrer les modifications ?");

            // Appliquer le style personnalisé
            styleAlert(alert);

            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    boolean success = dao.modifierItineraire(Id, VilleDepart, VilleArrive, Frais);
                    if (success) {
                        CloseEditForm(event);
                        addButtonToTable();
                        chargerTableView();
                    } else {
                        EditfromControler.Warning1();
                    }
                }
            });
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

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer cet itinéraire ?");

                    // Appliquer le style personnalisé
                    styleAlert(alert);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == javafx.scene.control.ButtonType.OK) {
                            String result = dao.supprimerItineraire(itineraire.getCodeit());
                            if (result != null) {
                                System.out.println("Suppression réussie");
                                chargerTableView();
                            } else {
                                System.err.println("Erreur suppression");
                            }
                        }
                    });
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