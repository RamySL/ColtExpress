package controleur;

import Vue.*;
import Vue.Bouttons.Bouttons;
import modele.*;
import modele.actions.Action;
import modele.actions.Braquer;
import modele.actions.SeDeplacer;
import modele.actions.Tirer;
import modele.personnages.Bandit;
import modele.personnages.Marshall;
import modele.personnages.Personnage;
import modele.trainEtComposantes.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controle tous les evenements pendant le deroulement de la partie
 */
public class CotroleurJeu implements ActionListener {
    Train train;
    Jeu vueJeu;
    Fenetre fenetre;
    int nbAction, nBandits;
    boolean actionPhase=false,planPhase=true;
    Bandit joueurCourant;
    Map<String, JouerSon> mapSonsJeu = new HashMap<>();

    int nbActionExecute;

    /**
     * Intialise le controleur et fait la liason avec les bouttons du jeu pour réagir au evenements
     * initialise une hashMap entre un identifiant et sa musique pour les effets pendant la partie
     * @param train
     * @param fenetre fenetre du jeu qui stock les identifiants de toutes les vues
     * @param nbAction nombre d'actions à planifier pour chaque bandit pendant la phase de planification
     */
    public CotroleurJeu(Train train, Fenetre fenetre, int nbAction){

        mapSonsJeu.put("tir", new JouerSon("src/assets/sons/gun-shot.wav"));
        mapSonsJeu.put("braquage", new JouerSon("src/assets/sons/braquage.wav"));
        mapSonsJeu.put("jeuBack", new JouerSon("src/assets/sons/jeuBack.wav"));

        this.train = train;
        this.fenetre = fenetre;
        this.vueJeu = this.fenetre.getJeuPanel();
        this.nbAction = nbAction;
        this.nBandits = this.train.getBandits().size();

        this.vueJeu.liaisonCommandesControleur(this);
        jeuBindingKeys ();

    }

    /**
     *  possede la boucle principale du jeu, alterne pour chaque manche entre la phase de planification quand les bandits auronts choisit toutes leurs actions
     * et la phase d'action quand toutes leurs actions ont été executées. quand la boucle est finis elle lance l'ecran de fin.
     * @param nbManches nombre de manche à jouer avant la fin du jeu
     */
    public void lancerJeu(int nbManches) {
        this.mapSonsJeu.get("jeuBack").jouer(true);

        int totaleActionsManche = this.nbAction * this.nBandits; // le nombre d'actions que planifie tous les joeurs en une manche
        int manche = 0;

        while (manche < nbManches) {
            //planification
            planPhase = true;
            actionPhase = false;

            this.vueJeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase de palinification pour la manche " + (manche+1) + "/" + nbManches);
            this.vueJeu.getCmdPanel().getPhaseFeedPanel().setPlanfication(this.train.getBandits().get(0));
            // concurrentmodifError avec for each
            for (int i = 0; i <this.nBandits; i++){

                this.joueurCourant = this.train.getBandits().get(i);
                if(i != 0){
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualiserPlanificateur(this.joueurCourant);
                }

                while (this.joueurCourant.lenAction() < this.nbAction) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
            // action
            this.vueJeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase d'action pour la manche " + (manche+1) + "/" + nbManches);
            this.vueJeu.getCmdPanel().getPhaseFeedPanel().setAction();

            this.nbActionExecute = 0;
            this.joueurCourant = this.train.getBandits().get(0);

            planPhase = false;
            actionPhase = true;

            Marshall marshall = this.train.getMarshall();
            String feed =  marshall.seDeplacer();
            this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);

            // fuite des bandits si le marshall vient dans le mm emplacement qu'eux
            ArrayList<Bandit> lstBandit = this.train.getMarshall().getEmplacement().getBanditListSauf(marshall);
            while (!lstBandit.isEmpty()){
                lstBandit.get(0).fuir();
                this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(lstBandit.get(0).getSurnom() + " Vient de fuir vers le toit ");
                lstBandit.remove(0);

            }

            while (this.nbActionExecute < totaleActionsManche) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            manche++;
        }
        // fin jeu
        this.versFinJeu();

    }

    /**
     * gerent les evenements qui proviennent des bouttons de jeu selon que ça soit la phase d'action ou de planification
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Marshall marshall = this.train.getMarshall();
        if( (e.getSource() instanceof Bouttons.BouttonAction) && actionPhase) {
            executionAction(marshall);

        }if (planPhase) {
            Action a;

            if (e.getSource() instanceof Bouttons.BouttonDeplacement) {
                a = new SeDeplacer(this.joueurCourant, ((Bouttons.BouttonDeplacement) e.getSource()).getDirection());
                this.joueurCourant.ajouterAction(a);
                this.vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }

            if (e.getSource() instanceof Bouttons.BouttonBraquage){
                a = new Braquer(this.joueurCourant);
                this.joueurCourant.ajouterAction(a);
                this.vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }


            if (e.getSource() instanceof Bouttons.BouttonTir){
                a = new Tirer(this.joueurCourant, ((Bouttons.BouttonTir) e.getSource()).getDirection());
                this.joueurCourant.ajouterAction(a);
                this.vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }


        }

    }

    /**
     *
     * @param marshall
     */
    private void executionAction(Marshall marshall) {
        this.mapSonsJeu.get("tir").arreter(); // pour que les sons se lance mm si on spam action
        this.mapSonsJeu.get("braquage").arreter();

        this.joueurCourant = this.train.getBandits().get(this.nbActionExecute % this.nBandits);
        Action actionAExecuter = this.joueurCourant.getActions().peek();

        boolean assezDeBalles = this.joueurCourant.getNbBalles() > 0;
        boolean braquageReussie = !this.joueurCourant.getEmplacement().getButtins().isEmpty();

        String feed = this.joueurCourant.executer();
        this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);

