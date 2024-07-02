package Vue;

import Vue.ComposantsPerso.Bouttons;
import multiJoueur.ControleurChoixLancerRejoindre;

import javax.swing.*;
import java.awt.*;

/**
 * Ecran de choix entre lancer ou rejoindre un serveur
 */
public class ChoixLancerRejoindre extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;
    private Bouttons.BouttonHorsJeu bouttonLancer, bouttonRejoindre;

    public ChoixLancerRejoindre(Fenetre fenetre){
        this.fenetre = fenetre;

        this.bouttonLancer = new Bouttons.BouttonHorsJeu(" Lancer un serveur ");
        this.bouttonRejoindre = new Bouttons.BouttonHorsJeu(" Rejoindre un serveur ");

        this.setBackground(new Color(0xA99100));
        this.imageFond = new ImageIcon("src/assets/images/back_2e_ecran.png").getImage();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(this.bouttonLancer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(this.bouttonRejoindre, gbc);

    }

    public void liaisonAvecControleur(ControleurChoixLancerRejoindre c){
        this.bouttonLancer.addActionListener(c);
        this.bouttonRejoindre.addActionListener(c);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Corrected method call
        g.drawImage(this.imageFond, 0, -20, this.fenetre.getWidth(), this.fenetre.getHeight(), this);
    }

    public Bouttons.BouttonHorsJeu getBouttonLancer() {
        return bouttonLancer;
    }

    public Bouttons.BouttonHorsJeu getBouttonRejoindre() {
        return bouttonRejoindre;
    }
}
