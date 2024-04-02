package modele;

import java.util.ArrayList;
import java.util.Random;

public class Bandit extends Personnage {

    ArrayList<Buttin> buttins = new ArrayList<>();
    ArrayList<Action> actions = new ArrayList<>();

    int nbBalles;

    public Bandit(ComposanteTrain emp, String surnom) {
        super(emp, surnom, 6);
        this.surnom = surnom;
    }

    /* executer la premiere action sur la file d'action*/
    public void executer() {
        // execute les action de la file des action du joueur
        if (!actions.isEmpty()) {
            actions.get(0).executer();
            actions.remove(0);
        }

        this.notifyObservers();
    }

    public void fuir() {
        // le bandit fuit vers le toit quand il voit un marshall et lache un butin

        // Butin perdu et rajouter à l'emplacement
        Random rnd = new Random();
        if (!this.buttins.isEmpty()) {
            Buttin butinPerdu = this.buttins.remove(rnd.nextInt(0, this.buttins.size()));
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

    public void ajouterButtin(Buttin b) {
        this.buttins.add(b);
    }


    public Buttin retirerButtin() {
        //!!!la liste des buttins doit etre verifiée c'est a dire pas vide
        Random rnd = new Random();
        Buttin buttinPerdu = this.buttins.remove(rnd.nextInt(0, this.buttins.size()));
        return  buttinPerdu;
    }

    public int score() {
        int res = 0;
        for (Buttin b : this.buttins) {
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

    public ArrayList<Buttin>getButtins(){ return this.buttins;}

}
