package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.CenteredPanel;
import controleur.multiJoueur.ControleurAccueilClient;
import controleur.multiJoueur.ControleurAccueilHost;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.io.Serializable;

/**
 * L'ecran où l'utilisateur chosit les options du jeu et créé les bandits
 */
public class Accueil extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;

    private Bouttons.BouttonHorsJeu bouttonRetour;
    protected OptionsJeu optionsJeu;

    public Accueil(Fenetre fenetre){
        this.fenetre = fenetre;
        this.imageFond = new ImageIcon("src/assets/images/colt_accueil.png").getImage();

        this.bouttonRetour = new Bouttons.BouttonHorsJeu("<");
        this.optionsJeu = new OptionsJeu();

        CenteredPanel.centerArrangement(this, this.bouttonRetour);

        this.add(this.optionsJeu,BorderLayout.CENTER);


    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.imageFond,0,-20,this.fenetre.getWidth(),this.fenetre.getHeight(),this);

    }
    public OptionsJeu getOptionsJeu(){return  this.optionsJeu;}

    public void liaisonAvecControleur(ControleurAccueilHost controleur){
        this.optionsJeu.lancerJeu.addActionListener(controleur);
        this.optionsJeu.getSlectionPersoPanel().bouttonCreationBandit.addActionListener(controleur);
    }

    public void liaisonControleurOffline(controleur.ControleurAccueil controleurAccueil){
        this.optionsJeu.lancerJeu.addActionListener(controleurAccueil);
        this.optionsJeu.getSlectionPersoPanel().bouttonCreationBandit.addActionListener(controleurAccueil);
        this.getBouttonRetour().addActionListener(controleurAccueil);
    }

    public void liaisonAvecControleurClient(ControleurAccueilClient controleur){
        for (ActionListener al : this.optionsJeu.lancerJeu.getActionListeners()) {
            this.optionsJeu.lancerJeu.removeActionListener(al);
        }

        for (ActionListener al : this.optionsJeu.getSlectionPersoPanel().bouttonCreationBandit.getActionListeners()) {
            this.optionsJeu.getSlectionPersoPanel().bouttonCreationBandit.removeActionListener(al);
        }

        this.optionsJeu.lancerJeu.addActionListener(controleur);
        this.optionsJeu.getSlectionPersoPanel().bouttonCreationBandit.addActionListener(controleur);
    }

    public Bouttons.BouttonHorsJeu getBouttonRetour() {
        return bouttonRetour;
    }

    /**
     * regroupe les composants swing qui stock les saisie utilisateur
     */
    public  class OptionsJeu extends JPanel{

        private Bouttons lancerJeu;
        protected JTextField saisieNbWagon, saisieNbActions, saisieNbBalles, saisieNbManches;

        private SelectionPersonnages slectPersoPanel;
        protected SlectionNervositeMarshall selectNervositePanel;

        public OptionsJeu(){

            this.setBackground(new Color(0,0,0, 200));
            //this.setBorder(new LineBorder(Color.GREEN,2));
            // on va faire un menu vertical
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

            JLabel titreMenu = new JLabel("  Choisissez les options du jeu  ");
            JPanel premierEtage = new JPanel(); // premiere etage du menu
            JPanel deuxiemeEtage = new JPanel();
            JPanel troisiemeEtage = new JPanel();
            JPanel quatriemeEtage = new JPanel();
            JPanel cinquiemeEtage = new JPanel();
            JPanel sixiemeEtage = new JPanel();
            JPanel dernierEtage = new JPanel();

            //Titre du menu
            titreMenu.setForeground(Color.WHITE);
            titreMenu.setFont(new Font("MV Boli", Font.BOLD, 25));
            titreMenu.setAlignmentX(Component.CENTER_ALIGNMENT); // pour centrer
            titreMenu.setBackground(new Color(0,0,0, 221));
            titreMenu.setOpaque(true); // pour que la couleur de fond soit visible
            // Premiere Etage

            premierEtage.setOpaque(false);
            premierEtage.setBorder(new LineBorder(Color.BLACK,1));
            slectPersoPanel = new SelectionPersonnages();
            premierEtage.add(slectPersoPanel);
            //Deuxieme Etage
            deuxiemeEtage.setPreferredSize(new Dimension(200,50));
            deuxiemeEtage.setBackground(new Color(0,0,0, 0));
            deuxiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            JLabel nbBallesLabel = new JLabel("Nombres de balles pour les Bandits");
            nbBallesLabel.setForeground(Color.WHITE);
            nbBallesLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
            saisieNbBalles= new JTextField("6");//
            saisieNbBalles.setPreferredSize(new Dimension(25,20));
            deuxiemeEtage.add(nbBallesLabel);
            deuxiemeEtage.add(saisieNbBalles);
            //Troiseme Etage
            troisiemeEtage.setPreferredSize(new Dimension(100,70));
            troisiemeEtage.setBackground(new Color(0,0,0, 0));
            troisiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            JLabel nbWagonLabel = new JLabel("Nombres de Wagons");
            nbWagonLabel.setForeground(Color.WHITE);
            nbWagonLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
            saisieNbWagon = new JTextField("4");//
            saisieNbWagon.setPreferredSize(new Dimension(25,20));
            troisiemeEtage.add(nbWagonLabel);
            troisiemeEtage.add(saisieNbWagon);
            //Quatrieme Etage
            quatriemeEtage.setPreferredSize(new Dimension(100,70));
            quatriemeEtage.setBackground(new Color(0,0,0, 0));
            quatriemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            JLabel nbActionsLabel = new JLabel("Nombres d'actions");
            nbActionsLabel.setForeground(Color.WHITE);
            nbActionsLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
            saisieNbActions = new JTextField("4");//
            saisieNbActions.setPreferredSize(new Dimension(25,20));
            quatriemeEtage.add(nbActionsLabel);
            quatriemeEtage.add(saisieNbActions);

            //Cinqieme etage
            cinquiemeEtage.setPreferredSize(new Dimension(100,70));
            cinquiemeEtage.setBackground(new Color(0,0,0, 0));
            cinquiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            JLabel nbManchesLabel = new JLabel("Nombre de manches");
            nbManchesLabel.setForeground(Color.WHITE);
            nbManchesLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
            saisieNbManches = new JTextField("5");//
            saisieNbManches.setPreferredSize(new Dimension(25,20));
            cinquiemeEtage.add(nbManchesLabel);
            cinquiemeEtage.add(saisieNbManches);

            // Cinqieme etage
            sixiemeEtage.setPreferredSize(new Dimension(100,70));
            sixiemeEtage.setBackground(new Color(0,0,0, 0));
            sixiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            this.selectNervositePanel = new SlectionNervositeMarshall();
            sixiemeEtage.add(selectNervositePanel);


            this.lancerJeu = new Bouttons.BouttonHorsJeu(" Lancer ");
            this.lancerJeu.setBackground(new Color(0x775533));
            this.lancerJeu.setForeground(Color.WHITE);
            this.lancerJeu.setEnabled(false);
            dernierEtage.add(this.lancerJeu);
            dernierEtage.setBackground(new Color(0,0,0, 0));
            dernierEtage.setBorder(new LineBorder(Color.BLACK,1));


            this.add(titreMenu);
            this.add(premierEtage);
            this.add(deuxiemeEtage);
            this.add(troisiemeEtage);
            this.add(quatriemeEtage);
            this.add(cinquiemeEtage);
            this.add(sixiemeEtage);

            this.add(dernierEtage);

            this.setPreferredSize(new Dimension(100,100));
        }

        public JTextField getSaisieNbWagon() {
            return saisieNbWagon;
        }

        public JTextField getSaisieNbBalles() {
            return saisieNbBalles;
        }

        public JTextField getSaisieNbManches() {
            return saisieNbManches;
        }

        public JTextField getSaisieNbActions() {
            return saisieNbActions;
        }

        public JButton getLancerJeu() {
            return lancerJeu;
        }

        public SelectionPersonnages getSlectionPersoPanel () { return this.slectPersoPanel;}
        public Double getNervosite(){return this.selectNervositePanel.getNervosteMarshall();}

        /**
         * contient la Jlist qui permet de selectionner l'icone pour son personnage et de saisir son surnom
         */
        public class SelectionPersonnages extends JPanel{

            private ImageIcon [] persoListIcones;
            private Bouttons bouttonCreationBandit;
            private JList<ImageIcon> jListPerso;
            private JTextField saisieNomJoueur;

            public SelectionPersonnages(){
                this.setLayout(new BorderLayout());
                this.setOpaque(false);

                JLabel descLabel = new JLabel("Créer le nombre de bandit que vous voulez");
                descLabel.setOpaque(false);
                descLabel.setForeground(Color.WHITE);
                descLabel.setHorizontalAlignment(SwingConstants.CENTER);
                descLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
                this.initPersoListeIcones("src/assets/images/");

                jListPerso = new JList<>(persoListIcones);
                // icone par défaut
                jListPerso.setSelectedIndex(0);
                // pour que ça s'affiche horizontalement
                jListPerso.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                jListPerso.setVisibleRowCount(1); // affichage d'une seule ligne
                jListPerso.setBackground(new Color(0x80FFE7A1));

                JPanel sudPanel = new JPanel();
                this.bouttonCreationBandit = new Bouttons.BouttonHorsJeu("Créer Bandit");
                this.bouttonCreationBandit.setBackground(new Color(0x775533));
                this.bouttonCreationBandit.setForeground(Color.WHITE);

                saisieNomJoueur = new JTextField("  Surnom  ");

                sudPanel.add(saisieNomJoueur);
                sudPanel.add(this.bouttonCreationBandit);
                sudPanel.setOpaque(false);

                this.add(descLabel, BorderLayout.NORTH);
                this.add(jListPerso, BorderLayout.CENTER);
                this.add(sudPanel, BorderLayout.SOUTH);

            }

            public void initPersoListeIcones (String chemin){
                this.persoListIcones = new ImageIcon[9];
                for (int i =1; i<=9;i++){
                    ImageIcon persoIcone = new ImageIcon((chemin + "bandit" + i + ".png"));
                    this.persoListIcones[i-1] = persoIcone;
                }

            }

            public ImageIcon getPersoSlectionneIcone (){
                return  this.jListPerso.getSelectedValue();
            }

            public String getBanditSurnom() { return this.saisieNomJoueur.getText();}

            public JButton getBouttonCreationBandit() {
                return bouttonCreationBandit;
            }

            /**
             * structure pour stocker les infos necessaire pour créer la vue du personnage avec son icone
             * et son surnom saisie dans la fenetre d'option
             */
            public static class JoueurInfoCreation implements Serializable {

                @Serial
                private static final long serialVersionUID = 1L;
                private ImageIcon icone;
                private String surnom;
                public JoueurInfoCreation (ImageIcon icone, String surnom){
                    this.icone = icone;
                    this.surnom = surnom;
                }

                public ImageIcon getIcone () {return this.icone;}
                public String getSurnom () {return this.surnom;}


            }
        }

    }

    /**
     * Jliste avec 3 nervosité differente pour le marshall 0.3,0.6 et 0.9
     */
    public class SlectionNervositeMarshall extends JPanel{
        protected JList<String> jListNervositeNiveaux;
        String calme, enerve,furieux;

        public SlectionNervositeMarshall(){
            calme   = "   Calme";
            enerve  = "   Enervé";
            furieux = "   Furieux";
            this.setOpaque(false);
            this.setLayout(new BorderLayout());
            JLabel descLabel = new JLabel(" Slectionnez la nervosité du marshall ");
            descLabel.setForeground(Color.WHITE);
            descLabel.setOpaque(false);
            descLabel.setFont(new Font("MV Boli", Font.BOLD, 15));

            String[] nervositeNiveaux = {calme,enerve,furieux};
            jListNervositeNiveaux = new JList<>(nervositeNiveaux);
            // nervosité par défaut c'est Calme
            jListNervositeNiveaux.setSelectedValue(calme,false);
            // pour que ça s'affiche horizontalement
            jListNervositeNiveaux.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            jListNervositeNiveaux.setVisibleRowCount(1); // affichage d'une seule ligne
            jListNervositeNiveaux.setFixedCellWidth(100);
            jListNervositeNiveaux.setFont(new Font("MV Boli", Font.BOLD, 13));
            jListNervositeNiveaux.setBackground(new Color(0x80FFE7A1));

            this.add(descLabel, BorderLayout.NORTH);
            this.add(this.jListNervositeNiveaux, BorderLayout.CENTER);

        }

        public Double getNervosteMarshall(){
            String selection = this.jListNervositeNiveaux.getSelectedValue();

            if (selection == calme){
                return 0.2;
            }else{
                if (selection == enerve) {
                    return 0.4;
                }else{
                    return 0.7;
                }
            }

        }

    }

}

