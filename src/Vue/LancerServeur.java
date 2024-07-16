package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.Police;
import multiJoueur.ControleurLancerServeur;

import javax.swing.*;
import java.awt.*;

public class LancerServeur extends LancerRejoindreServeur {
    private Bouttons.BouttonHorsJeu bouttonLancer,bouttonRejoindre;
    private JTextField portServer, nbJoueur;

    public LancerServeur(Fenetre fenetre){
        super(fenetre);
        Police police = new Police();

        JLabel nbjueurLabel = new JLabel(" Nombre de joueurs ");
        nbjueurLabel.setFont(police);

        this.bouttonLancer = new Bouttons.BouttonHorsJeu(" Lancer ");
        this.bouttonRejoindre = new Bouttons.BouttonHorsJeu(" Rejoindre ");
        this.portServer = new JTextField("12345");
        this.portServer.setBackground(new Color(0xFDF1D1));
        this.portServer.setOpaque(true);

        this.nbJoueur = new JTextField("2");
        this.nbJoueur.setBackground(new Color(0xFDF1D1));
        this.nbJoueur.setOpaque(true);

        this.portServer.setPreferredSize(new Dimension(50,30));
        this.nbJoueur.setPreferredSize(new Dimension(50,30));

        this.portPanel.add(portLabel, BorderLayout.NORTH);
        this.portPanel.add(this.portServer, BorderLayout.CENTER);

        this.nbOuIpPanel.add(nbjueurLabel, BorderLayout.NORTH);
        this.nbOuIpPanel.add(this.nbJoueur, BorderLayout.CENTER);

        this.dispositionComposants(this.bouttonLancer,this.portPanel, this.nbOuIpPanel);


    }


    public void liasonControleur(ControleurLancerServeur controleurLancerServeur) {
        this.bouttonLancer.addActionListener(controleurLancerServeur);
        this.bouttonRejoindre.addActionListener(controleurLancerServeur);
        this.bouttonRetour.addActionListener(controleurLancerServeur);
    }

    public Bouttons.BouttonHorsJeu getBouttonLancer() {
        return bouttonLancer;
    }

    public JTextField getPortServer() {
        return portServer;
    }

    public JTextField getNbJoueur() {
        return nbJoueur;
    }

    public Bouttons.BouttonHorsJeu getBouttonRejoindre() {
        return bouttonRejoindre;
    }


}
