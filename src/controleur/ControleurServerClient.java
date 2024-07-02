package controleur;

import Vue.Fenetre;
import Vue.LancerServeur;
import Vue.RejoindreServeur;
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
    private LancerServeur lancerServeur;
    private RejoindreServeur rejoindreServeur;
    private int nbCnxRestantes = 0;

    public ControleurServerClient(Fenetre fenetre, ControleurAccueilHost controleurAccueilHost){
        this.fenetre = fenetre;
        this.controleurAccueilHost = controleurAccueilHost;

    }

    public void setLancerServeur(LancerServeur lancerServeur) {
        this.lancerServeur = lancerServeur;
    }

    public void setRejoindreServeur(RejoindreServeur rejoindreServeur) {
        this.rejoindreServeur = rejoindreServeur;
    }

    public void vueClient(){
        this.fenetre.changerVue(this.fenetre.getAccueilClientId());
    }

    public void vueHost (){
        this.fenetre.changerVue(this.fenetre.getAccueilId());
    }

    public void ajouterConnexion(int nbCnxRestantes, String ip){
        if (this.lancerServeur != null) {
            this.lancerServeur.ajoutConnexion(ip);
        }else {
            this.rejoindreServeur.ajoutConnexion(ip);
        }
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



}
