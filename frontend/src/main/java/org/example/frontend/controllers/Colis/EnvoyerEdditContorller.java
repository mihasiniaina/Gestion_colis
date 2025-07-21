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

public class EnvoyerEdditContorller {

    private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());

    @FXML
    private TextField idenvoie;
    @FXML
    private TextField Voiture ;
    @FXML
    private TextField Envoyeur;
    @FXML
    private TextField Email;
    @FXML
    private TextField Recepteur;
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

    public String getidenvoie(){
        return idenvoie.getText();
    }
    public String getColis(){
        return Colis.getText();
    }
    public String getEnvoyer(){
        return Envoyeur.getText();
    }
    public String getEmail(){
        return Email.getText();
    }
    public String getVo(){
        return Voiture.getText();
    }
    public String getRecep(){
        return Recepteur.getText();
    }
    public String getContact(){
        return Contact.getText();
    }
    private StackPane formView;

    public void setFormView(StackPane formView) {
        this.formView = formView;
    }


    public void quit(ActionEvent event){
        reset();
        Node source = (Node) event.getSource(); // le bouton cliqué
        StackPane parent = (StackPane) source.getScene().lookup("#FormView");
        if (parent != null) {
            parent.setVisible(false);
        }
    }
    public void  reset(){
        Envoyeur.setText("");
        Voiture.setText("");
        Recepteur.setText("");
        Email.setText("");
        Contact.setText("");
        Colis.setText("");
    }

    public void Modifier(ActionEvent event){

        int id = Integer.parseInt(getidenvoie());
        String votiure = getVo();
        String Envoyeur = getEnvoyer();
        String Email = getEmail();
        String Recepteur = getRecep();
        String Contact = getContact();
        String Colis = getColis();
        LocalDateTime date = LocalDateTime.now();

        if(  votiure.isEmpty() || Envoyeur.isEmpty() || Email.isEmpty()|| Recepteur.isEmpty() || Contact.isEmpty()){
            WarningLabel.setText("veuillze remplir toutes les champs");
        }
        else{
            Boolean response = dao.modifierEnvoi(id,Colis,Envoyeur,Email,date,Recepteur,Contact);
            if(response){
                reset();
            }else{

            }
        }
    }

    public void setMainController(EnvoyerController mainController) {
        this.mainController = mainController;
    }

}
