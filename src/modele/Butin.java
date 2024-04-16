package modele;


import java.util.Random;

public abstract class Butin {
    protected int valeur;
    public int getValeur(){return this.valeur;}

}


class Bourse extends Butin {

    public Bourse (){
        Random rnd = new Random();

        this.valeur = rnd.nextInt(0,501);
    }

    public String toString(){
        return "Bourse";
    }


}

class Magot extends Butin {
    public Magot(){
        this.valeur = 1000;
    }
    public String toString(){
        return "Magot";
    }
}