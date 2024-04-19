package modele;

public enum Direction {
    Haut, Bas, Droite, Gauche;

    public String toString() {
        return this.name().toLowerCase();
    }

}

