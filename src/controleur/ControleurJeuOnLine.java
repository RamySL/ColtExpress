package controleur;

import Vue.EcranFin;
import Vue.Fenetre;
import modele.actions.Action;
import modele.actions.Braquer;
import modele.actions.SeDeplacer;
import modele.actions.Tirer;
import modele.personnages.Bandit;
import modele.personnages.Marshall;
import modele.trainEtComposantes.Train;
import network.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controle tous les evenements pendant le deroulement de la partie
 */

/**
 * - celui qui est entrain de planifier ses actions on lui affiche les actions qu'il a saisies comme d'hab
 * - pour les autres on affiche l'icone de celui qui planifie avec une phrase " joueur est entrain de planifier
 *      - pour passer au prochain un paquet "suivant" est envoyé par le serveur, si il reste des joueurs pour planifié il le font sinon action
 */

/**
 * - Un paquet "Planification" et un autre "Action" que le serveur envoi pour alterner
 * - Un paquet "JoueurCourant" qui contient l'indice dans la liste de celui qui planifie ou execute
 */
public class ControleurJeuOnLine extends ControleurJeu {
    private Client client;
    private final Bandit bandit;
    private boolean actionExecute = false;

    private int indiceBanditCourant = 0;
    private boolean finPartie = false, planPhase = true, actionPhase = false; // change par serveur

    private final int totaleActionsManche = this.nbAction * this.nBandits; // le nombre d'actions que planifie tous les joeurs en une manche
    private int manche = 0;


    /**
     * Intialise le controleur et fait la liason avec les bouttons du jeu pour réagir au evenements
     * initialise une hashMap entre un identifiant et sa musique pour les effets pendant la partie
     * @param train
     * @param fenetre fenetre du jeu qui stock les identifiants de toutes les vues
     * @param nbAction nombre d'actions à planifier pour chaque bandit pendant la phase de planification
     */
    public ControleurJeuOnLine(Train train, Fenetre fenetre, int nbAction,Client client) {
        super(train, fenetre, nbAction);
        this.client = client;
        this.client.setControleurJeu(this);
        this.bandit = this.client.getBandit();
        this.banditCourant = this.client.getBanditCourant();

    }

    public void setActionPhase() {
        indiceBanditCourant = 0;
        this.actionPhase = true;
        this.planPhase = false;
    }

    public void setFinPartie() {
        this.finPartie = true;
    }

    public void prochaineManche() {
        this.manche++;
    }

    public void setPlanPhase() {
        indiceBanditCourant = 0;
        this.actionPhase = false;
        this.planPhase = true;
    }

    public void nextBandit(int indice){
        this.indiceBanditCourant = indice;
    }

    public void sendListePlanififcation() throws IOException {
       this.client.sendListePlanififcation (this.bandit.getActions());
    }

    public void actualiserTrain(Train train){

        this.train = train;
        this.vueJeu.actualiserTrain(this.train);
    }

    public void setAppropriatEnterAction(){
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionPhase) {
                    //bandit.executer();
                    actionExecute = true;

                }
            }
        };

        vueJeu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "action");
        vueJeu.getActionMap().put("action", action);
    }

    public void executerCourant(int indice){
        if (indice == indiceBanditCourant){
            this.train.getBandits().get(indice).executer();
        }else {
            System.out.println("Indice envoyé par le serveur ne correspend pas à celui du client");
        }

    }



    /**
     *  possede la boucle principale du jeu, alterne pour chaque manche entre la phase de planification quand les bandits auronts choisit toutes leurs actions
     * et la phase d'action quand toutes leurs actions ont été executées. quand la boucle est finis elle lance l'ecran de fin.
     * @param nbManches nombre de manche à jouer avant la fin du jeu
     */
    @Override
    public void lancerJeu(int nbManches) {

        while (!finPartie){
            try {
                Thread.sleep(1000); // latence pour attendre des paquets du serveur
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.banditCourant = train.getBandits().get(indiceBanditCourant);
            if (this.banditCourant == this.bandit) {

                this.jeuBindingKeys();
                this.setAppropriatEnterAction();

                if (planPhase) {
                    while (this.bandit.getActions().size() < this.nbAction) {
                        // affichage des actions à celui qui planfie
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        this.sendListePlanififcation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (actionPhase) {
                    while (!this.actionExecute) {
                        System.out.print(""); // sans ça ça déconne
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    try {
                        this.client.actionExecute();
                        this.actionExecute = false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {

                if(!(vueJeu.getActionMap().size() == 0)) vueJeu.getActionMap().clear();

                if (planPhase){
                    System.out.println(this.banditCourant + " est entrain de planifier ");
                }else if (actionPhase){
                    System.out.println(this.banditCourant + " est entrain d'executer ");
                }
            }

        }


    }

    /**
     * gerent les evenements qui proviennent des bouttons de jeu selon que ça soit la phase d'action ou de planification
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     *
     * @param marshall
     */
    private void executionAction(Marshall marshall) {
        this.mapSonsJeu.get("tir").arreter(); // pour que les sons se lance mm si on spam action
        this.mapSonsJeu.get("braquage").arreter();

        this.banditCourant = this.train.getBandits().get(this.nbActionExecute % this.nBandits);
        Action actionAExecuter = this.banditCourant.getActions().peek();

        boolean assezDeBalles = this.banditCourant.getNbBalles() > 0;
        boolean braquageReussie = !this.banditCourant.getEmplacement().getButtins().isEmpty();

        String feed = this.banditCourant.executer();
        this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);

        if (actionAExecuter instanceof Tirer && assezDeBalles){
            this.mapSonsJeu.get("tir").jouer(false);
        }else {
            if (actionAExecuter instanceof Braquer && braquageReussie){
                this.mapSonsJeu.get("braquage").jouer(false);
            }else {
                if (actionAExecuter instanceof SeDeplacer ) {
                    // si bandit va vers marshall il lui tir dessus
                    if (this.banditCourant.getEmplacement().getPersoList().contains(marshall)) {
                        this.mapSonsJeu.get("tir").jouer(false);
                        this.banditCourant.fuir();
                        this.vueJeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(this.banditCourant.getSurnom() +
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
     */
    public void versFinJeu(ArrayList<Bandit> banditsGagnant){

        this.getMapSonsJeu().get("jeuBack").arreter();
        EcranFin ecranFin = new EcranFin(this.fenetre, banditsGagnant,banditsGagnant.getFirst().score(), this.fenetre.getJeuPanel().getMapPersonnageIcone());
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

}
