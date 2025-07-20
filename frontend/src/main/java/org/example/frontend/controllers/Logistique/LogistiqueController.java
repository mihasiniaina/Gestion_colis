package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.EOFException;
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

            // Injecter le contrôleur principal dans la toolbar
            LogNavbarController toolbarController = toolbarLoader.getController();
            toolbarController.setLogistiqueController(this);

            toolbarContainer.getChildren().add(toolbar);
            HBox.setMargin(toolbar, new Insets(0, 0, 70, 0));

            // Charger la vue itinéraire par défaut
            ShowItineraire();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowItineraire() {
        LoadBody("/org/example/frontend/Composant/Logistique/Itineraire.fxml");
    }

    public void ShowVoiture() {
        LoadBody("/org/example/frontend/Composant/Logistique/Voiture.fxml");
    }

    public void LoadBody(String fxmlpath) {
        try {
            // Supprimer le contenu actuel
            AffichageLogistique.setCenter(null);

            // Charger la nouvelle vue
            FXMLLoader centerLoader = new FXMLLoader(getClass().getResource(fxmlpath));
            Node centerContent = centerLoader.load();

            // Configurer la vue centrale
            BorderPane.setMargin(centerContent, new Insets(0));
            AffichageLogistique.setCenter(centerContent);

        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
