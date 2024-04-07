package modele;

import java.util.ArrayList;

public abstract class Personnage extends Observable{

    ComposanteTrain emplacement;

    String surnom;

    protected int nbBalles;
    public Personnage (ComposanteTrain emp, String surnom, int nbBalles){

        this.emplacement = emp;
        this.surnom = surnom;
        this.nbBalles = nbBalles;
        // on notifie le composant qui contient le personnage
        this.emplacement.ajouterPersonnage(this);
    }



    public void setWagon(ComposanteTrain newW){
        // on l'enleve de l'ancien emplacement
        this.emplacement.persoList.remove(this);
        this.emplacement = newW;
        this.emplacement.persoList.add(this);
    }

    public ComposanteTrain getEmplacement(){
        return this.emplacement;
    }

    public String getSurnom(){return this.surnom;}

    public int getNbBalles(){return this.nbBalles;}


}



