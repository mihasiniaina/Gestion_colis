package org.example.projetjavafx.DAO;

import org.example.projetjavafx.Model.Itineraire;

import java.util.List;

public interface ItineraireDAO {

    String newId();
    String ajouterItineraire(String villedep, String villearr, int frais);
    List<Itineraire> listerItineraire();
    String modifierItineraire(String codeit, String villedep, String villearr, int frais);
    String supprimerItineraire(String codeit);

}
