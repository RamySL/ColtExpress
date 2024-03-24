package Vue;


import modele.*;
import modele.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/* La vue implemente observer, le modele implemente observable, le mdoele contient la liste
la liste des observer et son but et de les notifier quand il ya un changement à son niveau,
et après l'observer reagit en changeant l'affichage
 */
public class Jeu extends JFrame implements Observer, ActionListener {

    Train train;
    Bandit b;
    public Jeu (Train t){
        this.train = t;
        this.train.getBandit().addObserver(this);
        this.setPreferredSize(new Dimension(1000,700));

        this.b = this.train.getBandit();

        this.getContentPane().setBackground(Color.CYAN);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/vue/header.jpg");

        this.setIconImage(icon.getImage());

        this.setLayout(null);

        JButton action = new JButton("action");

        action.setBounds(500,50,100,100);
        action.addActionListener(this);


        this.pack();
        this.add(action);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);

        g.setColor(Color.BLACK);
        g.drawLine(0,200,1000,200);
        int pos = 0;
        for (ComposanteTrain c : this.train){
            this.paintComposante(c,pos,g);
            pos++;
        }

    }

    public void paintComposante(ComposanteTrain c, int pos, Graphics g){

        g.drawRect(20+pos*200,250,200,300);
        // on dessine la liste des personnages
        ArrayList<Personnage> persos = c.getPersoList();
        for(Personnage p : persos){
            g.drawString(p.getSurnom(),20+pos*200 + 30, 250 + 30);
        }

        // on dessine les buttins
        for(int i = 0; i<c.getButtins().size(); i++){
            g.drawString(c.getButtins().get(i).toString(),20+pos*200 + 30, 250 + 100 + i*30);
        }
    }
    public void update(){
        repaint();
    }

    public static void main(String[] args) {
        Train train = new Train(4,"ramy");
        Bandit b = train.getBandit();
        Action depdroit = new SeDeplacer(b, Direction.Droite);
        b.ajouterAction(depdroit);
        b.ajouterAction(depdroit);
        b.ajouterAction(depdroit);
        b.ajouterAction(depdroit);

        new Jeu(train);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.train.getBandit().executer();

    }
}
