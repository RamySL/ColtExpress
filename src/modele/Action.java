package modele;


public abstract class Action {
    protected Personnage executeur;
    public Action (Personnage executeur){
        this.executeur = executeur;
    }
    // retourne un feedBack sur l'action
    public abstract String executer();
}


