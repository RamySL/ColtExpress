package modele;

import java.util.ArrayList;
import java.util.Random;

public class Train {

    private final int nWagons;

    private DernierWagon last;
    private Locomotive first;
    private Bandit bandit;



    public Train (int n, String nomBandit) {
        assert n >= 2;

        last = new DernierWagon(this);
        this.first = new Locomotive(this, new Magot());
        this.nWagons = n;

        Interieur courant = last;

        for (int i = 0; i<n-2; i++){ // n-2 pcq on a deja créer last et first

            Wagon prochain = new Wagon(this,courant);
            courant.ajouterWagon(prochain);

            courant = prochain;
        }

        this.first.ajouterWagon(courant);

        this.bandit = new Bandit(last, nomBandit);



    }
    public Bandit getBandit(){
        return this.bandit;
    }

    public int getSize(){ return this.nWagons;}
}

class Wagon extends Interieur {
    Interieur cabineGauche, CabineDroite;

    public Wagon(Train train, Interieur cabineGauche) {


        super(train);
        this.cabineGauche = cabineGauche;

        Random rnd = new Random();
        genererButtin(rnd.nextInt(1,5));

    }


    public void ajouterWagon (Interieur cab){
        this.CabineDroite = cab;
    }

    public ComposanteTrain getVoisin(Direction d){
        if (d == Direction.Gauche) return this.cabineGauche;
        else return this.CabineDroite;
    }
}

