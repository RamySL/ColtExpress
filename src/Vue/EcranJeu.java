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
public class EcranJeu extends JFrame implements Observer {

    Train train;


    int hauteur, lageur;
    // !!! il faut une solution pour lier les composants avec leur listener (le cntrl)
    public JButton action, gaucheDep,droiteDep,hautDep,basDep, braquage; // Dep = deplcament

    public JButton tirGauche, tirDroit,tirHaut,tirBas;
    public JLabel phase;
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
//        this.lageur = (int) (screenSize.width*0.5 );
//        this.hauteur = (int) (screenSize.height*0.5 );
        this.lageur = 1000;
        this.hauteur = 800;
        this.decalageXTrain = (int) (0.1 * this.lageur); // decalage sur l'axe X pour centré tjr le dessin du train

        this.setPreferredSize(new Dimension(this.lageur,this.hauteur));


        this.getContentPane().setBackground(Color.BLACK);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/vue/header.jpg");

        this.setIconImage(icon.getImage());

        this.setLayout(null);

        action = new JButton("act");
        gaucheDep = new JButton("<");
        droiteDep = new JButton(">");
        hautDep = new JButton("^");
        basDep = new JButton("|");
        braquage = new JButton("Braquer");

        tirGauche = new JButton("tir <");
        tirDroit = new JButton("tir >");
        tirBas = new JButton("tir |");
        tirHaut = new JButton("tir ^");

        action.setBounds(0,0,80,30);

        gaucheDep.setBounds(100, 50, 50,30);
        droiteDep.setBounds(170, 50, 50,30);
        basDep.setBounds(130, 100, 50,30);
        hautDep.setBounds(130, 0, 50,30);
        braquage.setBounds(230,50,150,30);

        tirHaut.setBounds(500,0,100,30);
        tirBas.setBounds(500,100,100,30);
        tirDroit.setBounds(600,50,100,30);
        tirGauche.setBounds(450,50,100,30);




        this.phase = new JLabel("Phase de planification");
        this.phase.setForeground(Color.white);
        this.phase.setBounds(200,0,300,20);
        this.add(this.phase);

        trainPanel.setBounds(0,0,this.lageur,this.hauteur);//il prend tte la fenetre le panel


        this.pack();
        this.add(action);
        this.add(gaucheDep);
        this.add(droiteDep);
        this.add(basDep);
        this.add(hautDep);
        this.add(braquage);
        this.add(trainPanel);

        this.add(tirHaut);
        this.add(tirBas);
        this.add(tirDroit);
        this.add(tirGauche);
        //this.setVisible(true);
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

            /*int lenSeparation = (int)(0.2*largeurCabine);
            int xDebutSep = EcranJeu.this.decalageXTrain + largeurCabine * (pos+1) + lenSeparation*(pos);
            g.drawLine(xDebutSep , (int) (y + 0.8*hauteurCabine) ,xDebutSep + lenSeparation, (int) (y + 0.8*hauteurCabine));*/

            g.drawRect(EcranJeu.this.decalageXTrain + pos * (largeurCabine ), y,largeurCabine ,hauteurCabine); // resté 40% de hauteur

            paintRoueArriere(EcranJeu.this.decalageXTrain, largeurCabine,40,pos,g);
            paintRoueAvant(EcranJeu.this.decalageXTrain,largeurCabine,40,pos,g);



            // on dessine la liste des personnages
            ArrayList<Personnage> persos = c.getPersoList();
            for(Personnage p : persos){
                if (p instanceof Marshall) g.setColor(new Color(36, 36, 229));
                else g.setColor(Color.RED);
                g.drawString(p.getSurnom(),EcranJeu.this.decalageXTrain + pos*largeurCabine + 30, y +30);
                g.setColor(Color.WHITE);
            }

            // on dessine les buttins
            for(int i = 0; i<c.getButtins().size(); i++){
                g.drawString(c.getButtins().get(i).toString(),EcranJeu.this.decalageXTrain + pos*largeurCabine + 30, y + hauteurCabine -3 + i*-30);
            }

        }

        public void paintRoueAvant(int decalageX, int largeurCabine, int diametreRoue,int pos, Graphics g){
            g.fillOval(decalageX + largeurCabine*pos+largeurCabine - diametreRoue, (int)(0.75*hauteur),diametreRoue,diametreRoue);
        }

        public void paintRoueArriere(int decalageX, int largeurCabine,int diametreRoue,int pos, Graphics g){
            g.fillOval(decalageX + pos*largeurCabine,(int)(0.75*hauteur),diametreRoue, diametreRoue);
        }


        public void paintToit(Toit c, int pos, int yCab, int lageurCabine, Graphics g){

            paintCheminee(yCab, lageurCabine,30, 60,g);

            // on dessine la liste des personnages
            ArrayList<Personnage> persos = c.getPersoList();
            for(Personnage p : persos){
                g.setColor(Color.RED);
                g.drawString(p.getSurnom(),EcranJeu.this.decalageXTrain + pos*lageurCabine + 30,yCab- 10);
                g.setColor(Color.WHITE);
            }

            // on dessine les buttins
            for(int i = 0; i<c.getButtins().size(); i++){
                g.drawString(c.getButtins().get(i).toString(),EcranJeu.this.decalageXTrain + pos*lageurCabine + 30, yCab- 20);// y = (int)(0.7*EcranJeu.this.hauteur) + i*30
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

    public void liaisonBottonsListener(ActionListener cntrl){
        // on dit au bouttons que c'est le controleur qui est en charge d'couter les evnt
        this.action.addActionListener(cntrl);
        this.basDep.addActionListener(cntrl);
        this.gaucheDep.addActionListener(cntrl);
        this.droiteDep.addActionListener(cntrl);
        this.hautDep.addActionListener(cntrl);
        this.braquage.addActionListener(cntrl);
        this.tirHaut.addActionListener(cntrl);
        this.tirBas.addActionListener(cntrl);
        this.tirDroit.addActionListener(cntrl);
        this.tirGauche.addActionListener(cntrl);


    }


}
