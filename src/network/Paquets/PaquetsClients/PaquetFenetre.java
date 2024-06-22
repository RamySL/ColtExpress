package network.Paquets.PaquetsClients;

import Vue.Fenetre;
import network.Paquets.Paquet;

public class PaquetFenetre extends Paquet {
    Fenetre fenetre;

    public PaquetFenetre(Fenetre fenetre){
        this.fenetre = fenetre;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }
}
