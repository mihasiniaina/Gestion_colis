package org.example.projetjavafx.Model;

import  jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "envoyer")
public class Envoyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idenvoi;

    @OneToOne(mappedBy = "envoyer", cascade = CascadeType.REMOVE, optional = true)
    private Recevoir recevoir;

    @ManyToOne
    @JoinColumn(name = "idvoit", referencedColumnName = "idvoit", nullable = false)
    private Voiture voiture;

    @Column(nullable = false)
    private String colis;

    @Column(nullable = false)
    private String nomEnvoyeur;

    @Column(nullable = false)
    private String emailEnvoyeur;

    @Column(nullable = false)
    private LocalDateTime date_envoi;

    @Column(nullable = false)
    private int frais ;

    @Column(nullable = false)
    private String nomRecepteur;

    @Column(nullable = false)
    private String contactRecepteur;

    @Column(nullable = false)
    private boolean arrived = false;

    public Envoyer(){}

    public Envoyer(Voiture voiture, String colis, String nomEnvoyeur, String emailEnvoyeur,
                    LocalDateTime date_envoi, int frais, String nomRecepteur, String contactRecepteur){
        this.voiture = voiture;
        this.colis = colis;
        this.nomEnvoyeur = nomEnvoyeur;
        this.emailEnvoyeur = emailEnvoyeur;
        this.date_envoi = date_envoi;
        this.frais = frais;
        this.nomRecepteur = nomRecepteur;
        this.contactRecepteur = contactRecepteur;
    }

    public int getIdenvoi(){ return idenvoi;}
    public Voiture getVoiture() {return voiture;}
    public String getColis(){ return colis;}
    public String getNomEnvoyeur(){ return nomEnvoyeur;}
    public String getEmailEnvoyeur(){ return emailEnvoyeur;}
    public LocalDateTime getDate_envoi(){ return date_envoi;}
    public int getFrais(){ return frais;}
    public String getNomRecepteur(){ return nomRecepteur;}
    public String getContactRecepteur(){ return contactRecepteur;}
    public boolean isArrived(){return arrived;};

    public void setColis(String colis) {
        this.colis = colis;
    }

    public void setDate_envoi(LocalDateTime date_envoi) {
        this.date_envoi = date_envoi;
    }

    public void setEmailEnvoyeur(String emailEnvoyeur) {
        this.emailEnvoyeur = emailEnvoyeur;
    }

    public void setNomEnvoyeur(String nomEnvoyeur) {
        this.nomEnvoyeur = nomEnvoyeur;
    }

    public void setContactRecepteur(String contactRecepteur) {
        this.contactRecepteur = contactRecepteur;
    }

    public void setNomRecepteur(String nomRecepteur) {
        this.nomRecepteur = nomRecepteur;
    }

    public Recevoir getRecevoir() {
        return recevoir;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }
}
