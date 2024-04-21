package Action;
import modele.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * toutes les méthodes intermediaires utilisées ont été testées dans le module TestAxiomes.java comme :
 * - la methode Bandit.changerEmplacement() etc
 */
public class TestDeplacement {
    Bandit bandit;
    ComposanteTrain posAvantDeplacement;
    Train train;

    Locomotive locomotive;

    /**
     * le bandit est ajouté au dernierWagon (tout à gauhce)
     */
    @Before
    public void initialisation(){
        this.train = new Train(4);
        this.bandit = new Bandit("test",6);
        for (ComposanteTrain empl : train) {
            // on l'ajoute dans la cabine du dernier wagon
            this.posAvantDeplacement = empl;
            break;
        }
        this.locomotive = (Locomotive) this.posAvantDeplacement.getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite);
        this.train.ajouterObjetBandit(this.bandit,this.posAvantDeplacement);

    }

    /**
     * la suite de 6 testes qui vont suivre vont etre fait pour des deplacement qui ont du sens, exemple pour aller tester
     * le deplacement à gauche on va pas laisser le bandit dans le dernier wagon
     */

    /**
     * teste le deplacement à droite à l'interieur du train
     */
    @Test
    public void deplacementDroiteInterieur(){
        Action dep = new SeDeplacer(this.bandit, Direction.Droite);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        assertNotEquals(this.posAvantDeplacement,this.posAvantDeplacement.getVoisin(Direction.Droite));
        ComposanteTrain posEspere = this.posAvantDeplacement.getVoisin(Direction.Droite);
        assertEquals(nouvellePos, posEspere);

    }
    /**
     * teste le deplacement à gauche à l'interieur du train le bandit
     */
    @Test
    public void deplacementGaucheInterieur(){
        Action dep = new SeDeplacer(this.bandit, Direction.Gauche);
        this.posAvantDeplacement = this.posAvantDeplacement.getVoisin(Direction.Droite);
        this.bandit.changerEmplacement(this.posAvantDeplacement);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        assertNotEquals(this.posAvantDeplacement,this.posAvantDeplacement.getVoisin(Direction.Gauche));
        ComposanteTrain posEspere = this.posAvantDeplacement.getVoisin(Direction.Gauche);
        assertEquals(nouvellePos, posEspere);

    }
    /**
     * teste est-ce que le bandit grimpe bien sur le toit
     */
    @Test
    public void deplacementHautInterieur(){

        Action dep = new SeDeplacer(this.bandit, Direction.Haut);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = ((Interieur) this.posAvantDeplacement).getToit();
        assertEquals(nouvellePos, posEspere);


    }

    /**
     * teste si le badit descend avec succes à l'interieur du train
     */
    @Test
    public void deplacementBasSurToit(){
        Action dep = new SeDeplacer(this.bandit, Direction.Bas);
        this.posAvantDeplacement = ((Interieur)this.posAvantDeplacement).getToit();
        this.bandit.changerEmplacement(this.posAvantDeplacement);
        this.bandit.ajouterAction(dep);
        this.bandit.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = (((Toit) this.posAvantDeplacement).getCabine());
        assertEquals(nouvellePos, posEspere);


    }

    /**
     * teste le deplacement à droite sur le toit
     */
    @Test
    public void deplacementDroiteToit(){
        Action dep = new SeDeplacer(this.bandit, Direction.Droite);
        this.posAvantDeplacement = ((Interieur) this.posAvantDeplacement).getToit();
        this.bandit.changerEmplacement(this.posAvantDeplacement);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        assertNotEquals(this.posAvantDeplacement,this.posAvantDeplacement.getVoisin(Direction.Droite));
        ComposanteTrain posEspere = this.posAvantDeplacement.getVoisin(Direction.Droite);
        assertEquals(nouvellePos, posEspere);

    }

    /**
     * teste le deplacement à gauche sur le toit
     */
    @Test
    public void deplacementGaucheToit(){
        Action dep = new SeDeplacer(this.bandit, Direction.Gauche);
        this.posAvantDeplacement = (((Interieur) this.posAvantDeplacement).getToit()).getVoisin(Direction.Droite);
        this.bandit.changerEmplacement(this.posAvantDeplacement);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        assertNotEquals(this.posAvantDeplacement,this.posAvantDeplacement.getVoisin(Direction.Gauche));
        ComposanteTrain posEspere = this.posAvantDeplacement.getVoisin(Direction.Gauche);
        assertEquals(nouvellePos, posEspere);

    }

    /**
     * les tests qui vont suivre c'est pour tester quand le deplacement est demandé pour une direction qui n'a
     * pas de sens ememple un deplacement à gauche quand on est dans le dernier wagon, le comportement qu'on veut
     * c'est de ne pas changer de position
     */
    @Test
    public void deplacementDroiteLocomotive(){
        Action dep = new SeDeplacer(this.bandit, Direction.Droite);
        this.posAvantDeplacement = this.locomotive;
        this.bandit.changerEmplacement(this.locomotive);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = this.posAvantDeplacement;
        assertEquals(nouvellePos, posEspere);

    }

    @Test
    public void deplacementGaucheDernierWagon(){
        Action dep = new SeDeplacer(this.bandit, Direction.Gauche);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = this.posAvantDeplacement;
        assertEquals(nouvellePos, posEspere);

    }

    @Test
    public void deplacementHautSurToit(){
        Action dep = new SeDeplacer(this.bandit, Direction.Haut);
        this.posAvantDeplacement = (((Interieur) this.posAvantDeplacement).getToit());
        this.bandit.changerEmplacement(this.posAvantDeplacement);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = this.posAvantDeplacement;
        assertEquals(nouvellePos, posEspere);

    }

    @Test
    public void deplacementBasInterieur(){
        Action dep = new SeDeplacer(this.bandit, Direction.Bas);
        this.bandit.ajouterAction(dep);
        dep.executer();

        ComposanteTrain nouvellePos = this.bandit.getEmplacement();
        ComposanteTrain posEspere = this.posAvantDeplacement;
        assertEquals(nouvellePos, posEspere);

    }












}
