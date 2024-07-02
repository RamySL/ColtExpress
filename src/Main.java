import Vue.Fenetre;
import controleur.ControleurTypePartie;
import multiJoueur.ControleurAccueilHost;

public class Main {
    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre();
        (new ControleurTypePartie(fenetre)).afficher();
    }


}