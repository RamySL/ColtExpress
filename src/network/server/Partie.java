package network.server;

import Vue.Accueil;
import Vue.Fenetre;
import Vue.Jeu;
import modele.personnages.Personnage;
import modele.trainEtComposantes.Train;
import network.Paquets.PaquetsServeur.PaquetParametrePartie;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * le serveur creer la partie et la transet a ses clients
 * */
public class Partie {
    PaquetParametrePartie paquetParametrePartie;
    Map<Server.ClientHandler, Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> mapClientPersos;

    /**
     * il faut recuperer la fenetre de chaque client
     * @param paquetParametrePartie
     * @param mapClientPersos
     */
    public Partie(PaquetParametrePartie paquetParametrePartie, Map<Server.ClientHandler, Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> mapClientPersos, Fenetre fenetre){
        this.paquetParametrePartie = paquetParametrePartie;
        this.mapClientPersos = mapClientPersos;

        Map<Personnage, ImageIcon> mapPersonnageIcone = new HashMap<>();

        Train train = new Train (Integer.parseInt(paquetParametrePartie.getNbWagons()));
        train.ajouterMarshall(paquetParametrePartie.getNervositeMarshall());
        // invariant qui garde ça correcte c'est que le premier elt  de this.creationsJouers va corresependre au
        // premier Personnage dans la liste du train et le deuxieme au deuxieme etc
        for (Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos : mapClientPersos.values()){
            train.ajouterBandit(infos.getSurnom(),Integer.parseInt(paquetParametrePartie.getNbBallesBandits()));
            mapPersonnageIcone.put(train.getBandits().getLast(),infos.getIcone());
        }
        mapPersonnageIcone.put(train.getMarshall(),new ImageIcon("src/assets/images/sherif.png"));
        Jeu jeu = new Jeu(train, fenetre, mapPersonnageIcone);
        fenetre.ajouterFenetreJeu(jeu);
        fenetre.changerVue(fenetre.getJeuId());
//        CotroleurJeu cotroleurJeu = new CotroleurJeu(train,this.fenetre,Integer.parseInt(paquetParametrePartie.getNbActions()) );
//
//        BoucleJeu boucleJeu = new BoucleJeu(cotroleurJeu,Integer.parseInt(paquetParametrePartie.getNbManches()) );
//        boucleJeu.execute();
    }
}
