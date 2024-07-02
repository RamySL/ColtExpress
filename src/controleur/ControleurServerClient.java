package controleur;

import Vue.Fenetre;
import network.client.Client;
import network.server.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * - le en attente doit s'afficher après avoir lancé le serveur donc dans le if du boutton
 * - après on peut le mettre en attribut et verifier si c'est null
 * - soit laisser le serveur modifier le gui
 */

public class ControleurServerClient {
    private Fenetre fenetre;
    private  Client client;
    private ControleurAccueilHost controleurAccueilHost;
    private ControleurLancerServeur controleurLancerServeur;
    private ControleurRejoindreServeur controleurRejoindreServeur;
    private int nbCnxRestantes = 0;

    public ControleurServerClient(Fenetre fenetre, ControleurAccueilHost controleurAccueilHost,
                                  ControleurLancerServeur controleurLancerServeur, ControleurRejoindreServeur controleurRejoindreServeur){
        this.fenetre = fenetre;
        this.controleurAccueilHost = controleurAccueilHost;
        this.controleurLancerServeur = controleurLancerServeur;
        this.controleurRejoindreServeur = controleurRejoindreServeur;
    }

    public void vueClient(){
        this.fenetre.changerVue(this.fenetre.getAccueilClientId());
    }

    public void vueHost (){
        this.fenetre.changerVue(this.fenetre.getAccueilId());
    }

    public void ajouterConnexion(int nbCnxRestantes, String ip){
        this.controleurLancerServeur.getLancerServeur().ajoutConnexion(ip);
        this.nbCnxRestantes = nbCnxRestantes;
    }

    /**
     * le set se fait quand tout le monde est connectés
     */
    public void setControleurAccueilClient() {
         if (this.client != null){
             new ControleurAccueilClient(this.fenetre,this.client);
         }
    }

    public Client getClient() {
        return client;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }

    public ControleurAccueilHost getControleurAccueil() {
        return controleurAccueilHost;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        Server server;
//        if (e.getSource() == this.olSettings.getLunchButton()){
//
//        }
//
//        if (e.getSource() == this.olSettings.getJoinButton()){
//            new Thread(() -> {
//                String serverAddress =this.olSettings.getIpServerClient(); // Server address
//                int serverPort = Integer.parseInt(this.olSettings.getPortServerClient()); // Server port
//                this.client = new Client(serverAddress, serverPort, this);
//                this.client.start();
//            }).start();
//            this.olSettings.getJoinButton().setEnabled(false);
//
//
//        }
//
//    }

}
