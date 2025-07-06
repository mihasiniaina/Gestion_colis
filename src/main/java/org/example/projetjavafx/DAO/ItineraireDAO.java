package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Database.BD;
import org.example.projetjavafx.Model.Itineraire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItineraireDAO {

    public String ajouterItineraire(String codeit, String villedep, String villearr, int frais ){

        String result = "";
        String sql = "insert into itineraire values (?, ?, ?, ?)";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, codeit);
            stmt.setString(2, villedep);
            stmt.setString(3, villearr);
            stmt.setInt(4, frais);

            stmt.executeUpdate();

            stmt.close();

            result = "Ajout réussi";

        } catch (SQLException e){
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
            result = "Echec de l'ajout";
        }
        return result;
    }


    public List<Itineraire> listerItineraire(){

        List<Itineraire> liste = new ArrayList<>();

        try(Connection conn = BD.connexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from itineraire"))
        {
            while (rs.next()){
                String codeit = rs.getString("codeit");
                String villedep = rs.getString("villedep");
                String villearr = rs.getString("villearr");
                int frais = rs.getInt("frais");

                Itineraire it = new Itineraire(codeit, villedep, villearr, frais);
                liste.add(it);
            }
        }
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
        }

        return liste;

    }

    public String modifierItineraire(String codeit, String villedep, String villearr, int frais){

        String sql = "update itineraire set villedep = ? , villearr = ? , frais = ? where codeit = ? ";
        String result = "";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, villedep);
            stmt.setString(2, villearr);
            stmt.setInt(3, frais);
            stmt.setString(4, codeit);

            stmt.executeUpdate();

            stmt.close();

            result = "Modification réussie";

        } catch (SQLException e){
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
            result = "Echec de la modification";
        }
        return result;
    }

    public String supprimerItineraire(String codeit){

        String sql = "delete from itineraire where codeit = ? ";
        String result = "";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, codeit);

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
