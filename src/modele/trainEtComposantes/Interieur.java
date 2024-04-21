package modele.trainEtComposantes;

import modele.butins.Bijou;
import modele.butins.Bourse;

import java.util.Random;
// probleme avec l'utilisation de genererButtin
/**
 * Représente les cabines du train et point vers son toit
 */
public abstract class Interieur extends ComposanteTrain {
    private Toit toit;

    /**
     * Créer aussi le toit avec lauqelle elle va etre liée
     * @param train
     */
    public Interieur(Train train) {
        super(train);
        this.toit = new Toit(train, this);

    }

    /**
     * lier l'objet courant à w
     * @param w
     */
    public abstract void lierAvec(Interieur w);

    /**
     * genere des buttin de maniere aléatoire entre Bourse et Bijou
     * @param nbButtin nombre de buttin à generer
     */
    public void genererButtin(int nbButtin) {
        Random rnd = new Random();

        for (int i = 0; i < nbButtin; i++) {
            int rndVal = rnd.nextInt(2);
            if (rndVal == 0) butins.add(new Bourse());
            else butins.add(new Bijou());

        }
    }

    public Toit getToit() {
        return this.toit;
    }
}
