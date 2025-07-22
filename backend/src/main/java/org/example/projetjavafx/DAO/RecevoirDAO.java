package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Recevoir;
import org.example.projetjavafx.util.TableauRecevoir;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RecevoirDAO {
    void envoyerMail(int idenvoi, LocalDateTime date_recept);
    String ajouterRecu(int idenvoi, LocalDateTime date_recept);
    List<TableauRecevoir> listerRecu();
    String modifierRecu(int idrecept, LocalDateTime date_recept);
    String supprimerRecu(int idrecept);
    List<TableauRecevoir> trierColis(LocalDate debut, LocalDate fin);
    List<TableauRecevoir> chercherColis (int idenvoi, String colis);
    Integer getIdRecevoirByIdEnvoi(int idenvoi);

}
