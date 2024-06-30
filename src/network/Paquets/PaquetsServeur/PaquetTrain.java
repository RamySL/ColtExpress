package network.Paquets.PaquetsServeur;

import modele.trainEtComposantes.Train;
import network.Paquets.Paquet;

import java.io.Serial;

public class PaquetTrain extends Paquet {
    @Serial
    private static final long serialVersionUID = 174L;

    private Train train;

    public PaquetTrain(Train train){
        this.train  =train;

    }

    public Train getTrain() {
        return train;
    }
}
