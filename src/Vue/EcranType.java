package Vue;

import Vue.Bouttons.Bouttons;
import controleur.ControleurTypePartie;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class EcranType extends JPanel {
    private Bouttons.BouttonHorsJeu bouttonHorsLigne, bouttonMultiJouer;
    private Image imageFond;
    private Fenetre fenetre;

    private AttenteMulti att;

    public EcranType(Fenetre fenetre){
        this.fenetre = fenetre;
        this.att = new AttenteMulti();


        this.bouttonHorsLigne = new Bouttons.BouttonHorsJeu("Hors Ligne");
        this.bouttonHorsLigne.setBackground(Color.WHITE);
        this.bouttonMultiJouer = new Bouttons.BouttonHorsJeu("MultiJoueur");
        this.bouttonMultiJouer.setBackground(Color.WHITE);

        this.add(bouttonHorsLigne);
        this.add(bouttonMultiJouer);
        // ici important de ne pas mettre le chemin à partir de src, à cause d'un truc lié au classpath
        URL gifUrl = Objects.requireNonNull(EcranType.class.getResource("/assets/images/typeBack.gif"));
        ImageIcon gifIcon = new ImageIcon(gifUrl);
        this.imageFond = gifIcon.getImage();

    }

    public void liaisonAvecControleur(ControleurTypePartie c){
        this.bouttonHorsLigne.addActionListener(c);
        this.bouttonMultiJouer.addActionListener(c);
    }

    public void displayAttente(){
        this.add(this.att);
        this.revalidate();
    }

    public Bouttons.BouttonHorsJeu getBouttonHorsLigne() {
        return bouttonHorsLigne;
    }

    public Bouttons.BouttonHorsJeu getBouttonMultiJouer() {
        return bouttonMultiJouer;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(this.imageFond,0,-20,this.fenetre.getWidth(),this.fenetre.getHeight(),this);
    }

    private class AttenteMulti extends JPanel {
        public AttenteMulti(){
            JLabel msg = new JLabel("Le multiJouer est en cours de development");
            msg.setFont(new Font("MV Boli", Font.BOLD, 20));
            msg.setForeground(Color.BLACK);

            this.add(msg);
        }
    }
}
