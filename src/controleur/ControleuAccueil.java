package controleur;

import Vue.Accueil;
import Vue.Fenetre;
import Vue.Jeu;
import modele.Personnage;
import modele.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Le controleur qui va gerer les événenement qui proviennent de l'initialisation du jeu avec la fenetre d'accueil
 * il doit ensuite intialiser le controleur du jeu avec le modele (train)
 */
public class ControleuAccueil implements ActionListener {

    private Accueil accueil;
    private Fenetre fenetre;
    // accumulation de classes internes
    private ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJouers = new ArrayList<>(); // contiendra le surnom et icone


    public ControleuAccueil(Fenetre fenetre){
        this.fenetre = fenetre;
        this.accueil = this.fenetre.getAccueil();
        this.accueil.liaisonAvecControleur(this);

    }

    public void lancer(){
        this.fenetre.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /// !!!! à changer il travail avec public
        if (e.getSource() == this.accueil.getOptionsJeu().lancerJeu){
            // On recupere ce qui a été saisie avec getTexte et on intialise le jeu
            // !! RECUERE LE NOMBRE DE BALLES

            Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();
            int nbBallesBandits = this.accueil.getOptionsJeu().getNbBalles();
            Double nervositeMarshall = this.accueil.getOptionsJeu().getNervosite();

            Train train = new Train(this.accueil.getOptionsJeu().getNbWagon());
            train.ajouterMarshall(nervositeMarshall);
            // boucle pour ajouter les bandit
            // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va etre le premier Personnage dans la liste du train
            for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : this.creationsJouers){
                // quand le bandit est ajouté au train il faut garder un lien avec le chemin de son icone
                // qu'il faudra passer à la vue
                train.ajouterBandit(infos.getSurnom(),nbBallesBandits);
                mapPersonnageIcone.put(train.getBandits().getLast(),infos.getIcone());
            }
            Jeu jeu = new Jeu(train, this.fenetre, mapPersonnageIcone);

            this.fenetre.ajouterFenetreJeu(jeu);
            this.fenetre.changerFenetre(this.fenetre.getJeuId());
            CotroleurJeu cotroleurJeu = new CotroleurJeu(train,this.fenetre,this.accueil.getOptionsJeu().getNbActions());

            BoucleJeu boucleJeu = new BoucleJeu(cotroleurJeu);
            boucleJeu.execute();


        }


        if (e.getSource() == this.accueil.getOptionsJeu().getSlectionPersoPanel().bouttonCreationBandit) {
            ImageIcon iconePerso = this.accueil.getOptionsJeu().getSlectionPersoPanel().getPersoSlectionneIcone();
            String surnom = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBanditSurnom();

            this.creationsJouers.add(new Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation(iconePerso,surnom));  // on recup le perso choisie sur la liste et le nomb saisie
        }

    }

    class BoucleJeu extends SwingWorker<Void, Void>{

        private CotroleurJeu controleur;

        public BoucleJeu(CotroleurJeu controleur) {
            this.controleur = controleur;
        }

        @Override
        // la boucle de notre jeu qui va tourner sur un thread different que l'EDT qui est responsable pour l'actualisation de l'affchage
        protected Void doInBackground() {
            controleur.lancerJeu(ControleuAccueil.this.accueil.getOptionsJeu().getNbManches());
            return null;
        }

        // c'est une méthode qui va etre appelé quand le travail en fond (doInBackGround) sera terminé
        @Override
        protected void done() {
            // il faut reinitialiser le modele quand une partie se termine pour relancer une nouvelle
            this.controleur.fenetre.changerFenetre(this.controleur.fenetre.getAccueilId());
        }
    }

    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre();
        ControleuAccueil controleuAccueil = new ControleuAccueil(fenetre);
        controleuAccueil.lancer();
    }
}
