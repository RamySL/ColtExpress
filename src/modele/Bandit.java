package modele;

import java.security.DigestException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
//!! tout est bon
/**
 * Un bandit se déplace dans tout le train en essayant de voler le maximum de butins
 */
public class Bandit extends Personnage {

    private ArrayList<Butin> butins = new ArrayList<>();
    private Queue<Action> actions = new ArrayDeque<>();
    private int nbBalles;

    /**
     *
     * @param emp emplacement dans le train
     * @param surnom surnom
     * @param nbBalles nombres de balles
     */
    public Bandit(ComposanteTrain emp, String surnom, int nbBalles) {
        super(emp, surnom);
        this.surnom = surnom;
        this.nbBalles = nbBalles;
    }

    /**
     * Execute la première action sur la file d'attente d'actions du bandit et la retire de la file
     * @return feedback de l'execution
     */
    public String executer() {

        String feed = "";
        if (!actions.isEmpty()) {
            feed = actions.remove().executer();
        }
        this.notifyObservers();
        return feed;
    }

    /**
     * Deplace le bandit vers le toit en le faisant perdre un butin aléatoire, et ajoute ce butin
     * perdu à l'emplacement
     */
    public void fuir() {
        //System.out.println("execution de fuir");
        if (!this.butins.isEmpty()) {
            Butin butinPerdu = this.retirerButtin();
            this.getEmplacement().ajouterButin(butinPerdu);
        }
        //fuite
//        this.ajouterAction(new SeDeplacer(this, Direction.Haut));
//        this.executer();
        new SeDeplacer(this, Direction.Haut).executer();

    }

    /**
     * ajoute une action à la file d'attente des actions
     * @param action
     */
    public void ajouterAction(Action action) {
        this.actions.add(action);
    }

    public String toString() {
        return "Bandit : " + this.surnom + "( " + this.emplacement + " )";
    }

    public void ajouterButtin(Butin b) {
        this.butins.add(b);
    }

    /**
     * retire un butin aléatoire du bandit
     * ! Précondition la liste de butins du bandit n'est pas vide
     * @return butin retiré du bandit
     */
    public Butin retirerButtin() {
        Random rnd = new Random();
        return this.butins.remove(rnd.nextInt(0, this.butins.size()));
    }

    /**
     * retourne la somme de valeur des butins possédés
     * @return
     */
    public int score() {
        int res = 0;
        for (Butin b : this.butins) {
            res += b.getValeur();
        }
        return res;
    }

    /**
     *
     * @return nombre d'actions dans la file
     */
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
    public Queue<Action> getActions (){return this.actions;}
    public int getNbBalles() {return nbBalles;}
}
