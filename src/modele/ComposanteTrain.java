package modele;

import java.util.ArrayList;
import java.util.Random;

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


    public ArrayList<Buttin> getButtins(){return this.buttins;}

     public Buttin EnleverButinAlea(){
         Random rnd = new Random();
         Buttin butinBraque = this.buttins.get(rnd.nextInt(0,this.buttins.size()));

         this.buttins.remove(butinBraque);
         return butinBraque;
     }
}
