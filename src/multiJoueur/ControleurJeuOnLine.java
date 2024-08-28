package multiJoueur;

import Vue.EcranFin;
import Vue.Fenetre;
import controleur.ControleurFinJeu;
import controleur.JouerSon;
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
    private Bandit bandit;
    private final int indiceBandit;

    private boolean actionExecute = false;

    private int indiceBanditCourant;
    private boolean finPartie = false, planPhase = true, actionPhase = false; // change par serveur

    private final int totaleActionsManche = this.nbAction * this.nBandits; // le nombre d'actions que planifie tous les joeurs en une manche
    private int manche = 0;

    private Object lock;
    private boolean packetReceived = true;


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
        this.indiceBandit = this.client.getIndiceBandit();
        this.indiceBanditCourant = this.client.getIndiceBanditCourant();
        this.bandit = train.getBandits().get(indiceBandit);
        this.banditCourant = train.getBandits().get(indiceBanditCourant);

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

    public void executerCourant(int indice, Action action){
        if (indice == indiceBanditCourant && this.bandit == this.banditCourant){
            this.train.getBandits().get(indice).executer();
        }else if (!(this.bandit == this.banditCourant) && indice == indiceBanditCourant){
            this.banditCourant.ajouterAction(this.getCopieAction(action,this.banditCourant));
            this.banditCourant.executer();

        }else {
            System.out.println("Indice envoyé par le serveur ne correspend pas à celui du client");
        }

    }

    public void actualiserBanditEtCourant(Train train){
        this.bandit = train.getBandits().get(this.indiceBandit);
        this.banditCourant = train.getBandits().get(this.indiceBanditCourant);
    }

    /**
     * on envoi l'indice suceptible de provoquer une actualisation d'affichage
     * @param train
     */
    public void actualiserTrain(Train train){
        this.train = train;
        this.actualiserBanditEtCourant(train);
        this.vueJeu.actualiserTrain(this.train);
    }

    public Action getCopieAction (Action action, Bandit bandit){
        Action output;
        switch(action){
            case SeDeplacer seDeplacer -> {
                output =  new SeDeplacer(bandit,seDeplacer.getDirection());
            }

            case Tirer tirer -> {
                output  = new Tirer(bandit,tirer.getDirection());
            }

            case Braquer braquer -> {
                output = new Braquer(bandit);
            }

            case null,default ->{
                output = null;
            }
        }
        return output;
    }



    /**
     *  possede la boucle principale du jeu, alterne pour chaque manche entre la phase de planification quand les bandits auronts choisit toutes leurs actions
     * et la phase d'action quand toutes leurs actions ont été executées. quand la boucle est finis elle lance l'ecran de fin.
     * @param nbManches nombre de manche à jouer avant la fin du jeu
     */
    public void lancerJeu(int nbManches) {
        while (!finPartie){
            // Wait for server response before proceeding
            synchronized (lock) {
                while (!packetReceived) {
                    try {
                        lock.wait(); // Wait until notified
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                packetReceived = false; // Reset the flag
            }

            this.banditCourant = train.getBandits().get(indiceBanditCourant);
            if (this.banditCourant == this.bandit) {

                this.jeuBindingKeys();
                this.setAppropriatEnterAction();

                if (planPhase) {
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase de palinification pour la manche " +
                            (manche+1) + "/" + nbManches);
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().setPlanfication(this.bandit);

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

                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase d'action pour la manche " + (manche+1) + "/" + nbManches +
                            " \ntour de " + this.bandit);
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().setAction();

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

                if(!(vueJeu.getActionMap().size() == 0)) vueJeu.getActionMap().clear(); // pour desactiver les touches (pas son tour)

                if (planPhase){
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase de palinification " +
                            "pour la manche " + (manche+1) + "/" + nbManches);
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().setPlanfication(this.banditCourant);
                }else if (actionPhase){
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase d'action pour la manche " + (manche+1) + "/" + nbManches +
                            " \ntour de " + this.banditCourant);
                    this.vueJeu.getCmdPanel().getPhaseFeedPanel().setAction();

                }
            }

        }

    }

    public void notifyGamePacketReceived() {
        synchronized (lock) {
            this.packetReceived = true;
            lock.notify(); // Notify the waiting thread to continue execution
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

    /**
     */
    public void versFinJeu(ArrayList<Integer> banditsGagnantIndices, int scoreMax){

        ArrayList<Bandit> banditsGagnants = new ArrayList<>();
        for (int i : banditsGagnantIndices) {
            banditsGagnants.add(this.train.getBandits().get(i));
        }

        this.getMapSonsJeu().get("jeuBack").arreter();
        EcranFin ecranFin = new EcranFin(this.fenetre, banditsGagnants,scoreMax, this.fenetre.getJeuPanel().getMapPersonnageIcone());
        new ControleurFinJeu(ecranFin,true);
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
