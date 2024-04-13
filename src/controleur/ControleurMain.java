package controleur;

import VuePlus.AccueilPlus;
import VuePlus.FenetrePlus;
import VuePlus.JeuPlus;
import modele.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le controleur qui va gerer les événenement qui proviennent de l'initialisation du jeu avec la fenetre d'accueil
 * il doit ensuite intialiser le controleur du jeu avec le modele (train)
 */
public class ControleurMain implements ActionListener {

    private AccueilPlus accueil;
    private FenetrePlus fenetre;

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
        if (e.getSource() == this.accueil.getOptionsJeu().lancerJeu){
            // On recupere ce qui a été saisie avec getTexte et on intialise le jeu

            Train train = new Train(this.accueil.getOptionsJeu().getNbWagon());
            // boucle pour ajouter les bandit
            int nbJoueur =  this.accueil.getOptionsJeu().getnbJouer();
            for (int i = 1; i<=nbJoueur; i++ ){
                train.ajouterBandit("Bandit"+i);
            }
            JeuPlus jeu = new JeuPlus(train, this.fenetre);
            this.fenetre.ajouterFenetreJeu(jeu);
            this.fenetre.changerFenetre(this.fenetre.getJeuId());
            ControleurPlus controleurPlus = new ControleurPlus(train,this.fenetre,3);

            BoucleJeu boucleJeu = new BoucleJeu(controleurPlus);
            boucleJeu.execute();


        }

    }

    class BoucleJeu extends SwingWorker<Void, Void>{

        private ControleurPlus controleur;

        // Constructeur
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
