package modele;

/**
 * deplacement vers droite,gauche,haut ou bas dans le train
 */
public class SeDeplacer extends Action {
    Direction direction;

    /**
     *
     * @param src celui qui va faire le déplacement
     * @param direction direction du déplacement
     */
    public SeDeplacer(Personnage src, Direction direction) {
        super(src);
        this.direction = direction;
    }

    /**
     *
     * @return feedback sur le déplacement
     */
    public String executer() {

        ComposanteTrain src = this.executeur.getEmplacement();
        String feed = "";

        switch (this.direction) {
            case Droite:
                // on regarde que si on est ni dans la locmotive ni sur son toit
                if (src instanceof Locomotive ||
                        (src instanceof Toit) &&
                                ((Toit) src).getCabine() instanceof Locomotive){
                    if (!(src instanceof Toit))
                        feed = this.executeur.getSurnom() + " Vous êtes dans la locomotive, pas de déplacement à droite";
                    else
                        feed = this.executeur.getSurnom() + " Vous êtes sur le toit de la locomotive, pas de déplacement à droite";


                } else {
                    // si on etait dans les cabines
                    if (src instanceof Interieur) {
                        this.executeur.changerEmplacement(src.getVoisin(Direction.Droite));
                        feed = this.executeur.getSurnom() + " s'est déplacé à droite";
                    } else {
                        // si on etait sur le toit on recuperere d'abbord la cabine à droite puis son toit
                        this.executeur.changerEmplacement(src.getVoisin(Direction.Droite));
                        feed = this.executeur.getSurnom() + " s'est déplacé à droite sur le toit";
                    }
                }
                break;

            case Gauche:
                if (src instanceof DernierWagon ||
                        (src instanceof Toit) &&
                                ((Toit) src).getCabine() instanceof DernierWagon) {

                    if (!(src instanceof Toit))
                        feed = this.executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de déplacement à gauche";
                    else
                        feed = this.executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de déplacement à gauche";
                } else {
                    if (src instanceof Toit) {
                        this.executeur.changerEmplacement(src.getVoisin(Direction.Gauche));
                        feed = this.executeur.getSurnom() + " s'est déplacé à gauche sur le toit";
                    } else {
                        this.executeur.changerEmplacement(src.getVoisin(Direction.Gauche));
                        feed = this.executeur.getSurnom() + " s'est déplacé à gauche dans les wagons";
                    }

                }
                break;


            case Haut:
                if (src instanceof Interieur) {
                    this.executeur.changerEmplacement( ((Interieur) src).getToit() );
                    feed = this.executeur.getSurnom() + " est monté sur le toit";
                } else {
                    feed = this.executeur.getSurnom() + " Vous êtes deja sur le toit";
                }
                break;

            case Bas:
                if (src instanceof Toit) {
                    this.executeur.changerEmplacement(((Toit) src).getCabine());
                    feed = this.executeur.getSurnom() + " est descendu à l'interieur du wagon";
                } else {
                    feed = this.executeur.getSurnom() + " vous ne pouvez pas descendre vous etes deja à l'interieur";
                }
                break;
        }
        return feed;
    }

    public String toString(){
        return " Deplacement en direction " + this.direction;
    }


}
