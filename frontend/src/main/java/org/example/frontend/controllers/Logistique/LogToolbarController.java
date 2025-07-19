package org.example.frontend.controllers.Logistique;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LogToolbarController {

    private LogistiqueController logistiqueController;

    @FXML
    private Rectangle activeIndicator;

    public void setLogistiqueController(LogistiqueController logistiqueController1){
        this.logistiqueController = logistiqueController1;
    }

    @FXML
    private void onShowItineraire () {
        animateIndicatorTo(-90.0);
        if(logistiqueController != null){
                logistiqueController.ShowItineraire();
        }
    }

    @FXML
    private void onShowVoiture() {
        animateIndicatorTo(83.0);
        if(logistiqueController !=null ){
            logistiqueController.ShowVoiture();
        }
    }

    private void animateIndicatorTo(double targetX) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.millis(300)); // Animation de 300ms
        transition.setNode(activeIndicator);
        transition.setToX(targetX);
        transition.play();
    }
}