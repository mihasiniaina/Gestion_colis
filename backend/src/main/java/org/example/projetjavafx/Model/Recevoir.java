package org.example.projetjavafx.Model;
import  jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table( name = "recevoir")
public class Recevoir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrecept;

    @OneToOne
    @JoinColumn(name = "idenvoi", referencedColumnName = "idenvoi", nullable = false)
    private Envoyer envoyer;

    @Column(nullable = false)
    private LocalDateTime date_recept;

    public Recevoir(){}
    public Recevoir( Envoyer envoi, LocalDateTime date_recept){
        this.envoyer = envoi;
        this.date_recept = date_recept;
    }

    public int getIdrecept(){return idrecept;}
    public Envoyer getEnvoyer() {return envoyer;}
    public LocalDateTime getDate_recept(){return date_recept;}

    public void setDate_recept(LocalDateTime date_recept) {
        this.date_recept = date_recept;
    }
}
