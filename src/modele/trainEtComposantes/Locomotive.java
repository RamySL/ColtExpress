package modele.trainEtComposantes;
// probleme avec generer buttin qcq nous garantit qu'elle va etre utiliser qu'une seule fois ?

import modele.Direction;
import modele.butins.Magot;
import modele.trainEtComposantes.ComposanteTrain;
import modele.trainEtComposantes.Extremite;
import modele.trainEtComposantes.Train;

/**
 * la locomotive ( qui est tout à droite dans notre interprétation)
 */
public class Locomotive extends Extremite {
    /**
     * intialise avec un magot à l'interieur
     * @param train
     */
    public Locomotive(Train train) {
        super(train);
        genererButtin(1);
    }

    @Override
    public void genererButtin(int nbButtin) {
        this.butins.add(new Magot());
    }

    /**
     * retourne le voisin de gauche si d pointe vers la gauche sinon retourne this
     * @param d pointe vers la composante voisine à récuperer
     * @return
     */
    public ComposanteTrain getVoisin(Direction d) {
        if (d == Direction.Gauche) return this.voisin;
        else return this;
    }

    public String toString (){
        return "Locomotive";
    }


}
