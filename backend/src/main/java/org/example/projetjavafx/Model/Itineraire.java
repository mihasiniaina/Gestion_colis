package org.example.projetjavafx.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( name = "itineraire")
public class Itineraire {
    @Id
    private String codeit;

    @OneToMany(mappedBy = "itineraire")
    private List<Voiture> voitures;

    @Column( nullable = false )
    private String villedep;

    @Column( nullable = false )
    private String villearr;

    @Column( nullable = false )
    private int frais;

    public Itineraire(){}

    public Itineraire(String codeit, String villedep, String villearr, int frais ){
        this.codeit = codeit;
        this.villedep = villedep;
        this.villearr = villearr;
        this.frais = frais;
    }

    public String getCodeit(){ return codeit;}
    public String getVilledep(){ return villedep;}
    public String getVillearr(){ return villearr;}
    public int getFrais(){ return frais;}

    public void setVilledep(String villedep){ this.villedep = villedep; }
    public void setVillearr(String villearr){ this.villearr = villearr; }
    public void setFrais(int frais){ this.frais = frais; }


}
