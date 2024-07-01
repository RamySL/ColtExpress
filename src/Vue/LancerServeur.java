package Vue;

import Vue.Bouttons.Bouttons;

import javax.swing.*;
import java.awt.*;

public class LancerServeur extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;
    private Bouttons.BouttonHorsJeu bouttonLancer;
    private JTextField portServer, nbJoueur;

    public LancerServeur(Fenetre fenetre){
        this.fenetre = fenetre;

        this.bouttonLancer = new Bouttons.BouttonHorsJeu(" Lancer ");
        this.portServer = new JTextField("12345");
        this.nbJoueur = new JTextField("2");

        this.portServer.setPreferredSize(new Dimension(50,30));
        this.nbJoueur.setPreferredSize(new Dimension(50,30));


        this.setBackground(new Color(0xA99100));
        this.imageFond = new ImageIcon("src/assets/images/back_2e_ecran.png").getImage();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 5;
        gbc.gridy = 1;
        this.add(this.bouttonLancer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(this.portServer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.nbJoueur, gbc);




    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Corrected method call
        g.drawImage(this.imageFond, 0, -20, this.fenetre.getWidth(), this.fenetre.getHeight(), this);
    }
}
