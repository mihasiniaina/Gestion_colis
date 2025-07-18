package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LogToolbarController {

    @FXML
    private Button ItinéraireBtn;

    @FXML
    private Button VoitureBtn;

    @FXML
    private void onShowItineraire() {
        LogistiqueController parentController = getParentController();
        if (parentController != null) {
            parentController.ShowItineraire();
        }
    }

    @FXML
    private void onShowVoiture() {
        LogistiqueController parentController = getParentController();
        if (parentController != null) {
            parentController.ShowVoiture();
        }
    }

    private LogistiqueController getParentController() {
        Parent current = ItinéraireBtn.getScene().getRoot();
        while (current != null) {
            Object userData = current.getUserData();
            if (userData instanceof LogistiqueController) {
                return (LogistiqueController) userData;
            }
            if (current.getParent() == null) break;
            current = current.getParent();
        }
        return null;
    }
}
