package Vue;

import Vue.ComposantsPerso.Bouttons;
import Vue.ComposantsPerso.Police;
import controleur.ControleurLancerServeur;

import javax.swing.*;
import java.awt.*;

public abstract class LancerRejoindreServeur extends JPanel {
    private Fenetre fenetre;
    private Image imageFond;
    private AttenteConnexionClients attenteConnexionClients;

    public LancerRejoindreServeur(Fenetre fenetre){
        this.fenetre = fenetre;

        this.setBackground(new Color(0xA99100));
        this.imageFond = new ImageIcon("src/assets/images/back_2e_ecran.png").getImage();

        this.setLayout(new GridBagLayout());

    }

    public void dispositionComposants(Bouttons.BouttonHorsJeu bouton, JTextField textField1, JTextField textField2){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        this.add(bouton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(textField1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(textField2, gbc);
    }

    public abstract void liasonControleur(ControleurLancerServeur controleurLancerServeur);

    public void disableConfigServer(Bouttons.BouttonHorsJeu bouton, JTextField textField1, JTextField textField2){
        bouton.setVisible(false);
        textField1.setVisible(false);
        textField2.setVisible(false);
    }


    /**
     *
     * @param bouton
     * @param textField1 le port
     * @param textField2
     */
    public void setVueApresLancement(Bouttons.BouttonHorsJeu bouton, JTextField textField1, JTextField textField2){
        // après lancement

        this.disableConfigServer(bouton, textField1,textField2);

        this.setLayout(new BorderLayout());

        // C'est pour centerer le menu d'option
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel northPanel = new JPanel();
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

        this.add(this.attenteConnexionClients,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(westPanel,BorderLayout.WEST);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(northPanel,BorderLayout.NORTH);
    }

    private class AttenteConnexionClients extends JPanel{
        public AttenteConnexionClients(){
            JPanel tittreAnimation = new JPanel();
            tittreAnimation.setOpaque(false);
            this.setPreferredSize(new Dimension(500,500));
            this.setBackground(new Color(0,0,0, 200));

            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

            JLabel titre = new JLabel("En attente de la connexion des joueurs  ");
            titre.setFont(new Police());
            titre.setForeground(Color.WHITE);
            titre.setAlignmentX(Component.CENTER_ALIGNMENT);

            JProgressBar animationAttente = new JProgressBar();
            animationAttente.setIndeterminate(true);
            animationAttente.setPreferredSize(new Dimension(50,20));
            animationAttente.setBackground(new Color(0xFDB531));

            tittreAnimation.add(titre);
            tittreAnimation.add(animationAttente);

            this.add(tittreAnimation);

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
     * @param ip
     */
    public void ajoutConnexion (String ip){
        this.add(new JLabel("Joueur connecté sur la machine : " + ip));
    }


}
