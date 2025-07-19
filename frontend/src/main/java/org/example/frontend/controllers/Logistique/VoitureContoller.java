package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class VoitureContoller {

    @FXML
    public VBox FormVoiture;

    @FXML
    public void initialize() {
        ShowAdd(); // Appelle directement la méthode pour afficher le formulaire "Add"
    }

    @FXML
    public void ShowAdd() {
        loadForm("/org/example/frontend/Composant/Logistique/AddFormVo.fxml");
    }

    @FXML
    public void ShowEdit() {
        loadForm("/org/example/frontend/Composant/Logistique/EditFormVo.fxml");
    }

    private void loadForm(String fxmlPath) {
        try {
            // Nettoie tous les enfants (au lieu de .remove(0), plus sûr)
            FormVoiture.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node formView = loader.load();

            FormVoiture.getChildren().add(formView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
