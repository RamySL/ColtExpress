package Vue;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {
    // on va utiliser cardLayout por avoir l'effet de naviguer entre plusieurs fenetre differentes

    private CardLayout cardLayout;
    private JPanel ecranLancement;
    private Accueil accueil;
    private Jeu jeu;

    private EcranFin ecranFin;

    //Il va contenir la liste des affichage (Acuueil, Jeu ..)
    private JPanel cards;


    // On definit les ID pour les fenetres
    private String jeuId, lancementId, accueilId, ecranFinId;
    public Fenetre(){


        this.ecranLancement = new EcranLancement(this);
        this.accueil = new Accueil( this);


        this.jeuId = "jeu";
        this.lancementId = "lancement";
        this.accueilId = "accueil";
        this.ecranFinId = "ecranFin";


        this.setTitle("ColtExpress");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1250, 700);
        this.setLocationRelativeTo(null);

        // Créer le conteneur pour les cartes
        this.cardLayout = new CardLayout();
        this.cards = new JPanel(cardLayout);
        this.add(cards);

        // On ajoute nos différentes fenetre
        cards.add(this.ecranLancement, this.lancementId);
        cards.add(this.accueil, this.accueilId);


        // On dit qu'on veut que ça soit EcranLancement qui s'affiche en premier
        cardLayout.show(cards, this.lancementId);


    }

    /**
     * va etre executer quand on appui sur lancer après avoir saisie les prametre du jeu
     * ajoute le modele initlisé au card et l'affiche
     * @param jeu
     */
    public void ajouterFenetreJeu(Jeu jeu){
        this.jeu = jeu;
        this.cards.add(jeu, this.jeuId);

    }

    public void ajouterEcranFin(EcranFin ecranFin){
        this.ecranFin = ecranFin;
        this.cards.add(this.ecranFin , this.ecranFinId);

    }


    public void changerFenetre(String nomFenetre) {
        // methode utilise par les differente fenetre pour changer de vue
        cardLayout.show(cards, nomFenetre);
    }

    public String getJeuId() {return this.jeuId;}
    public String getLancementId(){return this.lancementId;}
    public String getAccueilId(){return this.accueilId;}
    public String getEcranFinId() { return this.ecranFinId;}
    public Accueil getAccueil (){return this.accueil;}


    public Jeu getJeuPanel (){return this.jeu;}
    public JPanel getecranLancementPanel (){return this.ecranLancement;}
    public JPanel getAcueilPanel (){return this.accueil;}

}


