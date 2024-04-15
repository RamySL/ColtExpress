package Vue;

import Vue.Bouttons.BouttonsJeu;
import controleur.CotroleurJeu;
import modele.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Il faut que jeuPlus soit une fenetre independantte pour eviter les probleme de gestion de threads
 */
/* !!! L'actualisation des infos de bandit pour que ça soit optimisé peut etre faur pas la mettre dans dessineTrain */
public class Jeu extends JPanel implements Observer {
    private Fenetre fenetre;
    Train train;

    CommandePanel cmdPanel;
    JPanel panelCentrale;

    // liaison entre chaque bojet Personngae et son icone
    Map<Personnage, ImageIcon> mapPersonnageIcone;

    private JPanel trainPanel;
    public Jeu(Train t, Fenetre fenetre, Map<Personnage, ImageIcon> mapPersonnageIcone) {
        this.train = t;
        this.fenetre = fenetre;
        this.mapPersonnageIcone = mapPersonnageIcone;

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
        // si le nombre de wagons ne peut etre affiché en entier on scroll horizontalement

        this.trainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //this.trainPanel.setBorder(new LineBorder(Color.BLUE,2));
        this.trainPanel.setBackground(Color.BLACK);
        this.dessineTrain(); // on dessine les wagon dans le panel du train
        JScrollPane trainScrollPanel = new JScrollPane(this.trainPanel);
        trainScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        trainScrollPanel.setBorder(null);
        panelCentrale.add(trainScrollPanel, BorderLayout.CENTER);

        this.add(panelCentrale,BorderLayout.CENTER );

    }

    public void liaisonCommandesControleur(CotroleurJeu controleur){
        this.cmdPanel.liaisonBouttonsControleur(controleur);
    }
    public void dessineTrain(){
        this.trainPanel.removeAll();
        this.cmdPanel.getPanneauBandit().removeAll();
        for (ComposanteTrain c : this.train){
            this.trainPanel.add(new WagonPanel((Interieur) c));
        }
        this.cmdPanel.setPanneauBandit();
        repaint();
        revalidate();

    }



    public void update(){
        this.dessineTrain();
    }

    public CommandePanel getCmdPanel(){ return this.cmdPanel;}

    /**
     * Le Paneau d'affichage qui contient les bouttons et l'etat des bandit et le fille d'actions
     */
    public class CommandePanel extends JPanel {

        private BouttonsJeu.BouttonAction action;
        private BouttonsJeu.BouttonBraquage braquage;
        ArrayList<BouttonsJeu> bouttonsCommande = new ArrayList<>();
        private PanneauBandits panneauBandits;
        private PhaseFeedPanel phaseFeedPanel;

        public CommandePanel(){

            this.setBackground(Color.BLACK);
            //this.setBorder(new LineBorder(Color.GREEN,2));

            this.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
            DeplacementPanel  d = new DeplacementPanel();
            TirPanel t = new TirPanel();
            this.panneauBandits = new PanneauBandits();
            phaseFeedPanel = new PhaseFeedPanel();

            this.action = new BouttonsJeu.BouttonAction("Action");
            this.braquage = new BouttonsJeu.BouttonBraquage("Braquer");
            this.bouttonsCommande.add(this.action);
            this.bouttonsCommande.add(this.braquage);


            this.add(d);
            this.add(this.braquage);
            this.add(t);
            this.add(this.action);
            this.add(panneauBandits);
            this.add(phaseFeedPanel);
            //this.add(e);

        }

        public void liaisonBouttonsControleur(CotroleurJeu controleur){
            for(BouttonsJeu b : this.bouttonsCommande){
                b.addActionListener(controleur);
            }
        }

        public PanneauBandits getPanneauBandit (){ return this.panneauBandits;}
        public void setPanneauBandit() {
            // pour actualiser les infos du joeur
            for (Bandit b : Jeu.this.train.getBandits()){
                this.panneauBandits.add(this.panneauBandits.panneauBandit(b));
            }

        }

