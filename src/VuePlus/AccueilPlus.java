package VuePlus;

import Vue.Fenetre;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AccueilPlus extends JPanel {
    private FenetrePlus fenetre;
    private Image imageFond;

    private OptionsJeu optionsJeu;

    public AccueilPlus(FenetrePlus fenetre){
        this.setLayout(new BorderLayout());
        this.fenetre = fenetre;

        this.imageFond = new ImageIcon("src/assets/images/colt_express-banner.jpg").getImage();

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

    /**
     * On doit permettre de saisir : nb action à planifier, NB_BALLES peut etre NERVOSITE_MARSHALL,
     */

    public class OptionsJeu extends JPanel{

        private JButton lancerJeu;

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
            JPanel dernierEtage = new JPanel();

            //Titre du menu
            titreMenu.setForeground(Color.WHITE);
            titreMenu.setFont(new Font("MV Boli", Font.BOLD, 20));
            titreMenu.setAlignmentX(Component.CENTER_ALIGNMENT); // pour centrer
            titreMenu.setBackground(new Color(0,0,0, 221));
            titreMenu.setOpaque(true); // pour que la couleur de fond soit visible
            // Premiere Etage
            premierEtage.setPreferredSize(new Dimension(10,1));
            premierEtage.setBackground(new Color(0x0000000, true));
            premierEtage.setBorder(new LineBorder(Color.BLACK,1));
            JLabel nbJoeur = new JLabel("Nombres de jouers");//
            nbJoeur.setForeground(Color.WHITE);
            nbJoeur.setFont(new Font("MV Boli", Font.BOLD, 15));
            JTextField saisieNbJoueur = new JTextField("3");//
            saisieNbJoueur.setPreferredSize(new Dimension(40,20));
            JButton validerNbJoueur = new JButton("Entrez leur noms");
            premierEtage.add(nbJoeur);
            premierEtage.add(saisieNbJoueur);
            premierEtage.add(validerNbJoueur);
            //Deuxieme Etage
            deuxiemeEtage.setPreferredSize(new Dimension(200,50));
            deuxiemeEtage.setBackground(new Color(0,0,0, 0));
            deuxiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            JLabel nbWagonLabel = new JLabel("Nombres de Wagons");
            nbWagonLabel.setForeground(Color.WHITE);
            nbWagonLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
            JTextField saisieNbWagon = new JTextField("4");//
            saisieNbWagon.setPreferredSize(new Dimension(40,20));
            deuxiemeEtage.add(nbWagonLabel);
            deuxiemeEtage.add(saisieNbWagon);
            //Troiseme Etage
            troisiemeEtage.setPreferredSize(new Dimension(100,70));
            troisiemeEtage.setBackground(new Color(0,0,0, 0));
            troisiemeEtage.setBorder(new LineBorder(Color.BLACK,1));
            //Quatrieme Etage
            quatriemeEtage.setPreferredSize(new Dimension(100,70));
            quatriemeEtage.setBackground(new Color(0,0,0, 0));
            quatriemeEtage.setBorder(new LineBorder(Color.BLACK,1));

            this.lancerJeu = new JButton("Lancer");
            this.lancerJeu.addActionListener(e -> AccueilPlus.this.fenetre.changerFenetre(AccueilPlus.this.fenetre.getJeuId()));
            dernierEtage.add(this.lancerJeu);
            dernierEtage.setBackground(new Color(0,0,0, 0));
            dernierEtage.setBorder(new LineBorder(Color.BLACK,1));


            this.add(titreMenu);
            this.add(premierEtage);
            this.add(deuxiemeEtage);
            this.add(troisiemeEtage);
            this.add(quatriemeEtage);
            this.add(dernierEtage);

            this.setPreferredSize(new Dimension(100,100));
        }
    }

}

