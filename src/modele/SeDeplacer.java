package modele;

public class SeDeplacer extends Action {
    Direction direction;
    public SeDeplacer(Personnage src, Direction direction) {
        super(src);
        this.direction = direction;
    }

    // !!!! ESSAYE UN GET AVEC DIRECTION LE PLUS HAUT POSSIBLE

    public void executer() {

        ComposanteTrain src = this.executeur.getEmplacement();

        switch (this.direction) {
            case Droite:
                // on regarde que si on est ni dans la locmotive ni sur son toit
                if (src instanceof Locomotive || (src instanceof Toit) && ((Toit) src).getCabine() instanceof Locomotive){ //&& ((Toit) src).getCabine() instanceof Locomotive){
                    if (!(src instanceof Toit))
                        System.out.println(this.executeur.getSurnom() + " Vous êtes dans la locomotive, pas de déplacement à droite");
                    else
                        System.out.println(this.executeur.getSurnom() + " Vous êtes sur le toit de la locomotive, pas de déplacement à droite");


                } else {
                    // si on etait dans les cabines
                    if (src instanceof Interieur) {
                        this.executeur.setWagon(src.getVoisin(Direction.Droite));
                        System.out.println(this.executeur.getSurnom() + " s'est déplacer à droite");
                    } else {
                        // si on etait sur le toit on recuperere d'abbord la cabine à droite puis son toit
                        // on est passé de ça : ((Cabine) src.getTrain().getComposantes()[src.getPosition() + 1]).getToit()
                        this.executeur.setWagon(src.getVoisin(Direction.Droite));
                        System.out.println(this.executeur.getSurnom() + " s'est déplacer à droite sur le toit");
                    }
                }
                break;

            case Gauche:
                if (src instanceof DernierWagon || (src instanceof Toit) && ((Toit) src).getCabine() instanceof DernierWagon) {
                    if (!(src instanceof Toit))
                        System.out.println(this.executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de déplacement à gauche");
                    else
                        System.out.println(this.executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de déplacement à gauche");
                } else {
                    if (src instanceof DernierWagon) {
                        this.executeur.setWagon(src.getVoisin(Direction.Gauche));
                        System.out.println(this.executeur.getSurnom() + " s'est déplacé à gauche sur le toit");
                    } else {
                        this.executeur.setWagon(src.getVoisin(Direction.Gauche));
                        System.out.println(this.executeur.getSurnom() + " s'est déplacé à gauche dans les wagons");
                    }

                }
                break;


            case Haut:
                if (src instanceof Interieur) {
                    this.executeur.setWagon( ((Interieur) src).getToit() );
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
                    System.out.println(this.executeur.getSurnom() + " vous ne pouvez pas descendre vous etes deja à l'interieur");
                }
                break;
        }
    }
}
