package network.Paquets.PaquetsClients;

import modele.actions.Action;
import network.Paquets.Paquet;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Queue;

/**
 * la liste des actions planifiées
 */
public class PaquetListePlanififcation extends Paquet {
    @Serial
    private static final long serialVersionUID  = 16L;
    private Queue<Action> listeAction;
    public PaquetListePlanififcation (Queue<Action> listeAction){
        this.listeAction = listeAction;
    }

    public Queue<Action> getListeAction() {
        return listeAction;
    }
}
