package org.example.frontend.controllers.Logistique;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VoAddFormController {

    @FXML
    private TextField MatriculeInput;
    @FXML
    private TextField DesignInput;
    @FXML
    private TextField IdItineraire;
    @FXML
    private Button AnnulerBtn;
    @FXML
    private Button AddBtn;
    @FXML
    private Label WarningAddVoiture;

    public String getMatricule(){
        return  MatriculeInput.getText();
    }
    public String getDesign(){
        return DesignInput.getText();
    }
    public String getItineraire(){
        return IdItineraire.getText();
    }
    public void reset(){
        MatriculeInput.setText("");
        DesignInput.setText("");
        IdItineraire.setText("");
    }
    public void  Warning(){
        WarningAddVoiture.setText("Veuillez remplir toutes les champs");
    }
    public void Warning1(){WarningAddVoiture.setText("Erreur lors de l'ajout. Veuillez réessayer");}
    public void resetAddWarning(){WarningAddVoiture.setText("");}

}
