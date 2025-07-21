package org.example.frontend.controllers.Colis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.ImplementationDAO.EnvoyerImpl;
import org.example.projetjavafx.util.HibernateUtil;

public class EnvoyerDeleteController {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());
    private EnvoyerController mainController;

    @FXML
    private Label WarningLabel;
    @FXML
    private TextField IdEnvoie;

    public void Annuler(ActionEvent event) {
        Node source = (Node) event.getSource();
        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
        if (parent != null) {
            parent.setVisible(false);
        }
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

    public void Delete(ActionEvent event) {
        String idText = IdEnvoie.getText();

        if (idText != null && !idText.isEmpty()) {
            try {
                int id = Integer.parseInt(idText);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Suppression");
                alert.setContentText("Voulez-vous vraiment supprimer cet envoi ?");

                styleAlert(alert);

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        dao.supprimerEnvoi(id);
                        if (mainController != null) {
                            mainController.chargerTableView();
                        }
                        WarningLabel.setText(""); // reset message
                        // Optionnel : cacher la vue formulaire après suppression
                        Node source = (Node) event.getSource();
                        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
                        if (parent != null) {
                            parent.setVisible(false);
                        }
                    }
                });

            } catch (NumberFormatException e) {
                WarningLabel.setText("IdEnvoie invalide");
            } catch (Exception e) {
                e.printStackTrace();
                WarningLabel.setText("Erreur lors de la suppression");
            }
        } else {
            WarningLabel.setText("Veuillez entrer un ID d'envoi à supprimer.");
        }
    }

    public void setMainController(EnvoyerController controller) {
        this.mainController = controller;
    }
}
