package Vue;

import Vue.Bouttons.Bouttons;
import controleur.ControleurServerClient;
import controleur.ControleurTypePartie;

import javax.swing.*;
import java.awt.*;

public class OnLineSettigs extends JPanel {
    private Bouttons.BouttonHorsJeu lunchButton, joinButton;
    private JTextField portServer, ipServerClient, portServerClient, nbJoueur;
    private JLabel attenteJoueurLabel;

    public OnLineSettigs () {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel lunchJoing = new JPanel();
        JPanel attenteJoueurPanel = new JPanel();

        this.lunchButton = new Bouttons.BouttonHorsJeu(" Lancer un serveur ");
        this.portServer = new JTextField("12345");
        this.nbJoueur = new JTextField("2");
        this.joinButton = new Bouttons.BouttonHorsJeu(" rejoindre un serveur ");
        this.ipServerClient = new JTextField("localhost");
        this.portServerClient = new JTextField("12345");

        lunchJoing.add(lunchButton);
        lunchJoing.add(portServer);
        lunchJoing.add(nbJoueur);
        lunchJoing.add(joinButton);
        lunchJoing.add(ipServerClient);
        lunchJoing.add(portServerClient);

        attenteJoueurLabel = new JLabel("");
        attenteJoueurLabel.setForeground(Color.WHITE);
        this.setVisible(false);

        this.add(lunchJoing,BorderLayout.NORTH);
        this.add(attenteJoueurLabel, BorderLayout.CENTER);
    }

    public void liaisonAvecControleur(ControleurServerClient c){
        this.lunchButton.addActionListener(c);
        this.joinButton.addActionListener(c);
    }

    public Bouttons.BouttonHorsJeu getJoinButton() {
        return joinButton;
    }

    public Bouttons.BouttonHorsJeu getLunchButton() {
        return lunchButton;
    }

    public String getIpServerClient() {
        return ipServerClient.getText();
    }

    public String getPortServer() {
        return portServer.getText();
    }

    public String getPortServerClient() {
        return portServerClient.getText();
    }

    public String getNbJoueur() {
        return nbJoueur.getText();
    }

    public JLabel getAttenteJoueurLabel() {
        return attenteJoueurLabel;
    }
}
