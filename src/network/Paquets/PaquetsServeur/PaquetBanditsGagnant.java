package network.Paquets.PaquetsServeur;

import modele.personnages.Bandit;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

public class PaquetBanditsGagnant extends Paquet {
    @Serial
    private static final long serialVersionUID  = 13L;
    private ArrayList< Bandit> bandits;

    public PaquetBanditsGagnant(ArrayList< Bandit> bandits){
        this.bandits = bandits;
    }

    public ArrayList< Bandit> getBandits() {
        return bandits;
    }
}
