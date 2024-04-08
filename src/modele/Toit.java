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
        return ((Interieur)voisin).getToit(); // retourne le toit de la cabine voisine

    }

    public String  toString(){
        return "Toit";
    }


}
