package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Train implements Iterable <ComposanteTrain>{

    private final int nWagons;

    private DernierWagon last;
    private Locomotive first;
    private ArrayList<Bandit> bandits = new ArrayList<>();
    private Marshall marshall;

    public Train (int n) {
        assert n >= 2;

        last = new DernierWagon(this);
        this.first = new Locomotive(this);
        this.nWagons = n;

        Interieur courant = last;

        for (int i = 0; i<n-2; i++){ // n-2 pcq on a deja créé last et first

            Wagon prochain = new Wagon(this,courant);
            courant.ajouterWagon(prochain);

            courant = prochain;
        }

        courant.ajouterWagon(this.first);
        this.first.ajouterWagon(courant);

        this.marshall = new Marshall(first,0.3);



    }
//    public Bandit getBandit(){
//        return this.bandit;
//    }

    public int getSize(){ return this.nWagons;}

    public void ajouterBandit(String surnom){
        // ajout dans une position aleatoire d'un bandit
        Random rnd = new Random();
        int pos = rnd.nextInt(0,this.nWagons); // num du toit du bandit
        int i =0;
        for (ComposanteTrain c : this){
            if(i == pos){
                this.bandits.add(new Bandit(((Interieur)c).getToit(),surnom));
            }
            i++;
        }
    }

    public Bandit banditQuiJoue(){
        return this.bandits.get(0);
    }

    public ArrayList<Bandit> getBandits(){return this.bandits;}
    public Marshall getMarshall(){return this.marshall;}


    @Override
    public Iterator<ComposanteTrain> iterator() {
        return new IterateurTrain();
    }

    public class IterateurTrain implements Iterator<ComposanteTrain> {

        /* l'iterateur parcourt le train du dernier wagon jusqu'a la locomotive */
        ComposanteTrain last;
        ComposanteTrain composanteCourante;

        boolean end = false; // pour determiner la fin de l'iteration
        public IterateurTrain (){
            this.last = Train.this.last;
            this.composanteCourante = last;
        }
        @Override
        public boolean hasNext() {
            return !(this.composanteCourante instanceof Locomotive) || !end;
        }

        @Override
        public ComposanteTrain next() {
            ComposanteTrain tmp = this.composanteCourante;
            this.composanteCourante = this.composanteCourante.getVoisin(Direction.Droite);
            if ( tmp instanceof Locomotive) end = true;
            return  tmp;
        }
    }
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

    public String toString (){
        return "Wagon--";
    }
}

