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


    public ArrayList<Bandit>getBanditList(Personnage courant){ // return la liste de bandit sans le bandit courant
        ArrayList<Bandit>banditList = new ArrayList<>();
        for(Personnage b : this.persoList){
            if (b instanceof Bandit) {
                if (b.hashCode() != courant.hashCode()) {
                    banditList.add((Bandit)b);
                }
            }
        }
        return banditList;
    }

    public void ajouterButin(Buttin b) { this.buttins.add(b);}

    public ArrayList<Buttin> getButtins(){return this.buttins;}

    public Bandit getBanditAlea(Personnage courant) {
        //!!!la liste des bandit doit etre verifiée c'est a dire pas vide
        Random rnd = new Random();
        Bandit b= this.getBanditList(courant).get(rnd.nextInt(0, getBanditList(courant).size()));
        return b;
    }

    public Buttin EnleverButinAlea(){
        if (!this.buttins.isEmpty()){
            Random rnd = new Random();
            Buttin butinBraque = this.buttins.get(rnd.nextInt(0,this.buttins.size()));

            this.buttins.remove(butinBraque);
            return butinBraque;}

        return null;
    }
}
