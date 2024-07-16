package multiJoueur;

import Vue.Fenetre;
import Vue.LancerServeur;
import network.client.Client;
import network.server.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;

public class ControleurLancerServeur implements ActionListener {
    private LancerServeur lancerServeur;
    private Fenetre fenetre;
    private int nbCnxRestantes = 0;
    private Client client;

    private ControleurServerClient controleurServerClient;

    public ControleurLancerServeur (Fenetre fenetre, LancerServeur lancerServeur,ControleurServerClient controleurServerClient){
        this.lancerServeur = lancerServeur;
        this.fenetre = fenetre;
        this.controleurServerClient = controleurServerClient;
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
            this.lancerServeur.setVueApresLancement(this.lancerServeur.getBouttonLancer(), this.lancerServeur.getPortPanel(), this.lancerServeur.getNbOuIpPanel());
            // enable l'attente
//            this.lancerServeur.getAttenteJoueurLabel().setText("En attente de la connexion de tous les joueurs ( restant " + (server.getMaxPlayers() - server.getNbJoueurConnecte()) + ")");
        }else if (e.getSource() == this.lancerServeur.getBouttonRejoindre()){
            new Thread(() -> {
                String serverAddress ="localhost"; // Server address
                int serverPort = Integer.parseInt(this.lancerServeur.getPortServer().getText()); // Server port
                this.client = new Client(serverAddress, serverPort, this.controleurServerClient);
                this.controleurServerClient.setClient(this.client);
                client.start();


            }).start();
            this.lancerServeur.getBouttonRejoindre().setEnabled(false);

        }

        if (e.getSource() == this.lancerServeur.getBouttonRetour()){
            this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());
        }

    }
}
