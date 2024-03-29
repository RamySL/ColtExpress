package modele;


import java.util.Random;

public abstract class Buttin {
    protected int valeur;

    public int getValeur(){return this.valeur;}

}


class Bourse extends Buttin{

    public Bourse (){
        Random rnd = new Random();

        this.valeur = rnd.nextInt(0,501);
    }

    public String toString(){
        return "Bourse";
    }


}

class Magot extends Buttin{
    public Magot(){
        this.valeur = 1000;
    }

    public String toString(){
        return "Magot";
    }
}