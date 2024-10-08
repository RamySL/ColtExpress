package Vue;

import Vue.ComposantsPerso.Bouttons;
import controleur.ControleurFinJeu;
import modele.personnages.Bandit;
import modele.personnages.Personnage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class EcranFin extends JPanel {
    private ArrayList<Bandit>  banditGagnant;
    private int score;
    private Fenetre fenetre;
    private Bouttons.BouttonHorsJeu bouttonRejouer,bouttonQuiter;
    private Image imageFond;
    private Map<Personnage, ImageIcon> mapPersonnageIcone;
    public EcranFin(Fenetre fenetre, ArrayList<Bandit> banditGagnant,int score, Map<Personnage, ImageIcon> mapPersonnageIcone){
        this.mapPersonnageIcone = mapPersonnageIcone;
        this.score = score;
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
        bouttonRejouer = new Bouttons.BouttonHorsJeu("Relancer la partie");
        bouttonQuiter = new Bouttons.BouttonHorsJeu("Quitter");


        JPanel persoPanel;
        if (banditGagnant.size() == 1){
            persoPanel =  persoPanel(banditGagnant.get(0));
        }else {
            persoPanel = new JPanel(new  GridLayout (2,2));
            for (Bandit b : banditGagnant){
                JPanel perso = this.persoPanel(b);
                perso.setOpaque(false);
                persoPanel.add(perso);

            }
        }
        persoPanel.setOpaque(false);
        JLabel scoreLabel = new JLabel(score + " €");
        scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
        scoreLabel.setForeground(Color.black);
        panelCentrale.add(persoPanel,BorderLayout.CENTER);
        //persoPanel.setBorder(new LineBorder(Color.GREEN,3));
        scoreLabel.setBounds(131 + 70,538,200,40);
        panelCentrale.setBounds(88 ,169 ,270,274);

        wantedIconeLabel.setBounds(decalageX,decalageY,wantedIcone.getIconWidth(),wantedIcone.getIconHeight()-10);
        bouttonRejouer.setBounds(100,this.fenetre.getHeight()/2 - 35/2,200,35);
        bouttonQuiter.setBounds(100,this.fenetre.getHeight()/2 + 40 - 35/2,200,35);

        wantedIconeLabel.add(scoreLabel);
        wantedIconeLabel.add(panelCentrale);
        this.add(wantedIconeLabel);
        this.add(bouttonRejouer);
        this.add(bouttonQuiter);

    }

    private JPanel persoPanel(Bandit b) {
        // !!! La liste qu'il ya dans banditGagnant ne contient pas les mm bandit que dans le map

        JPanel persoPanel = new JPanel();
        // affichage pour un seul gagnant
        persoPanel.setOpaque(false);
        persoPanel.setLayout(new BorderLayout());

        JLabel iconeBandit = new JLabel(mapPersonnageIcone.get(b));
        JLabel surnomLabel = new JLabel(b.getSurnom());

        //iconeBandit.setBorder(new LineBorder(Color.BLUE));
        surnomLabel.setForeground(Color.black);
        surnomLabel.setFont(new Font("MV Boli", Font.BOLD, 20));

        surnomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        persoPanel.add(iconeBandit,BorderLayout.CENTER);
        persoPanel.add(surnomLabel, BorderLayout.SOUTH);

        return persoPanel;
    }

    public void liaisonControleur(ControleurFinJeu controleurFinJeu){
        this.bouttonRejouer.addActionListener(controleurFinJeu);
        this.bouttonQuiter.addActionListener(controleurFinJeu);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.imageFond,0,0,this.getWidth(),this.getHeight(),this);
    }

    public JButton getBouttonRejouer (){
        return this.bouttonRejouer;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }
}
