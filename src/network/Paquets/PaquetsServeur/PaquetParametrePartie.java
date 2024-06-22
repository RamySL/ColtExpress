package network.Paquets.PaquetsServeur;

import network.Paquets.Paquet;

import java.io.Serial;

/**
 * envoyé par la hote aux restes des clients pour initialiser et lancer la partie
 */
public class PaquetParametrePartie extends Paquet {
    @Serial
    private static final long serialVersionUID  = 19L; //1.1
    private String nbBallesBandits,nbWagons,nbActions,nbManches;
    private Double nervositeMarshall;

    public PaquetParametrePartie (String nbBallesBandits,String nbWagons,String nbActions,String nbManches, Double nervositeMarshall){
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
}
