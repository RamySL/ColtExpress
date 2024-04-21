package controleur;

import Vue.Accueil;
import Vue.Fenetre;
import Vue.Jeu;
import modele.personnages.Personnage;
import modele.trainEtComposantes.Train;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Le controleur qui va gerer les événenement qui proviennent de l'initialisation du jeu avec la fenetre d'accueil
 * et le choix des parametres du jeu, il doit ensuite intialiser le controleur du jeu avec le modele (train)
 */
public class ControleuAccueil implements ActionListener {

    private Accueil accueil;
    private Fenetre fenetre;
    // accumulation de classes internes
    private ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJouers = new ArrayList<>(); // contiendra le surnom et icone des bandits

    JouerSon misqueLancement;

    /**
     * Intialise le controleur et fait la liason avec les composantes d'accueil dont il va ecouter les evenements
     * @param fenetre du jeu qui contient tous les différentes vu du jeu
     */
    public ControleuAccueil(Fenetre fenetre){
        misqueLancement = new JouerSon("src/assets/sons/lancement.wav");
        //misqueLancement.jouer(true);

        this.fenetre = fenetre;
        this.accueil = this.fenetre.getAccueil();
        this.accueil.liaisonAvecControleur(this);

    }

    /**
     * lance le jeu en recuperant tous les parmetre saisie si le boutton de lancer le jeu et appuié
     * récupere l'icone et le surnom choisie si le boutton bouttonCreationBandit est appuyé
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouttonLancement = this.accueil.getOptionsJeu().getLancerJeu();
        if (e.getSource() == this.accueil.getOptionsJeu().getLancerJeu()){
            String nbBallesBandits = this.accueil.getOptionsJeu().getSaisieNbBalles().getText();
            Double nervositeMarshall = this.accueil.getOptionsJeu().getNervosite();
            String nbWagons = this.accueil.getOptionsJeu().getSaisieNbWagon().getText();
            String nbActions = this.accueil.getOptionsJeu().getSaisieNbActions().getText();
            String nbManches = ControleuAccueil.this.accueil.getOptionsJeu().getSaisieNbManches().getText();

            if (checkInfoSaisieValide(nbBallesBandits,nbWagons,nbActions,nbManches)){
                this.misqueLancement.arreter();
                Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();

                Train train = new Train (Integer.parseInt(nbWagons) );
                train.ajouterMarshall(nervositeMarshall);

                // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va etre le premier Personnage dans la liste du train
                for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : this.creationsJouers){
                    // quand le bandit est ajouté au train il faut garder un lien avec le chemin de son icone
                    // qu'il faudra passer à la vue
                    train.ajouterBandit(infos.getSurnom(),Integer.parseInt(nbBallesBandits));
                    mapPersonnageIcone.put(train.getBandits().getLast(),infos.getIcone());
                }
                mapPersonnageIcone.put(train.getMarshall(),new ImageIcon("src/assets/images/sherif.png"));
                Jeu jeu = new Jeu(train, this.fenetre, mapPersonnageIcone);

                this.fenetre.ajouterFenetreJeu(jeu);
                this.fenetre.changerFenetre(this.fenetre.getJeuId());
                CotroleurJeu cotroleurJeu = new CotroleurJeu(train,this.fenetre,Integer.parseInt(nbActions) );

                BoucleJeu boucleJeu = new BoucleJeu(cotroleurJeu);
                boucleJeu.execute();
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


    class BoucleJeu extends SwingWorker<Void, Void>{

        private CotroleurJeu controleur;

        public BoucleJeu(CotroleurJeu controleur) {
            this.controleur = controleur;
        }

        @Override
        // la boucle de notre jeu qui va tourner sur un thread different que l'EDT qui est responsable pour l'actualisation de l'affchage
        protected Void doInBackground() {
            controleur.lancerJeu(Integer.parseInt(ControleuAccueil.this.accueil.getOptionsJeu().getSaisieNbManches().getText()));
            return null;
        }


    }

    /**
     * Affcihe la fenetre du jeu
     */
    public void lancer(){
        this.fenetre.setVisible(true);
    }

    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre();
        ControleuAccueil controleuAccueil = new ControleuAccueil(fenetre);
        controleuAccueil.lancer();
    }
}
