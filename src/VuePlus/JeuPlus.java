package VuePlus;

import Vue.Fenetre;
import Vue.Jeu;
import modele.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JeuPlus extends JPanel implements Observer {
    private Fenetre fenetre;
    Train train;

    private JPanel trainPanel;
    public JeuPlus(Train t, FenetrePlus fenetre) {
        this.train = t;

        this.setLayout(new BorderLayout());
        CommandePanel c = new CommandePanel();

        this.add(c, BorderLayout.NORTH);

//        JPanel testTrain = new JPanel();
//        testTrain.setBackground(Color.BLACK);
//        this.add(testTrain,BorderLayout.CENTER);

        JPanel panelCentrale = new JPanel(new BorderLayout()); // pour pouvoir se placer à l'interieur d'un Jpanel c'est mieu
        panelCentrale.setBackground(Color.BLACK);
        panelCentrale.setBorder(new LineBorder(Color.WHITE,3));

        JPanel northPanelTrain = new JPanel(); // pour centrer le dessin du train
        northPanelTrain.setBackground(Color.RED);
        northPanelTrain.setPreferredSize(new Dimension(0,100));
        panelCentrale.add(northPanelTrain, BorderLayout.NORTH);

        this.trainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.trainPanel.setBorder(new LineBorder(Color.BLUE,2));
        this.dessineTrain(); // on dessine les wagon dans le panel du train
        panelCentrale.add(this.trainPanel, BorderLayout.CENTER);

        this.add(panelCentrale,BorderLayout.CENTER );

    }

    public void dessineTrain(){
        for (ComposanteTrain c : this.train){
            this.trainPanel.add(new WagonPanel((Interieur) c));
        }

    }



    public void update(){
        this.dessineTrain();
    }

    /*public void liaisonBottonsListener(ActionListener cntrl){
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


    }*/


}

/**
 * Le Paneau d'affichage qui contient les bouttons et l'etat des bandit et le fille d'actions
 */
class CommandePanel extends JPanel {

    public JButton action, braquage; // Dep = deplcament

    public CommandePanel(){
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.GREEN,2));

        this.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        DeplacementPanel  d = new DeplacementPanel();
        TirPanel t = new TirPanel();
        EtatPanel e = new EtatPanel();

        this.action = new JButton("Action");
        this.braquage = new JButton("Braquer");

        this.add(d);
        this.add(this.braquage);
        this.add(t);
        this.add(this.action);

        this.add(e);

    }

    class DeplacementPanel extends JPanel{
        private JButton gaucheDep,droiteDep,hautDep,basDep;
        public DeplacementPanel(){
            this.setLayout(new BorderLayout());

            JLabel tirLabel = new JLabel("Move");
            tirLabel.setPreferredSize(new Dimension(40,30));
            tirLabel.setHorizontalAlignment(SwingConstants.CENTER);

            gaucheDep = new JButton("<");
            droiteDep = new JButton(">");
            hautDep = new JButton("^");
            basDep = new JButton("v");

            this.add(tirLabel, BorderLayout.CENTER);
            this.add(gaucheDep, BorderLayout.WEST);
            this.add(droiteDep, BorderLayout.EAST);
            this.add(basDep, BorderLayout.SOUTH);
            this.add(hautDep, BorderLayout.NORTH);
        }

    }

    class TirPanel extends JPanel{

        private JButton tirGauche, tirDroit,tirHaut,tirBas;
        public TirPanel(){
            this.setLayout(new BorderLayout());

            JLabel tirLabel = new JLabel("Tir");
            tirLabel.setPreferredSize(new Dimension(40,30));
            tirLabel.setHorizontalAlignment(SwingConstants.CENTER);
            tirGauche = new JButton("<");
            tirDroit = new JButton(">");
            tirBas = new JButton("v");
            tirHaut = new JButton("^");

            this.add(tirLabel, BorderLayout.CENTER);
            this.add(tirGauche, BorderLayout.WEST);
            this.add(tirDroit, BorderLayout.EAST);
            this.add(tirBas, BorderLayout.SOUTH);
            this.add(tirHaut, BorderLayout.NORTH);
        }

    }

    /**
     * le panel qui va montrer c'est quelle phase, les feedback sur les actions ..
     */
    class EtatPanel extends JPanel{

        // A besoin d'une vision sur le train pour afficher les informations
        public EtatPanel (){
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(500,200));
        }

    }
}

/**
 * Va représenter graphiquement un wagon et son toit (wagon != locomotive)
 */
class WagonPanel extends JPanel{
    Interieur cabine; // et après cabine nous donne accès à son toit
    public WagonPanel(Interieur cabine){

        this.cabine = cabine;
        this.setPreferredSize(new Dimension(250,300));
        this.setBorder(new LineBorder(Color.BLACK));

        /*ArrayList<Personnage> persos = cabine.getPersoList();
        for(Personnage p : persos){
            if (p instanceof Marshall) g.setColor(new Color(36, 36, 229));
            else g.setColor(Color.RED);
            g.drawString(p.getSurnom(), Jeu.this.decalageXTrain + pos*largeurCabine + 30, y +30);
            g.setColor(Color.WHITE);
        }

        // on dessine les buttins
        for(int i = 0; i<c.getButtins().size(); i++){
            g.drawString(c.getButtins().get(i).toString(),Jeu.this.decalageXTrain + pos*largeurCabine + 30, y + hauteurCabine -3 + i*-30);
        }*/

    }

    class ButtinPanel{

    }

    class BanditPanel{

    }
}
