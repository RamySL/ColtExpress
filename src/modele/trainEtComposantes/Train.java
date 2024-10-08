package modele.trainEtComposantes;

import modele.*;
import modele.personnages.Bandit;
import modele.personnages.Marshall;
import network.server.Server;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
//!! Tout est Bon

/**
 * Le train représennté par une structure semblante à une liste doublement chainées entre les wagons
 * c'est l'élément pricipale du modèle de l'application il stock les bandits, marshall qu'il contient
 * en attribut. étant itérable une boucle for each sur les wagons est possible, iterable sur l'interieur du train
 */

class TrainSerialisable implements Serializable{

}
public class Train extends TrainSerialisable implements Iterable <Interieur>{
    @Serial
    private static final long serialVersionUID  = 1L;
    private  int nWagons;
    private DernierWagon last;
    private Locomotive first;
    private ArrayList<Bandit> bandits = new ArrayList<>();
    private Marshall marshall;

    /**
     * une précondition sur le nombre de wagos serait qu'il soit supérier ou égale à 2 pour que le jeu ait du sens
     * @param n nombre de composantes de train (wagons + locomotive)
     */
    public Train (int n) {

        this.nWagons = 2;
        last = new DernierWagon(this);
        this.first = new Locomotive(this);

        Interieur courant = last;
        for (int i = 0; i<n-2; i++){ // n-2 pcq on a deja créé last et first

            Wagon prochain = new Wagon(this,courant);
            courant.lierAvec(prochain);
            courant = prochain;
            this.nWagons++;
        }

        courant.lierAvec(this.first);
        this.first.lierAvec(courant);
    }

    /**
     * créer un objet Bandit et l'ajoute aleatoirement dans l'un des toits du train
     * @param surnom surnom bandit
     * @param nbBallles nombre de balles
     */
    public void ajouterBandit(String surnom, int nbBallles){
        Random rnd = new Random();
        int pos = rnd.nextInt(0,this.nWagons);
        int i =0;
        for (ComposanteTrain c : this){
            if(i == pos){
                this.bandits.add(new Bandit(((Interieur)c).getToit(),surnom,nbBallles));
            }
            i++;
        }
    }

    /**
     * Crée un Marshall avec une nervosité passée en parametre et l'ajoute dans la Locomotive
     * @param nervosite nervosité du marshall
     */
    public void ajouterMarshall(Double nervosite){
        this.marshall = new Marshall(this.first, nervosite);
    }

    public ArrayList<Bandit> getBandits(){return this.bandits;}
    public Marshall getMarshall(){return this.marshall;}


    @Override
    public Iterator<Interieur> iterator() {
        return new IterateurTrain();
    }


    public class IterateurTrain implements Iterator<Interieur> {
        private Interieur composanteCourante;
        private boolean end = false; // pour determiner la fin de l'iteration
        public IterateurTrain (){
            this.composanteCourante = Train.this.last;
        }
        @Override
        public boolean hasNext() {
            return !(this.composanteCourante instanceof Locomotive) || !end;
        }
        @Override
        public Interieur next() {
            Interieur tmp = this.composanteCourante;
            this.composanteCourante =(Interieur) this.composanteCourante.getVoisin(Direction.Droite);
            if ( tmp instanceof Locomotive) end = true;
            return  tmp;
        }
    }

    /**
     * pour les tests unitaires
     * @param bandit
     * @param emplacement
     */
    public void ajouterObjetBandit (Bandit bandit, ComposanteTrain emplacement){
        bandit.setEmplacement(emplacement);
        this.bandits.add(bandit);

    }

    @Override
    public String toString() {
        StringBuilder trainInfo = new StringBuilder();
        int wagonIndex = 0;

        for (Interieur composante : this) {
            trainInfo.append("Wagon ").append(wagonIndex).append(": ");

            if (!bandits.isEmpty()) {
                for (Bandit bandit : bandits) {
                    if (bandit.getEmplacement() == composante) {
                        trainInfo.append(bandit.getSurnom()).append(", ");
                    }
                }
            } else {
                trainInfo.append("No bandits, ");
            }

            trainInfo.append("\n");
            wagonIndex++;
        }

        return trainInfo.toString();
    }

}


