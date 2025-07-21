package org.example.frontend.controllers.Colis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import  org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.ImplementationDAO.EnvoyerImpl;
import  org.example.projetjavafx.util.HibernateUtil;


import java.time.LocalDateTime;

public class EnvoyeAddControler {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());

    @FXML
    private TextField Voiture ;
    @FXML
    private TextField NomEnv;
    @FXML
    private TextField Email;
    @FXML
    private TextField NomRecep;
    @FXML
    private TextField Contact;
    @FXML TextField Colis;
    @FXML
    private Button AnnulerBtn;
    @FXML
    private  Button ValidBtn;
    @FXML
    private Label WarningLabel;

    private EnvoyerController mainController;

    public String getColis(){
        return Colis.getText();
    }
    public String getEnvoyer(){
        return NomEnv.getText();
    }
    public String getEmail(){
        return Email.getText();
    }
    public String getVo(){
        return Voiture.getText();
    }
    public String getRecep(){
        return NomRecep.getText();
    }
    public String getContact(){
        return Contact.getText();
    }

    public void AddEnvoie(ActionEvent event){
        String voiture = getVo();
        String Env = getEnvoyer();
        String Email = getEmail();
        String Recep = getRecep();
        String Contact = getContact();
        String Colis = getColis();
        LocalDateTime date = LocalDateTime.now();

        if(voiture.isEmpty() || Env.isEmpty() || Email.isEmpty() || Recep.isEmpty() || Contact.isEmpty() || Colis.isEmpty()){
            WarningLabel.setText("veuillez remplir toutes les champs");
        }
        else{
           int response =  dao.ajouterEnvoi(voiture,Colis,Env,Email,date,Recep,Contact);
           if(response != 0){
                reset();
                dao.genererPDF(response);
           }
           else{

           }
        }
    }
    public void Annuler(ActionEvent event){
        reset();
        Node source = (Node) event.getSource(); // le bouton cliqué
        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
        if (parent != null) {
            parent.setVisible(false);
        }
    }
    public void  reset(){
        NomEnv.setText("");
        Voiture.setText("");
        NomRecep.setText("");
        Email.setText("");
        Contact.setText("");
        Colis.setText("");
    }
    public void setMainController(EnvoyerController mainController) {
        this.mainController = mainController;
    }


}
