package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Voiture;

import java.util.List;

public interface VoitureDAO {

    String newId();
    String ajouterVoiture(String design, String codeit);
    List<Voiture> listerVoiture();
    String modifierVoiture(String idvoit, String design, String codeit);
    String supprimerVoiture(String idvoit);


}
