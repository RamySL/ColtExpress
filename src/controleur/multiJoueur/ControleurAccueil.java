package controleur.multiJoueur;

import Vue.Accueil;
import Vue.Fenetre;
import Vue.Jeu;
import modele.personnages.Personnage;
import modele.trainEtComposantes.Train;
import network.Paquets.PaquetsServeur.PaquetInitialisationPartie;
import network.Paquets.PaquetsServeur.PaquetListePerso;
import network.client.Client;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ControleurAccueil implements ActionListener {
    protected Fenetre fenetre;
    protected ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJouers = new ArrayList<>();
    protected Client client;

    public ControleurAccueil (Fenetre fenetre){
    this.fenetre = fenetre;

    }

    public void lancerPartie(PaquetListePerso paquetListePersoInfo, PaquetInitialisationPartie paquetInitialisationPartie, Train train){
        Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();

        //Train train = new Train (Integer.parseInt(paquetParametrePartie.getNbWagons()) );
        //train.ajouterMarshall(paquetInitialisationPartie.getNervositeMarshall());
        // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va corresependre au
        // premier Personnage dans la liste du train et le deuxieme au deuxieme etc
        int i = 0;
        for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : paquetListePersoInfo.getListeInfos()){
            //train.ajouterBandit(infos.getSurnom(),Integer.parseInt(paquetInitialisationPartie.getNbBallesBandits()));
            mapPersonnageIcone.put(train.getBandits().get(i),infos.getIcone());
            i++;
        }
        mapPersonnageIcone.put(train.getMarshall(),new ImageIcon("src/assets/images/sherif.png"));
        Jeu jeu = new Jeu(train, this.fenetre, mapPersonnageIcone);
        this.fenetre.ajouterFenetreJeu(jeu);
        this.fenetre.changerVue(this.fenetre.getJeuId());
        ControleurJeu cotroleurJeu = new ControleurJeuOnLine(train,this.fenetre,Integer.parseInt(paquetInitialisationPartie.getNbActions()), this.client);

        BoucleJeu boucleJeu = new BoucleJeu(cotroleurJeu,Integer.parseInt(paquetInitialisationPartie.getNbManches()) );
        boucleJeu.execute();
    }

    protected class BoucleJeu extends SwingWorker<Void, Void>{

        private ControleurJeu controleur;
        private int nbManches;

        public BoucleJeu(ControleurJeu controleur, int nbManches) {

            this.controleur = controleur;
            this.nbManches = nbManches;
        }

        /**
         * on lance la boucle en arriere plans
         * @return
         */
        @Override
        protected Void doInBackground() {
            controleur.lancerJeu(this.nbManches);
            return null;
        }


    }
}
