package org.example.projetjavafx.Model;

import java.time.LocalDateTime;

public class Recevoir {
    private int idrecept;
    private int idenvoi;
    private LocalDateTime date_recept;

    public Recevoir( int idrecept, int idenvoi, LocalDateTime date_recept){
        this.idrecept = idrecept;
        this.idenvoi = idenvoi;
        this.date_recept = date_recept;
    }

    public int getIdrecept(){return idrecept;}
    public int getIdenvoi(){return idenvoi;}
    public LocalDateTime getDate_recept(){return date_recept;}
}
