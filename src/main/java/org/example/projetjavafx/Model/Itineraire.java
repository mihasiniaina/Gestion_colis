package org.example.projetjavafx.Model;


public class Itineraire {
    private String codeit;
    private String villedep;
    private String villearr;
    private int frais;

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

}
