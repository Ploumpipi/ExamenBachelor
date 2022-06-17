package com.example.examenbachelor.bean;

import java.io.Serializable;

public class NFT implements Serializable{
    private float Prix;
    private String Createur;
    private String Proprietaire;

    public NFT(){

    }
    public NFT(float Prix, String Createur, String Proprietaire){
        this.Prix = Prix;
        this.Createur = Createur;
        this.Proprietaire = Proprietaire;
    }

    public String getCreateur(){
        return Createur;
    }
    public void setCreateur(String Createur){
        this.Createur = Createur;
    }
    public String getProprietaire(){
        return Proprietaire;
    }
    public void setProprietaire(String Proprietaire){
        this.Proprietaire = Proprietaire;
    }
    public float getPrix(){
        return Prix;
    }
    public void setPrix(float Prix){
        this.Prix = Prix;
    }
}
