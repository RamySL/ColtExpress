package Vue;

import Vue.Bouttons.Bouttons;
import controleur.ControleurTypePartie;

import javax.swing.*;

public class EcranType extends JPanel {
    private Bouttons.BouttonHorsJeu bouttonHorsLigne, bouttonMultiJouer;

    public EcranType(){


        this.bouttonHorsLigne = new Bouttons.BouttonHorsJeu("Hors Ligne");
        this.bouttonMultiJouer = new Bouttons.BouttonHorsJeu("MultiJoueur");

        this.add(bouttonHorsLigne);
        this.add(bouttonMultiJouer);

    }

    public void liaisonAvecControleur(ControleurTypePartie c){
        this.bouttonHorsLigne.addActionListener(c);
        this.bouttonMultiJouer.addActionListener(c);
    }

    public Bouttons.BouttonHorsJeu getBouttonHorsLigne() {
        return bouttonHorsLigne;
    }

    public Bouttons.BouttonHorsJeu getBouttonMultiJouer() {
        return bouttonMultiJouer;
    }
}
