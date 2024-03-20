package modele;

import java.util.Random;

public abstract class Interieur extends ComposanteTrain {
    private Toit toit;

    public Interieur(Train train) {
        super(train);
        this.toit = new Toit(train, this);

    }

    public Toit getToit() {
        return this.toit;
    }

    public abstract void ajouterWagon(Interieur w);

    public void genererButtin(int nbButtin) {
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
