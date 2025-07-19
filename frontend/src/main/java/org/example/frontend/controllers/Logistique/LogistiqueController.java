package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LogistiqueController {

    @FXML
    private BorderPane AffichageLogistique;

    @FXML
    private HBox toolbarContainer;

    @FXML
    public void initialize() {
        try {
            // Charger dynamiquement la toolbar
            FXMLLoader toolbarLoader = new FXMLLoader(getClass().getResource("/org/example/frontend/Composant/Logistique/LogistiqueNavBar.fxml"));
            Node toolbar = toolbarLoader.load();
            toolbarContainer.getChildren().add(toolbar);
            HBox.setMargin(toolbar, new Insets(0, 0, 70, 0));

            // Charger la vue itinéraire
            FXMLLoader centerLoader = new FXMLLoader(getClass().getResource("/org/example/frontend/Composant/Logistique/Itineraire.fxml"));
            Node centerContent = centerLoader.load();

            // Assurer que le contenu central prend tout l’espace disponible
            BorderPane.setMargin(centerContent, new Insets(0));
            AffichageLogistique.setCenter(centerContent);

            // Étirer le node au maximum
            centerContent.maxWidth(Double.MAX_VALUE);
            centerContent.maxHeight(Double.MAX_VALUE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowItineraire() {
        // à implémenter si besoin
    }

    public void ShowVoiture() {
        // à implémenter si besoin
    }
}
