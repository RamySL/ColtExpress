package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranLancement extends JPanel implements ActionListener {
    private Fenetre fenetre;
    private Image imageFond;

    private JLabel text;


    public EcranLancement(Fenetre fenetre){
        this.fenetre = fenetre;
        text = new JLabel("page de lancement");
        // Charger l'image de fond
        this.imageFond = new ImageIcon("src/photoPOGL/colt_back.png").getImage();

        this.add(text);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(this.imageFond,0,0,this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("test");
        this.fenetre.changerFenetre(this.fenetre.getAccueilId());
    }
}
