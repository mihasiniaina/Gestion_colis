package org.example.projetjavafx.Model;

public class Voiture {
    private String idvoit;
    private String design;
    private String codeit;

    public Voiture(String idvoit, String design, String codeit){
        this.idvoit = idvoit;
        this.design = design;
        this.codeit = codeit;
    }

    public String getIdvoit(){return idvoit;}
    public String getDesign(){return design;}
    public String getCodeit(){return codeit;}

}
