package VuePlus;

import Vue.Fenetre;
import controleur.ControleurMain;
import controleur.ControleurPlus;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class AccueilPlus extends JPanel {
    private FenetrePlus fenetre;
    private Image imageFond;

    private OptionsJeu optionsJeu;

    public AccueilPlus(FenetrePlus fenetre){
        this.setLayout(new BorderLayout());
        this.fenetre = fenetre;

        this.imageFond = new ImageIcon("src/assets/images/colt_accueil.png").getImage();

        // C'est pour centerer le menu d'option
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(200,100));
        westPanel.setPreferredSize(new Dimension(200,100));
        northPanel.setPreferredSize(new Dimension(100,70));
        southPanel.setPreferredSize(new Dimension(100,70));
        eastPanel.setBackground(new Color(0x0EEEEEE, true));
        westPanel.setBackground(new Color(0x0EEEEEE, true));
        northPanel.setBackground(new Color(0x0EEEEEE, true));
        southPanel.setBackground(new Color(0x0EEEEEE, true));


        this.optionsJeu = new OptionsJeu();

        this.add(this.optionsJeu,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(westPanel,BorderLayout.WEST);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(northPanel,BorderLayout.NORTH);

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(this.imageFond,0,-20,this.fenetre.getWidth(),this.fenetre.getHeight(),this);

    }

    public OptionsJeu getOptionsJeu(){return  this.optionsJeu;}

    public void liaisonAvecControleur(ControleurMain controleur){
        this.optionsJeu.lancerJeu.addActionListener(controleur);
        this.optionsJeu.getSlectionPersoPanel().bouttonCreationBandit.addActionListener(controleur);
    }

    /**
     * On doit permettre de saisir : nb action à planifier, NB_BALLES peut etre NERVOSITE_MARSHALL,
     */

    public  class OptionsJeu extends JPanel{

        public JButton lancerJeu;
        private JTextField saisieNbWagon, saisieNbActions, saisieNbBalles, saisieNbManches;

        private SelectionPersonnages slectPersoPanel;
        private SlectionNervositeMarshall selectNervositePanel;

        public OptionsJeu(){

            this.setBackground(new Color(0,0,0, 200));
            //this.setBorder(new LineBorder(Color.GREEN,2));
            // on va faire un menu vertical
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

            JLabel titreMenu = new JLabel("Choisissez les options du jeu");
            JPanel premierEtage = new JPanel(); // premiere etage du menu
            JPanel deuxiemeEtage = new JPanel();
            JPanel troisiemeEtage = new JPanel();
            JPanel quatriemeEtage = new JPanel();
            JPanel cinquiemeEtage = new JPanel();
            JPanel sixiemeEtage = new JPanel();
            JPanel dernierEtage = new JPanel();

            //Titre du menu
            titreMenu.setForeground(Color.WHITE);
            titreMenu.setFont(new Font("MV Boli", Font.BOLD, 20));
            titreMenu.setAlignmentX(Component.CENTER_ALIGNMENT); // pour centrer
            titreMenu.setBackground(new Color(0,0,0, 221));
            titreMenu.setOpaque(true); // pour que la couleur de fond soit visible
            // Premiere Etage

            premierEtage.setBackground(new Color(0x0000000, true));
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
            saisieNbBalles.setPreferredSize(new Dimension(40,20));
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
            saisieNbWagon.setPreferredSize(new Dimension(40,20));
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
            saisieNbActions.setPreferredSize(new Dimension(40,20));
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
            saisieNbManches.setPreferredSize(new Dimension(40,20));
            cinquiemeEtage.add(nbManchesLabel);
            cinquiemeEtage.add(saisieNbManches);

            // Cinqieme etage
            sixiemeEtage.setPreferredSize(new Dimension(100,70));
            sixiemeEtage.setBackground(new Color(0,0,0, 0));
            sixiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
             this.selectNervositePanel = new SlectionNervositeMarshall();
            sixiemeEtage.add(selectNervositePanel);



            this.lancerJeu = new JButton("Lancer");
            //this.lancerJeu.addActionListener(e -> AccueilPlus.this.fenetre.changerFenetre(AccueilPlus.this.fenetre.getJeuId()));
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

        public Integer getNbWagon (){
            return Integer.parseInt(this.saisieNbWagon.getText());
        }


        public Integer getNbActions(){
            return Integer.parseInt(this.saisieNbActions.getText());
        }

        public Integer getNbBalles(){
            return Integer.parseInt(this.saisieNbBalles.getText());
        }

        public Integer getNbManches (){ return Integer.parseInt(this.saisieNbManches.getText()); }



        public SelectionPersonnages getSlectionPersoPanel () { return this.slectPersoPanel;}
        public Double getNervosite(){return this.selectNervositePanel.getNervosteMarshall();}


        /**
         * ici mettre une methode qui recupere la liste des bandit (nom et lien) quand on appui sur lancer jeu
         */

        public class SelectionPersonnages extends JPanel{

            private ImageIcon [] persoListIcones;
            public JButton bouttonCreationBandit;
            private JList<ImageIcon> jListPerso;

            private JTextField saisieNomJoueur;

            public SelectionPersonnages(){
                this.setLayout(new BorderLayout());

                JLabel descLabel = new JLabel("Créer le nombre de bandit que vous voulez");

                this.initPersoListeIcones("src/assets/images/");

                jListPerso = new JList<>(persoListIcones);

                // pour que ça s'affiche horizontalement
                jListPerso.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                jListPerso.setVisibleRowCount(1); // affichage d'une seule ligne

                JPanel sudPanel = new JPanel();
                this.bouttonCreationBandit = new JButton("Créer Bandit");
                saisieNomJoueur = new JTextField("Surnom");

                sudPanel.add(saisieNomJoueur);
                sudPanel.add(this.bouttonCreationBandit);

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

            /**
             * structure pour stocker les infos necessaire pour créer la vue du personnage et mm son surnom pour le créer dans le modele
             */
            public static class JoueurInfoCreation{
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

    public class SlectionNervositeMarshall extends JPanel{
        private JList<String> jListNervositeNiveaux;

        public SlectionNervositeMarshall(){

            this.setLayout(new BorderLayout());
            JLabel descLabel = new JLabel("Slectionnez la nervoté du marshall");

            String[] nervositeNiveaux = {"Calme","Enervé","Furieux"};
            jListNervositeNiveaux = new JList<>(nervositeNiveaux);

            // pour que ça s'affiche horizontalement
            jListNervositeNiveaux.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            jListNervositeNiveaux.setVisibleRowCount(1); // affichage d'une seule ligne

            this.add(descLabel, BorderLayout.NORTH);
            this.add(this.jListNervositeNiveaux, BorderLayout.CENTER);

        }

        public Double getNervosteMarshall(){
            String selection = this.jListNervositeNiveaux.getSelectedValue();

            if (selection == "Calme"){
                return 0.3;
            }else{
                if (selection == "Enervé") {
                    return 0.6;
                }else{
                    return 9.0;
                }
            }

        }

    }

}

