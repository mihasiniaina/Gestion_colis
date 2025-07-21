package org.example.frontend.controllers.Colis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.ImplementationDAO.EnvoyerImpl;
import org.example.projetjavafx.util.HibernateUtil;

import java.time.LocalDateTime;

public class EnvoyerEdditContorller {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());

    @FXML
    private TextField idenvoie;
    @FXML
    private TextField Voiture ;
    @FXML
    private TextField Envoyeur;
    @FXML
    private TextField Email;
    @FXML
    private TextField Recepteur;
    @FXML
    private TextField Contact;
    @FXML
    private TextField Colis;
    @FXML
    private Button AnnulerBtn;
    @FXML
    private Button ValidBtn;
    @FXML
    private Label WarningLabel;

    private EnvoyerController mainController;

    private StackPane formView;

    public String getidenvoie() { return idenvoie.getText(); }
    public String getColis() { return Colis.getText(); }
    public String getEnvoyer() { return Envoyeur.getText(); }
    public String getEmail() { return Email.getText(); }
    public String getVo() { return Voiture.getText(); }
    public String getRecep() { return Recepteur.getText(); }
    public String getContact() { return Contact.getText(); }

    public void setFormView(StackPane formView) {
        this.formView = formView;
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

    public void quit(ActionEvent event) {
        reset();
        Node source = (Node) event.getSource(); // le bouton cliqué
        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
        if (parent != null) {
            parent.setVisible(false);
        }
    }

    public void reset() {
        Envoyeur.setText("");
        Voiture.setText("");
        Recepteur.setText("");
        Email.setText("");
        Contact.setText("");
        Colis.setText("");
        WarningLabel.setText("");
    }

    public void Modifier(ActionEvent event) {
        int id;
        try {
            id = Integer.parseInt(getidenvoie());
        } catch (NumberFormatException e) {
            WarningLabel.setText("Identifiant invalide");
            return;
        }

        String voiture = getVo();
        String env = getEnvoyer();
        String email = getEmail();
        String recepteur = getRecep();
        String contact = getContact();
        String colis = getColis();
        LocalDateTime date = LocalDateTime.now();

        if (voiture.isEmpty() || env.isEmpty() || email.isEmpty() || recepteur.isEmpty() || contact.isEmpty() || colis.isEmpty()) {
            WarningLabel.setText("Veuillez remplir toutes les champs");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Modifier l'envoi ?");
            alert.setContentText("Voulez-vous enregistrer les modifications ?");

            styleAlert(alert);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Boolean responseModif = dao.modifierEnvoi(id, colis, env, email, date, recepteur, contact);
                    if (responseModif) {
                        if (mainController != null) {
                            mainController.chargerTableView();
                        }
                        Node source = (Node) event.getSource(); // le bouton cliqué
                        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
                        if (parent != null) {
                            parent.setVisible(false);
                        }
                        reset();
                    } else {
                        WarningLabel.setText("Erreur lors de la modification");
                    }
                }
            });
        }
    }

    public void setMainController(EnvoyerController mainController) {
        this.mainController = mainController;
    }
}
