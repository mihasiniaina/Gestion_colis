package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

import java.io.IOException;

public class LogistiqueController {

    @FXML
    private BorderPane AffichageLogistique;

    @FXML
    private HBox toolbarContainer;

    @FXML
    public void initialize() {
        try {
            // Charger dynamiquement la toolbar au démarrage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/frontend/Vue/LogToolbar.fxml"));
            Node toolbar = loader.load();
            toolbarContainer.getChildren().add(toolbar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthodes appelées par la toolbar (ces fonctions doivent être appelées depuis LogToolbarController)
    public void ShowItineraire() {
        try {
            BorderPane page = FXMLLoader.load(getClass().getResource("/org/example/frontend/Vue/Itineraire.fxml"));
            AffichageLogistique.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowVoiture() {
        try {
            BorderPane page = FXMLLoader.load(getClass().getResource("/org/example/frontend/Vue/Voiture.fxml"));
            AffichageLogistique.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
