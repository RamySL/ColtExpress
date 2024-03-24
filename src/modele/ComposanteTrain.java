package modele;

import java.util.ArrayList;

 public abstract class ComposanteTrain {
    Train train;

    protected ArrayList<Personnage> persoList = new ArrayList<>();
    protected ArrayList<Buttin> buttins = new ArrayList<>();

    public ComposanteTrain(Train train) {
        this.train = train;
    }

    public Train getTrain() {
        return this.train;
    }

    public abstract ComposanteTrain getVoisin(Direction d);

    public void ajouterPersonnage (Personnage p){
        this.persoList.add(p);
    }

    public ArrayList<Personnage> getPersoList(){return this.persoList;}
}
