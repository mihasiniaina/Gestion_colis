package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Database.BD;
import org.example.projetjavafx.Model.Envoyer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnvoyerDAO {

    public String ajouterEnvoi(int idenvoi, String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                               LocalDateTime date_envoi, int frais, String nomRecepteur, String contactRecepteur){

        String result = "";
        String sql = "insert into envoyer values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, idenvoi);
            stmt.setString(2, idvoit);
            stmt.setString(3, colis);
            stmt.setString(4, nomEnvoyeur);
            stmt.setString(5, emailEnvoyeur);
            stmt.setTimestamp(6, Timestamp.valueOf(date_envoi));
            stmt.setInt(7, frais);
            stmt.setString(8, nomRecepteur);
            stmt.setString(9, contactRecepteur);

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

    public List<Envoyer> listerEnvoi(){
        List<Envoyer> liste = new ArrayList<>();

        try(Connection conn = BD.connexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from envoyer"))
        {
            while (rs.next()){

                int idenvoi = rs.getInt("idenvoi");
                String idvoit = rs.getString("idvoit");
                String colis = rs.getString("colis");
                String nomEnvoyeur = rs.getString("nomEnvoyeur");
                String emailEnvoyeur = rs.getString("emailEnvoyeur");
                LocalDateTime date_envoi = rs.getTimestamp("date_envoi").toLocalDateTime();
                int frais = rs.getInt("frais");
                String nomRecepteur = rs.getString("nomRecepteur");
                String contactRecepteur = rs.getString("contactRecepteur");


                Envoyer envoyer = new Envoyer( idenvoi, idvoit, colis, nomEnvoyeur,
                        emailEnvoyeur, date_envoi, frais, nomRecepteur, contactRecepteur);
                liste.add(envoyer);
            }
        }
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
        }

        return liste;
    }


    public String modifierEnvoi(int idenvoi, String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                                LocalDateTime date_envoi, int frais, String nomRecepteur, String contactRecepteur)
    {
        String result = "";
        String sql = "update envoyer set colis = ?, nomEnvoyeur = ?, emailEnvoyeur = ?, date_envoi = ?," +
                " frais = ?, nomRecepteur = ?, contactRecepteur = ? where idenvoi = ? and idvoit = ?";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, colis);
            stmt.setString(2, nomEnvoyeur);
            stmt.setString(3, emailEnvoyeur);
            stmt.setTimestamp(4, Timestamp.valueOf(date_envoi));
            stmt.setInt(5, frais);
            stmt.setString(6, nomRecepteur);
            stmt.setString(7, contactRecepteur);
            stmt.setInt(8, idenvoi);
            stmt.setString(9, idvoit);

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

    public String supprimerEnvoi(String idenvoi, String idvoit){

        String sql = "delete from voiture where idenvoi = ? and  idvoit = ? ";
        String result = "";

        try (Connection conn = BD.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, idenvoi);
            stmt.setString(2, idvoit);


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
