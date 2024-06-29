package network.Paquets.PaquetsServeur;

import modele.trainEtComposantes.Train;
import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetExecuteActionServer extends Paquet {
    @Serial
    private static final long serialVersionUID  = 155L;

    private int indiceExecuteur;

    public PaquetExecuteActionServer(int indiceExecuteur){
        this.indiceExecuteur =  indiceExecuteur;
    }

    public int getIndiceExecuteur() {
        return indiceExecuteur;
    }
}
