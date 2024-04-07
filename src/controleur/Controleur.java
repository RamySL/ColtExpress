package controleur;
import Vue.EcranJeu;
import modele.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Le but du modèle étant de contenir la logique de l'application (tout ce qui est structure de données ...)
 * Le but de la vue étant de donner une représentation graphique du modèle et defournir des moyens d'interaction pour l'utilisateur (boutton ..)
 * Le controleur vient pour récuperer les evenement generer par les composants d'interaction de la vue et decider comment modifier le modele
 * Donc le controleur est un listener pour les action
 */
public class Controleur implements ActionListener {

    Train train;
    EcranJeu ecranJeu;

    int nbAction;

    boolean actionPhase=false,planPhase=true;
    Bandit joueurCourant;

    int tourne; // pour determiner que le boutton action à été appuer et qu'il faut passer au prochain ensemble d'action à executée

    public Controleur(Train train, EcranJeu e, int n){
        this.train = train;
        this.ecranJeu = e;
        this.nbAction = n;

        this.ecranJeu.liaisonBottonsListener(this);

    }
    // boucle du jeu
    public void lancerJeu() {
        this.ecranJeu.setVisible(true);
        int nbBandit = this.train.getBandits().size();
        // pour l'instant pas de condition d'arret
        while (true) {
            //planification

            // on utilise pas une boucle for each pour eviter la cocurrentmodifError avec la methode fuire de bandit
            for (int i = 0; i <nbBandit; i++){
                this.joueurCourant = this.train.getBandits().get(i); // pour que les boutton vide ce bandit specifiquement
                this.ecranJeu.phase.setText("Phase de planification : c'est le tour à " + this.joueurCourant.getSurnom());

                planPhase = true;
                actionPhase = false;

                while (this.joueurCourant.lenAction() < this.nbAction){
                    // pour l'instant il ya rien à mettre dans cette boucle mais elle necessaire pour attendre que joueur est planifié ces action
                    // on ne peut pas la mettre vide sinon je crois elle est ignoré par le compilateur
                    System.out.print("");
                }

            }

            // action
            this.tourne = 1;
            // le nombre totale d'iteration pour toutes les action des bandit = nbBandit * nbAction
            while (this.tourne <= this.nbAction){
                this.ecranJeu.phase.setText("Phase de d'action");

                planPhase = false;
                actionPhase = true;

            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() == ecranJeu.action && actionPhase) {
            this.train.getMarshall().executer();

            for(Bandit b : this.train.getBandits()){
                b.executer();
            }
            System.out.println(); // pour un affichage plus claire à la console
            this.tourne++;
        }else {
            if (planPhase) {
                Action a;
                if (e.getSource() == ecranJeu.droiteDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Droite);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.gaucheDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Gauche);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.hautDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Haut);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.basDep) {
                    a = new SeDeplacer(this.joueurCourant, Direction.Bas);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.braquage){
                    a = new Braquer(this.joueurCourant);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.tirHaut){
                    a = new Tirer(this.joueurCourant,Direction.Haut);
                    this.joueurCourant.ajouterAction(a);

                }

                if (e.getSource() == ecranJeu.tirBas){
                    a = new Tirer(this.joueurCourant,Direction.Bas);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.tirDroit){
                    a = new Tirer(this.joueurCourant,Direction.Droite);
                    this.joueurCourant.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.tirGauche){
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

        EcranJeu e = new EcranJeu(train);
        Controleur controleur = new Controleur(train,e,3);


        controleur.lancerJeu();


    }


}