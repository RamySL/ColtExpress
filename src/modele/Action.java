package modele;

import java.util.ArrayList;
import java.util.Random;

public abstract class Action {

    protected Personnage executeur;

    public Action (Personnage executeur){
        this.executeur = executeur;
    }
    public abstract void executer();
}


