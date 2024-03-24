package controleur;

import modele.*;

public class Main {

    public static void main(String[] args) {
        int NB_WAGONS = 7; //3 + 1
        String NOM_BANDIT_1 = "ramy";

        Train train = new Train(NB_WAGONS,NOM_BANDIT_1 );
        Bandit bandit = train.getBandit();
        System.out.println(bandit.getEmplacement().getPersoList().get(0));
        for( ComposanteTrain c : train){
            System.out.print(c);

        }

        Action deplacementaDroite = new SeDeplacer(bandit, Direction.Droite);
        Action deplacementaHaut = new SeDeplacer(bandit, Direction.Haut);
        Action deplacementBas = new SeDeplacer(bandit,Direction.Bas);

        bandit.ajouterAction(deplacementaDroite);
        bandit.ajouterAction(deplacementaHaut);
        bandit.ajouterAction(deplacementaHaut);
        bandit.ajouterAction(deplacementaDroite);
        bandit.ajouterAction(deplacementBas);
        bandit.ajouterAction(deplacementaDroite);
        bandit.ajouterAction(deplacementaDroite);


        bandit.executer();



    }
}
