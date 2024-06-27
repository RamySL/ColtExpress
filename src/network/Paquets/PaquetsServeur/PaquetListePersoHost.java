package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

/**
 * quand les clients reçoivent ils initialisent leur jeu en locale et le lance
 */
public class PaquetListePersoHost extends PaquetListePerso {
    @Serial
    private static final long serialVersionUID = 17L;
    public PaquetListePersoHost(ArrayList <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos){
        super(listeInfos);
    }
}
