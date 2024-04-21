package modele.trainEtComposantes;

import modele.Direction;

import java.util.Random;
// tout est bon
/**
 * le dernier wagon (dans notre représentation celui tout à gauche)
 */
public class DernierWagon extends Extremite {
    /**
     * intialise avec un nombre aleatoire de buttin entre 1 et 5
     * @param train
     */
    public DernierWagon (Train train){

        super(train);
        Random rnd = new Random();
        genererButtin(rnd.nextInt(1,5));
    }

    /**
     * retourne le voisin de droite si d pointe vers la droite sinon retourne this
     * @param d pointe vers la composante voisine à récuperer
     * @return
     */
    public ComposanteTrain getVoisin(Direction d){
        if (d == Direction.Droite) return this.voisin;
        else return this;
    }

    public String toString (){
        return "DernierWagon";
    }

}