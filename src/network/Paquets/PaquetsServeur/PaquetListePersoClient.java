package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

/**
 * quand les clients reçoivent ils initialisent leur jeu en locale et le lance
 */
public class PaquetListePersoClient extends PaquetListePerso {
    @Serial
    private static final long serialVersionUID = 17L;
    ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos;
    public PaquetListePersoClient(ArrayList <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos){
        super(listeInfos);
    }

}
