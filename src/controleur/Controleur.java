package controleur;
import Vue.EcranJeu;
import modele.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Controleur(Train train, EcranJeu e, int n){
        this.train = train;
        this.ecranJeu = e;
        this.nbAction = n;

        this.ecranJeu.liaisonBottonsListener(this);

    }
    // boucle du jeu
    public void lancerJeu() {
        this.ecranJeu.setVisible(true);

        // pour l'instant pas de condition d'arret
        while (true) {
            //planification
            while (this.train.banditQuiJoue().lenAction() < this.nbAction  ){
                this.ecranJeu.phase.setText("Phase de planification");
                planPhase = true;
                actionPhase = false;
            }
            // action
            while (this.train.banditQuiJoue().lenAction() > 0 ) {

                this.ecranJeu.phase.setText("Phase de d'action");
                planPhase = false;
                actionPhase = true;
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Bandit b = train.banditQuiJoue();

        if(e.getSource() == ecranJeu.action && actionPhase) {
            this.train.getMarshall().executer();
            b.executer();
        }else {
            if (planPhase) {
                Action a;
                if (e.getSource() == ecranJeu.droiteDep) {
                    a = new SeDeplacer(b, Direction.Droite);
                    b.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.gaucheDep) {
                    a = new SeDeplacer(b, Direction.Gauche);
                    b.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.hautDep) {
                    a = new SeDeplacer(b, Direction.Haut);
                    b.ajouterAction(a);
                }

                if (e.getSource() == ecranJeu.basDep) {
                    a = new SeDeplacer(b, Direction.Bas);
                    b.ajouterAction(a);
                }
            }
        }


    }

    public static void main(String[] args) {
        Train train = new Train(4);
        train.ajouterBandit("ouané");
        train.banditQuiJoue().ajouterButtin(new Bijou());
        EcranJeu e = new EcranJeu(train);
        Controleur controleur = new Controleur(train,e,4);

        //train.ajouterBandit("ramy");
        controleur.lancerJeu();
        //train.ajouterBandit("10Giga");



//        Action depdroit = new SeDeplacer(train.banditQuiJoue(), Direction.Droite);
//        Action depbas = new SeDeplacer(train.banditQuiJoue(), Direction.Bas);
//        Action dephaut = new SeDeplacer(train.banditQuiJoue(), Direction.Haut);
//        Action braquer = new Braquer(train.banditQuiJoue());








    }


}