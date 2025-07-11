package org.example.projetjavafx.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD {
    private static final String url = "jdbc:postgresql://localhost:5432/colis";
    private static final String user = "postgres";
    private static final String password = "Coldestshoot";

    public static Connection connexion() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.err.println("Connexion réussi");
            return conn;
        } catch (SQLException e) {
            System.err.println("Erreur JDBC : " + e.getMessage());
            return null;
        }
    }
}

