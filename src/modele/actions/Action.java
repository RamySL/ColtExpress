package modele.actions;
// tout est bon

import modele.personnages.Personnage;

import java.io.Serializable;

/**
 * Regroupe les actions possibles pour les personnages du jeu (deplacement, tir, braquage ..)
 */
public abstract class Action implements Serializable {
    protected Personnage executeur;

    /**
     *
     * @param executeur celui qui fait l'action
     */
    public Action (Personnage executeur){
        this.executeur = executeur;
    }
    // retourne un feedBack sur l'action

    /**
     * (exemple : indique qu'un braquage a bien réussie, une action de déplacement faite pour une direction impossible etc)
     * @return un feedback sur l'execution de l'action
     */
    public abstract String executer();

    public Personnage getExecuteur() {
        return executeur;
    }
}


