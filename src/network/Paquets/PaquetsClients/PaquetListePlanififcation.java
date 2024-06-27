package network.Paquets.PaquetsClients;

import modele.actions.Action;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;

/**
 * la liste des actions planifiées
 */
public class PaquetListePlanififcation extends Paquet {
    @Serial
    private static final long serialVersionUID  = 16L;
    private ArrayList<Action> listeAction;
    public PaquetListePlanififcation (ArrayList<Action> listeAction){
        this.listeAction = listeAction;
    }

    public ArrayList<Action> getListeAction() {
        return listeAction;
    }
}
