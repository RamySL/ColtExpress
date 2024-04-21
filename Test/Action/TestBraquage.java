package Action;

import modele.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * toutes les méthodes intermediaires utilisées ont été testées dans le module TestAxiomes.java
 */

/**
 * il n'ya pas besoin de tester specifiquement le braquage sur le toit parceque on a fait le teste sur un ComposanteTrain
 * et le toit est un ComposanteTrain
 */
public class TestBraquage {
    Train train;
    private Bandit bandit;
    private ComposanteTrain emplacementBandit;

    Locomotive locomotive;
    private Braquer braquage;
    @Before
    public void initialisation(){

        this.train = new Train(4);
        this.bandit = new Bandit("test",6);
        for (ComposanteTrain empl : train) {
            // on l'ajoute dans la cabine du dernier wagon
            this.emplacementBandit = empl;
            break;
        }
        this.locomotive = (Locomotive) this.emplacementBandit.getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite);
        this.train.ajouterObjetBandit(this.bandit,this.emplacementBandit);
        this.braquage = new Braquer(this.bandit);
        this.bandit.ajouterAction(this.braquage);

    }

    /**
     * teste que rien ne change si on braque dans un emplacement vide
     */
    @Test
    public void BraquerVide(){
        this.emplacementBandit.getButtins().clear();
        ArrayList<Butin> butinsBandit = this.bandit.getButtins();
        ArrayList<Butin> butinsEmplacement= this.emplacementBandit.getButtins();

        assertTrue(butinsBandit.isEmpty());
        assertTrue(butinsEmplacement.isEmpty());

        this.bandit.executer();

        assertTrue(butinsBandit.isEmpty());
        assertTrue(butinsEmplacement.isEmpty());
    }

    /**
     * on teste que si il ya un seule butin dans son emplacement à l'interieur du train alors il le prend lui
     * on force ça en vidant son emplacement de butin et en ajoutant un butin
     */
    @Test
    public void BraquerUnButtin(){
        this.emplacementBandit.getButtins().clear();
        Butin butinABraquer = new Bijou();
        this.emplacementBandit.ajouterButin(butinABraquer);

        ArrayList<Butin> butinsBandit = this.bandit.getButtins();
        ArrayList<Butin> butinsEmplacement= this.emplacementBandit.getButtins();

        assertTrue( butinsEmplacement.contains(butinABraquer) &&
                !butinsBandit.contains(butinABraquer));

        this.bandit.executer();

        // on s'assure que après le braquage l'emplacement ne contient plus le buttin brauqer et que le bandit le possede
        assertTrue( !butinsEmplacement.contains(butinABraquer) &&
                butinsBandit.contains(butinABraquer));



    }

    /**
     * on s'assure que si il est dans un emplacement avec plusiuers butins alors il en prend qu'un seule
     * on remplie pour cela son emplacement de butins
     */
    @Test
    public void BraquerPlusieursButtin(){
        this.emplacementBandit.getButtins().clear();
        this.remplireButins(this.emplacementBandit);

        ArrayList<Butin> butinsBandit = this.bandit.getButtins();
        ArrayList<Butin> butinsEmplacement= this.emplacementBandit.getButtins();

        assertEquals(butinsEmplacement.size(),5);
        assertTrue(butinsBandit.isEmpty());

        this.bandit.executer();

        assertEquals(butinsEmplacement.size(),4);
        assertEquals(butinsBandit.size(),1);



    }
    public void remplireButins (ComposanteTrain emplacement){
        for (int i = 0;i<5;i++){
            emplacement.ajouterButin(new Bijou());
        }
    }



}
