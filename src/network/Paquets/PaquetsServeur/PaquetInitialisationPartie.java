package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import modele.personnages.Bandit;
import modele.trainEtComposantes.Train;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

/**
 * envoyé par la hote aux restes des clients pour initialiser et lancer la partie
 */
public class PaquetInitialisationPartie extends Paquet {
    @Serial
    private static final long serialVersionUID  = 19L; //1.1
    private String nbBallesBandits,nbWagons,nbActions,nbManches;
    private Double nervositeMarshall;
    private Train train;
    private Bandit joueurCourant;


    public PaquetInitialisationPartie(String nbBallesBandits, String nbWagons, String nbActions, String nbManches, Double nervositeMarshall){
        this.nbActions = nbActions;
        this.nbBallesBandits = nbBallesBandits;
        this.nbWagons = nbWagons;
        this.nbManches = nbManches;
        this.nervositeMarshall = nervositeMarshall;

    }

    public Double getNervositeMarshall() {
        return nervositeMarshall;
    }

    public String getNbActions() {
        return nbActions;
    }

    public String getNbBallesBandits() {
        return nbBallesBandits;
    }

    public String getNbManches() {
        return nbManches;
    }

    public String getNbWagons() {
        return nbWagons;
    }

    public void initTrain (ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> infos){
        this.train = new Train(Integer.parseInt(this.nbWagons));
        train.ajouterMarshall(this.nervositeMarshall);

        for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation info : infos){
            train.ajouterBandit(info.getSurnom(),Integer.parseInt(this.nbBallesBandits));
        }
        this.joueurCourant = this.train.getBandits().getFirst();

    }

    public Train getTrain() {
        return train;
    }

    public Bandit getJoueurCourant() {
        return joueurCourant;
    }
}
