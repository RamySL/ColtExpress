package Vue;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {
    // on va utiliser cardLayout por avoir l'effet de naviguer entre plusieurs fenetre differentes
    private CardLayout cardLayout;
    //Il va contenir la liste des affichage (Acuueil, Jeu ..)
    private JPanel cards;

    // On definit les ID pour les fenetres
    private String jeuId, lancementId, accueilId;
    public Fenetre(){
        this.jeuId = "jeu";
        this.lancementId = "lancement";
        this.accueilId = "accueil";


        this.setTitle("ColtExpress");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 700);

        // Créer le conteneur pour les cartes
        this.cardLayout = new CardLayout();
        this.cards = new JPanel(cardLayout);
        this.add(cards);

        // On ajoute nos différentes fenetre
        cards.add(new Accueil(this), this.accueilId);
        cards.add(new Jeu(this), this.jeuId);
        cards.add(new EcranLancement(this), this.lancementId);

        // On dit qu'on veut que ça soit EcranLancement qui s'affiche en premier
        cardLayout.show(cards, this.lancementId);

        this.setVisible(true);

    }

    public void changerFenetre(String nomFenetre) {
        // methode utilise par les differente fenetre pour changer de vue
        cardLayout.show(cards, nomFenetre);
    }

    public String getJeuId() {return this.jeuId;}
    public String getLancementId(){return this.lancementId;}
    public String getAccueilId(){return this.accueilId;}

    public static void main(String[] args) {
        new Fenetre();
    }
}
