package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.CenteredPanel;
import Vue.ComposantsPerso.Police;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class LancerRejoindreServeur extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;
    JPanel northPanel = new JPanel();
    protected JPanel portPanel, nbOuIpPanel, panelCentrale;

    protected JLabel portLabel;

    protected AttenteConnexionClients attenteConnexionClients;

    protected JLabel decompteLancement = new JLabel();
    protected Bouttons.BouttonHorsJeu bouttonRetour;

    public LancerRejoindreServeur(Fenetre fenetre){
        this.fenetre = fenetre;
        this.decompteLancement.setFont(new Police());

        Police police = new Police();
        this.panelCentrale = new JPanel(new GridBagLayout());
        panelCentrale.setOpaque(false);
        this.portPanel = new JPanel(new BorderLayout());
        this.portPanel.setBackground(new Color(0xFFE7A1));
        this.portLabel = new JLabel("   Port   ");
        portLabel.setFont(police);

        this.bouttonRetour = new Bouttons.BouttonHorsJeu("<");
        this.nbOuIpPanel = new JPanel(new BorderLayout());
        this.nbOuIpPanel.setBackground(new Color(0xFFE7A1));


        this.setBackground(new Color(0xA99100));
        this.imageFond = new ImageIcon("src/assets/images/back_2e_ecran.png").getImage();

        //this.setLayout(new GridBagLayout());
        CenteredPanel.centerArrangement(this,this.bouttonRetour);

    }

    public void dispositionComposants(Bouttons.BouttonHorsJeu bouton, JPanel panel1, JPanel panel2){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        panelCentrale.add(bouton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentrale.add(panel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCentrale.add(panel2, gbc);

        this.add(panelCentrale, BorderLayout.CENTER);
    }

    public void disableConfigServer(Bouttons.BouttonHorsJeu bouton, JPanel textField1, JPanel textField2){
        bouton.setVisible(false);
        textField1.setVisible(false);
        textField2.setVisible(false);
        bouttonRetour.setVisible(false);
    }

    public JPanel getNbOuIpPanel() {
        return nbOuIpPanel;
    }

    public JPanel getPortPanel() {
        return portPanel;
    }

    public Bouttons.BouttonHorsJeu getBouttonRetour() {
        return bouttonRetour;
    }

    /**
     *
     * @param bouton
     * @param panel1 le port
     * @param panel2
     */
    public void setVueApresLancement(Bouttons.BouttonHorsJeu bouton, JPanel panel1, JPanel panel2){
        // après lancement

        this.disableConfigServer(bouton, panel1,panel2);

        this.setLayout(new BorderLayout());

        // C'est pour centerer le menu d'option
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        this.northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(200,100));
        westPanel.setPreferredSize(new Dimension(200,100));
        northPanel.setPreferredSize(new Dimension(100,70));
        southPanel.setPreferredSize(new Dimension(100,70));
        eastPanel.setOpaque(false);
        westPanel.setOpaque(false);
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);

        this.attenteConnexionClients = new AttenteConnexionClients();

        if (LancerRejoindreServeur.this instanceof LancerServeur){
            southPanel.add(((LancerServeur) LancerRejoindreServeur.this).getBouttonRejoindre()) ;
        }

        this.add(this.attenteConnexionClients,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(westPanel,BorderLayout.WEST);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(northPanel,BorderLayout.NORTH);

    }

    protected class AttenteConnexionClients extends JPanel{
        private JPanel connexionsPanel;
        private boolean connecte = false;
        public AttenteConnexionClients(){

            this.setPreferredSize(new Dimension(500,500));
            this.setBackground(new Color(0,0,0, 200));
            this.setLayout(new BorderLayout());

            JLabel titre = new JLabel("En attente de la connexion des joueurs  ");
            titre.setFont(new Police());
            titre.setForeground(Color.WHITE);
            titre.setAlignmentX(Component.CENTER_ALIGNMENT);

            JProgressBar animationAttente = new JProgressBar();
            animationAttente.setIndeterminate(true);
            animationAttente.setPreferredSize(new Dimension(50,20));
            animationAttente.setBackground(new Color(0xFDB531));

            JPanel tittreAnimation = new JPanel();
            tittreAnimation.setPreferredSize(new Dimension(0,100));
            tittreAnimation.setOpaque(false);

            this.connexionsPanel = new JPanel();
            connexionsPanel.setLayout(new BoxLayout(connexionsPanel,BoxLayout.Y_AXIS));
            connexionsPanel.setOpaque(false);

            tittreAnimation.add(titre);
            tittreAnimation.add(animationAttente);


            this.add(tittreAnimation,BorderLayout.NORTH);
            this.add(connexionsPanel,BorderLayout.CENTER);


        }

        /**
         *
         * @param ips
         */
        public void ajouterConnexion(ArrayList<String> ips){
            if (!connecte){
                for (String ip : ips){

                    JLabel label =  new JLabel("Joueur connecté sur la machine : " + ip + " ");
                    label.setFont(new Police());
                    label.setForeground(Color.WHITE);
                    label.setBackground(new Color(0xED000000, true));
                    label.setOpaque(true);
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    this.connexionsPanel.add(label);
                    connecte = true;

                }
            }else{
                JLabel label =  new JLabel("Joueur connecté sur la machine : " + ips.getLast() + " ");
                label.setFont(new Police());
                label.setForeground(Color.WHITE);
                label.setBackground(new Color(0xED000000, true));
                label.setOpaque(true);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                this.connexionsPanel.add(label);
            }

        }


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Corrected method call
        g.drawImage(this.imageFond, 0, -20, this.fenetre.getWidth(), this.fenetre.getHeight(), this);
    }

    public AttenteConnexionClients getAttenteConnexionClients(){
        return this.attenteConnexionClients;
    }

    /**
     * ajoute un client connecté à la liste
     * @param ips
     */
    public void ajoutConnexion (ArrayList<String> ips){
        this.attenteConnexionClients.ajouterConnexion(ips);
    }

    public void displayAttenteLancement(int attente){
        final int[] decompte = {attente/1000};
        decompteLancement.setText("Lancement de la partie dans " + decompte[0]);
        decompteLancement.setForeground(Color.BLACK);
        Police police = new Police();
        police.setPoliceTaille(25);
        decompteLancement.setFont(police);
        this.northPanel.add(decompteLancement);

        new Timer(1000,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // reduire l'affichage des seconds
                        decompte[0]--;
                        decompteLancement.setText("Lancement de la partie dans " + decompte[0]);
                    }
                }

        ).start();
    }


}
