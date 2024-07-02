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
    public ControleurTypePartie(Fenetre fenetre){
        this.fenetre = fenetre;
        this.ecranType = this.fenetre.getEcranTpe();
        this.ecranType.liaisonAvecControleur(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ecranType.getBouttonHorsLigne()){
            new ControleurAccueil(this.fenetre);
            this.fenetre.changerVue(this.fenetre.getAccueilId());
        }

        if (e.getSource() == this.ecranType.getBouttonMultiJouer()){
            new ControleurChoixLancerRejoindre(this.fenetre.getChoixLancerRejoindre(),this.fenetre,  new ControleurAccueilHost(this.fenetre));

            this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());
        }

    }

    public void afficher(){
        this.fenetre.setVisible(true);
    }
}