        if (actionAExecuter instanceof Tirer && assezDeBalles){
            this.mapSonsJeu.get("tir").jouer(false);
        }else {
            if (actionAExecuter instanceof Braquer && braquageReussie){
                this.mapSonsJeu.get("braquage").jouer(false);
            }else {
                if (actionAExecuter instanceof SeDeplacer ) {
                    // si bandit va vers marshall il lui tir dessus
                    if (this.joueurCourant.getEmplacement().getPersoList().contains(marshall)) {
                        this.mapSonsJeu.get("tir").jouer(false);
                        this.joueurCourant.fuir();
                        this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(this.joueurCourant.getSurnom() +
                                " a fuit vers le toit");
                    }
                }
            }
        }
        // execution avant prochaine action
        feed =  marshall.seDeplacer();
        this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);

        // fuite des bandits si le marshall vient dans le mm emplacement qu'eux
        ArrayList<Bandit> lstBandit = this.train.getMarshall().getEmplacement().getBanditListSauf(marshall);
        while (!lstBandit.isEmpty()){
            lstBandit.get(0).fuir();
            this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(lstBandit.get(0).getSurnom() + " Vient de fuir vers le toit ");
            lstBandit.remove(0);

        }
        this.nbActionExecute++;
    }


    /**
     * calcule le gagnant (ou les gagnants) de la partie et l'envoi à l'ecran de fin,
     * change l'affichage vers l'ecran de fin
     */
    private void versFinJeu(){
        ArrayList<Bandit> bandits = this.train.getBandits();
        int scoreMax = 0;
        ArrayList<Bandit>  banditsGagnant = new ArrayList<>();
        for (Bandit b : bandits){
            if (b.score() > scoreMax){
                scoreMax = b.score();
            }
        }
        for (Bandit b : bandits){
            if (b.score() == scoreMax){
                banditsGagnant.add(b);
            }
        }
        this.getMapSonsJeu().get("jeuBack").arreter();
        EcranFin ecranFin = new EcranFin(this.fenetre, banditsGagnant,scoreMax, this.fenetre.getJeuPanel().getMapPersonnageIcone());
        new ControleurFinJeu(ecranFin);
        this.fenetre.ajouterEcranFin(ecranFin);
        this.fenetre.changerVue(this.fenetre.getEcranFinId());

    }

    /**
     *
     * @return hashMap entre un identifiant (String) de son et l'objet correspendant
     */
    public Map<String, JouerSon> getMapSonsJeu(){ return this.mapSonsJeu;}

    /**
     * Ajout de la possibilité de jouer avec les touches du clavier en faisant la liason entre les touche du clavier et les action à effectuer
     * pour le panel du jeu
     */
    public void jeuBindingKeys (){
        AbstractAction deplacementDroite = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new SeDeplacer(joueurCourant, Direction.Droite);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction deplacementHaut = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new SeDeplacer(joueurCourant, Direction.Haut);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction deplacementBas = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new SeDeplacer(joueurCourant, Direction.Bas);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction deplacementGauche = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new SeDeplacer(joueurCourant, Direction.Gauche);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction braquage = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new Braquer(joueurCourant);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction tirDroit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new Tirer(joueurCourant, Direction.Droite);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction tirHaut = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if   (planPhase) {
                    Action a;
                    a = new Tirer(joueurCourant, Direction.Haut);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction tirBas = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new Tirer(joueurCourant, Direction.Bas);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction tirGauche = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (planPhase) {
                    Action a;
                    a = new Tirer(joueurCourant, Direction.Gauche);
                    joueurCourant.ajouterAction(a);
                    vueJeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
                }
            }
        };

        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionPhase) {
                    executionAction(train.getMarshall());
                }
            }
        };


        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "deplacementDroite");
        vueJeu.getActionMap().put("deplacementDroite", deplacementDroite);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "deplacementHaut");
        vueJeu.getActionMap().put("deplacementHaut", deplacementHaut);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "deplacementBas");
        vueJeu.getActionMap().put("deplacementBas", deplacementBas);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "deplacementGauche");
        vueJeu.getActionMap().put("deplacementGauche", deplacementGauche);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed b"), "braquage");
        vueJeu.getActionMap().put("braquage", braquage);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"), "tirHaut");
        vueJeu.getActionMap().put("tirHaut", tirHaut);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "tirBas");
        vueJeu.getActionMap().put("tirBas", tirBas);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"), "tirGauche");
        vueJeu.getActionMap().put("tirGauche", tirGauche);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "tirDroit");
        vueJeu.getActionMap().put("tirDroit", tirDroit);

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "action");
        vueJeu.getActionMap().put("action", action);

    }

}
