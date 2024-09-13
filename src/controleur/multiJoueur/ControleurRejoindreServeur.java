package controleur.multiJoueur;

import Vue.Fenetre;
import Vue.RejoindreServeur;
import network.client.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurRejoindreServeur implements ActionListener {
    private Fenetre fenetre;
    private RejoindreServeur rejoindreServeur;
    private ControleurServerClient controleurServerClient;
    public ControleurRejoindreServeur(Fenetre fenetre, RejoindreServeur rejoindreServeur, ControleurServerClient controleurServerClient){
        this.fenetre = fenetre;
        this.rejoindreServeur = rejoindreServeur;
        this.controleurServerClient = controleurServerClient;
        this.rejoindreServeur.liasonControleur(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.rejoindreServeur.getBouttonRejoindre()){

            new Thread(() -> {
                String serverAddress = this.rejoindreServeur.getIpServer().getText(); // Server address
                int serverPort = Integer.parseInt(this.rejoindreServeur.getPortServer().getText()); // Server port
                Client client = new Client(serverAddress, serverPort, this.controleurServerClient);
                this.controleurServerClient.setClient(client);
                client.start();

            }).start();

            this.rejoindreServeur.getBouttonRejoindre().setEnabled(false);
            this.rejoindreServeur.setVueApresLancement(this.rejoindreServeur.getBouttonRejoindre(), this.rejoindreServeur.getPortPanel()
                                                        ,this.rejoindreServeur.getNbOuIpPanel());
        }

        if (e.getSource() == this.rejoindreServeur.getBouttonRetour()){
            this.fenetre.changerVue(this.fenetre.getChoixLancerRejoindreId());
        }
    }
}
