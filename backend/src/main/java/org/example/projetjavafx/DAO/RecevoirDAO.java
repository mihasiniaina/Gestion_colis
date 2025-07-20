package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Recevoir;

import java.time.LocalDateTime;
import java.util.List;

public interface RecevoirDAO {
    void envoyerMail(int idenvoi);
    String ajouterRecu(int idenvoi, LocalDateTime date_recept);
    List<Recevoir> listerRecu();
    String modifierRecu(int idrecept, LocalDateTime date_recept);
    String supprimerRecu(int idrecept);

}
