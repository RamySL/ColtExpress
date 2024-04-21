package modele.butins;

import modele.butins.Butin;

/**
 * un butin avec une valeur de 500 $
 */
public class Bijou extends Butin {
    public Bijou() {
        this.valeur = 500;
    }

    public String toString() {
        return "Bijou";
    }
}
