package controleur;

import Vue.EcranType;
import Vue.Fenetre;
import multiJoueur.ControleurAccueilHost;
import multiJoueur.ControleurChoixLancerRejoindre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurTypePartie implements ActionListener {

    public EcranType ecranType;
    private Fenetre fenetre;
    ControleurAccueilHost controleurAccueilHost;

    public ControleurTypePartie(Fenetre fenetre, EcranType ecranType){
        this.fenetre = fenetre;
        this.ecranType = ecranType;

    }

    public void setControleurAccueilHost(ControleurAccueilHost controleurAccueilHost) {
        this.controleurAccueilHost = controleurAccueilHost;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ecranType.getBouttonHorsLigne()){
            this.fenetre.changerVue(this.fenetre.getAccueilId());
        }

        if (e.getSource() == this.ecranType.getBouttonMultiJouer()){
            new ControleurChoixLancerRejoindre(this.fenetre.getChoixLancerRejoindre(),this.fenetre, this.controleurAccueilHost);
            this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());
        }

    }
}
