package controleur;

import Vue.Fenetre;
import Vue.LancerServeur;
import network.client.Client;
import network.server.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurLancerServeur implements ActionListener {
    private LancerServeur lancerServeur;
    private Fenetre fenetre;
    private int nbCnxRestantes = 0;
    private Client client;
    private ControleurAccueilHost controleurAccueilHost;

    public ControleurLancerServeur (Fenetre fenetre, LancerServeur lancerServeur){
        this.lancerServeur = lancerServeur;
        this.fenetre = fenetre;
        this.controleurAccueilHost = controleurAccueilHost;
        this.lancerServeur.liasonControleur(this);
    }


    public void vueHost (){
        this.fenetre.changerVue(this.fenetre.getAccueilId());
    }

    public LancerServeur getLancerServeur() {
        return lancerServeur;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.lancerServeur.getBouttonLancer()){

            int port = Integer.parseInt(this.lancerServeur.getPortServer().getText()); // Port to listen on
            int maxPlayers = Integer.parseInt(this.lancerServeur.getNbJoueur().getText()); // Number of players needed to start the game
            Server server = new Server(port, maxPlayers);

            new Thread(server::start).start(); // c'est une manière plus concise pour les lambda expressions, Server::start est pris comme
            // une implementation de run() de Runnable parceque ça prend aucun param et retourne void
            this.lancerServeur.setVueApresLancement(this.lancerServeur.getBouttonLancer(), this.lancerServeur.getPortServer(), this.lancerServeur.getNbJoueur());
            // enable l'attente
//            this.lancerServeur.getAttenteJoueurLabel().setText("En attente de la connexion de tous les joueurs ( restant " + (server.getMaxPlayers() - server.getNbJoueurConnecte()) + ")");
        }

    }
}
