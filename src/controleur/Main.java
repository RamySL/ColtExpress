package controleur;

import modele.*;

public class Main {

    public static void main(String[] args) {
        int NB_WAGONS = 3; //3 + 1
        String NOM_BANDIT_1 = "ramy";

        Train train = new Train(NB_WAGONS,NOM_BANDIT_1 );
        Bandit bandit = train.getBandit();

        Action deplacementaDroite = new SeDelacer(bandit, Direction.Droite);
        Action deplacementaHaut = new SeDelacer(bandit, Direction.Haut);
        Action deplacementBas = new SeDelacer(bandit,Direction.Bas);

        bandit.ajouterAction(deplacementaDroite);
        bandit.ajouterAction(deplacementaHaut);
        bandit.ajouterAction(deplacementaHaut);
        bandit.ajouterAction(deplacementaDroite);
        bandit.ajouterAction(deplacementBas);
        bandit.ajouterAction(deplacementaDroite);
        bandit.ajouterAction(deplacementaDroite);


        bandit.executer();
        bandit.executer();
        bandit.executer();
        bandit.executer();
        bandit.executer();

    }
}
