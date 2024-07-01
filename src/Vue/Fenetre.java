package Vue;

import javax.swing.*;
import java.awt.*;

/**
 * La fenetre du jeu, utilisée avec CardLayout pour changer simplement entre les différentes vu de notre jeu
 */
public class Fenetre extends JFrame {
    private CardLayout cardLayout;
    private JPanel ecranLancement;
    private Accueil accueil;
    private EcranType ecranTpe;
    private Jeu jeu;

    private AccueilClient accueilClient;
    private ChoixLancerRejoindre choixLancerRejoindre;
    private LancerServeur lancerServeur;
    private EcranFin ecranFin;
    private JPanel cards;
    private String jeuId, lancementId, accueilId, ecranFinId, typeID, accueilClientId,choixLancerRejoindreId,lancerServeurId;
    public Fenetre(){

        this.ecranLancement = new EcranLancement(this);
        this.accueil = new Accueil( this);
        this.accueilClient = new AccueilClient(this);
        this.ecranTpe = new EcranType(this);
        this.choixLancerRejoindre = new ChoixLancerRejoindre(this); // peut etre optimiser on instancier avec un seter seulement si on va vers le multijoueur


        this.jeuId = "jeu";
        this.lancementId = "lancement";
        this.accueilId = "accueil";
        this.ecranFinId = "ecranFin";
        this.typeID = "ecranType";
        this.accueilClientId = "accueilClient";
        this.choixLancerRejoindreId = "choixLancerRejoindre";


        this.setTitle("ColtExpress");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1250, 700);
        this.setLocationRelativeTo(null);

        this.cardLayout = new CardLayout();
        this.cards = new JPanel(cardLayout);
        this.add(cards);

        cards.add(this.ecranLancement, this.lancementId);
        cards.add(this.accueil, this.accueilId);
        cards.add(this.ecranTpe, this.typeID);
        cards.add(this.accueilClient, this.accueilClientId);
        cards.add(this.choixLancerRejoindre, this.choixLancerRejoindreId);

        cardLayout.show(cards, this.lancementId);


    }

    /**
     * ajoute l'afichage du deroulement de la partie à la liste des cards
     * @param jeu
     */
    public void ajouterFenetreJeu(Jeu jeu){
        this.jeu = jeu;
        this.cards.add(jeu, this.jeuId);

    }

    /**
     * ajoute l'afichage de l'ecran de fin de partie à la liste des cards
     * @param
     */
    public void ajouterEcranFin(EcranFin ecranFin){
        this.ecranFin = ecranFin;
        this.cards.add(this.ecranFin , this.ecranFinId);

    }

    public void setLancerServeur(String lancerServeurId, LancerServeur lancerServeur) {
        this.lancerServeurId = lancerServeurId;
        this.lancerServeur = lancerServeur;
        this.cards.add(this.lancerServeur, this.lancerServeurId);
    }

    public void changerVue(String idVue) {
        cardLayout.show(cards, idVue);
    }

    public String getJeuId() {return this.jeuId;}
    public String getLancementId(){return this.lancementId;}
    public String getAccueilId(){return this.accueilId;}
    public String getEcranFinId() { return this.ecranFinId;}
    public Accueil getAccueil (){return this.accueil;}
    public String getTypeId (){ return this.typeID;}


    public EcranType getEcranTpe() {
        return ecranTpe;
    }

    public Jeu getJeuPanel (){return this.jeu;}

    public String getAccueilClientId() {
        return accueilClientId;
    }

    public AccueilClient getAccueilClient() {
        return accueilClient;
    }

    public String getChoixLancerRejoindreId() {
        return choixLancerRejoindreId;
    }

    public String getLancerServeurId() {
        return lancerServeurId;
    }

    public ChoixLancerRejoindre getChoixLancerRejoindre() {
        return choixLancerRejoindre;
    }
}


