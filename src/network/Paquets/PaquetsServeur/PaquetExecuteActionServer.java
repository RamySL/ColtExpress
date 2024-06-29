package network.Paquets.PaquetsServeur;

import modele.actions.Action;
import modele.trainEtComposantes.Train;
import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetExecuteActionServer extends Paquet {
    @Serial
    private static final long serialVersionUID  = 155L;

    private int indiceExecuteur;
    private Action action;

    public PaquetExecuteActionServer(int indiceExecuteur,Action action){

        this.indiceExecuteur =  indiceExecuteur;
        this.action = action;
    }

    public int getIndiceExecuteur() {
        return indiceExecuteur;
    }

    public Action getAction() {
        return action;
    }
}
