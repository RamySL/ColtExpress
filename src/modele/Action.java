package modele;

public abstract class Action {

    protected Personnage executeur;

    public Action (Personnage executeur){
        this.executeur = executeur;
    }
    public abstract void executer();
}

class Tirer extends Action {

    private Personnage cible;

    public Tirer (Personnage tireur){
        super(tireur);
    }

    public void executer() {
    }
}

class Braquer extends Action {
    // braque doit savoir quel wagon, mais elle connait le joueur qui lui connait le wagon

    public Braquer (Personnage braqueur){
        super(braqueur);
    }
    public void executer() {
    }
}


