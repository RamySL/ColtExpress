package Vue;

import javax.swing.*;
import java.awt.*;
import modele.*;

public class Fenetre extends JFrame {
    // on va utiliser cardLayout por avoir l'effet de naviguer entre plusieurs fenetre differentes
    private CardLayout cardLayout;
    private JPanel ecranLancement, accueil;
    private Jeu jeu;

    //Il va contenir la liste des affichage (Acuueil, Jeu ..)
    private JPanel cards;

    private Train train; // c'est le controleur qui initialise cet attribut

    // On definit les ID pour les fenetres
    private String jeuId, lancementId, accueilId;
    public Fenetre(Train train){
        this.train = train;

        this.ecranLancement = new EcranLancement(this);
        this.accueil = new Accueil( this);
        this.jeu = new Jeu(this.train, this);

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
        cardLayout.show(cards, this.lancementId);


    }

    public void changerFenetre(String nomFenetre) {
        // methode utilise par les differente fenetre pour changer de vue
        cardLayout.show(cards, nomFenetre);
    }

    public String getJeuId() {return this.jeuId;}
    public String getLancementId(){return this.lancementId;}
    public String getAccueilId(){return this.accueilId;}

    public Jeu getJeuPanel (){return this.jeu;}
    public JPanel getecranLancementPanel (){return this.ecranLancement;}
    public JPanel getAcueilPanel (){return this.accueil;}

//    public static void main(String[] args) {
//        new Fenetre();
//    }
}
