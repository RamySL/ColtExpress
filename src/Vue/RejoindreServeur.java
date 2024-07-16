package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.Police;
import multiJoueur.ControleurRejoindreServeur;

import javax.swing.*;
import java.awt.*;

public class RejoindreServeur extends LancerRejoindreServeur {

    private Bouttons.BouttonHorsJeu bouttonRejoindre;
    private JTextField portServer, ipServer;

    public RejoindreServeur(Fenetre fenetre){
        super(fenetre);

        Police police = new Police();

        JLabel ipServerLabel = new JLabel(" IP du serveur ");
        ipServerLabel.setFont(police);

        this.bouttonRejoindre = new Bouttons.BouttonHorsJeu(" Rejoindre ");
        this.portServer = new JTextField("12345");
        this.portServer.setBackground(new Color(0xFDF1D1));
        this.portServer.setOpaque(true);

        this.ipServer = new JTextField("192.168.50.128");
        this.ipServer.setBackground(new Color(0xFDF1D1));
        this.ipServer.setOpaque(true);

        this.portServer.setPreferredSize(new Dimension(50,30));
        this.ipServer.setPreferredSize(new Dimension(50,30));

        this.portPanel.add(portLabel, BorderLayout.NORTH);
        this.portPanel.add(this.portServer, BorderLayout.CENTER);

        this.nbOuIpPanel.add(ipServerLabel, BorderLayout.NORTH);
        this.nbOuIpPanel.add(this.ipServer, BorderLayout.CENTER);

        this.dispositionComposants(this.bouttonRejoindre,this.portPanel, this.nbOuIpPanel);


    }

    public void liasonControleur(ControleurRejoindreServeur controleurRejoindreServeur) {
        this.bouttonRejoindre.addActionListener(controleurRejoindreServeur);
        this.bouttonRetour.addActionListener(controleurRejoindreServeur);
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
