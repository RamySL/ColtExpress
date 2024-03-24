package modele;

import java.util.ArrayList;

public class Bandit extends Personnage {

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

        this.notifyObservers();
    }

    public void ajouterAction(Action action) {
        this.actions.add(action);
    }

    public String toString(){
        return "Bandit : " + this.surnom + "( " + this.emplacement + " )";
    }

}
