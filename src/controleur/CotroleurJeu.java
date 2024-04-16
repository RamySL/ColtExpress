package controleur;

import Vue.*;
import Vue.Bouttons.BouttonsJeu;
import modele.*;
import modele.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CotroleurJeu implements ActionListener {
    Train train;
    Jeu jeu;
    Fenetre fenetre;
    int nbAction, nJoueurs;

    boolean actionPhase=false,planPhase=true;
    Bandit joueurCourant;

    int tourne; // pour determiner que le boutton action à été appuer et qu'il faut passer au prochain ensemble d'action à executée

    public CotroleurJeu(Train train, Fenetre fenetre, int n){
        this.train = train;
        this.fenetre = fenetre;
        this.jeu = this.fenetre.getJeuPanel();
        this.nbAction = n;
        this.nJoueurs = this.train.getBandits().size(); // le nombre de jr doit etre donnée en pramatere d'une classe

        this.jeu.liaisonCommandesControleur(this);

    }
    // boucle du jeu !!! PROBELEME AVEC LA GESTIONS DES THREADS
    public void lancerJeu(int nbManches) {

        int nbBandit = this.train.getBandits().size();
        // Exemple d'utilisation de SwingUtilities.invokeLater() pour mettre à jour l'interface utilisateur


        // pour l'instant pas de condition d'arret
        int manche = 0;
        while (manche < nbManches) {
            //planification
             this.jeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase de palinification pour la manche " + (manche+1) + "/" + nbManches);
             this.jeu.getCmdPanel().getPhaseFeedPanel().setPlanfication(this.train.getBandits().get(0)); //init
            // on utilise pas une boucle for each pour eviter la cocurrentmodifError avec la methode fuire de bandit
            for (int i = 0; i <nbBandit; i++){

                this.joueurCourant = this.train.getBandits().get(i); // pour que les boutton vide ce bandit specifiquement
                if(i != 0){
                    this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualiserPlanificateur(this.joueurCourant);
                }

                //this.jeu.phase.setText("Phase de planification : c'est le tour à " + this.joueurCourant.getSurnom());
                //System.out.println("tour de " + this.joueurCourant.getSurnom());

                planPhase = true;
                actionPhase = false;

                while (this.joueurCourant.lenAction() < this.nbAction){
                    // pour l'instant il ya rien à mettre dans cette boucle mais elle necessaire pour attendre que joueur est planifié ces action
                    // on ne peut pas la mettre vide sinon je crois elle est ignoré par le compilateur
                    System.out.print("");
                }

            }
            this.jeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase d'action pour la manche " + (manche+1) + "/" + nbManches);
            this.jeu.getCmdPanel().getPhaseFeedPanel().setAction();

            // action
            this.tourne = 0;
            // le nombre totale d'iteration pour toutes les action des bandit = nbBandit * nbAction
            this.joueurCourant = this.train.getBandits().get(0);
            //this.jeu.phase.setText("Phase de d'action " + this.joueurCourant.getSurnom());
            while (this.tourne < this.nbAction * this.nJoueurs ){ // optimise
                System.out.print("");
                planPhase = false;
                actionPhase = true;

            }
            manche++;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if( (e.getSource() instanceof BouttonsJeu.BouttonAction) && actionPhase) {

            String feed =  this.train.getMarshall().executer(); // l'actions est executer et renvoi un feedback
            this.jeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);
            // le principe c'est qu'on veut executer la premiere action du premier bandit ensuite passer
            // à l apremiere action du deuxieme bandit et après quand on arrive au dernier bandit on doit reotuner
            // au premeir et ainsi de suite, il ya une periodicité en le nombre de bandit, qui naturellement traduite par
            // l'opération de modulo
            this.joueurCourant = this.train.getBandits().get(this.tourne % this.nJoueurs);
            feed = this.joueurCourant.executer();
            this.jeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);
            // on affiche le prchain qui va executer

            Bandit bProchain = this.train.getBandits().get((this.tourne + 1) % this.nJoueurs);
            //this.jeu.phase.setText("Phase de d'action " + bProchain.getSurnom());

            //System.out.println(); // pour un affichage plus claire à la console
            this.tourne++;

        }if (planPhase) {
            Action a;

            if (e.getSource() instanceof BouttonsJeu.BouttonDeplacement) {
                a = new SeDeplacer(this.joueurCourant, ((BouttonsJeu.BouttonDeplacement) e.getSource()).getDirection());
                this.joueurCourant.ajouterAction(a);
                this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }

            if (e.getSource() instanceof BouttonsJeu.BouttonBraquage){
                a = new Braquer(this.joueurCourant);
                this.joueurCourant.ajouterAction(a);
                this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }

            if (e.getSource() instanceof BouttonsJeu.BouttonTir){
                a = new Tirer(this.joueurCourant, ((BouttonsJeu.BouttonTir) e.getSource()).getDirection());
                this.joueurCourant.ajouterAction(a);
                this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }


        }


    }

//    public static void main(String[] args) {
//
//        Train train = new Train(4);
//        train.ajouterBandit("ouané");
//        train.ajouterBandit("ramy");
//        train.ajouterBandit("kelia");
//
//
//        FenetrePlus fen = new FenetrePlus();
//        ControleurPlus controleur = new ControleurPlus(train,fen,3); // n : nActions
//
//        controleur.lancerJeu();
//
//
//    }
}
