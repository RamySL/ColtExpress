package multiJoueur;

import Vue.*;
import network.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Le controleur qui va gerer les événenement qui proviennent de l'initialisation du jeu avec la fenetre d'accueil
 * et le choix des parametres du jeu, il doit ensuite intialiser le controleur du jeu avec le modele (train)
 */
public class ControleurAccueilClient extends ControleurAccueil {

    protected AccueilClient accueil;
    // accumulation de classes internes

    /**
     * Intialise le controleur et fait la liason avec les composantes d'accueil dont il va ecouter les evenements
     * @param fenetre du jeu qui contient tous les différentes vu du jeu
     */
    public ControleurAccueilClient(Fenetre fenetre, Client client){
        super(fenetre);
        this.accueil = this.fenetre.getAccueilClient();
        this.accueil.liaisonAvecControleurClient(this);
        this.client = client;
        this.client.setControleurAccueilClient(this);

    }

    /**
     * Envoi le choix du personnage et le surnom à la classe client pour qu'elle l'envoi au serveur quand LancerJeu est appuyé
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouttonLancement = this.accueil.getOptionsJeu().getLancerJeu();
        JButton bouttonCreation = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBouttonCreationBandit();

        if (e.getSource() == bouttonLancement) {
            this.accueil.getOptionsJeu().getLancerJeu().setEnabled(false);
            try {
                this.client.sendChoixPerso(this.creationsJouers.get(0));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
                    }
        if (e.getSource() == bouttonCreation) {
            bouttonLancement.setEnabled(true);
            bouttonCreation.setEnabled(false);
            ImageIcon iconePerso = this.accueil.getOptionsJeu().getSlectionPersoPanel().getPersoSlectionneIcone();
            String surnom = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBanditSurnom();
            this.creationsJouers.add(new Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation(iconePerso, surnom));  // on recup le perso choisie sur la liste et le nomb saisie
        }

    }




}
