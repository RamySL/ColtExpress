package controleur;

import Vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Le controleur qui va gerer les événenement qui proviennent de l'initialisation du jeu avec la fenetre d'accueil
 * et le choix des parametres du jeu, il doit ensuite intialiser le controleur du jeu avec le modele (train)
 */
public class ControleurAccueilClient implements ActionListener {

    protected AccueilClient accueil;
    protected Fenetre fenetre;
    // accumulation de classes internes
    protected ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJouers = new ArrayList<>();

    /**
     * Intialise le controleur et fait la liason avec les composantes d'accueil dont il va ecouter les evenements
     * @param fenetre du jeu qui contient tous les différentes vu du jeu
     */
    public ControleurAccueilClient(Fenetre fenetre){

        this.fenetre = fenetre;
        this.accueil = this.fenetre.getAccueilClient();
        this.accueil.liaisonAvecControleurClient(this);

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
        if (e.getSource() == this.accueil.getOptionsJeu().getLancerJeu()) {

//                this.misqueLancement.arreter();
//                Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();
//
//                Train train = new Train (Integer.parseInt(nbWagons) );
//                train.ajouterMarshall(nervositeMarshall);
//
//                // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va corresependre au
//                // premier Personnage dans la liste du train et le deuxieme au deuxieme etc
//                for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : this.creationsJouers){
//
//                    train.ajouterBandit(infos.getSurnom(),Integer.parseInt(nbBallesBandits));
//                    mapPersonnageIcone.put(train.getBandits().getLast(),infos.getIcone());
//                }
//                mapPersonnageIcone.put(train.getMarshall(),new ImageIcon("src/assets/images/sherif.png"));
//                Jeu jeu = new Jeu(train, this.fenetre, mapPersonnageIcone);
//
//                this.fenetre.ajouterFenetreJeu(jeu);
//                this.fenetre.changerVue(this.fenetre.getJeuId());
//                CotroleurJeu cotroleurJeu = new CotroleurJeu(train,this.fenetre,Integer.parseInt(nbActions) );
//
//                ControleurAccueilHost.BoucleJeu boucleJeu = new ControleurAccueilHost.BoucleJeu(cotroleurJeu);
//                boucleJeu.execute();
//            }else {
//                bouttonLancement.setBackground(Color.RED);
//                bouttonLancement.setText("Invalide !");
//            }
        }
        if (e.getSource() == this.accueil.getOptionsJeu().getSlectionPersoPanel().getBouttonCreationBandit()) {
            bouttonLancement.setEnabled(true);
            ImageIcon iconePerso = this.accueil.getOptionsJeu().getSlectionPersoPanel().getPersoSlectionneIcone();
            String surnom = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBanditSurnom();
            this.creationsJouers.add(new Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation(iconePerso, surnom));  // on recup le perso choisie sur la liste et le nomb saisie
        }

    }

    @Override
    public String toString() {
        return "Accueil Client";
    }
}
