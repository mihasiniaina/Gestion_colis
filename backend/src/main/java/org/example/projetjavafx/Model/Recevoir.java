package org.example.projetjavafx.Model;
import  jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table( name = "recevoir")
public class Recevoir {
    @Id
    private int idrecept;

    @ManyToOne
    @JoinColumn(name = "idenvoi")
    private Envoyer envoi;

    @Column(nullable = false)
    private LocalDateTime date_recept;

    public Recevoir(){}
    public Recevoir( int idrecept, Envoyer envoi, LocalDateTime date_recept){
        this.idrecept = idrecept;
        this.envoi = envoi;
        this.date_recept = date_recept;
    }

    public int getIdrecept(){return idrecept;}
    public Envoyer getEnvoi() {return envoi;}
    public LocalDateTime getDate_recept(){return date_recept;}

    public void setDate_recept(LocalDateTime date_recept) {
        this.date_recept = date_recept;
    }
}
