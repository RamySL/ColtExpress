package network;

import Vue.Accueil;

import java.io.Serial;

public class PaquetLancementHost {
    @Serial
    private static final long serialVersionUID  = 16L;
    private Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos;

    public PaquetLancementHost(Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos){
        this.infos = infos;
    }

    public Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation getInfos() {
        return infos;
    }
}
