package org.example.frontend.controllers.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane Mainview;

    @FXML
    private BorderPane ChangeView;

    private NavBarController navBarController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/frontend/Composant/NavBar-vue.fxml"));
            Pane navBar = loader.load();
            navBarController = loader.getController();

            navBarController.setMainController(this);

            Mainview.setLeft(navBar);

            // Charger la vue Logistique par défaut
            ShowLogistique();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowLogistique() {
        loadView("/org/example/frontend/Vue/Logistique-vue.fxml");
        updateButtonStyles(navBarController.getBtnLogistique());
    }

    public void ShowColis() {
        loadView("/org/example/frontend/Vue/Colis-vue.fxml");
        updateButtonStyles(navBarController.getBtnColis());
    }

    public void ShowRevenu() {
        loadView("/org/example/frontend/Vue/Revenu-vue.fxml");
        updateButtonStyles(navBarController.getBtnRevenu());
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane view = loader.load();
            ChangeView.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la vue : " + fxmlPath);
        }
    }

    private void updateButtonStyles(Button activeButton) {
        navBarController.getBtnLogistique().setStyle("-fx-background-color: transparent; -fx-border-radius: 120;");
        navBarController.getBtnColis().setStyle("-fx-background-color: transparent; -fx-border-radius: 120;");
        navBarController.getBtnRevenu().setStyle("-fx-background-color: transparent; -fx-border-radius: 120;");

        activeButton.setStyle("-fx-background-color: #4A5B66; -fx-border-radius: 120;");
    }
}
