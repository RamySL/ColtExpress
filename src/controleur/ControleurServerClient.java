package controleur;

import Vue.Fenetre;
import Vue.OnLineSettigs;
import network.client.Client;
import network.server.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * - le en attente doit s'afficher après avoir lancé le serveur donc dans le if du boutton
 * - après on peut le mettre en attribut et verifier si c'est null
 * - soit laisser le serveur modifier le gui
 */

public class ControleurServerClient implements ActionListener {
    private OnLineSettigs olSettings;

    Fenetre fenetre;

    public ControleurServerClient(OnLineSettigs olSettings, Fenetre fenetre){

        this.olSettings = olSettings;
        this.fenetre = fenetre;
    }

    public void updateNbJoueurConnecte (int n){
        this.olSettings.getAttenteJoueurLabel().setText("En attente de la connexion de tous les joueurs ( restant " + (n) + ")");

    }

    public void vueClient(){
        this.fenetre.changerVue(this.fenetre.getAccueilClientId());
    }

    public void vueHost (){
        this.fenetre.changerVue(this.fenetre.getAccueilId());
    }

    public void setControleurAccueilClient() {
         new ControleurAccueilClient(this.fenetre);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Server server;
        if (e.getSource() == this.olSettings.getLunchButton()){

            int port = Integer.parseInt(this.olSettings.getPortServer()); // Port to listen on
            int maxPlayers = Integer.parseInt(this.olSettings.getNbJoueur()); // Number of players needed to start the game
            server = new Server(port, maxPlayers);

            new Thread(server::start).start(); // c'est une manière plus concise pour les lambda expressions, Server::start est pris comme
                                                // une implementation de run() de Runnable parceque ça prend aucun param et retourne void

            this.olSettings.getLunchButton().setEnabled(false);
            this.olSettings.getAttenteJoueurLabel().setText("En attente de la connexion de tous les joueurs ( restant " + (server.getMaxPlayers() - server.getNbJoueurConnecte()) + ")");
        }

        if (e.getSource() == this.olSettings.getJoinButton()){
            new Thread(() -> {
                String serverAddress =this.olSettings.getIpServerClient(); // Server address
                int serverPort = Integer.parseInt(this.olSettings.getPortServerClient()); // Server port
                Client client = new Client(serverAddress, serverPort, this);
                client.start();
            }).start();
            this.olSettings.getJoinButton().setEnabled(false);


        }

    }

}
