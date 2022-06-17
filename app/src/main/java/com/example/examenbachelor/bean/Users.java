package com.example.examenbachelor.bean;

import java.io.Serializable;
import java.util.Date;

public class Users implements  Serializable{
    private String Pseudo;
    private String Mdp;
    private String AdresseMail;
    private float Solde;
    private String DoB;

    public Users(){

    }

    public Users(String Pseudo, String Mdp, String Adressemail, String DoB){
        this.Pseudo = Pseudo;
        this.Mdp = Mdp;
        this.AdresseMail = Adressemail;
        this.Solde = Solde;
        this.DoB = DoB;
    }

    public String getPseudo(){
        return Pseudo;
    }
    public void setPseudo(String Pseudo){
        this.Pseudo = Pseudo;
    }

    public String getMdp(){
        return Mdp;
    }
    public void setMdp(String Mdp){
        this.Mdp = Mdp;
    }

    public String getAdresseMail(){
        return AdresseMail;
    }
    public void setAdresseMail(String AdresseMail){
        this.AdresseMail = AdresseMail;
    }
    public float getSolde(){
        return Solde;
    }
    public void setSolde(float Solde){
        this.Solde = Solde;
    }
    public String getDoB(){
        return DoB;
    }
    public void setDoB(String DoB){
        this.DoB = DoB;
    }
}



