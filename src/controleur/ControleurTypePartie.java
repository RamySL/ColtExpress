package controleur;

import Vue.EcranType;
import Vue.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurTypePartie implements ActionListener {

    public EcranType ecranType;
    private Fenetre fenetre;

    public ControleurTypePartie(Fenetre fenetre, EcranType ecranType){
        this.fenetre = fenetre;
        this.ecranType = ecranType;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ecranType.getBouttonHorsLigne()){
            this.fenetre.changerVue(this.fenetre.getAccueilId());
        }
    }
}
