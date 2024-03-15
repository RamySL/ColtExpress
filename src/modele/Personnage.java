package modele;

public abstract class Personnage {
    ComposanteTrain emplacement;
}

class Bandit extends Personnage {
    String surnom;
    Action[] actions;
    Buttin[] buttin;
    int nbBalles;
}

class Marshall extends Personnage{
    int nervosite;
    int nbBalles;
}

