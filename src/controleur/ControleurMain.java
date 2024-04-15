package controleur;

import VuePlus.AccueilPlus;
import VuePlus.FenetrePlus;
import VuePlus.JeuPlus;
import modele.Personnage;
import modele.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Le controleur qui va gerer les événenement qui proviennent de l'initialisation du jeu avec la fenetre d'accueil
 * il doit ensuite intialiser le controleur du jeu avec le modele (train)
 */
public class ControleurMain implements ActionListener {

    private AccueilPlus accueil;
    private FenetrePlus fenetre;
    // accumulation de classes internes
    private ArrayList<AccueilPlus.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJouers = new ArrayList<>(); // contiendra le surnom et icone


    public ControleurMain(FenetrePlus fenetre){
        this.fenetre = fenetre;
        this.accueil = this.fenetre.getAccueil();
        this.accueil.liaisonAvecControleur(this);

    }

    public void lancer(){
        this.fenetre.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /// !!!! à changer il travail avec public
        if (e.getSource() == this.accueil.getOptionsJeu().lancerJeu){
            // On recupere ce qui a été saisie avec getTexte et on intialise le jeu
            // !! RECUERE LE NOMBRE DE BALLES

            Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();

            Train train = new Train(this.accueil.getOptionsJeu().getNbWagon());
            // boucle pour ajouter les bandit
            // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va etre le premier Personnage dans la liste du train
            for (AccueilPlus.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : this.creationsJouers){
                // quand le bandit est ajouté au train il faut garder un lien avec le chemin de son icone
                // qu'il faudra passer à la vue
                train.ajouterBandit(infos.getSurnom());
                mapPersonnageIcone.put(train.getBandits().getLast(),infos.getIcone());
            }
            JeuPlus jeu = new JeuPlus(train, this.fenetre, mapPersonnageIcone);

            this.fenetre.ajouterFenetreJeu(jeu);
            this.fenetre.changerFenetre(this.fenetre.getJeuId());
            ControleurPlus controleurPlus = new ControleurPlus(train,this.fenetre,this.accueil.getOptionsJeu().getNbActions());

            BoucleJeu boucleJeu = new BoucleJeu(controleurPlus);
            boucleJeu.execute();


        }


        if (e.getSource() == this.accueil.getOptionsJeu().getSlectionPersoPanel().bouttonCreationBandit) {
            ImageIcon iconePerso = this.accueil.getOptionsJeu().getSlectionPersoPanel().getPersoSlectionneIcone();
            String surnom = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBanditSurnom();

            this.creationsJouers.add(new AccueilPlus.OptionsJeu.SelectionPersonnages.JoueurInfoCreation(iconePerso,surnom));  // on recup le perso choisie sur la liste et le nomb saisie
        }

    }

    class BoucleJeu extends SwingWorker<Void, Void>{

        private ControleurPlus controleur;

        public BoucleJeu(ControleurPlus controleur) {
            this.controleur = controleur;
        }

        @Override
        // la boucle de notre jeu qui va tourner sur un thread different que l'EDT qui est responsable pour l'actualisation de l'affchage
        protected Void doInBackground() {
            controleur.lancerJeu();
            return null;
        }

        // c'est une méthode qui va etre appelé quand le travail en fond (doInBackGround) sera terminé
        @Override
        protected void done() {

        }
    }

    public static void main(String[] args) {
        FenetrePlus fenetre = new FenetrePlus();
        ControleurMain controleurMain = new ControleurMain(fenetre);
        controleurMain.lancer();
    }
}
