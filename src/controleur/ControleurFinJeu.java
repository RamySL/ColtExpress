package controleur;

import Vue.EcranFin;
import Vue.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * controle la fenetre de  fin de partie si le joueur veut relancer une nouvelle partie
 *
 */
public class ControleurFinJeu implements ActionListener {
    EcranFin ecranFin;
    public ControleurFinJeu(EcranFin ecranFin){
        this.ecranFin = ecranFin;
        this.ecranFin.liaisonControleur(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.ecranFin.getBouttonRejouer()){
            Fenetre fenetre = new Fenetre();
            fenetre.changerFenetre(fenetre.getAccueilId());
            ControleuAccueil controleuAccueil = new ControleuAccueil(fenetre);
            controleuAccueil.lancer();

        }
    }
}
