package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory;

public class AddFormController {

    @FXML
    private TextField AddVilleDepartInput;

    @FXML
    private TextField AddVilleArriveInput;

    @FXML
    private Spinner<Integer> AddFraisInput;

    @FXML
    private Label WarningAddItineraire;

    @FXML
    public void initialize() {
        AddFraisInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, Integer.MAX_VALUE, 1000));
    }

    public String getVilleDepart() {
        return AddVilleDepartInput.getText();
    }

    public String getVilleArrive() {
        return AddVilleArriveInput.getText();
    }

    public Integer getFrais() {
        return AddFraisInput.getValue();
    }

    public void reset(){
        AddVilleDepartInput.setText("");
        AddVilleArriveInput.setText("");
    }

    public void Warning (){
        WarningAddItineraire.setText("Veuillez remplir toutes les champs");
    }

    public void Warning1(){
        WarningAddItineraire.setText("Cette itinéraire existe déjà");
    }
    public  void resetWarning(){WarningAddItineraire.setText("");}

    public void Clean(){
        WarningAddItineraire.setText("");
        AddVilleDepartInput.setText("");
        AddVilleArriveInput.setText("");
    }

}
