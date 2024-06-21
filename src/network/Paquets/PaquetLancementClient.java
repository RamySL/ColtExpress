package network.Paquets;

import Vue.Accueil;

import java.io.Serial;

/**
 * Paquet envoyé par le client qui contient les informations du personnage créer au moment d'appuyer sur lancer
 */
public class PaquetLancementClient extends PaquetLancement {
    @Serial
    private static final long serialVersionUID  = 15L;

    public PaquetLancementClient(Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos){
        super(infos);
    }


}
