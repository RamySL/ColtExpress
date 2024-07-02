package controleur;

import Vue.ChoixLancerRejoindre;
import Vue.Fenetre;
import Vue.LancerServeur;
import Vue.RejoindreServeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurChoixLancerRejoindre implements ActionListener {
    // utilise le seter de fenetre
    private Fenetre fenetre;
    private ChoixLancerRejoindre choixLancerRejoindre;

    public ControleurChoixLancerRejoindre(ChoixLancerRejoindre choixLancerRejoindre, Fenetre fenetre){
        this.fenetre = fenetre;
        this.choixLancerRejoindre = choixLancerRejoindre;
        this.choixLancerRejoindre.liaisonAvecControleur(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.choixLancerRejoindre.getBouttonLancer()){

            LancerServeur lancerServeur = new LancerServeur(this.fenetre);
            this.fenetre.SetLancerRejoindre("lancerServeurId",lancerServeur );
            new ControleurLancerServeur(this.fenetre,lancerServeur);
            this.fenetre.changerVue(this.fenetre.getLancerRejoindreServeurId());

        }else if (e.getSource() == this.choixLancerRejoindre.getBouttonRejoindre()){

            RejoindreServeur rejoindreServeur = new RejoindreServeur(this.fenetre);
            this.fenetre.SetLancerRejoindre("rejoindreServeurId",rejoindreServeur );
            new ControleurRejoindreServeur(this.fenetre,rejoindreServeur);
            this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());

        }
    }
}
