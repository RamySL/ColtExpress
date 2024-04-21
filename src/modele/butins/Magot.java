package modele.butins;

/**
 * un personnage qui possede une magot qui a une valeur de 1000$
 */
public class Magot extends Butin {
    public Magot() {
        this.valeur = 1000;
    }

    public String toString() {
        return "Magot";
    }
}
