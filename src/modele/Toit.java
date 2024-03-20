package modele;

public class Toit extends ComposanteTrain{
    Interieur cabine; // la cabine du toit

    public Toit(Train train,Interieur cabine){
        super (train);
        this.cabine = cabine;
    }

    public Interieur getCabine(){ return this.cabine;}


    public ComposanteTrain getVoisin(Direction d){

        ComposanteTrain voisin = this.cabine.getVoisin(d); // on recupere la CABINE voisine

        if (voisin != null){
            // on est sur que c'est un objet Interieur
            return ((Interieur)voisin).getToit();
        }else {
            return null;
        }

    }


}
