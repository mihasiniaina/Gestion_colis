package org.example.frontend.controllers.Colis;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ColisNavbarController {

    private ColisController colisController;

    @FXML
    private Rectangle activeIndicator;

    public void setLogistiqueController(ColisController colisController1){
        this.colisController = colisController1;
    }

    @FXML
    private void onShowEnvoyer () {
        animateIndicatorTo(-86.0);
        colisController.ShowEnvoyer();

    }

    @FXML
    private void onShowRecevoir() {
        animateIndicatorTo(81.0);
        colisController.ShowRecevoir();

    }

    private void animateIndicatorTo(double targetX) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.millis(300)); // Animation de 300ms
        transition.setNode(activeIndicator);
        transition.setToX(targetX);
        transition.play();
    }
}