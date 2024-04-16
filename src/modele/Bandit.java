package modele;

import java.util.ArrayList;
import java.util.Random;

public class Bandit extends Personnage {

    private ArrayList<Butin> butins = new ArrayList<>();
    private ArrayList<Action> actions = new ArrayList<>();



    public Bandit(ComposanteTrain emp, String surnom, int nbBalles) {
        super(emp, surnom, nbBalles);
        this.surnom = surnom;
    }

    /* executer la premiere action sur la file d'action et renvoi un feedback */
    public String executer() {
        // execute les action de la file des action du joueur
        String feed = "";
        if (!actions.isEmpty()) {
            feed = actions.get(0).executer();
            actions.remove(0);
        }

        this.notifyObservers();
        return feed;
    }

    public void fuir() {
        // le bandit fuit vers le toit quand il voit un marshall et lache un butin

        // Butin perdu et rajouter à l'emplacement
        Random rnd = new Random();
        if (!this.butins.isEmpty()) {
            Butin butinPerdu = this.butins.remove(rnd.nextInt(0, this.butins.size()));
            this.getEmplacement().ajouterButin(butinPerdu);
        }

        //fuite
        Action a = new SeDeplacer(this, Direction.Haut);
        a.executer();
    }

    public void ajouterAction(Action action) {
        this.actions.add(action);
    }

    public String toString() {
        return "Bandit : " + this.surnom + "( " + this.emplacement + " )";
    }

    public void ajouterButtin(Butin b) {
        this.butins.add(b);
    }


    public Butin retirerButtin() {
        //!!!la liste des buttins doit etre verifiée c'est a dire pas vide
        Random rnd = new Random();
        Butin butinPerdu = this.butins.remove(rnd.nextInt(0, this.butins.size()));
        return butinPerdu;
    }

    public int score() {
        int res = 0;
        for (Butin b : this.butins) {
            res += b.getValeur();
        }
        return res;
    }

    public int lenAction() {
        // va etre utilisé par le ctrl pour determiner si le tour de plan est terminé pour ce bandit
        return this.actions.size();
    }

    public void diminueBalle() {
        if (this.nbBalles > 0) {
            nbBalles--;
        }
    }

    public ArrayList<Butin>getButtins(){ return this.butins;}
    public ArrayList<Action> getActions (){return this.actions;}

}
