package controleur;

import Vue.ChoixLancerRejoindre;
import Vue.Fenetre;
import Vue.LancerServeur;

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
            this.fenetre.setLancerServeur("lancerServeurId", new LancerServeur(this.fenetre));
            this.fenetre.changerVue(this.fenetre.getLancerServeurId());
        }else if (e.getSource() == this.choixLancerRejoindre.getBouttonRejoindre()){

        }
    }
}
