module org.example.projetjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.mail;
    requires openhtmltopdf.pdfbox;
    requires openhtmltopdf.core;

    opens org.example.projetjavafx to javafx.fxml;
    exports org.example.projetjavafx;
    exports org.example.projetjavafx.Model;
    exports org.example.projetjavafx.util;

    opens org.example.projetjavafx.Model to org.hibernate.orm.core;
    exports org.example.projetjavafx.DAO;
    exports org.example.projetjavafx.ImplementationDAO;
}