package controleur;

import Vue.EcranFin;
import Vue.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * controle la fenetre de  fin de partie, si le joueur veut relancer une nouvelle partie
 *
 */
public class ControleurFinJeu implements ActionListener {
    EcranFin ecranFin;
    private boolean isMultiPlayer;
    private Fenetre fenetre;
    public ControleurFinJeu(EcranFin ecranFin, boolean isMultiPlayer){
        this.ecranFin = ecranFin;
        this.ecranFin.liaisonControleur(this);
        this.isMultiPlayer = isMultiPlayer;
        this.fenetre =  ecranFin.getFenetre();
    }

    public void setMultiPlayer(boolean multiPlayer) {
        isMultiPlayer = multiPlayer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /// !!!!! IL FAUT REINITIALISER QUAND ON RELANCE
        if (e.getSource() == this.ecranFin.getBouttonRejouer()){
            if (isMultiPlayer){
                this.fenetre.changerVue(this.fenetre.getLancerRejoindreServeurId());
            }else {
                this.fenetre.changerVue(this.fenetre.getJeuId());
            }
//            this.ecranFin.getFenetre().dispose(); // ferme l'ancienne fenetre
//            Fenetre fenetre = new Fenetre();
//            fenetre.changerVue(fenetre.getAccueilId());
//            //ControleurAccueilClient controleurAccueilClient = new ControleurAccueilClient(fenetre);
//            //controleuAccueilClient.lancer(); à rectiver pour rejouer

        }else{
            if(isMultiPlayer){
                this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());
            }else {
                this.fenetre.changerVue(this.fenetre.getAccueilId());
            }
        }


    }
}
