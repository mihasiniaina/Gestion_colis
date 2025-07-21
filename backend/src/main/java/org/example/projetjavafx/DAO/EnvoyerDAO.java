package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Envoyer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EnvoyerDAO {
    int ajouterEnvoi(String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                        LocalDateTime date_envoi, String nomRecepteur, String contactRecepteur);
    List<Envoyer> listerEnvoi();
    Boolean modifierEnvoi(int idenvoi, String colis, String nomEnvoyeur, String emailEnvoyeur,
                         LocalDateTime date_envoi, String nomRecepteur, String contactRecepteur);
    String supprimerEnvoi(int idenvoi);
    void genererPDF(int idenvoi);
    List<Envoyer> trierColis(LocalDate debut, LocalDate fin);
}
