module org.example.projetjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.example.projetjavafx to javafx.fxml;
    exports org.example.projetjavafx;
}