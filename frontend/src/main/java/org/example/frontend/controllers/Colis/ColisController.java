package org.example.frontend.controllers.Colis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.EOFException;
import java.io.IOException;

public class ColisController {

    @FXML
    private BorderPane AffichageColis;

    @FXML
    private HBox toolbarContainer;

    @FXML
    public void initialize() {
        try {
            // Charger dynamiquement la toolbar
            FXMLLoader toolbarLoader = new FXMLLoader(getClass().getResource("/org/example/frontend/Composant/Colis/ColisNavBar.fxml"));
            Node toolbar = toolbarLoader.load();

            // Injecter le contrôleur principal dans la toolbar
            ColisNavbarController NavbarController = toolbarLoader.getController();
            NavbarController.setLogistiqueController(this);

            toolbarContainer.getChildren().add(toolbar);
            HBox.setMargin(toolbar, new Insets(0, 0, 70, 0));

            ShowEnvoyer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowEnvoyer() {
        LoadBody("/org/example/frontend/Composant/Colis/Envoyer.fxml");
    }

    public void ShowRecevoir() {
        LoadBody("/org/example/frontend/Composant/Colis/Recevoir.fxml");
    }

    private void LoadBody(String fxmlpath) {
        try {
            // Supprimer le contenu actuel
            AffichageColis.setCenter(null);

            // Charger la nouvelle vue
            FXMLLoader centerLoader = new FXMLLoader(getClass().getResource(fxmlpath));
            Node centerContent = centerLoader.load();

            // Configurer la vue centrale
            BorderPane.setMargin(centerContent, new Insets(0));
            AffichageColis.setCenter(centerContent);

        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
