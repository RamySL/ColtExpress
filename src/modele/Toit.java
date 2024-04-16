package modele;

/**
 * Toit d'un Objet Interieur
 */
public class Toit extends ComposanteTrain{
    private Interieur cabine;

    /**
     *
     * @param train train
     * @param cabine la cabine dont this est le toit
     */
    public Toit(Train train,Interieur cabine){
        super (train);
        this.cabine = cabine;
    }

    public Interieur getCabine(){ return this.cabine;}

    /**
     * retourne le toit voisin dans la direction d si elle est appropriée sinon return this
     * @param d
     * @return toit voisin
     */
    public ComposanteTrain getVoisin(Direction d){

        ComposanteTrain voisin = this.cabine.getVoisin(d); // on recupere la CABINE voisine
        return ((Interieur)voisin).getToit(); // retourne le toit de la cabine voisine

    }

    public String  toString(){
        return "Toit";
    }


}
