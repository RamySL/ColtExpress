package modele;

public class Locomotive extends Extremite {


    public Locomotive(Train train) {
        super(train);
        genererButtin(1);
    }

    @Override
    public void genererButtin(int nbButtin) {
        this.buttins.add(new Magot());
    }

    public ComposanteTrain getVoisin(Direction d) {
        if (d == Direction.Gauche) return this.voisin;
        else return this;
    }

    public String toString (){
        return "Locomotive";
    }


}