        public PhaseFeedPanel getPhaseFeedPanel() {
            return phaseFeedPanel;
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
        public class PhaseFeedPanel extends JPanel{

            // doit etre actualiser par le controleur
            JLabel phase;
            public PhaseFeedPanel (){
                this.phase = new JLabel("Phase de planification");
                this.setBackground(Color.WHITE);
                this.setPreferredSize(new Dimension(400,200));

                this.add(this.phase);
            }

            public void actuPhase(String txt){
                this.phase.setText(txt);

            }

        }

        class PanneauBandits extends  JPanel{

            public PanneauBandits(){
                this.setLayout(new FlowLayout(FlowLayout.LEFT));

                ArrayList<Bandit> banitListe = Jeu.this.train.getBandits();
                for (Bandit b : banitListe){
                    this.add(panneauBandit(b));
                }
            }

            public JPanel panneauBandit(Bandit b){

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                JLabel iconeBandit = new JLabel(Jeu.this.mapPersonnageIcone.get(b));
                JLabel surnomLabel = new JLabel( " Surnom : " + b.getSurnom());
                JLabel ballesLabel = new JLabel("Balles restantes : " + b.getNbBalles());
                JLabel scoreLabel = new JLabel("Score : " + b.score());

                panel.add(iconeBandit);
                panel.add(surnomLabel);
                panel.add(ballesLabel);
                panel.add(scoreLabel);
                panel.setBorder(new LineBorder(Color.GREEN, 2));

                return panel;
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

//            JScrollPane buttinScroll = new JScrollPane(buttinsPanel);
//            buttinScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//            buttinScroll.setBorder(null);

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
                //this.setPreferredSize(new Dimension(50,100));
                this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

                for (Buttin b : buttins){

                    JLabel buttinLabel = new JLabel( new ImageIcon("src/assets/images/" + b + ".png"));
                    buttinLabel.setForeground(Color.WHITE);
                    this.add(buttinLabel);
                }

            }

        }

        class PersoPanel extends JPanel{
            ArrayList<Personnage> persos;

            /**
             * une maniere plus simple de faire aurait été de donner le chemin de l'icone du perssonage mais je trouve
             * que ça peut etre un non respet de l'architecture MVC parceque le lien c'est quand même lié à la vue
             * @param persos
             */
            public PersoPanel(ArrayList<Personnage> persos){
                this.persos = persos;

                this.setLayout(new FlowLayout(FlowLayout.LEFT,5,0)); // à cause du 0 dans verticale le marshall est en haut

                for (Personnage p : persos){
                    if (!(p instanceof Marshall)) {
                        JPanel persoPanel = new JPanel(new BorderLayout());
                        persoPanel.setBackground(new Color(0x00000FF, true));

                        JLabel persoIcone = new JLabel(Jeu.this.mapPersonnageIcone.get(p));
                        JLabel persoLabel = new JLabel(p.getSurnom());
                        //persoLabel.setPreferredSize(new Dimension(0,10));
                        persoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        persoLabel.setForeground(Color.WHITE);
                        persoLabel.setFont(new Font("MV Boli", Font.BOLD, 13));

                        persoPanel.add(persoIcone, BorderLayout.CENTER);
                        persoPanel.add(persoLabel, BorderLayout.NORTH);

                        this.add(persoPanel);
                    }else {
                        JPanel persoPanel = new JPanel(new BorderLayout());
                        persoPanel.setBackground(new Color(0x00000FF, true));

                        JLabel persoIcone = new JLabel(new ImageIcon("src/assets/images/sherif.png"));
                        JLabel persoLabel = new JLabel(p.getSurnom());

                        persoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        persoLabel.setForeground(Color.WHITE);
                        persoLabel.setFont(new Font("MV Boli", Font.BOLD, 13));

                        persoPanel.add(persoIcone, BorderLayout.CENTER);
                        persoPanel.add(persoLabel, BorderLayout.NORTH);

                        this.add(persoPanel);
                    }
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
                this.setPreferredSize(new Dimension(0,90));
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






}
