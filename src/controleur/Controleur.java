package controleur;
import Vue.EcranJeu;
import modele.*;

public class Controleur {

    Train train;
    EcranJeu ecranJeu;

    public Controleur(Train train, EcranJeu e){
        this.train = train;
        this.ecranJeu = e;

    }
    // boucle du jeu
    public void lancerJeu(){
        this.ecranJeu.setVisible(true);
    }
    public static void main(String[] args) {
        Train train = new Train(4);


        //train.ajouterBandit("ramy");
        train.ajouterBandit("ouané");
        //train.ajouterBandit("10Giga");



        Action depdroit = new SeDeplacer(train.banditQuiJoue(), Direction.Droite);
        Action depbas = new SeDeplacer(train.banditQuiJoue(), Direction.Bas);
        Action dephaut = new SeDeplacer(train.banditQuiJoue(), Direction.Haut);
        Action braquer = new Braquer(train.banditQuiJoue());

        train.banditQuiJoue().ajouterButtin(new Bijou());
        train.banditQuiJoue().ajouterAction(depbas);

        EcranJeu e = new EcranJeu(train);
        Controleur controleur = new Controleur(train,e);

        controleur.lancerJeu();






    }


}