package Vue;

import Vue.Bouttons.Bouttons;
import controleur.ControleurTypePartie;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EcranType extends JPanel {
    private Bouttons.BouttonHorsJeu bouttonHorsLigne, bouttonMultiJouer;
    private Image imageFond;
    private Fenetre fenetre;

    public EcranType(Fenetre fenetre){
        this.fenetre = fenetre;

        this.bouttonHorsLigne = new Bouttons.BouttonHorsJeu("Hors Ligne");
        this.bouttonMultiJouer = new Bouttons.BouttonHorsJeu("MultiJoueur");

        this.setBackground(new Color(0xA99100));
        this.imageFond = new ImageIcon("src/assets/images/back_2e_ecran.png").getImage();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(bouttonHorsLigne, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(bouttonMultiJouer, gbc);


    }

    public void liaisonAvecControleur(ControleurTypePartie c){
        this.bouttonHorsLigne.addActionListener(c);
        this.bouttonMultiJouer.addActionListener(c);
    }

    public Bouttons.BouttonHorsJeu getBouttonHorsLigne() {
        return bouttonHorsLigne;
    }

    public Bouttons.BouttonHorsJeu getBouttonMultiJouer() {
        return bouttonMultiJouer;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Corrected method call
        g.drawImage(this.imageFond, 0, -20, this.fenetre.getWidth(), this.fenetre.getHeight(), this);
    }
}
