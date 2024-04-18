package controleur;

import Vue.*;
import Vue.Bouttons.BouttonsJeu;
import modele.*;
import modele.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CotroleurJeu implements ActionListener {
    Train train;
    Jeu jeu;
    Fenetre fenetre;
    int nbAction, nJoueurs;

    boolean actionPhase=false,planPhase=true;
    Bandit joueurCourant;

    Map<String, PlaySound> mapSonsJeu = new HashMap<>();

    int tourne; // pour determiner que le boutton action à été appuer et qu'il faut passer au prochain ensemble d'action à executée

    public CotroleurJeu(Train train, Fenetre fenetre, int n){

        mapSonsJeu.put("tir", new PlaySound("src/assets/sons/gun-shot.wav"));
        mapSonsJeu.put("braquage", new PlaySound("src/assets/sons/braquage.wav"));
        mapSonsJeu.put("jeuBack", new PlaySound("src/assets/sons/jeuBack.wav"));

        this.train = train;
        this.fenetre = fenetre;
        this.jeu = this.fenetre.getJeuPanel();
        this.nbAction = n;
        this.nJoueurs = this.train.getBandits().size(); // le nombre de jr doit etre donnée en pramatere d'une classe

        this.jeu.liaisonCommandesControleur(this);

    }
    // boucle du jeu !!! PROBELEME AVEC LA GESTIONS DES THREADS
    public void lancerJeu(int nbManches) {
        this.mapSonsJeu.get("jeuBack").jouer(true);
        int nbBandit = this.train.getBandits().size();
        // Exemple d'utilisation de SwingUtilities.invokeLater() pour mettre à jour l'interface utilisateur


        // pour l'instant pas de condition d'arret
        int manche = 0;
        while (manche < nbManches) {
            //planification
            this.jeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase de palinification pour la manche " + (manche+1) + "/" + nbManches);
            this.jeu.getCmdPanel().getPhaseFeedPanel().setPlanfication(this.train.getBandits().get(0)); //init
            // on utilise pas une boucle for each pour eviter la cocurrentmodifError avec la methode fuire de bandit
            for (int i = 0; i <nbBandit; i++){

                this.joueurCourant = this.train.getBandits().get(i); // pour que les boutton vide ce bandit specifiquement
                if(i != 0){
                    this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualiserPlanificateur(this.joueurCourant);
                }

                //this.jeu.phase.setText("Phase de planification : c'est le tour à " + this.joueurCourant.getSurnom());
                //System.out.println("tour de " + this.joueurCourant.getSurnom());

                planPhase = true;
                actionPhase = false;

                while (this.joueurCourant.lenAction() < this.nbAction){
                    // pour l'instant il ya rien à mettre dans cette boucle mais elle necessaire pour attendre que joueur est planifié ces action
                    // on ne peut pas la mettre vide sinon je crois elle est ignoré par le compilateur
                    System.out.print("");
                }

            }
            this.jeu.getCmdPanel().getPhaseFeedPanel().actuPhase("Phase d'action pour la manche " + (manche+1) + "/" + nbManches);
            this.jeu.getCmdPanel().getPhaseFeedPanel().setAction();

            // action
            this.tourne = 0;
            // le nombre totale d'iteration pour toutes les action des bandit = nbBandit * nbAction
            this.joueurCourant = this.train.getBandits().get(0);
            //this.jeu.phase.setText("Phase de d'action " + this.joueurCourant.getSurnom());
            while (this.tourne < this.nbAction * this.nJoueurs ){ // optimise
                System.out.print("");
                planPhase = false;
                actionPhase = true;

            }
            manche++;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Marshall marshall = this.train.getMarshall();
        if( (e.getSource() instanceof BouttonsJeu.BouttonAction) && actionPhase) {
            this.mapSonsJeu.get("tir").arreter(); // pour que les sons se lance mm si on spam action
            this.mapSonsJeu.get("braquage").arreter();
            String feed =  marshall.seDeplacer(); // l'actions est executer et renvoi un feedback

            if (feed != ""){ // les bandits fuits
                // ça veut dire le marshall s'est déplacé et a donné un feedback sur son deplacement
                // dans ce cas les bandit qui sont sur le nouvel emplacement fuit
                // peut etre c'est le controleur qui fait fuir les bandit
                ArrayList<Bandit> lstBandit = marshall.getEmplacement().getBanditListSauf(marshall);
                while (!lstBandit.isEmpty()){
                    System.out.println(lstBandit);
                    lstBandit.get(0).fuir();
                    lstBandit.remove(0);
                }
            }
            this.jeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);
            // le principe c'est qu'on veut executer la premiere action du premier bandit ensuite passer
            // à l apremiere action du deuxieme bandit et après quand on arrive au dernier bandit on doit reotuner
            // au premeir et ainsi de suite, il ya une periodicité en le nombre de bandit, qui naturellement traduite par
            // l'opération de modulo
            this.joueurCourant = this.train.getBandits().get(this.tourne % this.nJoueurs);
            Action actionAExecuter = this.joueurCourant.getActions().peek();

            boolean assezDeBalles = this.joueurCourant.getNbBalles() > 0;
            boolean braquageReussie = !this.joueurCourant.getEmplacement().getButtins().isEmpty();

            feed = this.joueurCourant.executer();
            this.jeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(feed);

            if (actionAExecuter instanceof Tirer && assezDeBalles){
                this.mapSonsJeu.get("tir").jouer(false);
            }else {
                if (actionAExecuter instanceof Braquer && braquageReussie){
                    this.mapSonsJeu.get("braquage").jouer(false);
                }else {

                    if (actionAExecuter instanceof SeDeplacer) {
                        if (this.joueurCourant.getEmplacement().getPersoList().contains(marshall)) { // marsall tire quand un bandit arrive sur lui

                            this.jeu.getCmdPanel().getPhaseFeedPanel().getFeedActionPanel().ajoutFeed(marshall.tirer());
                            this.mapSonsJeu.get("tir").jouer(false);
                            new SeDeplacer(this.joueurCourant, Direction.Haut).executer();
                        }
                    }
                }
            }

            // on affiche le prchain qui va executer
//            Bandit bProchain = this.train.getBandits().get((this.tourne + 1) % this.nJoueurs);
//            //this.jeu.phase.setText("Phase de d'action " + bProchain.getSurnom());

            this.tourne++;

        }if (planPhase) {
            Action a;

            if (e.getSource() instanceof BouttonsJeu.BouttonDeplacement) {
                a = new SeDeplacer(this.joueurCourant, ((BouttonsJeu.BouttonDeplacement) e.getSource()).getDirection());
                this.joueurCourant.ajouterAction(a);
                this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }

            if (e.getSource() instanceof BouttonsJeu.BouttonBraquage){
                a = new Braquer(this.joueurCourant);
                this.joueurCourant.ajouterAction(a);
                this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }


            if (e.getSource() instanceof BouttonsJeu.BouttonTir){
                a = new Tirer(this.joueurCourant, ((BouttonsJeu.BouttonTir) e.getSource()).getDirection());
                this.joueurCourant.ajouterAction(a);
                this.jeu.getCmdPanel().getPhaseFeedPanel().getPlanificationPanel().actualisePlanfication(a.toString());
            }


        }


    }

    public Map<String,PlaySound> getMapSonsJeu(){ return this.mapSonsJeu;}

}
