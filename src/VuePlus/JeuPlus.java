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
        c.setBorder(new LineBorder(Color.WHITE,3));
        this.add(c, BorderLayout.NORTH);


        JPanel panelCentrale = new JPanel(new BorderLayout()); // pour pouvoir se placer à l'interieur d'un Jpanel c'est mieu
        panelCentrale.setBackground(Color.BLACK);
        panelCentrale.setBorder(new LineBorder(Color.WHITE,3));

        JPanel northPanelTrain = new JPanel(); // pour centrer le dessin du train
        northPanelTrain.setBackground(Color.BLACK);
        northPanelTrain.setPreferredSize(new Dimension(0,30));
        panelCentrale.add(northPanelTrain, BorderLayout.NORTH);

        this.trainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //this.trainPanel.setBorder(new LineBorder(Color.BLUE,2));
        this.trainPanel.setBackground(Color.BLACK);
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
        //this.setBorder(new LineBorder(Color.GREEN,2));

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

        this.setLayout(new BorderLayout());

        JPanel cabinePanel = new JPanel(new BorderLayout());
        cabinePanel.setBackground(Color.BLACK);
        cabinePanel.setBorder(new LineBorder(Color.WHITE));

        JPanel toitPanel = new toitPanel(this.cabine.getToit());
        //toitPanel.setBackground(Color.BLUE);
        //toitPanel.setPreferredSize(new Dimension(0,100));


        ButtinPanel buttinsPanel = new ButtinPanel(this.cabine.getButtins());
        PersoPanel persoPanel = new PersoPanel(this.cabine.getPersoList());
        buttinsPanel.setBackground(Color.BLACK);
        persoPanel.setBackground(Color.BLACK);

        this.setPreferredSize(new Dimension(280,350));

        cabinePanel.add(buttinsPanel, BorderLayout.SOUTH);
        cabinePanel.add(persoPanel, BorderLayout.EAST);

        this.add(toitPanel, BorderLayout.NORTH);
        this.add(cabinePanel,BorderLayout.CENTER);

    }

    class ButtinPanel extends JPanel{
        ArrayList<Buttin> buttins;
        public ButtinPanel(ArrayList<Buttin> buttins){
            this.buttins = buttins;

            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

            for (Buttin b : buttins){
                JLabel buttinLabel = new JLabel(b.toString());
                buttinLabel.setForeground(Color.WHITE);
                this.add(buttinLabel);
            }

        }

    }

    class PersoPanel extends JPanel{
        ArrayList<Personnage> persos;
        public PersoPanel(ArrayList<Personnage> persos){
            this.persos = persos;

            this.setLayout(new FlowLayout(FlowLayout.LEFT,5,0)); // à cause du 0 dans verticale le marshall est en haut

            for (Personnage p : persos){
                JLabel persoLabel = new JLabel(p.getSurnom());
//                persoLabel.setBackground(Color.BLACK);
                persoLabel.setForeground(Color.WHITE);

                this.add(persoLabel);
            }
            this.setBackground(Color.BLACK);


        }

    }

    class toitPanel extends JPanel{
        Toit toit;

        public toitPanel(Toit toit){
            this.setBackground(Color.BLACK);
            this.toit = toit;
            this.setLayout(new BorderLayout());

            JPanel conteneur = new JPanel(); // on a besoin d'un panel parceque si on veut mettre plusieur elemnt dans South il s'ecrase
            conteneur.setBackground(Color.black);

            ButtinPanel buttinsPanel = new ButtinPanel(this.toit.getButtins());
            PersoPanel persoPanel = new PersoPanel(this.toit.getPersoList());
            this.setPreferredSize(new Dimension(0,70));
            //this.setBorder(new LineBorder(Color.RED,5));
            conteneur.add(buttinsPanel);
            conteneur.add(persoPanel);
            this.add(conteneur, BorderLayout.SOUTH);


//            if (! (WagonPanel.this.cabine instanceof Locomotive) ){
//
//                this.setPreferredSize(new Dimension(0,70));
//                //this.setBorder(new LineBorder(Color.RED,5));
//                conteneur.add(buttinsPanel);
//                conteneur.add(persoPanel);
//            }else {
//
//                JPanel chemine = new JPanel();
//                chemine.setPreferredSize(new Dimension(30,70));
//                chemine.setBorder(new LineBorder(Color.BLACK, 1));
//
//                conteneur.add(buttinsPanel);
//                conteneur.add(persoPanel);
//                conteneur.add(chemine);
//
//            }




        }
    }


}
