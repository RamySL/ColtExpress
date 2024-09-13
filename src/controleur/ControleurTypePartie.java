package controleur;

import Vue.EcranType;
import Vue.Fenetre;
import controleur.multiJoueur.ControleurAccueilHost;
import controleur.multiJoueur.ControleurChoixLancerRejoindre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurTypePartie implements ActionListener {

    public EcranType ecranType;
    private Fenetre fenetre;
    private ControleurAccueil controleurAccueil;
    private ControleurChoixLancerRejoindre controleurChoixLancerRejoindre;


    public ControleurTypePartie(Fenetre fenetre){
        this.fenetre = fenetre;
        this.ecranType = this.fenetre.getEcranTpe();
        this.ecranType.liaisonAvecControleur(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ecranType.getBouttonHorsLigne()){
            if(this.controleurAccueil == null) {
                // peut etre plus efficace en terme de memoire ? (le gc collectera this.controleurChoixLancerRejoindr)
                if(this.controleurChoixLancerRejoindre != null) this.controleurChoixLancerRejoindre = null;
                this.controleurAccueil = new ControleurAccueil(this.fenetre);
            }
            this.fenetre.changerVue(this.fenetre.getAccueilId());
        }

        if (e.getSource() == this.ecranType.getBouttonMultiJouer()){
            if(this.controleurChoixLancerRejoindre == null){
                if(this.controleurAccueil != null) this.controleurAccueil = null;
                this.controleurChoixLancerRejoindre = new ControleurChoixLancerRejoindre(this.fenetre.getChoixLancerRejoindre(),this.fenetre,  new ControleurAccueilHost(this.fenetre));
            }
            this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());
        }

    }

    public void afficher(){
        this.fenetre.setVisible(true);
    }
}
