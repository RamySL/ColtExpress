package Vue;

import controleur.ControleurFinJeu;
import modele.Bandit;
import modele.Personnage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EcranFin extends JPanel {
    Bandit banditGagnant;
    Fenetre fenetre;

    JButton bouttonRejouer;
    Image imageFond;
    public EcranFin(Fenetre fenetre, Bandit banditGagnant, Map<Personnage, ImageIcon> mapPersonnageIcone){

        this.imageFond =  new ImageIcon("src/assets/images/FondFin.jpg").getImage();
        this.fenetre = fenetre;
        this.banditGagnant = banditGagnant;

        this.setLayout(null);
        ImageIcon wantedIcone = new ImageIcon("src/assets/images/wanted.png");
        // les decalage à faire pour que wanted soit au centre
        int decalageX = (this.fenetre.getWidth()-wantedIcone.getIconWidth())/2;
        int decalageY = (this.fenetre.getHeight()-wantedIcone.getIconHeight())/2;


        JLabel wantedIconeLabel = new JLabel(wantedIcone);
        wantedIconeLabel.setLayout(null);

        JPanel panelCentrale = new JPanel(new BorderLayout());
        //panelCentrale.setBackground(new Color(0xCF000000, true));
        panelCentrale.setOpaque(false);
        bouttonRejouer = new JButton("Rejouer");
        bouttonRejouer.setForeground(Color.WHITE);
        bouttonRejouer.setBackground(new Color(0xDA523B25, true));
        bouttonRejouer.setFont(new Font("MV Boli", Font.BOLD, 20));

////////////////////////////////////// copier coller de panneau de config
        JPanel persoPanel = new JPanel();
        //persoPanel.setBackground(new Color(0xA6000000, true));
        persoPanel.setOpaque(false);
        persoPanel.setLayout(new BorderLayout());
        JLabel iconeBandit = new JLabel(mapPersonnageIcone.get(banditGagnant));
        JLabel surnomLabel = new JLabel( banditGagnant.getSurnom());
        JLabel scoreLabel = new JLabel(banditGagnant.score() + " €");
        //iconeBandit.setBorder(new LineBorder(Color.BLUE));
        surnomLabel.setForeground(Color.black);
        scoreLabel.setForeground(Color.black);

        surnomLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
        scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
        surnomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        persoPanel.add(iconeBandit,BorderLayout.CENTER);
        persoPanel.add(surnomLabel, BorderLayout.SOUTH);

        //persoPanel.setBorder(new LineBorder(Color.BLACK, 1));
        ////////////////////////////////////

        panelCentrale.add(persoPanel,BorderLayout.CENTER);
        //persoPanel.setBorder(new LineBorder(Color.GREEN,3));
        scoreLabel.setBounds(131 + 70,538,200,40);
        panelCentrale.setBounds(88 ,169 ,270,274);
        wantedIconeLabel.setBounds(decalageX,decalageY,wantedIcone.getIconWidth(),wantedIcone.getIconHeight()-10);
        bouttonRejouer.setBounds(100,this.fenetre.getHeight()/2,130,35);
        wantedIconeLabel.add(scoreLabel);
        wantedIconeLabel.add(panelCentrale);

        this.add(wantedIconeLabel);
        this.add(bouttonRejouer);

    }

    public void liaisonControleur(ControleurFinJeu controleurFinJeu){
        this.bouttonRejouer.addActionListener(controleurFinJeu);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(this.imageFond,0,0,this.getWidth(),this.getHeight(),this);
    }

    public JButton getBouttonRejouer (){
        return this.bouttonRejouer;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }
}
