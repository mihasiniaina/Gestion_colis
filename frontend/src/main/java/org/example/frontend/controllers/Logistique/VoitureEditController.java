package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.projetjavafx.Model.Voiture;

public class VoitureEditController {

    @FXML
    private TextField MatriculeInput;

    @FXML
    private TextField DesignationInput;

    @FXML
    private TextField IdItineraire;

    @FXML
    private Label WarningEditVoiture;

    @FXML
    public void initialize() {
        // Pas besoin de setup particulier pour des TextField
    }

    public String getMatricule() {
        return MatriculeInput.getText();
    }

    public String getDesignation() {
        return DesignationInput.getText();
    }

    public String getItineraire() {
        return IdItineraire.getText();
    }

    public void Warning() {
        WarningEditVoiture.setText("Veuillez remplir tous les champs");
    }

    public void WarningExist() {
        WarningEditVoiture.setText("Voiture déjà existante");
    }

    public void RemplirForm(Voiture voiture) {
        MatriculeInput.setText(voiture.getIdvoit());
        DesignationInput.setText(voiture.getDesign());
        IdItineraire.setText(voiture.getItineraire().getCodeit());
    }

    public void reset() {
        MatriculeInput.setText("");
        DesignationInput.setText("");
        IdItineraire.setText("");
    }
}