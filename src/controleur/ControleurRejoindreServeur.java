package controleur;

import Vue.Fenetre;
import Vue.RejoindreServeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurRejoindreServeur implements ActionListener {
    private Fenetre fenetre;
    private RejoindreServeur rejoindreServeur;
    public ControleurRejoindreServeur(Fenetre fenetre, RejoindreServeur rejoindreServeur){
        this.fenetre = fenetre;
        this.rejoindreServeur = rejoindreServeur;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
