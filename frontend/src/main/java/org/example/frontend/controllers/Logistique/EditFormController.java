package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory;
import org.example.projetjavafx.Model.Itineraire;

public class EditFormController {

    @FXML
    private TextField IdItineraireInput;
    @FXML
    private TextField VilleDepInput;

    @FXML
    private TextField VilleArInput;

    @FXML
    private Spinner<Integer> FraisInput;

    @FXML
    private Label WarningEditItineraire;

    @FXML
    public void initialize() {
        FraisInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1000));
    }

    public String getIdIT(){
        return IdItineraireInput.getText();
    }

    public String getVilleDepart() {
        return VilleDepInput.getText();
    }

    public String getVilleArrive() {
        return VilleArInput.getText();
    }

    public Integer getFrais() {
        return FraisInput.getValue();
    }

    public void Warning (){
        WarningEditItineraire.setText("Veuillez remplir tous les champs");
    }

    public void Warning1(){
        WarningEditItineraire.setText("Itinéraire déjà existante");
    }


    public void RemplirForm(Itineraire itineraire) {
        IdItineraireInput.setText(itineraire.getCodeit());
        VilleDepInput.setText(itineraire.getVilledep());
        VilleArInput.setText(itineraire.getVillearr());
        FraisInput.getValueFactory().setValue(itineraire.getFrais());
    }


}
