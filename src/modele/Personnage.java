package modele;

import java.util.ArrayList;

public abstract class Personnage extends Observable{

    ComposanteTrain emplacement;

    String surnom;
    public Personnage (ComposanteTrain emp, String surnom){

        this.emplacement = emp;
        this.surnom = surnom;
    }

    public void setWagon(ComposanteTrain newW){
        this.emplacement = newW;
    }

    public ComposanteTrain getEmplacement(){
        return this.emplacement;
    }

    public String getSurnom(){return this.surnom;}
}


class Marshall extends Personnage {

    public Marshall(ComposanteTrain emp) {
        super(emp, "Marshall");
    }
}

