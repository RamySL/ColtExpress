package modele;
// !! probleme normalement le personnage ne doit pas avoir accès à tous les personnage de son emplacement
/**
 * regroupe les objets qui interviennent pendant le déroulement de la partie (Marshall et Bandits pour l'instant)
 * étend observable parce que sa methode executer modifie le modèle
 */
public abstract class Personnage extends Observable{
    ComposanteTrain emplacement;
    String surnom;

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

    /**
     *
     * @return un feedback sur l'execution de l'action
     */
    public abstract String executer();
    /**
     * Enlève le personnage de son ancien emplacement et le met dans celui précisé en paramètre
     * @param nouvelleEmplacement la nouvelle position
     */
    public void changerEmplacement(ComposanteTrain nouvelleEmplacement){
        // on l'enleve de l'ancien emplacement
        this.emplacement.persoList.remove(this);
        this.emplacement = nouvelleEmplacement;
        this.emplacement.persoList.add(this);
    }

    public ComposanteTrain getEmplacement(){
        return this.emplacement;
    }

    public String getSurnom(){return this.surnom;}


}



