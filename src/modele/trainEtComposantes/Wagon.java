package modele.trainEtComposantes;

import modele.Direction;

import java.util.Random;

/**
 * Wagon est un composant Interieur (cabine) qui possede forcément deux voisins un gauche et un droit
 */
public class Wagon extends Interieur {
    private Interieur cabineGauche, CabineDroite;

    /**
     * Wagon lié directement à la construction avec le voisin gauche, et est construit avec un nombre
     * aléatoire de butins entre 1 et 4 à l'interieur
     *
     * @param train        train auquel appartient le wagon
     * @param cabineGauche voisin gauche du wagon
     */
    public Wagon(Train train, Interieur cabineGauche) {

        super(train);
        this.cabineGauche = cabineGauche;
        Random rnd = new Random();
        genererButtin(rnd.nextInt(1, 5));

    }

    /**
     * lie le wagon avec le voisin droit
     *
     * @param cab voisin droit de Wagon
     */
    public void lierAvec(Interieur cab) {
        this.CabineDroite = cab;
    }

    /**
     * retourne un des voisins de Wagon si la direction est droite ou gauche sinon renvoi wagon lui même
     *
     * @param d spécifie si c'est le voisin gauche ou droit qu'on veut récupérer
     * @return voisin du coté d de wagon
     */
    public ComposanteTrain getVoisin(Direction d) {
        if (d == Direction.Gauche) return this.cabineGauche;
        else if (d == Direction.Droite) return this.CabineDroite;
        else return this;
    }


    public String toString() {
        return "Wagon";
    }
}
