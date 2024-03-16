package modele;

import java.util.ArrayList;
import java.util.Random;

public class Train {

    private final int nWagons;
    private ComposanteTrain [] composantes;
    private Bandit bandit;



    public Train (int n, String nomBandit) {
        this.nWagons = n;
        // init avec une seule locmotive
        this.composantes = new ComposanteTrain[n+1]; // n wagon + 1 loco



        Random rnd = new Random();

        for (int i = 0; i<n; i++){
            int nbButtins = rnd.nextInt(1,5);
            this.composantes[i] = new Wagon(i, this,nbButtins);
        }
        this.composantes[n] = new Locomotive(n,this, new Magot());

        // on le met sur le toit du premier wagon
        this.bandit = new Bandit (this.composantes[0],nomBandit);
    }

    public Bandit getBandit(){
        return this.bandit;
    }

    public ComposanteTrain[] getComposantes (){return this.composantes;}
}

abstract class ComposanteTrain {
    int position; // dans le train
    Train train;

    ArrayList<Buttin> buttins = new ArrayList<>();

    public ComposanteTrain(int pos, Train train) {
        this.position = pos;
        this.train = train;
    }

    public int getPosition() { return this.position;}

    public Train getTrain(){ return this.train;}
}

abstract class Cabine extends ComposanteTrain{

    Toit toit;
    public Cabine (int pos, Train train){
        super (pos, train);
        this.toit = new Toit(this.position, this.train, this);
    }

    public Toit getToit(){return this.toit;}
}

class Toit extends ComposanteTrain {
    Cabine cabine; // la cabine du toit

    public Toit(int pos, Train train, Cabine cabine) {
        super(pos, train);
        this.cabine = cabine;
    }

    public Cabine getCabine() {
        return this.cabine;
    }

}
class Wagon extends Cabine {

    public Wagon(int pos, Train train, int nbButtin) {
        super(pos, train);

        // remplissage du wagon avec les buttins
        Random rnd = new Random();

        for (int i = 0; i < nbButtin; i++) {
            int rndVal = rnd.nextInt(2);

            switch (rndVal) {
                case 0:

                    buttins.add(new Bourse());
                    break;

                case 1:
                    buttins.add(new Bijou());
                    break;
            }
        }

    }



}

class Locomotive extends Cabine {

    Magot magot;

    public Locomotive(int pos,Train train, Magot magot) {
        super(pos, train);
        this.magot = magot;
    }
}
