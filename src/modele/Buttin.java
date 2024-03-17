package modele;


import java.util.Random;

public abstract class Buttin {
    protected int valeur;

}


class Bourse extends Buttin{

    public Bourse (){
        Random rnd = new Random();

        this.valeur = rnd.nextInt(0,501);
    }


}

class Bijou extends Buttin{
    public Bijou(){
        this.valeur = 500;
    }
}

class Magot extends Buttin{
    public Magot(){
        this.valeur = 1000;
    }
}