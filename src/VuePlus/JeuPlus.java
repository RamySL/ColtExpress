package VuePlus;

import Vue.Fenetre;
import Vue.Jeu;
import VuePlus.Bouttons.BouttonsJeu;
import controleur.ControleurPlus;
import modele.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class JeuPlus extends JPanel implements Observer {
    private FenetrePlus fenetre;
    Train train;

    CommandePanel cmdPanel;
    JPanel panelCentrale;

    private JPanel trainPanel;
    public JeuPlus(Train t, FenetrePlus fenetre) {
        this.train = t;
        this.fenetre = fenetre;

        for (Bandit b : train.getBandits()){
            b.addObserver(this);
        }
        train.getMarshall().addObserver(this);

        this.setLayout(new BorderLayout());
        cmdPanel = new CommandePanel();

        cmdPanel.setBorder(new LineBorder(Color.WHITE,3));
        this.add(cmdPanel, BorderLayout.NORTH);


        this.panelCentrale = new JPanel(new BorderLayout()); // pour pouvoir se placer à l'interieur d'un Jpanel c'est mieu
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

    public void liaisonCommandesControleur(ControleurPlus controleur){
        this.cmdPanel.liaisonBouttonsControleur(controleur);
    }
    public void dessineTrain(){
        this.trainPanel.removeAll();
        for (ComposanteTrain c : this.train){
            this.trainPanel.add(new WagonPanel((Interieur) c));
        }
        repaint();
        revalidate();

    }



    public void update(){
        this.dessineTrain();
    }


}

/**
 * Le Paneau d'affichage qui contient les bouttons et l'etat des bandit et le fille d'actions
 */
class CommandePanel extends JPanel {

    private BouttonsJeu.BouttonAction action;
    private BouttonsJeu.BouttonBraquage braquage;
    ArrayList<BouttonsJeu> bouttonsCommande = new ArrayList<>();

    public CommandePanel(){

        this.setBackground(Color.BLACK);
        //this.setBorder(new LineBorder(Color.GREEN,2));

        this.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        DeplacementPanel  d = new DeplacementPanel();
        TirPanel t = new TirPanel();
        EtatPanel e = new EtatPanel();

        this.action = new BouttonsJeu.BouttonAction("Action");
        this.braquage = new BouttonsJeu.BouttonBraquage("Braquer");
        this.bouttonsCommande.add(this.action);
        this.bouttonsCommande.add(this.braquage);


        this.add(d);
        this.add(this.braquage);
        this.add(t);
        this.add(this.action);

        this.add(e);

    }

    public void liaisonBouttonsControleur(ControleurPlus controleur){
        for(BouttonsJeu b : this.bouttonsCommande){
            b.addActionListener(controleur);
        }
    }

    class DeplacementPanel extends JPanel{
        private BouttonsJeu.BouttonDeplacement gaucheDep,droiteDep,hautDep,basDep;
        public DeplacementPanel(){
            this.setLayout(new BorderLayout());

            JLabel tirLabel = new JLabel("Move");
            tirLabel.setPreferredSize(new Dimension(40,30));
            tirLabel.setHorizontalAlignment(SwingConstants.CENTER);

            gaucheDep = new BouttonsJeu.BouttonDeplacement(Direction.Gauche,"<");
            droiteDep = new BouttonsJeu.BouttonDeplacement(Direction.Droite,">");
            hautDep =new BouttonsJeu.BouttonDeplacement(Direction.Haut,"^");
            basDep = new BouttonsJeu.BouttonDeplacement(Direction.Bas,"v");
            CommandePanel.this.bouttonsCommande.add(gaucheDep);
            CommandePanel.this.bouttonsCommande.add(droiteDep);
            CommandePanel.this.bouttonsCommande.add(basDep);
            CommandePanel.this.bouttonsCommande.add(hautDep);

            this.add(tirLabel, BorderLayout.CENTER);
            this.add(gaucheDep, BorderLayout.WEST);
            this.add(droiteDep, BorderLayout.EAST);
            this.add(basDep, BorderLayout.SOUTH);
            this.add(hautDep, BorderLayout.NORTH);
        }

    }

    class TirPanel extends JPanel{

        private BouttonsJeu.BouttonTir tirGauche, tirDroit,tirHaut,tirBas;
        public TirPanel(){
            this.setLayout(new BorderLayout());

            JLabel tirLabel = new JLabel("Tir");
            tirLabel.setPreferredSize(new Dimension(40,30));
            tirLabel.setHorizontalAlignment(SwingConstants.CENTER);
            tirGauche = new BouttonsJeu.BouttonTir(Direction.Gauche,"<");
            tirDroit = new BouttonsJeu.BouttonTir(Direction.Droite,">");
            tirBas = new BouttonsJeu.BouttonTir(Direction.Bas,"v");
            tirHaut = new BouttonsJeu.BouttonTir(Direction.Haut,"^");
            CommandePanel.this.bouttonsCommande.add(tirGauche);
            CommandePanel.this.bouttonsCommande.add(tirDroit);
            CommandePanel.this.bouttonsCommande.add(tirBas);
            CommandePanel.this.bouttonsCommande.add(tirHaut);

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
        JPanel solCabine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        solCabine.setBackground(Color.BLACK);

        JPanel toitPanel = new toitPanel(this.cabine.getToit());


        ButtinPanel buttinsPanel = new ButtinPanel(this.cabine.getButtins());
        PersoPanel persoPanel = new PersoPanel(this.cabine.getPersoList());
        buttinsPanel.setBackground(Color.BLACK);
        persoPanel.setBackground(Color.BLACK);

        solCabine.add(buttinsPanel);
        solCabine.add(persoPanel);

        this.setPreferredSize(new Dimension(280,350));

        cabinePanel.add(solCabine, BorderLayout.SOUTH);

        this.add(toitPanel,BorderLayout.NORTH);
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
            // Au cas ou on tombe dans une situation ou le nombre de badit et trop grand pour etre affiché on fait une barre de scroll
            JScrollPane scrollConteneur = new JScrollPane(conteneur);
            // on veut qu'une barre horizontale
            scrollConteneur.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            // La barre s'affiche qua quand ça déborde
            scrollConteneur.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollConteneur.getHorizontalScrollBar().setPreferredSize(new Dimension(2,10));

            scrollConteneur.setBorder(null);

            scrollConteneur.getViewport().setViewPosition(new Point(0,5));
            scrollConteneur.getHorizontalScrollBar().setBackground(Color.BLACK);

            this.add(scrollConteneur, BorderLayout.SOUTH);


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
