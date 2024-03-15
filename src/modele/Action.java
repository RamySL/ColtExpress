package modele;

enum Direction {
    Haut, Bas, Droite, Gauche;
}
public abstract class Action {
    Personnage executeur;

    public abstract void executer();
}

class Braquer extends Action{
    public void executer() {}
}
class Tirer extends Action{
    Personnage cible;
    public void executer(){}
}
class SeDeplacer extends Action{
    Direction direction;
    public void executer() {}
}
