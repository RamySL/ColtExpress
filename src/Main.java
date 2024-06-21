import Vue.Fenetre;
import controleur.ControleurAccueilHost;

public class Main {
    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre();
        ControleurAccueilHost controleuAccueil = new ControleurAccueilHost(fenetre);
        controleuAccueil.lancer();
    }
}