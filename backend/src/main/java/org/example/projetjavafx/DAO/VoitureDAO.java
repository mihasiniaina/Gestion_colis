package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Voiture;

import java.util.List;

public interface VoitureDAO {

    Boolean ajouterVoiture(String idvoit, String design, String codeit);
    List<Voiture> listerVoiture();
    boolean modifierVoiture(String idvoit, String design, String codeit);
    String supprimerVoiture(String idvoit);


}
