package network.Paquets.PaquetsServeur;

import Vue.Accueil;
import network.Paquets.Paquet;

import javax.security.auth.callback.PasswordCallback;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class PaquetListePerso extends Paquet  {
    ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos;
    public PaquetListePerso(ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> listeInfos){
        this.listeInfos = listeInfos;
    }

    public ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> getListeInfos() {
        return listeInfos;
    }

}
