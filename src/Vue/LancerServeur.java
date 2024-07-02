package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.Police;
import controleur.ControleurLancerServeur;

import javax.swing.*;
import java.awt.*;

public class LancerServeur extends LancerRejoindreServeur {
    private Bouttons.BouttonHorsJeu bouttonLancer;
    private JTextField portServer, nbJoueur;

    public LancerServeur(Fenetre fenetre){
        super(fenetre);

        this.bouttonLancer = new Bouttons.BouttonHorsJeu(" Lancer ");
        this.portServer = new JTextField("12345");
        this.nbJoueur = new JTextField("2");

        this.portServer.setPreferredSize(new Dimension(50,30));
        this.nbJoueur.setPreferredSize(new Dimension(50,30));

        this.dispositionComposants(this.bouttonLancer,this.portServer, this.nbJoueur);


    }

    @Override
    public void liasonControleur(ControleurLancerServeur controleurLancerServeur) {
        this.bouttonLancer.addActionListener(controleurLancerServeur);
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
}
