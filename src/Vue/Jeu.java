package Vue;


import modele.*;

import javax.swing.*;
import java.awt.*;

/* La vue implemente observer, le modele implemente observable, le mdoele contient la liste
la liste des observer et son but et de les notifier quand il ya un changement à son niveau,
et après l'observer reagit en changeant l'affichage
 */
public class Jeu extends JFrame implements Observer {

    Train train;

    public Jeu (Train t){
        this.train = t;
        this.train.getBandit().addObserver(this);
        this.setPreferredSize(new Dimension(1000,700));

        this.getContentPane().setBackground(Color.CYAN);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/vue/header.jpg");

        this.setIconImage(icon.getImage());



        JButton action = new JButton("action");
        action.setSize(new Dimension(100,100));
        this.add(action);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);

        g.setColor(Color.BLACK);
        g.drawLine(0,200,1000,200);

    }
    public void update(){
        repaint();
    }

    public static void main(String[] args) {
        new Jeu(new Train(4,"ramy"));
    }
}
