package org.example.frontend.controllers.Colis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import  org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.ImplementationDAO.EnvoyerImpl;
import  org.example.projetjavafx.util.HibernateUtil;

import java.io.IOException;

public class EnvoyerDeleteController {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());
    private EnvoyerController mainController;

    @FXML
    private Label WarningLabel;
    @FXML
    private TextField IdEnvoie;

    public  void Annuler(ActionEvent event){
        Node source = (Node) event.getSource(); // le bouton cliqué
        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
        if (parent != null) {
            parent.setVisible(false);
        }
    }
    public void Delete(ActionEvent event) {
        String idText = IdEnvoie.getText();

        if (idText != null && !idText.isEmpty()) {
            try {
                int id = Integer.parseInt(idText);
                dao.supprimerEnvoi(id);
                if (mainController != null) {
                    mainController.chargerTableView();
                }
            } catch (NumberFormatException e) {
                WarningLabel.setText("IdEnvoi invalide");
            } catch (Exception e) {
                e.printStackTrace(); // ou afficher une alerte dans l'interface
            }
        } else {
            System.out.println("Veuillez entrer un ID d'envoi à supprimer.");
        }
    }
    public void setMainController(EnvoyerController controller) {
        this.mainController = controller;
    }

}
