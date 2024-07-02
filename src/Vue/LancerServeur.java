package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.Police;
import controleur.ControleurLancerServeur;

import javax.swing.*;
import java.awt.*;

public class LancerServeur extends LancerRejoindreServeur {
    private Bouttons.BouttonHorsJeu bouttonLancer,bouttonRejoindre;
    private JTextField portServer, nbJoueur;

    public LancerServeur(Fenetre fenetre){
        super(fenetre);

        this.bouttonLancer = new Bouttons.BouttonHorsJeu(" Lancer ");
        this.bouttonRejoindre = new Bouttons.BouttonHorsJeu(" Rejoindre ");
        this.portServer = new JTextField("12345");
        this.nbJoueur = new JTextField("2");

        this.portServer.setPreferredSize(new Dimension(50,30));
        this.nbJoueur.setPreferredSize(new Dimension(50,30));

        this.dispositionComposants(this.bouttonLancer,this.portServer, this.nbJoueur);


    }


    public void liasonControleur(ControleurLancerServeur controleurLancerServeur) {
        this.bouttonLancer.addActionListener(controleurLancerServeur);
        this.bouttonRejoindre.addActionListener(controleurLancerServeur);
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
