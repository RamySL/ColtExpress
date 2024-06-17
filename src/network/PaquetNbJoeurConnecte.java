package network;

public class PaquetNbJoeurConnecte extends Paquet{
    private int nbJoueurRestants;
    public static final long serialVersionUid = 1L;

    public PaquetNbJoeurConnecte(int nbJoueurRestants){
        this.nbJoueurRestants = nbJoueurRestants;
    }

    public int getNbJoueurRestants() {
        return nbJoueurRestants;
    }
}
