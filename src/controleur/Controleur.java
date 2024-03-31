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
                // on varie le joueur avec une periodicité de 3 (plutot le nombre de jr en generale)
                // la formule (n-1)mod(nombre de joueur) (le -1 c'est pour le décalage puisque les indice commence à 0)

                //this.joueurCourant = this.train.getBandits().get((n-1) / this.train.getBandits().size());


//                while (b.lenAction() > 0 ) {
//                    // on doit attendre ici jusqu'a ce qu'il joue
//                    System.out.println("c'est " + b.getSurnom() + " qui execute ");
//                }




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
                    //a = new Tirer()
                    //this.joueurCourant.ajouterAction(a);
                    System.out.println("tir haut appuye");
                }

                if (e.getSource() == ecranJeu.tirBas){
                    //a = new Tirer()
                    //this.joueurCourant.ajouterAction(a);
                    System.out.println("tir bas appuye");
                }

                if (e.getSource() == ecranJeu.tirDroit){
                    //a = new Tirer()
                    //this.joueurCourant.ajouterAction(a);
                    System.out.println("tir droit appuye");
                }

                if (e.getSource() == ecranJeu.tirGauche){
                    //a = new Tirer()
                    //this.joueurCourant.ajouterAction(a);
                    System.out.println("tir gauche appuye");
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
        Controleur controleur = new Controleur(train,e,2);

        //train.ajouterBandit("ramy");
        controleur.lancerJeu();
        //train.ajouterBandit("10Giga");



//        Action depdroit = new SeDeplacer(train.banditQuiJoue(), Direction.Droite);
//        Action depbas = new SeDeplacer(train.banditQuiJoue(), Direction.Bas);
//        Action dephaut = new SeDeplacer(train.banditQuiJoue(), Direction.Haut);
//        Action braquer = new Braquer(train.banditQuiJoue());








    }


}