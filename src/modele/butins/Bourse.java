package modele.butins;

import java.util.Random;

/**
 * un personnage qui possede une bourse qui a une valeur aléatoire entre 0 et 500
 */
public class Bourse extends Butin {

    public Bourse() {
        Random rnd = new Random();
        this.valeur = rnd.nextInt(0, 501);
    }

    public String toString() {
        return "Bourse";
    }
}
