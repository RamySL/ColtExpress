package modele.trainEtComposantes;

/**
 * Pour représenter le dernier wagon et la locomotive du train
 */
public abstract class Extremite extends Interieur {
    Interieur voisin;
    public Extremite(Train train){
        super ( train);

    }
    public void lierAvec(Interieur wagonVois){
        this.voisin = wagonVois;
    }

}

