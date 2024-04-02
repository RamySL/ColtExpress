package modele;

import java.util.ArrayList;
import java.util.Random;

public class Marshall extends Personnage {

    private double nervosite;

    public Marshall(ComposanteTrain emp, double nervosite) {

        super(emp, "Marshall");
        this.nervosite = nervosite;

    }

    public void executer(){
        // execute une action avec une proba de p
        // on modelise ce fait par le faite de tirer n un aleatoire entre [1-10]
        // et que si n appartient à [1-p*10]

        Random rnd = new Random();
        int n = rnd.nextInt(1,10);

        if ( n <= 10*this.nervosite ){
            Action a;
            // on choisit encore aleatoirement soit de tirer soit de se deplacer
            if (this.getEmplacement() instanceof Locomotive){ a = new SeDeplacer(this, Direction.Gauche);}
            else {
                if (this.getEmplacement() instanceof DernierWagon) {
                    a = new SeDeplacer(this,Direction.Droite);
                }
                else{
                    int alea = rnd.nextInt(0,2);
                    if (alea == 0){
                        a = new SeDeplacer(this, Direction.Gauche);
                    }else{
                        a = new SeDeplacer(this, Direction.Droite);
                    }
                }
            }
            a.executer();
        }

        ArrayList<Personnage> lstBandit = this.getEmplacement().getPersoList();
        for (Personnage p : lstBandit){ // ICI ERREUR
            if (p instanceof Bandit){
                //((Bandit)p).fuir();
            }
        }


    }


    public double getNervosite() {
        return this.nervosite;
    }
}
