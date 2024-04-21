package Action;

import modele.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * tests unitaires de toutes les fonctions sur lesquelles les tests d'actions reposent pour être créé et testé
 */
public class TestAxiomes {
    Bandit bandit;
    Train train;
    DernierWagon dernierWagon;
    Locomotive locomotive;
    @Before
    public void initialisation(){

        this.train = new Train(4);
        this.bandit = new Bandit("test",6);
        for (ComposanteTrain empl : train) {
            // on l'ajoute dans la cabine du dernier wagon
            this.dernierWagon =(DernierWagon) empl;
            break;
        }
        this.locomotive = (Locomotive) this.dernierWagon.getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite);


    }
    /**
     * test que le train est bien créé avec le nombre de wagons spécifié
     */
    @Test
    public void nbWagonRespecte(){
        int n =0;
        for (Interieur c : this.train){
            n++;
        }
        assertEquals(n,4);
    }

    /**
     * le train est bien vide quand il est créé
     */
    @Test
    public void trainVideAlaCreation(){
        assertTrue(this.train.getBandits().isEmpty() && train.getMarshall() == null);
    }

    /**
     * l'ajout de bandit dans le train marche
     */
    @Test
    public void ajoutBanditAtrain (){
        assertFalse(this.train.getBandits().contains(this.bandit));

        this.train.ajouterObjetBandit(this.bandit, this.dernierWagon);

        assertTrue(this.train.getBandits().contains(this.bandit));
    }

    /**
     * on verifie que la composante reçoit le bandit quand il est ajouté dedans
     */
    @Test
    public void ajoutBanditAemplacement(){
        assertTrue(this.dernierWagon.getPersoList().isEmpty() && !this.dernierWagon.getPersoList().contains(this.bandit));

        this.dernierWagon.ajouterPersonnage(this.bandit);

        assertTrue(this.dernierWagon.getPersoList().contains(this.bandit));
    }

    /**
     * on vérifie que le bandit est céer sans actions dans sa liste
     */
    @Test
    public void banditVideAlaCreation(){
       assertTrue(this.bandit.getActions().isEmpty());
    }

    /**
     * on vérifie que l'ajout d'action aux bandits marche
     */
    @Test
    public void ajoutActionAbandit(){
        Action action = new Braquer(this.bandit);
        this.bandit.ajouterAction(action);
        assertTrue(this.bandit.getActions().contains(action));
    }

    /**
     * on s'assure que l'ajout d'un butin à un emplacement marche
     */
    @Test
    public void ajoutButinEmplacement(){
        Butin butin = new Bijou();
        assertFalse(this.dernierWagon.getButtins().contains(butin));
        this.dernierWagon.ajouterButin(butin);
        assertTrue(this.dernierWagon.getButtins().contains(butin));
    }

    /**
     * on teste la methode getVoisin(direction d) qui retourne le wagon voisin dans la direction d par exemple du wagon
     * sur laquelle est appliqué
     */
    @Test
    public void  getVoisin(){
        assertTrue(this.dernierWagon.getVoisin(Direction.Droite) instanceof Wagon);
        assertTrue(this.dernierWagon.getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite) instanceof Locomotive);
    }

    /**
     * on teste par exemple en transferant le bandit dans le wagon à gauche de la locomotive alors qu'il est dans
     * le dernier wagon
     */
    @Test
    public void changerEmplacementBandit(){
        this.train.ajouterObjetBandit(this.bandit,this.dernierWagon);
        ComposanteTrain destination = this.locomotive.getVoisin(Direction.Gauche);
        assertTrue (destination.getPersoList().isEmpty());
        this.bandit.changerEmplacement(destination);
        assertTrue(destination.getPersoList().contains(this.bandit));
    }


}
