package org.example.frontend.controllers.Revenu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import org.example.projetjavafx.DAO.RevenuDAO;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RevenuComponentController implements Initializable {

    @FXML private Label labelMensuel;
    @FXML private Label labelAnnuel;
    @FXML private Label labelTotal;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    private final RevenuDAO revenuDAO = new RevenuDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Afficher les revenus
        labelMensuel.setText("Ar " + revenuDAO.getRevenuMensuel());
        labelAnnuel.setText("Ar " + revenuDAO.getRevenuAnnuel());
        labelTotal.setText("Ar " + revenuDAO.getRevenuTotal());

        // Graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenus");

        Map<String, Integer> revenus = revenuDAO.getRevenus6DerniersMois();
        revenus.forEach((mois, montant) -> {
            series.getData().add(new XYChart.Data<>(mois, montant));
        });

        barChart.getData().add(series);
    }
}
