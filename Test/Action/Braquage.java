package Action;

import modele.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;



public class Braquage {

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

    // on verifie que la liste du bandit à augmenter et que la liste dans les wagon à diminuer

    // verifier le type perdu ?

    // si il ya rien on verifie qu'on a rien de plus
     @Test
        public void ajoutAction (){
            // on verifie est-ce que l'action de braquage s'ajoute avec succes a la liste d'actions du bandit
            assertFalse(this.bandit.getActions().contains(this.braquage)); // on s'assure qu'elle n'existait pas vant l'ajout
            this.bandit.ajouterAction(this.braquage);
            assertTrue(this.bandit.getActions().contains(this.braquage)); // mais qu'elle existe après l'ajout
        }
    @Test
    public void emplecementVide(){
        // elle est fait parceque le la methode suivante n'a du sens que si l'emplacement es vide
        assertTrue(this.emplacementBandit.getButtins().isEmpty());
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
