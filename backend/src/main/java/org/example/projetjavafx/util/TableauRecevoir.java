package org.example.projetjavafx.util;

import java.time.LocalDateTime;

public class TableauRecevoir {
    private int idenvoi;
    private LocalDateTime date_envoi;
    private String colis;
    private LocalDateTime date_recept;
    private String receptionneur;
    private String contact;
    private boolean status;

    public TableauRecevoir( int idenvoi, LocalDateTime date_envoi, String colis, LocalDateTime date_recept, String receptionneur, String contact, boolean status){
        this.idenvoi = idenvoi ;
        this.date_envoi = date_envoi ;
        this.colis = colis ;
        this.receptionneur = receptionneur ;
        this.contact = contact ;
        this.status = status ;
        this.date_recept = date_recept ;
    }

    public int getIdEnvoi(){
        return idenvoi;
    }
    public LocalDateTime getDate_envoi(){
        return date_envoi;
    }

    public String getColis() {
        return colis;
    }

    public LocalDateTime getDate_recept() {
        return date_recept;
    }

    public String getReceptionneur() {
        return receptionneur;
    }


    public String getContact() {
        return contact;
    }

    public boolean isStatus() {
        return status;
    }
}
