package network.Paquets;

import Vue.Accueil;

import java.io.Serial;
import java.util.ArrayList;

/**
 * quand les clients reçoivent ils initialisent leur jeu en locale et le lance
 */
public class PaquetListePersoClient extends Paquet {
    @Serial
    private static final long serialVersionUID = 17L;
    ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos;
    public PaquetListePersoClient(ArrayList <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos){
        this.listeInfos = listeInfos;
    }
    public ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> getListeInfos() {
        return listeInfos;
    }
}
