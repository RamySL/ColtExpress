package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import modele.personnages.Bandit;
import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetBandit extends Paquet {
    private Bandit bandit;
    private Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infosBanditCourant;
    @Serial
    private static final long serialVersionUID  = 155L;

    public PaquetBandit(Bandit bandit , Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infosBanditCourant){
        this.bandit = bandit;
        this.infosBanditCourant = infosBanditCourant;
    }

    public Bandit getBandit() {
        return bandit;
    }
    public Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation getInfosBanditCourant() {
        return infosBanditCourant;
    }
}
