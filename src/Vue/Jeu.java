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

    int hauteur, lageur;
    public Jeu (Train t){
        this.train = t;
        this.train.getBandit().addObserver(this);
        this.hauteur = 700;
        this.lageur = 1000;

        this.setPreferredSize(new Dimension(this.lageur,this.hauteur));

        this.b = this.train.getBandit();

        this.getContentPane().setBackground(Color.CYAN);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        // definis la partie des touches pour les joueurs
        g.drawLine(0,(int)(0.3*this.hauteur),this.lageur,(int)(0.3*this.hauteur));

        int pos = 0;
        for (ComposanteTrain c : this.train){
            this.paintComposante(c,pos,g);
            pos++;
        }

    }

    public void paintComposante(ComposanteTrain c, int pos, Graphics g){

        //on dessines les wagons et leurs toit
        // 0.1 pcq en prenant 200 pour largeur des wagons avec 4 wagons on prend 800 ce qui represente 80%
        // il reste donc 20% pour les coté on prend 0.1 (10%) pour chaque coté comme ça c'est centré
        g.drawRect((int)(0.1 * this.lageur) + pos*200, (int)(0.6*this.hauteur),200 ,(int)(0.35*this.hauteur)); // resté 40% de hauteur

        // Toit
        g.drawRect((int)(0.1 * this.lageur) + pos*200,(int)(0.4*this.hauteur),200 ,(int)(0.2*this.hauteur)); //0.1 parceque 0.6-0.4=0.2

        // on dessine la liste des personnages
        // il reste 70% de hauteur pour le train
        ArrayList<Personnage> persos = c.getPersoList();

        if (c instanceof Toit) {
            // distinction entre le dessin des joueurs sur le toit ou pas
            for(Personnage p : persos){
                g.drawString(p.getSurnom(),(int)(0.1 * this.lageur) + pos*200 + 30, (int)(0.2*this.hauteur) +20);
            }
        }else{
            for(Personnage p : persos){
                g.drawString(p.getSurnom(),(int)(0.1 * this.lageur) + pos*200 + 30, (int)(0.6*this.hauteur) +30);
            }
        }

        // on dessine les buttins
        for(int i = 0; i<c.getButtins().size(); i++){
            g.drawString(c.getButtins().get(i).toString(),(int)(0.1 * this.lageur) + pos*200 + 30, (int)(0.7*this.hauteur) + i*30);
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
