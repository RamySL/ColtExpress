package modele.personnages;

import modele.trainEtComposantes.ComposanteTrain;
import modele.Observable;

import java.io.Serializable;

/**
 * regroupe les objets qui interviennent pendant le déroulement de la partie (Marshall et Bandits pour l'instant)
 * étend observable parce que sa methode executer modifie le modèle
 */
public abstract class Personnage extends Observable implements Serializable {
    protected ComposanteTrain emplacement;
    protected String surnom;

    /**
     * un personnage est créé en indiquant son surnom et la position qu'il a dans le train
     * le composant du train qui va le contenir est notifié par cette même construction
     * @param emp emplacement du personnage dans le train
     * @param surnom surnom du personnage
     */
    public Personnage (ComposanteTrain emp, String surnom){

        this.emplacement = emp;
        this.surnom = surnom;
        this.emplacement.ajouterPersonnage(this);
    }

    public Personnage (String surnom){
        this.surnom = surnom;
    }

    /**
     * Enlève le personnage de son ancien emplacement et le met dans celui précisé en paramètre
     * @param nouvelleEmplacement la nouvelle position
     */
    public void changerEmplacement(ComposanteTrain nouvelleEmplacement){
        // on l'enleve de l'ancien emplacement
        this.emplacement.getPersoList().remove(this);
        this.emplacement = nouvelleEmplacement;
        this.emplacement.getPersoList().add(this);
    }

    public ComposanteTrain getEmplacement(){
        return this.emplacement;
    }

    public String getSurnom(){return this.surnom;}


}



