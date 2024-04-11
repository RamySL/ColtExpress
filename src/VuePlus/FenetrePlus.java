package VuePlus;

import Vue.Accueil;
import Vue.Jeu;
import controleur.ControleurPlus;
import modele.Train;

import javax.swing.*;
import java.awt.*;

public class FenetrePlus extends JFrame {
    // on va utiliser cardLayout por avoir l'effet de naviguer entre plusieurs fenetre differentes
    private ControleurPlus controleur;
    private CardLayout cardLayout;
    private JPanel ecranLancement, accueil;
    private JeuPlus jeu;

    //Il va contenir la liste des affichage (Acuueil, Jeu ..)
    private JPanel cards;

    private Train train; // c'est le controleur qui initialise cet attribut

    // On definit les ID pour les fenetres
    private String jeuId, lancementId, accueilId;
    public FenetrePlus(Train train){
        this.train = train;

        this.ecranLancement = new EcranLancementPlus(this);
        this.accueil = new AccueilPlus( this);
        this.jeu = new JeuPlus(this.train, this);

        this.jeuId = "jeu";
        this.lancementId = "lancement";
        this.accueilId = "accueil";


        this.setTitle("ColtExpress");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);

        // Créer le conteneur pour les cartes
        this.cardLayout = new CardLayout();
        this.cards = new JPanel(cardLayout);
        this.add(cards);

        // On ajoute nos différentes fenetre
        cards.add(this.ecranLancement, this.lancementId);
        cards.add(this.accueil, this.accueilId);
        cards.add(this.jeu, this.jeuId);


        // On dit qu'on veut que ça soit EcranLancement qui s'affiche en premier
        cardLayout.show(cards, this.jeuId);


    }

    public void lierAvecControleur(ControleurPlus controleur){
        this.controleur = controleur;
    }

    public void changerFenetre(String nomFenetre) {
        // methode utilise par les differente fenetre pour changer de vue
        cardLayout.show(cards, nomFenetre);
    }

    public String getJeuId() {return this.jeuId;}
    public String getLancementId(){return this.lancementId;}
    public String getAccueilId(){return this.accueilId;}

    public JeuPlus getJeuPanel (){return this.jeu;}
    public JPanel getecranLancementPanel (){return this.ecranLancement;}
    public JPanel getAcueilPanel (){return this.accueil;}

    public ControleurPlus getControleur (){return this.controleur;}

//    public static void main(String[] args) {
//        new Fenetre();
//    }
}


