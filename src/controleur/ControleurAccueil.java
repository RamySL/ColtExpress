package controleur;

import Vue.Accueil;
import Vue.EcranType;
import Vue.Fenetre;
import Vue.Jeu;
import modele.personnages.Personnage;
import modele.trainEtComposantes.Train;
import network.PaquetListePersoHost;
import network.PaquetParametrePartie;
import network.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ControleurAccueil implements ActionListener {
    protected Accueil accueil;
    protected EcranType ecranType;
    protected Fenetre fenetre;
    // accumulation de classes internes
    protected ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJouers = new ArrayList<>();
    private JouerSon misqueLancement;
    private Client client;
    public ControleurAccueil(Fenetre fenetre)  {
        misqueLancement = new JouerSon("src/assets/sons/lancement.wav");
        //misqueLancement.jouer(true);

        this.fenetre = fenetre;
        this.accueil = this.fenetre.getAccueil();
        this.ecranType = this.fenetre.getEcranTpe();
        this.accueil.liaisonAvecControleur(this);
        this.ecranType.liaisonAvecControleur(new ControleurTypePartie(this.fenetre, this.fenetre.getEcranTpe(),this));
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * lance le jeu en recuperant tous les parmetre saisie si le boutton de lancer le jeu et appuié (et vérifie que les parametres sont valides)
     * récupere l'icone et le surnom choisie si le boutton bouttonCreationBandit est appuyé et permet de lier ensuite cette icone
     * à l'objet Bandit correspendant quand le boutton de lancer le jeu est appuyé
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouttonLancement = this.accueil.getOptionsJeu().getLancerJeu();
        if (e.getSource() == this.accueil.getOptionsJeu().getLancerJeu()){
            String nbBallesBandits = this.accueil.getOptionsJeu().getSaisieNbBalles().getText();
            Double nervositeMarshall = this.accueil.getOptionsJeu().getNervosite();
            String nbWagons = this.accueil.getOptionsJeu().getSaisieNbWagon().getText();
            String nbActions = this.accueil.getOptionsJeu().getSaisieNbActions().getText();
            String nbManches = this.accueil.getOptionsJeu().getSaisieNbManches().getText();

            if (checkInfoSaisieValide(nbBallesBandits,nbWagons,nbActions,nbManches)){
                try {
                    this.client.sendChoixPerso(this.creationsJouers.get(0));
                    this.client.sendParamJeu(nbBallesBandits,nbWagons,nbActions,nbManches,nervositeMarshall);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                bouttonLancement.setBackground(Color.RED);
                bouttonLancement.setText("Invalide !");
            }

        }
        if (e.getSource() == this.accueil.getOptionsJeu().getSlectionPersoPanel().getBouttonCreationBandit()) {
            bouttonLancement.setEnabled(true);
            ImageIcon iconePerso = this.accueil.getOptionsJeu().getSlectionPersoPanel().getPersoSlectionneIcone();
            String surnom = this.accueil.getOptionsJeu().getSlectionPersoPanel().getBanditSurnom();
            this.creationsJouers.add(new Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation(iconePerso,surnom));  // on recup le perso choisie sur la liste et le nomb saisie
        }

    }

    /**
     * pour verifier les options choisis par l'utilisateur
     * @param nbBalles
     * @param nbWagons
     * @param nbActions
     * @param nbManches
     * @return
     */
    private boolean checkInfoSaisieValide (String nbBalles, String nbWagons, String nbActions,String nbManches){
        try {
            int nbBallesI =  Integer.parseInt(nbBalles);
            int nbWagonsI =  Integer.parseInt(nbWagons);
            int nbActionsI = Integer.parseInt(nbActions);
            int nbManchesI =   Integer.parseInt(nbManches);
            return (  nbBallesI >= 0 && nbWagonsI>= 2 && nbActionsI >= 1 && nbManchesI >=1 );
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void lancerPartie(PaquetListePersoHost paquetListePersoInfo, PaquetParametrePartie paquetParametrePartie){
        Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();

        Train train = new Train (Integer.parseInt(paquetParametrePartie.getNbWagons()) );
        train.ajouterMarshall(paquetParametrePartie.getNervositeMarshall());
        // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va corresependre au
        // premier Personnage dans la liste du train et le deuxieme au deuxieme etc
        for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : paquetListePersoInfo.getListeInfos()){
            train.ajouterBandit(infos.getSurnom(),Integer.parseInt(paquetParametrePartie.getNbBallesBandits()));
            mapPersonnageIcone.put(train.getBandits().getLast(),infos.getIcone());
        }
        mapPersonnageIcone.put(train.getMarshall(),new ImageIcon("src/assets/images/sherif.png"));
        Jeu jeu = new Jeu(train, this.fenetre, mapPersonnageIcone);
        this.fenetre.ajouterFenetreJeu(jeu);
        this.fenetre.changerVue(this.fenetre.getJeuId());
        CotroleurJeu cotroleurJeu = new CotroleurJeu(train,this.fenetre,Integer.parseInt(paquetParametrePartie.getNbActions()) );

        BoucleJeu boucleJeu = new BoucleJeu(cotroleurJeu, Integer.parseInt(paquetParametrePartie.getNbManches()) );
        boucleJeu.execute();
    }



    /**
     * Boucle principale de notre jeu va tourner sur un thread différent que EDT  en arriere plans pour garder la reactivité de
     * l'affichage avec la boucle du jeu et ne pas bloquer l'EDT
     * Void : premier void pour préciser que ça retourne rien à la fin de l'execution, le deuxieme c'est pour dire ça publi rien
     * pendant l'execution
     */
    private class BoucleJeu extends SwingWorker<Void, Void>{

        private CotroleurJeu controleur;
        private int nbManches;

        public BoucleJeu(CotroleurJeu controleur, int nbManches) {

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

    /**
     * Affcihe la fenetre du jeu
     */
    public void lancer(){
        this.fenetre.setVisible(true);
    }

    @Override
    public String toString() {
        return "Accueil normal";
    }

}

class Main {
    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre();
        ControleurAccueil controleuAccueil = new ControleurAccueil(fenetre);
        controleuAccueil.lancer();
    }
}
