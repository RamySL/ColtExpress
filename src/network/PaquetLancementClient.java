package network;

import Vue.Accueil;

import java.io.Serial;

/**
 * Paquet envoyé par le client qui contient les information du personnage créer au moment d'appuyer sur lancer
 */
public class PaquetLancementClient {
    @Serial
    private static final long serialVersionUID  = 15L;
    private Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos;

    public PaquetLancementClient(Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos){
        this.infos = infos;
    }

    public Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation getInfos() {
        return infos;
    }
}
