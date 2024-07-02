package network.Paquets.PaquetsServeur;

import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

public class PaquetClientConnecte extends Paquet {
    @Serial
    private static final long serialVersionUID  = 11L; //1.1
    private int nbJoueurRestants;
    private ArrayList<String>  ips;

    public PaquetClientConnecte(int nbJoueurRestants, ArrayList<String> ips){

        this.nbJoueurRestants = nbJoueurRestants;
        this.ips = ips;
    }

    public int getNbJoueurRestants() {
        return nbJoueurRestants;
    }

    public ArrayList<String> getIp() {
        return ips;
    }
}
