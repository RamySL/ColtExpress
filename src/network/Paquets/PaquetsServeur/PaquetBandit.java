package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import modele.personnages.Bandit;
import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetBandit extends Paquet {
    private int indiceBandit,indiceBanditCourant;
    @Serial
    private static final long serialVersionUID  = 155L;

    public PaquetBandit(int indicebandit , int indiceBanditCourant){
        this.indiceBandit = indicebandit;
        this.indiceBanditCourant = indiceBanditCourant;
    }

    public int getIndiceBandit() {
        return indiceBandit;
    }

    public int getIndiceBanditCourant() {
        return indiceBanditCourant;
    }
}
