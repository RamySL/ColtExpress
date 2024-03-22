package modele;

public class Locomotive extends Extremite {
    Magot magot;

    public Locomotive(Train train, Magot magot) {
        super(train);
        this.magot = magot;
    }

    @Override
    public void genererButtin(int nbButtin) {
        this.buttins.add(magot);
    }

    public ComposanteTrain getVoisin(Direction d) {
        if (d == Direction.Gauche) return this.voisin;
        else return this;
    }


}
