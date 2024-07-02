package network.Paquets.PaquetsServeur;

import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetClientConnecte extends Paquet {
    @Serial
    private static final long serialVersionUID  = 11L; //1.1
    private int nbJoueurRestants;
    private String ip;

    public PaquetClientConnecte(int nbJoueurRestants, String ip){

        this.nbJoueurRestants = nbJoueurRestants;
        this.ip = ip;
    }

    public int getNbJoueurRestants() {
        return nbJoueurRestants;
    }

    public String getIp() {
        return ip;
    }
}
