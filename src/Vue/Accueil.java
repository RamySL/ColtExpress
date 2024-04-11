package Vue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;

    private OptionsJeu optionsJeu;

    public Accueil(Fenetre fenetre){
        this.setLayout(new BorderLayout());
        this.fenetre = fenetre;

        this.imageFond = new ImageIcon("src/assets/images/colt_express-banner.jpg").getImage();


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
            JPanel northPanel = new JPanel();
            JPanel southPanel = new JPanel();
            JPanel dernierEtage = new JPanel();

            titreMenu.setForeground(Color.WHITE);
            titreMenu.setFont(new Font("MV Boli", Font.BOLD, 20));
            titreMenu.setAlignmentX(Component.CENTER_ALIGNMENT); // pour centrer
            titreMenu.setBackground(new Color(0,0,0, 221));
            titreMenu.setOpaque(true); // pour que la couleur de fond soit visible

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


            deuxiemeEtage.setPreferredSize(new Dimension(200,50));
            deuxiemeEtage.setBackground(new Color(0,0,0, 0));

            northPanel.setPreferredSize(new Dimension(100,70));
            northPanel.setBackground(new Color(0,0,0, 0));
            southPanel.setPreferredSize(new Dimension(100,70));
            southPanel.setBackground(new Color(0,0,0, 0));

            this.lancerJeu = new JButton("Lancer");
            this.lancerJeu.addActionListener(e -> Accueil.this.fenetre.changerFenetre(Accueil.this.fenetre.getJeuId()));
            dernierEtage.add(this.lancerJeu);
            dernierEtage.setBackground(new Color(0,0,0, 0));


            this.add(titreMenu);
            this.add(premierEtage);
            this.add(deuxiemeEtage);
            this.add(northPanel);
            this.add(southPanel);
            this.add(dernierEtage);

            this.setPreferredSize(new Dimension(100,100));
        }
    }

}

