package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jeu extends JPanel implements ActionListener {
    private Fenetre fenetre;

    public Jeu(Fenetre fenetre){
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.fenetre.changerFenetre(this.fenetre.getLancementId());
    }
}
