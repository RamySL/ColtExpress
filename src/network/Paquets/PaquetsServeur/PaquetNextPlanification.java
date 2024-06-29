package network.Paquets.PaquetsServeur;

import modele.personnages.Bandit;
import network.Paquets.Paquet;

import java.io.Serial;

/**
 * dit au clients de passer au prochain planificateur
 */
public class PaquetNextPlanification extends Paquet {
    @Serial
    private static final long serialVersionUID = 174L;
    private int indice;

    public PaquetNextPlanification(int indice){
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }
}
