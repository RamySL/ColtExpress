package Vue;

import modele.butins.Butin;
import modele.personnages.Bandit;
import modele.personnages.Marshall;
import modele.personnages.Personnage;
import modele.trainEtComposantes.ComposanteTrain;
import modele.trainEtComposantes.Interieur;
import modele.trainEtComposantes.Toit;
import modele.trainEtComposantes.Train;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;


public class Jeu extends JPanel implements Observer {
    private Fenetre fenetre;
    private Train train;
    private Image imageFond;
    private CommandePanel cmdPanel;
    private JPanel panelCentrale;
    // liaison entre chaque bojet Personngae et son icone
    private Map<Personnage, ImageIcon> mapPersonnageIcone;

    private JPanel trainPanel;
    public Jeu(Train t, Fenetre fenetre, Map<Personnage, ImageIcon> mapPersonnageIcone) {
        this.train = t;
        this.fenetre = fenetre;
        this.mapPersonnageIcone = mapPersonnageIcone;

        this.imageFond = new ImageIcon("src/assets/images/jeuBack.jpg").getImage();

        for (Bandit b : train.getBandits()){
            b.addObserver(this);
        }
        train.getMarshall().addObserver(this);

        this.setLayout(new BorderLayout());
        cmdPanel = new CommandePanel();
        cmdPanel.setBorder(new LineBorder(Color.WHITE,3));

        JScrollPane scrollCmdPanel = this.getScrollHorizontal(cmdPanel);
        this.add(scrollCmdPanel, BorderLayout.NORTH);

        this.panelCentrale = new JPanel(new BorderLayout()); // pour pouvoir se placer à l'interieur d'un Jpanel c'est mieu
        panelCentrale.setBorder(new LineBorder(Color.WHITE,3));
        panelCentrale.setOpaque(false);

        this.trainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.dessineTrain(); // on dessine les wagon dans le panel du train
        this.trainPanel.setOpaque(false);
        JScrollPane trainScrollPanel = this.getScrollHorizontal(this.trainPanel);
        panelCentrale.add(trainScrollPanel, BorderLayout.CENTER);

        this.add(panelCentrale,BorderLayout.CENTER );
        this.setFocusable(true);


    }

    /**
     * methode coté multijoueur
     * @param train
     */
    public void actualiserTrain(Train train){
        this.train = train;
        this.dessineTrain();
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

    public Map<Personnage, ImageIcon> getMapPersonnageIcone(){
        return this.mapPersonnageIcone;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.imageFond,0,3,this.getWidth(),this.getHeight(),this);
    }



    public void update(){
        this.dessineTrain();
    }

    public CommandePanel getCmdPanel(){ return this.cmdPanel;}

    /**
     * Le Paneau d'affichage qui contient les bouttons et l'etat des bandit et le fille d'actions
     */
    public class CommandePanel extends JPanel {
        private PanneauBandits panneauBandits;
        private PhaseFeedPanel phaseFeedPanel;

