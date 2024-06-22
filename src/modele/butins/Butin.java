package modele.butins;


import java.io.Serializable;
import java.util.Random;
// !! tout est bon
/**
 * On représente les passagers présents sur le train par les butins qu'il possède
 */
public abstract class Butin implements Serializable {
    protected int valeur;
    public int getValeur(){return this.valeur;}

}

