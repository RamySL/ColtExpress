package network.Paquets;

/**
 * envoyé par la hote aux restes des clients pour initialiser leurs parties
 */
public class PaquetParametrePartie extends Paquet {
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
