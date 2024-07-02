package multiJoueur;

import Vue.Accueil;
import Vue.EcranType;
import Vue.Fenetre;
import controleur.ControleurTypePartie;
import controleur.JouerSon;
import network.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControleurAccueilHost extends ControleurAccueil {
    protected Accueil accueil;
    protected EcranType ecranType;
    // accumulation de classes internes
    private JouerSon misqueLancement;
    public ControleurAccueilHost(Fenetre fenetre)  {
        super(fenetre);
        misqueLancement = new JouerSon("src/assets/sons/lancement.wav");
        //misqueLancement.jouer(true);
        this.accueil = this.fenetre.getAccueil();
        this.ecranType = this.fenetre.getEcranTpe();
        this.accueil.liaisonAvecControleur(this);
        ControleurTypePartie controleurTypePartie = new ControleurTypePartie(this.fenetre, this.fenetre.getEcranTpe());
        controleurTypePartie.setControleurAccueilHost(this);
        this.ecranType.liaisonAvecControleur(controleurTypePartie);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * lance le jeu en recuperant tous les parmetre saisie si le boutton de lancer le jeu et appuié (et vérifie que les parametres sont valides)
     * récupere l'icone et le surnom choisie si le boutton bouttonCreationBandit est appuyé et permet de lier ensuite cette icone
     * à l'objet Bandit correspendant quand le boutton de lancer le jeu est appuyé
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouttonLancement = this.accueil.getOptionsJeu().getLancerJeu();
        if (e.getSource() == this.accueil.getOptionsJeu().getLancerJeu()){
            this.accueil.getOptionsJeu().getLancerJeu().setEnabled(false);
            String nbBallesBandits = this.accueil.getOptionsJeu().getSaisieNbBalles().getText();
            Double nervositeMarshall = this.accueil.getOptionsJeu().getNervosite();
            String nbWagons = this.accueil.getOptionsJeu().getSaisieNbWagon().getText();
            String nbActions = this.accueil.getOptionsJeu().getSaisieNbActions().getText();
            String nbManches = this.accueil.getOptionsJeu().getSaisieNbManches().getText();

            if (checkInfoSaisieValide(nbBallesBandits,nbWagons,nbActions,nbManches)){
                try {
                    this.client.sendChoixPerso(this.creationsJouers.get(0));
                    this.client.sendParamJeu(nbBallesBandits,nbWagons,nbActions,nbManches,nervositeMarshall);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                bouttonLancement.setBackground(Color.RED);
                bouttonLancement.setText("Invalide !");
            }

        }
        if (e.getSource() == this.accueil.getOptionsJeu().getSlectionPersoPanel().getBouttonCreationBandit()) {
            bouttonLancement.setEnabled(true);
            ImageIcon iconePerso = this.accueil.getOptionsJeu().getSlectionPersoPanel().getPersoSlectionneIcone();
            String surnom = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBanditSurnom();
            this.creationsJouers.add(new Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation(iconePerso,surnom));  // on recup le perso choisie sur la liste et le nomb saisie
        }

    }

    /**
     * pour verifier les options choisis par l'utilisateur
     * @param nbBalles
     * @param nbWagons
     * @param nbActions
     * @param nbManches
     * @return
     */
    private boolean checkInfoSaisieValide (String nbBalles, String nbWagons, String nbActions,String nbManches){
        try {
            int nbBallesI =  Integer.parseInt(nbBalles);
            int nbWagonsI =  Integer.parseInt(nbWagons);
            int nbActionsI = Integer.parseInt(nbActions);
            int nbManchesI =   Integer.parseInt(nbManches);
            return (  nbBallesI >= 0 && nbWagonsI>= 2 && nbActionsI >= 1 && nbManchesI >=1 );
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    /**
     * Affcihe la fenetre du jeu
     */
    public void lancer(){
        this.fenetre.setVisible(true);
    }

    @Override
    public String toString() {
        return "Accueil normal";
    }

}

