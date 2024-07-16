package multiJoueur;

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

    private ControleurAccueilHost controleurAccueilHost;


    public ControleurChoixLancerRejoindre(ChoixLancerRejoindre choixLancerRejoindre, Fenetre fenetre, ControleurAccueilHost controleurAccueilHost){
        this.fenetre = fenetre;
        this.choixLancerRejoindre = choixLancerRejoindre;
        this.controleurAccueilHost = controleurAccueilHost;
        this.choixLancerRejoindre.liaisonAvecControleur(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.choixLancerRejoindre.getBouttonLancer() || e.getSource() == this.choixLancerRejoindre.getBouttonRejoindre() ){

        }

        if (e.getSource() == this.choixLancerRejoindre.getBouttonLancer()){

            LancerServeur lancerServeur = new LancerServeur(this.fenetre);

            ControleurServerClient controleurServerClient = new ControleurServerClient(this.fenetre,this.controleurAccueilHost);
            controleurServerClient.setLancerServeur(lancerServeur);

            this.fenetre.SetLancerRejoindre("lancerServeurId",lancerServeur );
            new ControleurLancerServeur(this.fenetre,lancerServeur,controleurServerClient);
            this.fenetre.changerVue(this.fenetre.getLancerRejoindreServeurId());

        }else if (e.getSource() == this.choixLancerRejoindre.getBouttonRejoindre()){

            RejoindreServeur rejoindreServeur = new RejoindreServeur(this.fenetre);

            ControleurServerClient controleurServerClient = new ControleurServerClient(this.fenetre,this.controleurAccueilHost);
            controleurServerClient.setRejoindreServeur(rejoindreServeur);

            this.fenetre.SetLancerRejoindre("rejoindreServeurId",rejoindreServeur );
            new ControleurRejoindreServeur(this.fenetre,rejoindreServeur, controleurServerClient);
            this.fenetre.changerVue(this.fenetre.getLancerRejoindreServeurId());

        }

        if (e.getSource() == this.choixLancerRejoindre.getBouttonRetour()){
            this.fenetre.changerVue(this.fenetre.getTypeId());
        }
    }
}
