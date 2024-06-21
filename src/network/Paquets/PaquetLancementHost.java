package network.Paquets;

import Vue.Accueil;

import java.io.Serial;

public class PaquetLancementHost extends PaquetLancement {
    @Serial
    private static final long serialVersionUID  = 16L;
    private String nbBallesBandits,nbWagons,nbActions,nbManches;
    private Double nervositeMarshall;

    public PaquetLancementHost(Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos){
        super(infos);
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
