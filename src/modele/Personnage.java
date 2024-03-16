package modele;

import java.util.ArrayList;

public abstract class Personnage {

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

class Bandit extends Personnage {

    ArrayList<Buttin> buttins = new ArrayList<>();
    ArrayList<Action> actions = new ArrayList<>();

    int nbBalles;

    public Bandit(ComposanteTrain emp, String surnom) {
        super(emp, surnom);
        this.surnom = surnom;
    }

    /* executer la premiere action sur la file d'action*/
    public void executer() {
        if (!actions.isEmpty()) {
            actions.get(0).executer();
            actions.remove(0);
        }
    }

    public void ajouterAction(Action action){
        this.actions.add(action);
    }

}


class Marshall extends Personnage {

    public Marshall(ComposanteTrain emp) {
        super(emp, "Marshall");
    }
}

