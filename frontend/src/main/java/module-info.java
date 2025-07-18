module org.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.example.projetjavafx;

    opens org.example.frontend to javafx.fxml;
    exports org.example.frontend;

    // Ne PAS exporter ni ouvrir ce package vide
    // exports org.example.frontend.controllers;  // <-- supprimer
    // opens org.example.frontend.controllers to javafx.fxml;  // <-- supprimer

    exports org.example.frontend.controllers.Colis;
    opens org.example.frontend.controllers.Colis to javafx.fxml;

    exports org.example.frontend.controllers.Logistique;
    opens org.example.frontend.controllers.Logistique to javafx.fxml;

    exports org.example.frontend.controllers.Main;
    opens org.example.frontend.controllers.Main to javafx.fxml;

    exports org.example.frontend.controllers.Revenu;
    opens org.example.frontend.controllers.Revenu to javafx.fxml;
}
