package modele;

public abstract class Extremite extends Interieur{

    Interieur voisin;

    public Extremite(Train train){
        super ( train);

    }

    public void ajouterWagon(Interieur wagonVois){
        this.voisin = wagonVois;
    }

}

