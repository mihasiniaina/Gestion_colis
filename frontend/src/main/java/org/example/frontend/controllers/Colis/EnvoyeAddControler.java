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

public class EnvoyeAddControler {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());

    @FXML
    private TextField Voiture;
    @FXML
    private TextField NomEnv;
    @FXML
    private TextField Email;
    @FXML
    private TextField NomRecep;
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

    public String getColis() { return Colis.getText(); }
    public String getEnvoyer() { return NomEnv.getText(); }
    public String getEmail() { return Email.getText(); }
    public String getVo() { return Voiture.getText(); }
    public String getRecep() { return NomRecep.getText(); }
    public String getContact() { return Contact.getText(); }

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

    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public void AddEnvoie(ActionEvent event) {
        String voiture = getVo();
        String Env = getEnvoyer();
        String emailTxt = getEmail();
        if (!isValidEmail(emailTxt)) {
            Email.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            System.out.println("emailTxt invalide !");
            return;
        }
        Email.setStyle("");

        String Recep = getRecep();
        String Contact = getContact();
        String Colis = getColis();
        LocalDateTime date = LocalDateTime.now();

        if (voiture.isEmpty() || Env.isEmpty() || emailTxt.isEmpty() || Recep.isEmpty() || Contact.isEmpty() || Colis.isEmpty()) {
            WarningLabel.setText("Veuillez remplir toutes les champs");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Ajouter un envoi ?");
            alert.setContentText("Voulez-vous vraiment ajouter cet envoi ?");

            styleAlert(alert);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    int responseId = dao.ajouterEnvoi(voiture, Colis, Env, emailTxt, date, Recep, Contact);
                    if (responseId != 0) {
                        reset();
                        dao.genererPDF(responseId);
                        if (mainController != null) {
                            mainController.chargerTableView();
                        }
                        Node source = (Node) event.getSource();
                        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
                        if (parent != null) {
                            parent.setVisible(false);
                        }
                    } else {
                        WarningLabel.setText("Erreur lors de l'ajout");
                    }
                }
            });
        }
    }

    public void Annuler(ActionEvent event) {
        reset();
        Node source = (Node) event.getSource();
        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
        if (parent != null) {
            parent.setVisible(false);
        }
    }



    public void reset() {
        NomEnv.setText("");
        Voiture.setText("");
        NomRecep.setText("");
        Email.setText("");
        Contact.setText("");
        Colis.setText("");
        WarningLabel.setText("");
    }

    public void setMainController(EnvoyerController mainController) {
        this.mainController = mainController;
    }
}
