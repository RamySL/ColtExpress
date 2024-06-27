package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import modele.personnages.Bandit;
import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetBandit extends Paquet {
    private Bandit bandit,banditCourant;
    @Serial
    private static final long serialVersionUID  = 155L;

    public PaquetBandit(Bandit bandit , Bandit infosBanditCourant){
        this.bandit = bandit;
        this.banditCourant = infosBanditCourant;
    }

    public Bandit getBandit() {
        return bandit;
    }

    public Bandit getBanditCourant() {
        return banditCourant;
    }
}
