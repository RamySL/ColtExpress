package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.CenteredPanel;
import multiJoueur.ControleurChoixLancerRejoindre;

import javax.swing.*;
import java.awt.*;

/**
 * Ecran de choix entre lancer ou rejoindre un serveur
 */
public class ChoixLancerRejoindre extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;
    private Bouttons.BouttonHorsJeu bouttonLancer, bouttonRejoindre,bouttonRetour;

    public ChoixLancerRejoindre(Fenetre fenetre){
        this.fenetre = fenetre;

        this.bouttonLancer = new Bouttons.BouttonHorsJeu(" Lancer un serveur ");
        this.bouttonRejoindre = new Bouttons.BouttonHorsJeu(" Rejoindre un serveur ");
        this.bouttonRetour = new Bouttons.BouttonHorsJeu("<");

        this.setBackground(new Color(0xA99100));
        this.imageFond = new ImageIcon("src/assets/images/back_2e_ecran.png").getImage();

        CenteredPanel.centerArrangement(this, this.bouttonRetour);

        JPanel panelCentrale = new JPanel(new GridBagLayout());
        panelCentrale.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentrale.add(this.bouttonLancer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCentrale.add(this.bouttonRejoindre, gbc);

        this.add(panelCentrale,BorderLayout.CENTER);

    }

    public void liaisonAvecControleur(ControleurChoixLancerRejoindre c){
        this.bouttonLancer.addActionListener(c);
        this.bouttonRejoindre.addActionListener(c);
        this.getBouttonRetour().addActionListener(c);
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

    public Bouttons.BouttonHorsJeu getBouttonRetour() {
        return bouttonRetour;
    }
}
