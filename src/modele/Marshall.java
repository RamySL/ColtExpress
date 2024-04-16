package modele;

import java.util.ArrayList;
import java.util.Random;

public class Marshall extends Personnage {

    private double nervosite;

    public Marshall(ComposanteTrain emp, double nervosite) {

        super(emp, "Marshall", 1);
        this.nervosite = nervosite;

    }

    public String executer(){
        // execute une action avec une proba de p
        // on modelise ce fait par le faite de tirer n un aleatoire entre [1-10]
        // et que si n appartient à [1-p*10]
        String feed = "";
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
            feed = a.executer();
        }

        ArrayList<Personnage> lstBandit = this.getEmplacement().getPersoList();
        for (int i = 0; i<lstBandit.size(); i++){ // on utilise pas une boucle for each pour eviter la cocurrentmodifError avec la methode fuire de bandit
            Personnage p = lstBandit.get(i);
            if (p instanceof Bandit){
                ((Bandit)p).fuir();
            }
        }

        return feed;


    }


    public double getNervosite() {
        return this.nervosite;
    }
}
