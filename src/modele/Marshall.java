package modele;

import java.util.ArrayList;
import java.util.Random;
// rajouter une methode check des wagon pour savoir si un bandit est arrivé à son emplacement pour lui tirer dessun ce check se fait dans le controleur
/**
 * marshall se deplace uniquement à l'interieur du train en essayant de chasser les bandits
 */
public class Marshall extends Personnage {
    private final double nervosite;

    /**
     * un marshall est créer avec une nervosité qui représente la probabilité de son déplacement dans le train
     * @param emp
     * @param nervosite entre 0 et 1
     */
    public Marshall(ComposanteTrain emp, double nervosite) {
        super(emp, "Marshall");
        this.nervosite = nervosite;

    }

    /**
     * Deplace le marshall avec une probabilité p (p== nervosité) dans une direction qui a du sense (si il est au millieu le déplace aléatoirement entre gauche et droite)
     * et fait fuire les bandits se trouvant à cette emplacement
     * on modélise le coté aléatoire le faite de tirer un aleatoire n entre [1,10] et que si n appartient à [1,p*10] le marshall se déplace
     * @return feedback
     */
    public String seDeplacer(){

        String feed = "";
        Random rnd = new Random();
        int n = rnd.nextInt(1,10);

        if ( n <= 10*this.nervosite ){
            Action a;
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
        return feed;
    }

    public String tirer (){
        // tire que dans son emplacement et que quand un bandit vient vers lui
        return new Tirer(this, Direction.Bas).executer();
    }

}
