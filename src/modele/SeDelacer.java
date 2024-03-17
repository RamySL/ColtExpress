package modele;

public class SeDelacer extends Action {

    Direction direction;

    public SeDelacer(Personnage src, Direction direction) {
        super(src);
        this.direction = direction;
    }

    public void executer() {

        ComposanteTrain src = this.executeur.getEmplacement();

        switch (this.direction) {
            case Droite:
                if (src instanceof Toit && (((Toit) src).getCabine() instanceof Wagon) || src instanceof Wagon) { //&& ((Toit) src).getCabine() instanceof Locomotive){
                    // si on etait dans les cabines
                    if (src instanceof Wagon) {
                        this.executeur.setWagon(src.getTrain().getComposantes()[src.getPosition() + 1]);
                        System.out.println(this.executeur.getSurnom() + " s'est déplacer à droite");
                    } else {
                        // si on etait sur le toit on recuperere d'abbord la cabine à droite puis son toit
                        this.executeur.setWagon(((Cabine) src.getTrain().getComposantes()[src.getPosition() + 1]).getToit());
                        System.out.println(this.executeur.getSurnom() + " s'est déplacer à droite sur le toit");
                    }

                } else {
                    if (!(src instanceof Toit))
                        System.out.println(this.executeur.getSurnom() + " Vous êtes dans la locomotive, pas de déplacement à droite");
                    else
                        System.out.println(this.executeur.getSurnom() + " Vous êtes sur le toit de la locomotive, pas de déplacement à droite");
                }
                break;

            case Gauche:
                if (!(src.getPosition() == 0)) {
                    if (src instanceof Toit) {
                        this.executeur.setWagon(((Cabine) src.getTrain().getComposantes()[src.getPosition() - 1]).getToit());
                        System.out.println(this.executeur.getSurnom() + " s'est déplacer à gauche sur le toit");
                    } else {
                        this.executeur.setWagon((src.getTrain().getComposantes()[src.getPosition() - 1]));
                        System.out.println(this.executeur.getSurnom() + " s'est déplacer à gauche dans les wagon");
                    }
                } else {
                    if (!(src instanceof Toit))
                        System.out.println(this.executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de déplacement à gauche");
                    else
                        System.out.println(this.executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de déplacement à gauche");
                }
                break;


            case Haut:
                if (!(src instanceof Toit)) {
                    this.executeur.setWagon(((Cabine) src).getToit());
                    System.out.println(this.executeur.getSurnom() + " est monté sur le toit");
                } else {
                    System.out.println(this.executeur.getSurnom() + " Vous êtes deja sur le toit");
                }
                break;

            case Bas:
                if (src instanceof Toit) {
                    this.executeur.setWagon(((Toit) src).getCabine());
                    System.out.println(this.executeur.getSurnom() + " est descendu à l'interieur du wagon");
                } else {
                    System.out.println(this.executeur.getSurnom() + " vous ne pouvez pas descendre vous etes deja à l'inter");
                }
                break;
        }
    }
}
