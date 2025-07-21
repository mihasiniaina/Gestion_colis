    package org.example.frontend.controllers.Colis;

    import javafx.collections.FXCollections;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.StackPane;
    import java.io.IOException;
    import java.util.List;

    import  org.example.projetjavafx.DAO.EnvoyerDAO;
    import org.example.projetjavafx.ImplementationDAO.EnvoyerImpl;
    import org.example.projetjavafx.Model.Itineraire;
    import  org.example.projetjavafx.util.HibernateUtil;
    import  org.example.projetjavafx.Model.Envoyer;

    public class EnvoyerController {

        private EnvoyerDAO dao = new EnvoyerImpl(HibernateUtil.getSessionFactory());

        @FXML
        private TableView<Envoyer> Table;
        @FXML
        private TableColumn<Envoyer,Integer> id;
        @FXML
        private TableColumn<Envoyer,String>envoyeur;
        @FXML
        private TableColumn<Envoyer,String> email;
        @FXML
        private TableColumn<Envoyer,String> colis;
        @FXML
        private TableColumn<Envoyer,String> itineraire;
        @FXML
        private TableColumn<Envoyer,String> voiture;
        @FXML
        private TableColumn<Envoyer,String> date;
        @FXML
        private TableColumn<Envoyer,String> recepteur;
        @FXML
        private TableColumn<Envoyer,String> contact;

        @FXML
        public StackPane FormView;

        @FXML
        public void initialize(){
            id.setCellValueFactory(new PropertyValueFactory<>(""));
            envoyeur.setCellValueFactory(new PropertyValueFactory<>(""));
            email.setCellValueFactory(new PropertyValueFactory<>(""));
            colis.setCellValueFactory(new PropertyValueFactory<>(""));
            recepteur.setCellValueFactory(new PropertyValueFactory<>(""));
            contact.setCellValueFactory(new PropertyValueFactory<>(""));
            date.setCellValueFactory(new PropertyValueFactory<>(""));
            voiture.setCellValueFactory(new PropertyValueFactory<>(""));
            itineraire.setCellValueFactory(new PropertyValueFactory<>(""));

            FormView.setVisible(false);
            chargerTableView();
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

        //logique métier

        public void chargerTableView() {
            List<Envoyer> list = dao.listerEnvoi();
            if (list != null) {
                Table.setItems(FXCollections.observableArrayList(list));
            }
        }

    }
