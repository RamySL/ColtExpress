package modele;

import java.util.Random;

public class DernierWagon extends Extremite{

    public DernierWagon (Train train){

        super(train);
        Random rnd = new Random();
        genererButtin(rnd.nextInt(1,5));
    }

    public ComposanteTrain getVoisin(Direction d){
        if (d == Direction.Droite) return this.voisin;
        else return this;
    }


}