package Vue;


import modele.*;
import modele.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/* La vue implemente observer, le modele implemente observable, le modele contient la liste
la liste des observer et son but et de les notifier quand il y a un changement à son niveau,
et après l'observer reagit en changeant l'affichage
 */
public class EcranJeu extends JFrame implements Observer, ActionListener {

    Train train;


    int hauteur, lageur;
    int decalageXTrain;
    public EcranJeu(Train t){
        //Il ne faut pas dessiner directement sur la fenetre (this), c'est trainPanel (Jpanel)
        // qui recouvre tte la surface de la fenetre

        this.train = t;
        for (Bandit b : train.getBandits()){
            b.addObserver(this);
        }
        train.getMarshall().addObserver(this);

        JPanel trainPanel = new TrainPanel();


        // on récupère les dimension de l'écran de l'ordinateur
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // on définit les dimensions de la fenetre relativement au dimension du pc de l'utilisateur
        this.lageur = (int) (screenSize.width *1);
        this.hauteur = (int) (screenSize.height * 1 );
        this.decalageXTrain = (int) (0.1 * this.lageur); // decalage sur l'axe X pour centré tjr le dessin du train

        this.setPreferredSize(new Dimension(this.lageur,this.hauteur));


        this.getContentPane().setBackground(Color.BLACK);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/vue/header.jpg");

        this.setIconImage(icon.getImage());

        this.setLayout(null);

        JButton action = new JButton("action");

        action.setBounds(500,50,100,100);
        action.addActionListener(this);
        trainPanel.setBounds(0,0,this.lageur,this.hauteur);//il prend tte la fenetre le panel


        this.pack();
        this.add(action);
        this.add(trainPanel);
        this.setVisible(true);
    }
    public class TrainPanel extends JPanel {

        public TrainPanel(){
            this.setBackground(Color.BLACK);
        }
        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            g.setColor(Color.WHITE);
            // definis la partie des touches pour les joueurs
            g.drawLine(0,(int)(0.3*EcranJeu.this.hauteur),EcranJeu.this.lageur,(int)(0.3*EcranJeu.this.hauteur));

            int pos = 0;
            for (ComposanteTrain c : EcranJeu.this.train){
                this.paintComposante(c,pos,g);
                pos++;

            }

        }
        //!!!!! les roues doivent etre relative à au Y de la cabine et sa hauteur (0.4+0.35)
        //!! la largeur de la cabine avec 20% marche pour centrer avec le 10% que pour 4 wagon

        public void paintComposante(ComposanteTrain c, int pos, Graphics g){

            Interieur cabine = (Interieur) c;
            Toit toit = cabine.getToit();
            int yCabine = (int)(0.4*EcranJeu.this.hauteur); //correspond à la hauteur où débute la cabine
            int largeurCabine =(int) (0.2*EcranJeu.this.lageur);



            // pour dessine une cabine on a besoin de l'objet pour recuperer les elts qu'il possede (buttins, perso..)
            // de sa position dans le train de  la laregeur de la cabine et des hauteur
            this.paintCabine(cabine,pos,yCabine,largeurCabine,(int)(0.35*EcranJeu.this.hauteur), g);
            // largeur du toit c'est la mm que la largeur de la cabine, et il depend aussi du y de la cab c'est pour ça qu'on lui donne en param
            this.paintToit(toit,pos,yCabine, largeurCabine, g);
        }

        public void paintCabine(Interieur c, int pos,int y, int largeurCabine,int hauteurCabine, Graphics g){
            //on dessine les wagons
            // 0.1 pcq en prenant 200 pour largeur des wagons avec 4 wagons on prend 800 ce qui represente 80%
            // il reste donc 20% pour les coté on prend 0.1 (10%) pour chaque coté comme ça c'est centré

            g.drawRect(EcranJeu.this.decalageXTrain + pos*largeurCabine, y,largeurCabine ,hauteurCabine); // resté 40% de hauteur

            paintRoueArriere(EcranJeu.this.decalageXTrain, largeurCabine,40,pos,g);
            paintRoueAvant(EcranJeu.this.decalageXTrain,largeurCabine,40,pos,g);



            // on dessine la liste des personnages
            ArrayList<Personnage> persos = c.getPersoList();
            for(Personnage p : persos){
                g.drawString(p.getSurnom(),EcranJeu.this.decalageXTrain + pos*largeurCabine + 30, y +30);
            }

            // on dessine les buttins
            for(int i = 0; i<c.getButtins().size(); i++){
                g.drawString(c.getButtins().get(i).toString(),EcranJeu.this.decalageXTrain + pos*largeurCabine + 30, y + hauteurCabine -3 + i*-30);
            }

        }

        public void paintRoueAvant(int decalageX, int largeurCabine, int diametreRoue,int pos, Graphics g){
            g.drawOval(decalageX + largeurCabine*pos+largeurCabine - diametreRoue, (int)(0.75*hauteur),diametreRoue,diametreRoue);
        }

        public void paintRoueArriere(int decalageX, int largeurCabine,int diametreRoue,int pos, Graphics g){
            g.drawOval(decalageX + pos*largeurCabine,(int)(0.75*hauteur),diametreRoue, diametreRoue);
        }


        public void paintToit(Toit c, int pos, int yCab, int lageurCabine, Graphics g){

            paintCheminee(yCab, lageurCabine,30, 60,g);

            // on dessine la liste des personnages
            ArrayList<Personnage> persos = c.getPersoList();
            for(Personnage p : persos){
                g.drawString(p.getSurnom(),EcranJeu.this.decalageXTrain + pos*lageurCabine + 30,yCab- 20);
            }

            // on dessine les buttins
            for(int i = 0; i<c.getButtins().size(); i++){
                g.drawString(c.getButtins().get(i).toString(),EcranJeu.this.decalageXTrain + pos*lageurCabine + 30, (int)(0.7*EcranJeu.this.hauteur) + i*30);
            }
        }

        public void paintCheminee(int yCab, int largeurCabine, int largeurCheminee, int hauteurCheminee, Graphics g){
            g.setColor(Color.WHITE);
            g.drawRect(EcranJeu.this.decalageXTrain + train.getSize()*largeurCabine-largeurCheminee,yCab-hauteurCheminee, largeurCheminee, hauteurCheminee);

        }

    }

    public void update(){
        repaint();
    }


    public static void main(String[] args) {
        Train train = new Train(4);

        //train.ajouterBandit("ramy");
        train.ajouterBandit("KeKe");
        //train.ajouterBandit("10Giga");



        Action depdroit = new SeDeplacer(train.banditQuiJoue(), Direction.Droite);
        Action depbas = new SeDeplacer(train.banditQuiJoue(), Direction.Bas);
        Action dephaut = new SeDeplacer(train.banditQuiJoue(), Direction.Haut);
        Action braquer = new Braquer(train.banditQuiJoue());

        train.banditQuiJoue().ajouterButtin(new Bijou());
        train.banditQuiJoue().ajouterAction(depbas);


        new EcranJeu(train);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.train.getMarshall().executer();
        this.train.banditQuiJoue().executer();

    }
}
