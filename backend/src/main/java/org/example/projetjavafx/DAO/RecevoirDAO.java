package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Database.BD;
import org.example.projetjavafx.Model.Recevoir;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecevoirDAO {

    public String ajouterReception(int idrecept, int idenvoi, LocalDateTime date_recept){

        String result = "";
        String sql = "insert into envoyer values (?, ?, ?)";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, idrecept);
            stmt.setInt(2, idenvoi);
            stmt.setTimestamp(3, Timestamp.valueOf(date_recept));


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

    public List<Recevoir> listerRecevoir(){

        List<Recevoir> liste = new ArrayList<>();

        try(Connection conn = BD.connexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from recevoir"))
        {
            while (rs.next()){
                int idrecept = rs.getInt("idrecept");
                int idenvoi = rs.getInt("idenvoi");
                LocalDateTime date_recept = rs.getTimestamp("date_recept").toLocalDateTime();

                Recevoir rec = new Recevoir(idrecept, idenvoi, date_recept);
                liste.add(rec);
            }
        }
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
        }

        return liste;

    }

    public String modifierReception(int idrecept, int idenvoi, LocalDateTime date_recept)
    {
        String result = "";
        String sql = "update envoyer set date_recept = ? where idrecept = ? and idenvoi = ?";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setTimestamp(1, Timestamp.valueOf(date_recept));
            stmt.setInt(2, idrecept);
            stmt.setInt(3, idenvoi);

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

    public String supprimerReception(String idrecept, String idenvoi){

        String sql = "delete from voiture where idrecept = ? and  idenvoi = ? ";
        String result = "";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, idrecept);
            stmt.setString(2, idenvoi);


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
