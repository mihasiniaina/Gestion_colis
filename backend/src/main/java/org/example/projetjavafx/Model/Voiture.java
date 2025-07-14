package org.example.projetjavafx.Model;
import  jakarta.persistence.*;

@Entity
@Table(name = "voiture")
public class Voiture {

    @Id
    private String idvoit;

    @Column(nullable = false)
    private String design;

    @ManyToOne
    @JoinColumn (name = "codeit")
    private Itineraire itineraire;

    public Voiture(){}

    public Voiture(String idvoit, String design, Itineraire itineraire){
        this.idvoit = idvoit;
        this.design = design;
        this.itineraire = itineraire;
    }

    public String getIdvoit(){return idvoit;}
    public String getDesign(){return design;}
    public Itineraire getItineraire(){return itineraire;}


    public void setDesign(String design) {
        this.design = design;
    }

    public void setItineraire(Itineraire itineraire) {
        this.itineraire = itineraire;
    }
}
