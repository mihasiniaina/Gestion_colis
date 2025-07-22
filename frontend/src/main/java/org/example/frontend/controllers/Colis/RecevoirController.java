package org.example.frontend.controllers.Colis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import org.example.projetjavafx.DAO.RecevoirDAO;
import org.example.projetjavafx.ImplementationDAO.RecevoirImpl;
import org.example.projetjavafx.util.HibernateUtil;
import org.example.projetjavafx.util.TableauRecevoir;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RecevoirController {

    private final RecevoirDAO dao = new RecevoirImpl(HibernateUtil.getSessionFactory());
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    @FXML
    private TableView<TableauRecevoir> Table;
    @FXML
    private TableColumn<TableauRecevoir, Integer> Id;
    @FXML
    private TableColumn<TableauRecevoir, String> DtEnvoye;
    @FXML
    private TableColumn<TableauRecevoir, String> Colis;
    @FXML
    private TableColumn<TableauRecevoir, String> DtReception;
    @FXML
    private TableColumn<TableauRecevoir, String> Recepteur;
    @FXML
    private TableColumn<TableauRecevoir, String> Contact;
    @FXML
    private TableColumn<TableauRecevoir, String> Status;
    @FXML
    private TableColumn<TableauRecevoir, Void> BtnColonne;

    @FXML
    private Button SearchBtn;
    @FXML
    private Button TrierBtn;
    @FXML
    private Button Rafraichir;
    @FXML
    private DatePicker Date1;
    @FXML
    private DatePicker Date2;
    @FXML
    private TextField SearchBarre;

    @FXML
    private void initialize() {
        Id.setCellValueFactory(new PropertyValueFactory<>("idEnvoi"));
        DtEnvoye.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        (cellData.getValue().getDate_envoi() != null)
                                ? cellData.getValue().getDate_envoi().toString()
                                : ""));
        Colis.setCellValueFactory(new PropertyValueFactory<>("colis"));

        // Colonne modifiable
        DtReception.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        (cellData.getValue().getDate_recept() != null)
                                ? cellData.getValue().getDate_recept().format(formatter)
                                : ""));
        DtReception.setCellFactory(TextFieldTableCell.forTableColumn());
        DtReception.setOnEditCommit(event -> {
            TableauRecevoir item = event.getRowValue();
            String newValue = event.getNewValue();
            try {
                LocalDateTime nouvelleDate = LocalDateTime.parse(newValue, formatter);
                int idrecept = dao.getIdRecevoirByIdEnvoi(item.getIdEnvoi());

                if (idrecept == -1) {
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setTitle("Erreur");
                    err.setHeaderText(null);
                    err.setContentText("Impossible de retrouver l'ID de réception.");
                    styleAlert(err);
                    err.showAndWait();
                    chargerTable();
                    return;
                }

                // Confirmation modification
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmation");
                confirm.setHeaderText("Modifier la date de réception ?");
                confirm.setContentText("Nouvelle date : " + newValue);
                styleAlert(confirm);
                confirm.showAndWait().ifPresent(result -> {
                    if (result == ButtonType.OK) {
                        dao.modifierRecu(idrecept, nouvelleDate);
                    }
                    chargerTable();
                });

            } catch (DateTimeParseException e) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Format invalide");
                err.setHeaderText(null);
                err.setContentText("Utilisez ce format exact : yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                styleAlert(err);
                err.showAndWait();
                chargerTable();
            }
        });

        Recepteur.setCellValueFactory(new PropertyValueFactory<>("receptionneur"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        Status.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().isStatus() ? "Reçu" : "En attente"));

        Table.setEditable(true);
        chargerTable();
        ajouterBoutons();
    }

    private void chargerTable() {
        ObservableList<TableauRecevoir> data = FXCollections.observableArrayList();
        var liste = dao.listerRecu();
        if (liste != null) {
            data.addAll(liste);
            Table.setItems(data);
        } else {
            System.err.println("Erreur lors du chargement des données !");
        }
    }

    private void ajouterBoutons() {
        BtnColonne.setCellFactory(col -> new TableCell<>() {

            private final Button actionButton = new Button();
            private final ImageView iconRecevoir = createImageView("/org/example/frontend/Images/icons8-recevoir-50.png");
            private final ImageView iconAnnuler = createImageView("/org/example/frontend/Images/icons8-annuler-50.png");

            {
                actionButton.setStyle("-fx-cursor: hand; -fx-background-radius: 5;");
                actionButton.setOnAction(event -> {
                    TableauRecevoir item = getTableView().getItems().get(getIndex());

                    if (!item.isStatus()) {
                        // Confirmation avant ajout réception
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Confirmation");
                        confirm.setHeaderText("Recevoir le colis ?");
                        confirm.setContentText("Colis : " + item.getColis());
                        styleAlert(confirm);
                        confirm.showAndWait().ifPresent(result -> {
                            if (result == ButtonType.OK) {
                                String res = dao.ajouterRecu(item.getIdEnvoi(), LocalDateTime.now());
                                System.out.println(res != null ? "Réception réussie" : "Erreur réception");
                                chargerTable();
                            }
                        });
                    } else {
                        // Confirmation avant annulation réception
                        Integer idRecevoir = dao.getIdRecevoirByIdEnvoi(item.getIdEnvoi());
                        if (idRecevoir != null) {
                            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                            confirm.setTitle("Confirmation");
                            confirm.setHeaderText("Annuler la réception ?");
                            confirm.setContentText("Colis : " + item.getColis());
                            styleAlert(confirm);
                            confirm.showAndWait().ifPresent(result -> {
                                if (result == ButtonType.OK) {
                                    String res = dao.supprimerRecu(idRecevoir);
                                    System.out.println(res != null ? "Annulation réussie" : "Erreur annulation");
                                    chargerTable();
                                }
                            });
                        } else {
                            System.err.println("Impossible de trouver l'ID de réception pour annuler.");
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                TableauRecevoir item = getTableView().getItems().get(getIndex());

                if (!item.isStatus()) {
                    actionButton.setGraphic(iconRecevoir);
                    actionButton.setStyle("-fx-background-color: #20323D; -fx-cursor: hand; -fx-background-radius: 5;");
                } else {
                    actionButton.setGraphic(iconAnnuler);
                    actionButton.setStyle("-fx-background-color: red; -fx-cursor: hand; -fx-background-radius: 5;");
                }

                setGraphic(actionButton);
            }

            private ImageView createImageView(String resourcePath) {
                try {
                    Image img = new Image(getClass().getResourceAsStream(resourcePath));
                    ImageView iv = new ImageView(img);
                    iv.setFitWidth(20);
                    iv.setFitHeight(20);
                    iv.setPreserveRatio(true);
                    return iv;
                } catch (Exception e) {
                    System.err.println("Erreur chargement image: " + resourcePath);
                    return null;
                }
            }
        });
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        styleAlert(alert);
        alert.showAndWait();
    }

    private void styleAlert(Alert alert) {
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

        alert.getDialogPane().applyCss();
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double dialogWidth = 400;
        double dialogHeight = 200;

        alertStage.setX((screenBounds.getWidth() - dialogWidth) / 2);
        alertStage.setY((screenBounds.getHeight() - dialogHeight) / 2 + 100);
    }

    public void Trier(){
        LocalDate debut = Date1.getValue();
        LocalDate fin = Date2.getValue();

        if (debut == null || fin == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tri");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner les deux dates pour trier.");
            styleAlert(alert);
            alert.showAndWait();
            return;
        }

        var listeTriee = dao.trierColis(debut, fin);

        if (listeTriee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors du tri.");
            styleAlert(alert);
            alert.showAndWait();
        } else {
            // Même si vide, on met à jour la table (tableau vide)
            ObservableList<TableauRecevoir> data = FXCollections.observableArrayList(listeTriee);
            Table.setItems(data);
        }
    }

    public void Rechercher(){
        String input = SearchBarre.getText().trim();

        if (input.isEmpty()) {
            // Si rien, recharge toute la table
            chargerTable();
            return;
        }

        List<TableauRecevoir> resultat;
        try {
            int id = Integer.parseInt(input);
            resultat = dao.chercherColis(id, input);
        } catch (NumberFormatException e) {
            // Si pas un entier, recherche par colis (texte)
            resultat = dao.chercherColis(-1, input);
        }

        if (resultat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la recherche.");
            styleAlert(alert);
            alert.showAndWait();
        } else {
            ObservableList<TableauRecevoir> data = FXCollections.observableArrayList(resultat);
            Table.setItems(data);
        }
    }

    public void Actualiser(){
        chargerTable();
        Date1.setValue(null);
        Date2.setValue(null);
        SearchBarre.setText("");
    }
}
