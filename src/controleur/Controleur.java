package controleur;

import Vue.*;
import modele.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener {
    Train train;
    Jeu jeu;
    Fenetre fenetre;
    int nbAction, nJoueurs;

    boolean actionPhase=false,planPhase=true;
    Bandit joueurCourant;

    int tourne; // pour determiner que le boutton action à été appuer et qu'il faut passer au prochain ensemble d'action à executée

    public Controleur(Train train, Fenetre fenetre, int n){
        this.train = train;
        this.fenetre = fenetre;
        this.jeu = this.fenetre.getJeuPanel();
        this.nbAction = n;
        this.nJoueurs = this.train.getBandits().size(); // le nombre de jr doit etre donnée en pramatere d'une classe

        this.jeu.liaisonBottonsListener(this);

    }
    // boucle du jeu
    public void lancerJeu() {
        this.fenetre.setVisible(true);


        int nbBandit = this.train.getBandits().size();
        // pour l'instant pas de condition d'arret
        while (true) {
            //planification

            // on utilise pas une boucle for each pour eviter la cocurrentmodifError avec la methode fuire de bandit
            for (int i = 0; i <nbBandit; i++){
                this.joueurCourant = this.train.getBandits().get(i); // pour que les boutton vide ce bandit specifiquement
                this.jeu.phase.setText("Phase de planification : c'est le tour à " + this.joueurCourant.getSurnom());

                planPhase = true;
                actionPhase = false;

                while (this.joueurCourant.lenAction() < this.nbAction){
                    // pour l'instant il ya rien à mettre dans cette boucle mais elle necessaire pour attendre que joueur est planifié ces action
                    // on ne peut pas la mettre vide sinon je crois elle est ignoré par le compilateur
                    System.out.print("");
                }

            }

            // action
            this.tourne = 0;
            // le nombre totale d'iteration pour toutes les action des bandit = nbBandit * nbAction
            this.joueurCourant = this.train.getBandits().get(0);
            this.jeu.phase.setText("Phase de d'action " + this.joueurCourant.getSurnom());

            while (this.tourne < this.nbAction * this.nJoueurs ){ // optimise


                System.out.print("");
                planPhase = false;
                actionPhase = true;

            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == jeu.action && actionPhase) {
            this.train.getMarshall().executer();

            // le principe c'est qu'on veut executer la premiere action du premier bandit ensuite passer
            // à l apremiere action du deuxieme bandit et après quand on arrive au dernier bandit on doit reotuner
            // au premeir et ainsi de suite, il ya une periodicité en le nombre de bandit, qui naturellement traduite par
            // l'opération de modulo
            this.joueurCourant = this.train.getBandits().get(this.tourne % this.nJoueurs);
            this.joueurCourant.executer();
            // on affiche le prchain qui va executer

                Bandit bProchain = this.train.getBandits().get((this.tourne + 1) % this.nJoueurs);
                this.jeu.phase.setText("Phase de d'action " + bProchain.getSurnom());

            //System.out.println(); // pour un affichage plus claire à la console
            this.tourne++;

        }else {
            if (planPhase) {
                Action a;
                if (e.getSource() == jeu.droiteDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Droite);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.gaucheDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Gauche);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.hautDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Haut);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.basDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Bas);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.braquage){
                    a = new Braquer(this.joueurCourant);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.tirHaut){
                    a = new Tirer(this.joueurCourant,Direction.Haut);
                    this.joueurCourant.ajouterAction(a);

                }

                if (e.getSource() == jeu.tirBas){
                    a = new Tirer(this.joueurCourant,Direction.Bas);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.tirDroit){
                    a = new Tirer(this.joueurCourant,Direction.Droite);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == jeu.tirGauche){
                    a = new Tirer(this.joueurCourant,Direction.Gauche);
                    this.joueurCourant.ajouterAction(a);
                }


            }
        }


    }

    public static void main(String[] args) {

        Train train = new Train(4);
        train.ajouterBandit("ouané");
        train.ajouterBandit("ramy");
        train.ajouterBandit("kelia");

        Fenetre fen = new Fenetre(train);
        Controleur controleur = new Controleur(train,fen,3); // n : nActions

        controleur.lancerJeu();


    }
}
