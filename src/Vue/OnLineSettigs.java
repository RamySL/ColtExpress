package Vue;

import Vue.Bouttons.Bouttons;
import controleur.ControleurServerClient;
import controleur.ControleurTypePartie;

import javax.swing.*;
import java.awt.*;

public class OnLineSettigs extends JPanel {
    private Bouttons.BouttonHorsJeu lunchButton, joinButton;

    public OnLineSettigs () {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel lunchJoing = new JPanel();
        this.lunchButton = new Bouttons.BouttonHorsJeu("Lancer un serveur");
        this.joinButton = new Bouttons.BouttonHorsJeu("rejoindre un serveur");

        lunchJoing.add(lunchButton);
        lunchJoing.add(joinButton);

        this.add(lunchJoing,BorderLayout.NORTH);


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
}
