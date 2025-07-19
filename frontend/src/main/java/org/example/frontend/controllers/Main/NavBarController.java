package org.example.frontend.controllers.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NavBarController {

    private MainController mainController;

    @FXML
    private Button BtnLogistique;

    @FXML
    private Button BtnColis;

    @FXML
    private Button BtnRevenu;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onShowLogistique() {
        if (mainController != null) {
            mainController.ShowLogistique();
        }
    }

    @FXML
    private void onShowColis() {
        if (mainController != null) {
            mainController.ShowColis();
        }
    }

    @FXML
    private void onShowRevenu() {
        if (mainController != null) {
            mainController.ShowRevenu();
        }
    }

    // getters si besoin

    public Button getBtnLogistique() {
        return BtnLogistique;
    }

    public Button getBtnColis() {
        return BtnColis;
    }

    public Button getBtnRevenu() {
        return BtnRevenu;
    }
}
