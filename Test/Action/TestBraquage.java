package Action;

import modele.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;



public class TestBraquage {

    private Bandit bandit;
    private ComposanteTrain emplacementBandit;
    private Braquer braquage;
    private ArrayList<Butin> buttinsBanditAvantBraquage,buttinsEmplacementAvantBraquage;
    @Before
    public void initialisation(){

        Train train = new Train(4);
        train.ajouterBandit("ramy",2);
        this.bandit = train.getBandits().get(0); // verfier que bandit contient vraiment le bandit ajoute avant

        this.buttinsBanditAvantBraquage = this.bandit.getButtins();
        this.buttinsEmplacementAvantBraquage = this.bandit.getEmplacement().getButtins();

        this.emplacementBandit = this.bandit.getEmplacement();

        this.braquage = new Braquer(this.bandit);

    }

    @Test
    // a on s'apuit sur l'hypothese qu'on commence sur le toit et que sur le toit il n'ya pas de buttin
    public void BraquerVide(){
        // !!! pas forcement besoin de faire ajouter action
        // rjouter dans personnage la liste d'action (donc mem le marshall en a ?)
        this.bandit.ajouterAction(this.braquage);
        this.bandit.executer();

        ArrayList<Butin> listeButinEmplacementApres = this.bandit.getEmplacement().getButtins();
        ArrayList<Butin> listeAprèsBraquage = this.bandit.getButtins();

        assertEquals(this.buttinsEmplacementAvantBraquage , listeButinEmplacementApres);
        assertEquals(listeAprèsBraquage , this.buttinsBanditAvantBraquage);
    }
    @Test
    public void ajoutButtinEmplacement(){
        // on s'assure que l'ajout marche avec succes
        Butin butinABraquer = new Bijou();
        this.emplacementBandit.ajouterButin(butinABraquer);

        assertTrue(this.emplacementBandit.getButtins().size() == 1 &&
                this.emplacementBandit.getButtins().contains(butinABraquer));
    }
    @Test
    public void BraquerCabineUnButtin(){
        //il faut qu'on le force dans un emplacement ou il ya des buttin, donc on ajoute un buttin
        Butin butinABraquer = new Bijou();
        this.emplacementBandit.ajouterButin(butinABraquer);

        this.bandit.ajouterAction(this.braquage);
        this.bandit.executer();

        ArrayList<Butin> butinEmplacementApres = this.bandit.getEmplacement().getButtins();
        ArrayList<Butin> butinBanditApres = this.bandit.getButtins();
        // on s'assure que après le braquage l'emplacement ne contient plus le buttin brauqer et que le bandit le possede
        assertTrue( !butinEmplacementApres.contains(butinABraquer) &&
                                        butinBanditApres.contains(butinABraquer));


    }



}
