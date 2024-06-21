package network.Paquets;

import Vue.Accueil;

public abstract class PaquetLancement extends Paquet{
    protected Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos;

    public PaquetLancement (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos){
        this.infos = infos;
    }

    public Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation getInfos() {
        return this.infos;
    }

}
