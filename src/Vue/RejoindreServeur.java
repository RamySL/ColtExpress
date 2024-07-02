package Vue;

import Vue.ComposantsPerso.Bouttons;
import controleur.ControleurLancerServeur;
import controleur.ControleurRejoindreServeur;

import javax.swing.*;
import java.awt.*;

public class RejoindreServeur extends LancerRejoindreServeur {

    private Bouttons.BouttonHorsJeu bouttonRejoindre;
    private JTextField portServer, ipServer;

    public RejoindreServeur(Fenetre fenetre){
        super(fenetre);

        this.bouttonRejoindre = new Bouttons.BouttonHorsJeu(" Rejoindre ");
        this.portServer = new JTextField("12345");
        this.ipServer = new JTextField("localhost");

        this.portServer.setPreferredSize(new Dimension(50,30));
        this.ipServer.setPreferredSize(new Dimension(50,30));

        this.dispositionComposants(this.bouttonRejoindre,this.portServer, this.ipServer);


    }


    public void liasonControleur(ControleurRejoindreServeur controleurRejoindreServeur) {
            this.bouttonRejoindre.addActionListener(controleurRejoindreServeur);
    }

    public Bouttons.BouttonHorsJeu getBouttonRejoindre() {
        return bouttonRejoindre;
    }

    public JTextField getPortServer() {
        return portServer;
    }

    public JTextField getIpServer() {
        return ipServer;
    }
}
