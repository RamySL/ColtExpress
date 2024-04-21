package Vue;

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
    private JButton bouttonRejouer;
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
        bouttonRejouer = new JButton("Rejouer");
        bouttonRejouer.setForeground(Color.WHITE);
        bouttonRejouer.setBackground(new Color(0xDA523B25, true));
        bouttonRejouer.setFont(new Font("MV Boli", Font.BOLD, 20));

        JPanel persoPanel;
        if (banditGagnant.size() == 1){
            persoPanel = persoPanel(banditGagnant.get(0));
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
        bouttonRejouer.setBounds(100,this.fenetre.getHeight()/2,130,35);

        wantedIconeLabel.add(scoreLabel);
        wantedIconeLabel.add(panelCentrale);
        this.add(wantedIconeLabel);
        this.add(bouttonRejouer);

    }

    private JPanel persoPanel(Bandit b) {

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
