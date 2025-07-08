package org.example.projetjavafx.Model;

import java.time.LocalDateTime;

public class Envoyer {
    private int idenvoi;
    private String idvoit;
    private String colis;
    private String nomEnvoyeur;
    private String emailEnvoyeur;
    private LocalDateTime date_envoi;
    private int frais ;
    private String nomRecepteur;
    private String contactRecepteur;

    public Envoyer( int idenvoi, String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                    LocalDateTime date_envoi, int frais, String nomRecepteur, String contactRecepteur){
        this.idenvoi = idenvoi;
        this.idvoit = idvoit;
        this.colis = colis;
        this.nomEnvoyeur = nomEnvoyeur;
        this.emailEnvoyeur = emailEnvoyeur;
        this.date_envoi = date_envoi;
        this.frais = frais;
        this.nomRecepteur = nomRecepteur;
        this.contactRecepteur = contactRecepteur;
    }

    public int getIdenvoi(){ return idenvoi;}
    public String getIdvoit(){ return idvoit;}
    public String getColis(){ return colis;}
    public String getNomEnvoyeur(){ return nomEnvoyeur;}
    public String getEmailEnvoyeur(){ return emailEnvoyeur;}
    public LocalDateTime getDate_envoi(){ return date_envoi;}
    public int getFrais(){ return frais;}
    public String getNomRecepteur(){ return nomRecepteur;}
    public String getContactRecepteur(){ return contactRecepteur;}
}
