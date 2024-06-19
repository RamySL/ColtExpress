package network;

import Vue.Accueil;

import java.io.Serial;
import java.util.Collection;

/**
 * quand les clients reçoivent ils initialisent leur jeu en locale et le lance
 */
public class PaquetListePersoClient extends Paquet {
    @Serial
    private static final long serialVersionUID = 17L;

    Collection <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos;
    public PaquetListePersoClient(Collection <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos){
        this.listeInfos = listeInfos;
    }

    public Collection<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> getListeInfos() {
        return listeInfos;
    }
}
