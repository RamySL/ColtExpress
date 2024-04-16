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
        this.imageFond =  new ImageIcon("src/assets/images/jeuBack.jpg").getImage();
        this.fenetre = fenetre;
        this.banditGagnant = banditGagnant;

        this.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel westhPanel = new JPanel();
        JPanel southhPanel = new JPanel();

        northPanel.setPreferredSize(new Dimension(0,200));
        southhPanel.setPreferredSize(new Dimension(0,200));
        eastPanel.setPreferredSize(new Dimension(200,0));
        westhPanel.setPreferredSize(new Dimension(200,0));

        northPanel.setOpaque(false);
        eastPanel.setOpaque(false);
        westhPanel.setOpaque(false);
        southhPanel.setOpaque(false);

        JPanel panelCentrale = new JPanel(new BorderLayout());
        panelCentrale.setBackground(new Color(0xCF000000, true));

         bouttonRejouer = new JButton("Rejouer");

////////////////////////////////////// copier coller de panneau de config
        JPanel persoPanel = new JPanel();
        persoPanel.setBackground(new Color(0xA6000000, true));
        persoPanel.setLayout(new BoxLayout(persoPanel,BoxLayout.Y_AXIS));
        JLabel iconeBandit = new JLabel(mapPersonnageIcone.get(banditGagnant));
        JLabel surnomLabel = new JLabel( "Surnom : " + banditGagnant.getSurnom());
        JLabel ballesLabel = new JLabel("Balles restantes : " + banditGagnant.getNbBalles());
        JLabel scoreLabel = new JLabel("Score : " + banditGagnant.score());

        surnomLabel.setForeground(Color.WHITE);
        ballesLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE);

        surnomLabel.setFont(new Font("MV Boli", Font.BOLD, 13));
        ballesLabel.setFont(new Font("MV Boli", Font.BOLD, 13));
        scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 13));

        persoPanel.add(iconeBandit);
        persoPanel.add(surnomLabel);
        persoPanel.add(ballesLabel);
        persoPanel.add(scoreLabel);
        persoPanel.setBorder(new LineBorder(Color.BLACK, 1));
        ////////////////////////////////////

        panelCentrale.add(persoPanel,BorderLayout.CENTER);
        panelCentrale.add(bouttonRejouer, BorderLayout.SOUTH);


        this.add(panelCentrale, BorderLayout.CENTER);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westhPanel, BorderLayout.WEST);
        this.add(southhPanel, BorderLayout.SOUTH);

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





}
