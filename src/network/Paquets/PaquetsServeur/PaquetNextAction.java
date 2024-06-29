package network.Paquets.PaquetsServeur;

import network.Paquets.Paquet;

import java.io.Serial;

/**
 * passez au prochain qui execute
 */
public class PaquetNextAction extends Paquet {
    @Serial
    private static final long serialVersionUID = 174L;
    private int indice;

    public PaquetNextAction(int indice){
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }
}
