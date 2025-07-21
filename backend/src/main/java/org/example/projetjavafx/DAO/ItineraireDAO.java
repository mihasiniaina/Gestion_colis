package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Itineraire;

import java.util.List;

public interface ItineraireDAO {

    String newId();
    Boolean checkIt(String villedep, String villearr);
    Boolean ajouterItineraire(String villedep, String villearr, int frais);
    List<Itineraire> listerItineraire();
    Boolean modifierItineraire(String codeit, String villedep, String villearr, int frais);
    String supprimerItineraire(String codeit);

}
