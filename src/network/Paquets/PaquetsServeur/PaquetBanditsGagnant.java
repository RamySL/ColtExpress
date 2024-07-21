package network.Paquets.PaquetsServeur;

import modele.personnages.Bandit;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

public class PaquetBanditsGagnant extends Paquet {
    @Serial
    private static final long serialVersionUID  = 13L;
    private ArrayList< Integer> banditsIndices;
    private int scoreMax;

    public PaquetBanditsGagnant(ArrayList< Integer> banditsIndices, int scoreMax){
        this.banditsIndices = banditsIndices;
        this.scoreMax = scoreMax;

    }

    public ArrayList<Integer> getBanditsIndices() {
        return banditsIndices;
    }

    public int getScoreMax() {
        return scoreMax;
    }
}
