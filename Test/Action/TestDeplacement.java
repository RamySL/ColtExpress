package Action;

import modele.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;



/**
 Pour compiler et exécuter en ligne de commande, après compilation de Bolide.java
 javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar BolideTest.java
 java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore BolideTest
 */

public class TestDeplacement {
    Bandit bandit;
    ComposanteTrain positionCourante;
    @Before
    // initialisation
    public void initialisation(){

        /*Train train = new Train(4,1);
        train.ajouterBandit("Ramy");
        this.bandit = train.getBandits().get(0); //new Bandit(train.getLast(), "ramy");*/

//        Train train = new Train(4,"ramy");
//
//
//        this.bandit = train.getBandit();
        this.positionCourante = this.bandit.getEmplacement();

    }
    @Test
    public void deplacementDroiteInterieur(){
        Action dep = new SeDeplacer(this.bandit, Direction.Droite);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = this.positionCourante.getVoisin(Direction.Droite);

        assertEquals(nouvellePos, posEspere);


    }
    @Test
    public void deplacementGaucheInterieur(){
        Action dep = new SeDeplacer(this.bandit, Direction.Gauche);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = this.positionCourante.getVoisin(Direction.Gauche);

        assertEquals(nouvellePos, posEspere);

    }
    @Test
    public void deplacementHaut(){

        Action dep = new SeDeplacer(this.bandit, Direction.Haut);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();


        ComposanteTrain nouvellePos = this.bandit.getEmplacement();

        if (this.positionCourante instanceof Toit) assertEquals(nouvellePos, this.positionCourante); // si on était sur le toit alors on doit pas changer de place
        else {
            ComposanteTrain posEspere = ((Interieur) this.positionCourante).getToit();
            assertEquals(nouvellePos, posEspere);
        }

    }
    @Test
    public void deplacementBas(){
        Action dep = new SeDeplacer(this.bandit, Direction.Bas);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();

        if( this.positionCourante instanceof Toit) {

            ComposanteTrain posEspere = (((Toit) this.positionCourante).getCabine());
            assertEquals(nouvellePos, posEspere);
        }else {
            assertEquals(nouvellePos, this.positionCourante); // on verifie qu'on a pas changer de place
        }

    }








}
