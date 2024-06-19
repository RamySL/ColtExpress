package controleur;

import Vue.EcranType;
import Vue.Fenetre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurTypePartie implements ActionListener {

    public EcranType ecranType;
    private Fenetre fenetre;

    ControleurAccueil controleurAccueil;

    public ControleurTypePartie(Fenetre fenetre, EcranType ecranType,ControleurAccueil controleurAccueil){
        this.fenetre = fenetre;
        this.ecranType = ecranType;
        this.controleurAccueil = controleurAccueil;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ecranType.getBouttonHorsLigne()){
            this.fenetre.changerVue(this.fenetre.getAccueilId());
        }

        if (e.getSource() == this.ecranType.getBouttonMultiJouer()){
            this.fenetre.getOlSettings().liaisonAvecControleur(new ControleurServerClient(this.fenetre.getOlSettings(), this.fenetre,this.controleurAccueil));
            this.fenetre.changerVue(this.fenetre.getOlSettingsId());
        }

    }
}
