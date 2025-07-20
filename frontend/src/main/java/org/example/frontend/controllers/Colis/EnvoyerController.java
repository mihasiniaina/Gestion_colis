package org.example.frontend.controllers.Colis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class EnvoyerController {
    @FXML
    public StackPane FormView;

    @FXML
    public void initialize(){
        FormView.setVisible(false);
    }

    @FXML
    public void ShowAd(ActionEvent event){
        FormView.setVisible(true);
        LoadForm("/org/example/frontend/Composant/Colis/AddEnv.fxml");
    }

    @FXML
    public void ShowEdit(ActionEvent event){
        FormView.setVisible(true);
        LoadForm("/org/example/frontend/Composant/Colis/EditEnv.fxml");
    }

    @FXML
    public void ShowDelete(ActionEvent event){
        FormView.setVisible(true);
        LoadForm("/org/example/frontend/Composant/Colis/DeleteEnv.fxml");
    }

    private void LoadForm(String fxmlPath) {
        try {
            FormView.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node formView = loader.load();

            FormView.getChildren().add(formView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
