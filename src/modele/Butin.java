package modele;


import java.util.Random;
// !! tout est bon
/**
 * On représente les passagers présents sur le train par les butins qu'il possède
 */
public abstract class Butin {
    protected int valeur;
    public int getValeur(){return this.valeur;}

}

/**
 * un personnage qui possede une bourse qui a une valeur aléatoire entre 0 et 500
 */
class Bourse extends Butin {

    public Bourse (){
        Random rnd = new Random();
        this.valeur = rnd.nextInt(0,501);
    }
    public String toString(){
        return "Bourse";
    }
}

/**
 * un personnage qui possede une magot qui a une valeur de 1000$
 */
class Magot extends Butin {
    public Magot(){
        this.valeur = 1000;
    }
    public String toString(){
        return "Magot";
    }
}