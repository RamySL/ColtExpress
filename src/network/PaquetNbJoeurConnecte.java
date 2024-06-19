package network;

import java.io.Serial;

public class PaquetNbJoeurConnecte extends Paquet{
    @Serial
    private static final long serialVersionUID  = 11L; //1.1
    private int nbJoueurRestants;

    public PaquetNbJoeurConnecte(int nbJoueurRestants){
        this.nbJoueurRestants = nbJoueurRestants;
    }

    public int getNbJoueurRestants() {
        return nbJoueurRestants;
    }
}
