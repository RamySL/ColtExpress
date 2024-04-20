package Action;

import modele.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TestTir {

    private Bandit tireur;
    private Train train;
    private ComposanteTrain emplacementTireur;

    @Before
    public void initialisation() {

        this.train = new Train(4);
        this.tireur = new Bandit("tireur", 6);

        for (ComposanteTrain empl : train) {
            // on l'ajoute dans la cabine du dernier wagon
            train.ajouterObjetBandit(tireur, empl);
            this.emplacementTireur = empl;
            break;
        }

    }

    @Test
    public void ajoutTir() {
        // on verifie est-ce que l'action de braquage s'ajoute avec succes a la liste d'actions du bandit
        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        assertFalse(this.tireur.getActions().contains(tir)); // on s'assure qu'elle n'existait pas vant l'ajout
        this.tireur.ajouterAction(tir);
        assertTrue(this.tireur.getActions().contains(tir)); // mais qu'elle existe après l'ajout
    }

    /**
     * verifie que le tir fait diminuer le nombre de balles du bandit
     */
    @Test
    public void nbBallesDiminue() {

        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        tir.executer();
        assertEquals(this.tireur.getNbBalles(),5);

    }

    /**
     * verifie que le nombre de balles ne devient pas negatif
     */
    @Test
    public void nbBallesNegatif() {
        // nb balles ne descend en dessous de 0
        this.tireur = new Bandit("test", 0);
        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        tir.executer();
        assertEquals(this.tireur.getNbBalles(),0);

    }

    /**
     * on teste que rien ne change dans l'emplacement vers lequel on vise si on tire sur un bandit qui n'a pas de buttin
     */
    @Test
    public void tireVersBanditSansButtin() {

        Bandit vicitime = new Bandit("victime",6);
        vicitime.setEmplacement(this.emplacementTireur.getVoisin(Direction.Droite));

        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        ArrayList<Butin> buttinEmplacementAvantTir =  vicitime.getEmplacement().getButtins();
        tir.executer();
        assertEquals(buttinEmplacementAvantTir,vicitime.getEmplacement().getButtins());

    }
    /**
     * on regarde si le tir dans la direction gauche dans les cabines touche bien la cible (victime) qui est placé dans
     * le deuxieme wagon (à droite du tireur)
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
     public void  tireDroiteversCibleDansCabine(){
         Bandit victime = new Bandit("victime",6);
         train.ajouterObjetBandit(victime, this.emplacementTireur.getVoisin(Direction.Droite));
         Butin buttinDeVictime = new Bijou();
         victime.ajouterButtin(buttinDeVictime);

         ArrayList<Butin> buttinEmplacementVctime =  victime.getEmplacement().getButtins();

         assertTrue(victime.getButtins().contains(buttinDeVictime));
         assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

         Tirer tir = new Tirer(this.tireur, Direction.Droite);
         tir.executer();

         assertFalse(victime.getButtins().contains(buttinDeVictime));
         assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));

     }
    /**
     * on regarde si le tir dans la direction gauche dans les cabines touche bien la cible (victime) qui est placé dans
     * le dernier wagon a gauche du tireur
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void  tireGaucheversCibleDansCabine(){
        Bandit victime = new Bandit("victime",6);
        this.emplacementTireur = this.emplacementTireur.getVoisin(Direction.Droite);// le tireur est dans le wagon juste à droite du dernier
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime, this.emplacementTireur.getVoisin(Direction.Gauche));
        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime =  victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Gauche);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));

    }

    /**
     * on regarde si le tir dans la direction gauche dans les cabines touche bien la cible (victime) qui est placé dans
     * sur le toit du dernier wagon
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void  tireHautversCibleDansCabine(){
        Bandit victime = new Bandit("victime",6);
        train.ajouterObjetBandit(victime,((Interieur) this.emplacementTireur).getToit());
        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime =  victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Haut);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));

    }

    /**
     * on regarde si le tir dans la direction gauche dans les cabines touche bien la cible (victime) qui est placé dans
     * le dernier wagon avec le tireur
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void  tireBasversCibleDansCabine(){
        Bandit victime = new Bandit("victime",6);
        train.ajouterObjetBandit(victime, this.emplacementTireur);
        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime =  victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Bas);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }
    /**
     * on regarde si le tir dans la droite en etant sur le toit touche bien la cible (victime) qui est placé dans
     * le toit à droite du tireur
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void tireDroiteversCibleSurToit() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = ((Interieur) this.emplacementTireur).getToit();
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime,this.emplacementTireur.getVoisin(Direction.Droite));

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }
    /**
     * on regarde si le tir dans à gauche en etant sur le toit touche bien la cible (victime) qui est placé dans
     * le toit du dernier wagon juste à gauche du tireur
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void tireGaucheversCibleSurToit() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = (((Interieur) this.emplacementTireur).getToit()).getVoisin(Direction.Droite);
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime, this.emplacementTireur.getVoisin(Direction.Gauche));

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Gauche);
        System.out.println(tir.executer());

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }
    /**
     * on regarde si le tir dans vers le haut en ayant le tireur et la cible sur le meme toit touche la cible
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void tireHautversCibleSurToit() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = ((Interieur) this.emplacementTireur).getToit();
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime, this.emplacementTireur);

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Haut);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }
    /**
     * on regarde si le tir dans vers le bas en ayant le tireur sur le toit et la cible à l'interieur en bas de lui touche la cible
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void tireBasversCibleSurToit() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = ((Interieur) this.emplacementTireur).getToit();
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime, ((Toit) this.emplacementTireur).getCabine());

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Bas);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }

    /**
     * on regarde si le tir dans la droite en etant sur le toit et en ayant la victime plusieurs toit à coté touche
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void tireDroiteversSurToitLongueDistance() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = ((Interieur) this.emplacementTireur).getToit();
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime,this.emplacementTireur.getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite));

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }
    /**
     * on regarde si le tir vers la gauche en etant sur le toit et en ayant la victime plusieurs toit vers la gauche touche
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : après le tir on verifie que la victime a perdu son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime contient le buttin qu'elle a perdu
     */
    @Test
    public void tireGaucheSurToitLongueDistance() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = (((Interieur) this.emplacementTireur).getToit()).getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite);
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime,this.emplacementTireur.getVoisin(Direction.Gauche).getVoisin(Direction.Gauche).getVoisin(Direction.Gauche));

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Gauche);
        tir.executer();

        assertFalse(victime.getButtins().contains(buttinDeVictime));
        assertTrue(buttinEmplacementVctime.contains(buttinDeVictime));
    }

    /**
     * on verifie que si le tireur et sa cible sont à l'interieur du train et à une distance de plusieurs wagons
     * alors la balle ne touche pas
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : on verifie que la cible a toujours son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime ne contient tjr pas le buttin que possedée la cible
     */
    @Test
    public void tireDroiteDansCabineLongueDistance() {
        Bandit victime = new Bandit("victime", 6);
        train.ajouterObjetBandit(victime,this.emplacementTireur.getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite));

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Droite);
        tir.executer();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));
    }

    /**
     * on verifie que si le tireur et sa cible sont à l'interieur du train et à une distance de plusieurs wagons
     * alors la balle ne touche pas
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : on verifie que la cible a toujours son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime ne contient tjr pas le buttin que possedée la cible
     */
    @Test
    public void tireGaucheDansCabineLongueDistance() {
        Bandit victime = new Bandit("victime", 6);
        this.emplacementTireur = (this.emplacementTireur).getVoisin(Direction.Droite).getVoisin(Direction.Droite).getVoisin(Direction.Droite);
        this.tireur.setEmplacement(this.emplacementTireur);
        train.ajouterObjetBandit(victime,this.emplacementTireur.getVoisin(Direction.Gauche).getVoisin(Direction.Gauche).getVoisin(Direction.Gauche));

        Butin buttinDeVictime = new Bijou();
        victime.ajouterButtin(buttinDeVictime);

        ArrayList<Butin> buttinEmplacementVctime = victime.getEmplacement().getButtins();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));

        Tirer tir = new Tirer(this.tireur, Direction.Gauche);
        tir.executer();

        assertTrue(victime.getButtins().contains(buttinDeVictime));
        assertFalse(buttinEmplacementVctime.contains(buttinDeVictime));
    }

    /**
     * on verifie que si le tireur et sa cible sont à l'interieur du train et à une distance de plusieurs wagons
     * alors la balle ne touche pas
     * - premiere assertion : on verifie bien que la victime possede le buttin qu'on lui a ajouter
     * - deuxieme : on verifie que son emplacement ne contient pas encore le buttin qu'on lui a ajouté
     * - troiseme : on verifie que la cible a toujours son butin
     * - quatrieme : après le tir on verifie que l'emplacement de la victime ne contient tjr pas le buttin que possedée la cible
     */

    /**
     * remplie le train avec des bandit dans le même emplaement que le tireur
     * @return la liste des bandits ajoutée au train
     */
    public ArrayList<Bandit> remplissagePlusieurBandits(){
        ArrayList<Bandit> listeCibles = new ArrayList<>();
        for (int i = 0; i<4;i++){
            Bandit cible = new Bandit("cible"+(i+1), 6);
            listeCibles.add(cible);
            train.ajouterObjetBandit(cible,this.emplacementTireur);
            Butin buttinCible = new Bijou();
            cible.ajouterButtin(buttinCible);
        }
        return listeCibles;
    }

    /**
     * verifi que si un tir est declenché dans un emplacement où il ya plusieurs bandit un seule d'entre eux est touché
     */
    @Test
    public void tirVersPlusieurBandits() {
        ArrayList<Bandit> listeCibles = this.remplissagePlusieurBandits();
        Tirer tir = new Tirer(this.tireur, Direction.Bas);
        tir.executer();
        int nbBanditTouche = 0;
        for (Bandit b : listeCibles){
            if (b.getButtins().isEmpty()) nbBanditTouche++;
        }
        assertEquals(nbBanditTouche,1);

    }












}