        public CommandePanel(){

            this.setOpaque(false);
            //this.setBorder(new LineBorder(Color.GREEN,2));

            this.setLayout(new FlowLayout(FlowLayout.LEFT,8,0));
            this.panneauBandits = new PanneauBandits();
            phaseFeedPanel = new PhaseFeedPanel();

            this.add(panneauBandits);
            this.add(phaseFeedPanel);
            //this.add(e);

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




        /**
         * le panel qui va montrer c'est quelle phase, les feedback sur les actions ..
         */
        public class PhaseFeedPanel extends JPanel{

            // doit etre actualiser par le controleur
            private JLabel phase;
            private PlanififcationPanel planificationPanel;
            private FeedActionPanel feedActionPanel;
            public PhaseFeedPanel (){
                this.setBackground(new Color(0xFDB531));
                this.setLayout(new BorderLayout());

                this.phase = new JLabel("Phase de planification");
                this.phase.setForeground(Color.BLACK);
                phase.setFont(new Font("MV Boli", Font.BOLD, 17));
                this.setPreferredSize(new Dimension(700,250));
                this.phase.setHorizontalAlignment(SwingConstants.CENTER);


            }

            public void actuPhase(String txt){
                this.phase.setText(txt);
            }

            public void setPlanfication(Bandit planificateur){
                // on retire aussi phase
                this.removeAll();
                this.add(this.phase, BorderLayout.NORTH);
                this.planificationPanel = new PlanififcationPanel(planificateur);
                planificationPanel.setOpaque(false);
                JScrollPane planScroll = getScrollHorizontal(planificationPanel);
                planScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                this.add(planScroll, BorderLayout.CENTER);
                repaint();
                revalidate();
            }

            public void setAction(){
                this.removeAll();
                this.add(this.phase, BorderLayout.NORTH);
                this.feedActionPanel = new FeedActionPanel();
                feedActionPanel.setOpaque(false);
                JScrollPane feedScroll = getScrollHorizontal(feedActionPanel);
                feedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                this.add(feedScroll, BorderLayout.CENTER);
                repaint();
                revalidate();
            }

            public PlanififcationPanel getPlanificationPanel() {
                return planificationPanel;
            }

            public FeedActionPanel getFeedActionPanel() {
                return feedActionPanel;
            }

            public class PlanififcationPanel extends JPanel{
                Bandit banditPlanificateur;
                JPanel actionsPlanifiePanel;
                public PlanififcationPanel (Bandit banditPlanificateur){
                    //this.setBackground(Color.BLACK);
                    this.banditPlanificateur = banditPlanificateur;
                    this.setLayout(new FlowLayout(FlowLayout.LEFT));
                    this.actualiserPlanificateur(this.banditPlanificateur);
                    //this.add(actionsPlanifiePanel);


                }
                // quand le bandit qui planifi change
                public void actualiserPlanificateur(Bandit planificateur){

                    this.removeAll();
                    JPanel persoPanel = genererPersoIconeSurnom(planificateur,mapPersonnageIcone.get(planificateur));

                    this.actionsPlanifiePanel = new JPanel();
                    this.actionsPlanifiePanel.setOpaque(false);
                    actionsPlanifiePanel.setLayout(new BoxLayout(actionsPlanifiePanel,BoxLayout.Y_AXIS));

                    this.add(persoPanel);
                    this.add(actionsPlanifiePanel);

                    repaint();
                    revalidate();
                }
                // quand le bandit liste ses planifications
                public void actualisePlanfication(String descAction) {

                    JLabel actionLabel = new JLabel(descAction);
                    actionLabel.setForeground(Color.BLACK);
                    actionLabel.setFont(new Font("MV Boli", Font.BOLD, 13));
                    this.actionsPlanifiePanel.add(actionLabel);
                    // on actualise tout de suite
                    this.actionsPlanifiePanel.repaint();
                    this.actionsPlanifiePanel.revalidate();



                }

            }

            public class FeedActionPanel extends  JPanel{

                public FeedActionPanel(){
                    this.setOpaque(false);
                    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                }

                public void ajoutFeed (String feed){

                    JLabel feedLabel = new JLabel(feed);
                    feedLabel.setForeground(Color.BLACK);
                    feedLabel.setFont(new Font("MV Boli", Font.BOLD, 13));
                    this.add(feedLabel);
                    // on actualise tout de suite
                    this.revalidate();
                    this.repaint();



                }

            }

        }

        class PanneauBandits extends  JPanel{

            public PanneauBandits(){
                this.setLayout(new GridLayout(2,2));
                this.setOpaque(false);
                ArrayList<Bandit> banitListe = Jeu.this.train.getBandits();
                for (Bandit b : banitListe){
                    this.add(panneauBandit(b));
                }
            }

            public JPanel panneauBandit(Bandit b){

                JPanel panel = new JPanel();
                panel.setBackground(new Color(0xFDB531));
                panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                JLabel iconeBandit = new JLabel(Jeu.this.mapPersonnageIcone.get(b));
                JLabel surnomLabel = new JLabel( "Surnom : " + b.getSurnom());
                JLabel ballesLabel = new JLabel("Balles restantes : " + b.getNbBalles());
                JLabel scoreLabel = new JLabel("Score : " + b.score());

                surnomLabel.setForeground(Color.BLACK);
                ballesLabel.setForeground(Color.BLACK);
                scoreLabel.setForeground(Color.BLACK);

                surnomLabel.setFont(new Font("MV Boli", Font.BOLD, 13));
                ballesLabel.setFont(new Font("MV Boli", Font.BOLD, 13));
                scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 13));

                panel.add(iconeBandit);
                panel.add(surnomLabel);
                panel.add(ballesLabel);
                panel.add(scoreLabel);
                panel.setBorder(new LineBorder(Color.BLACK, 1));

                return panel;
            }
        }
    }

    /**
     * Va représenter graphiquement un wagon et son toit
     */
    class WagonPanel extends JPanel{
        Interieur cabine;
        ImageIcon iconeWagon = new ImageIcon("src/assets/images/wagon.png");
        public WagonPanel(Interieur cabine){
            this.setOpaque(false);
            this.cabine = cabine;

            this.setLayout(new BorderLayout());

            JPanel cabinePanel = new JPanel(new BorderLayout());
            cabinePanel.setOpaque(false);

            cabinePanel.setBorder(new LineBorder(Color.WHITE,5));

            JPanel solCabine = new JPanel(new FlowLayout(FlowLayout.LEFT));
            solCabine.setOpaque(false);

            JPanel toitPanel = new toitPanel(this.cabine.getToit());
            toitPanel.setOpaque(false);


            ButtinPanel buttinsPanel = new ButtinPanel(this.cabine.getButtins());
            PersoPanel persoPanel = new PersoPanel(this.cabine.getPersoList());
            buttinsPanel.setOpaque(false);
            persoPanel.setOpaque(false);

            JScrollPane buttinScroll = getScrollButtin(buttinsPanel);

            solCabine.add(buttinScroll);
            solCabine.add(persoPanel);

            JScrollPane scrollSolCabine = getScrollHorizontal(solCabine);

            this.setPreferredSize(new Dimension(280,350));


            cabinePanel.add(scrollSolCabine, BorderLayout.SOUTH);

            this.add(toitPanel,BorderLayout.NORTH);
            this.add(cabinePanel,BorderLayout.CENTER);

        }


        class ButtinPanel extends JPanel{
            ArrayList<Butin> butins;
            public ButtinPanel(ArrayList<Butin> butins){
                this.setOpaque(false);
                this.butins = butins;
                //this.setPreferredSize(new Dimension(50,100));
                this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
                this.add(Box.createVerticalGlue()); // pour que les buttin se remplissent à partir du bas

                for (Butin b : butins){

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
                this.setOpaque(false);
                this.setLayout(new FlowLayout(FlowLayout.LEFT,5,0)); // à cause du 0 dans verticale le marshall est en haut

                for (Personnage p : persos){
                    JPanel persoPanel = genererPersoIconeSurnom(p,Jeu.this.mapPersonnageIcone.get(p));
                    persoPanel.setOpaque(false);
                    this.add(persoPanel);

                }

            }

        }

        class toitPanel extends JPanel{
            Toit toit;

            public toitPanel(Toit toit){
                this.setOpaque(false);
                this.toit = toit;
                this.setLayout(new BorderLayout());

                JPanel conteneur = new JPanel(); // on a besoin d'un panel parceque si on veut mettre plusieur elemnt dans South il s'ecrase
                conteneur.setOpaque(false);

                ButtinPanel buttinsPanel = new ButtinPanel(this.toit.getButtins());
                buttinsPanel.setOpaque(false);
                JScrollPane buttinScroll = getScrollButtin(buttinsPanel);
                buttinScroll.setPreferredSize(new Dimension(64,80));

                PersoPanel persoPanel = new PersoPanel(this.toit.getPersoList());
                persoPanel.setOpaque(false);
                this.setPreferredSize(new Dimension(0,90));
                //this.setBorder(new LineBorder(Color.RED,5));
                conteneur.add(buttinScroll);
                conteneur.add(persoPanel);
                // Au cas ou on tombe dans une situation ou le nombre de badit et trop grand pour etre affiché on fait une barre de scroll
                JScrollPane scrollConteneur = getScrollHorizontal(conteneur);

                this.add(scrollConteneur, BorderLayout.SOUTH);


            }


        }

        /**
         * factorisation parceque on met buttin en scroll exactement de la meme maniere entre wagon et toit
         * @param buttinsPanel
         * @return
         */
        private JScrollPane getScrollButtin(ButtinPanel buttinsPanel) {
            JScrollPane buttinScroll = new JScrollPane(buttinsPanel);
            buttinScroll.setPreferredSize(new Dimension(64,64*4 -20)); // 64*64 pour les icones
            buttinScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            buttinScroll.setBorder(null);
            buttinScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            buttinScroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); //pour que la barre de scroll se mette à gauche
            buttinScroll.setOpaque(false);
            buttinScroll.getViewport().setOpaque(false);
            buttinScroll.getVerticalScrollBar().setOpaque(false);
            buttinScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));
            return buttinScroll;
        }




    }

    /**
     * factorisation du scroll pour les personnages
     * @param conteneur
     * @return
     */
    protected JScrollPane getScrollHorizontal(JPanel conteneur) {
        JScrollPane scrollConteneur = new JScrollPane(conteneur);
        scrollConteneur.setOpaque(false);
        scrollConteneur.getViewport().setOpaque(false);
        scrollConteneur.setBorder(null);
        // on veut qu'une barre horizontale
        scrollConteneur.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        // La barre s'affiche qua quand ça déborde
        scrollConteneur.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollConteneur.getHorizontalScrollBar().setPreferredSize(new Dimension(2,10));
        scrollConteneur.setBorder(null);
        scrollConteneur.getViewport().setViewPosition(new Point(0,5));
        scrollConteneur.getHorizontalScrollBar().setOpaque(false);
        return scrollConteneur;
    }

    private JPanel genererPersoIconeSurnom(Personnage planificateur, ImageIcon icone) {
        JPanel persoPanel = new JPanel(new BorderLayout());
        persoPanel.setOpaque(false);

        JLabel persoIcone = new JLabel(icone);
        JLabel persoLabel = new JLabel(planificateur.getSurnom());

        persoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (planificateur instanceof Marshall) persoLabel.setForeground(new Color(45, 45, 246));
        else persoLabel.setForeground(Color.BLACK);
        persoLabel.setFont(new Font("MV Boli", Font.BOLD, 13));

        persoPanel.add(persoIcone, BorderLayout.CENTER);
        persoPanel.add(persoLabel, BorderLayout.NORTH);
        return persoPanel;
    }




}
