package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Database.BD;
import org.example.projetjavafx.Model.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureDAO {
    
    public String ajouterVoiture(String idvoit, String design, String codeit){

        String result = "";
        String sql = "insert into voiture values (?, ?, ?)";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, idvoit);
            stmt.setString(2, design);
            stmt.setString(3, codeit);

            stmt.executeUpdate();

            stmt.close();

            result = "Ajout réussi";

        }
        catch (SQLException e){
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
            result = "Echec de l'ajout";
        }
        return result;

    }

    public List<Voiture> listerVoiture(){
        List<Voiture> liste = new ArrayList<>();

        try(Connection conn = BD.connexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from voiture"))
        {
            while (rs.next()){
                String idvoit = rs.getString("idvoit");
                String design = rs.getString("design");
                String codeit = rs.getString("codeit");

                Voiture voiture = new Voiture(idvoit, design, codeit);
                liste.add(voiture);
            }
        }
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
        }

        return liste;
    }

    public String modifierVoiture(String idvoit, String design, String codeit){

        String result = "";
        String sql = "update voiture set design = ? where idvoit = ? and codeit = ?";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, design);
            stmt.setString(2, idvoit);
            stmt.setString(3, codeit);

            stmt.executeUpdate();

            stmt.close();

            result = "Modification réussie";

        }
        catch (SQLException e){
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
            result = "Echec de la modification";
        }

        return result;

    }

    public String supprimerVoiture(String idvoit, String codeit){

        String sql = "delete from voiture where idvoit = ? and  codeit = ? ";
        String result = "";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, idvoit);
            stmt.setString(2, codeit);


            stmt.executeUpdate();

            stmt.close();

            result = "Suppression réussie";

        } catch (SQLException e){
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
            result = "Echec de la suppression";
        }

        return result;
    }
}
