package modele.actions;
//!! tout est bon

import modele.*;
import modele.personnages.Personnage;
import modele.trainEtComposantes.*;

/**
 * Permet le deplacement des personnages dans le train dans les 4 directions
 * pour réaliser ça elle s'appuie sur ses 4 classes internes qui chacunes d'entre eux gere un deplacement dans une des
 * 4 directions
 */
public class SeDeplacer extends Action {
    private Direction direction;
    public SeDeplacer(Personnage executeur, Direction direction) {
        super(executeur);
        this.direction = direction;


    }

    /**
     * Déplace le personnage vers laquelle elle pointe vers la direction si c'est possible
     * @return feedback sur le déplacement
     */
    public String executer() {
        String feed = switch (this.direction) {
            case Droite -> new SeDeplacerADroite().executer();
            case Gauche -> new SeDeplacerAGauche().executer();
            case Haut -> new SeDeplacerHaut().executer();
            case Bas -> new SeDeplacerBas().executer();
        };
        return feed;
    }
    public String toString(){
        return " Deplacement en direction " + this.direction;
    }

    private class SeDeplacerADroite {
        private ComposanteTrain ancienEmplacement;
        public SeDeplacerADroite(){
            this.ancienEmplacement = executeur.getEmplacement();
        }

        /**
         * traite tous les cas possible pour une demande de déplacement à droite du personnage
         * Si le personage est dans la locomotive ou sur son toit elle ne le déplace pas et renvoi le feedback approprié
         * sinon recupere le voisin droit de l'ancien emplacement et l'affecte au personnage
         * @return feedback
         */
        public String executer(){

            String feed;
            if (ancienEmplacement instanceof Locomotive ||
                    (ancienEmplacement instanceof Toit) &&
                            ((Toit) ancienEmplacement).getCabine() instanceof Locomotive){
                if (!(ancienEmplacement instanceof Toit))
                    feed = executeur.getSurnom() + " Vous êtes dans la locomotive, pas de déplacement à droite";
                else
                    feed = executeur.getSurnom() + " Vous êtes sur le toit de la locomotive, pas de déplacement à droite";


            } else {

                if (ancienEmplacement instanceof Interieur) {
                    executeur.changerEmplacement(ancienEmplacement.getVoisin(Direction.Droite));
                    feed = executeur.getSurnom() + " s'est déplacé à droite dans les cabines";
                } else {

                    executeur.changerEmplacement(ancienEmplacement.getVoisin(Direction.Droite));
                    feed = executeur.getSurnom() + " s'est déplacé à droite sur le toit";
                }
            }
            return feed;
        }

    }
    private class SeDeplacerAGauche {
        private ComposanteTrain ancienEmplacement;
        public SeDeplacerAGauche(){
            this.ancienEmplacement = executeur.getEmplacement();
        }

        /**
         * traite tous les cas possible pour une demande de déplacement à gauche du personnage
         * Si le personage est dans le dernier wagon ou sur son toit elle ne le déplace pas et renvoi le feedback approprié
         * sinon recupere le voisin gauche de l'ancien emplacement et l'affecte au personnage
         * @return feedback
         */
        public String executer(){
            String feed;
            if (ancienEmplacement instanceof DernierWagon ||
                    (ancienEmplacement instanceof Toit) &&
                            ((Toit) ancienEmplacement).getCabine() instanceof DernierWagon) {

                if (!(ancienEmplacement instanceof Toit))
                    feed = executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de déplacement à gauche";
                else
                    feed = executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de déplacement à gauche";
            } else {
                if (ancienEmplacement instanceof Toit) {
                    executeur.changerEmplacement(ancienEmplacement.getVoisin(Direction.Gauche));
                    feed = executeur.getSurnom() + " s'est déplacé à gauche sur le toit";
                } else {
                    executeur.changerEmplacement(ancienEmplacement.getVoisin(Direction.Gauche));
                    feed = executeur.getSurnom() + " s'est déplacé à gauche dans les cabines";
                }

            }
            return feed;
        }

    }
    private class SeDeplacerHaut{
        private ComposanteTrain ancienEmplacement;
        public SeDeplacerHaut(){
            this.ancienEmplacement = executeur.getEmplacement();
        }

        /**
         * traite tous les cas possible pour une demande de déplacement vers le haut du personnage
         * Si le personage est sur le toit elle ne le déplace pas et renvoi le feedback approprié
         * sinon recupere le toit de l'emplacement et l'affecte au personnage
         * @return feedback
         */
        public String executer(){
            String feed;
            if (ancienEmplacement instanceof Interieur) {
                executeur.changerEmplacement( ((Interieur) ancienEmplacement).getToit() );
                feed = executeur.getSurnom() + " est monté sur le toit";
            } else {
                feed = executeur.getSurnom() + " Vous êtes deja sur le toit";
            }
            return feed;
        }
    }
    private class SeDeplacerBas {
        private ComposanteTrain ancienEmplacement;
        public SeDeplacerBas(){
            this.ancienEmplacement = executeur.getEmplacement();
        }

        /**
         * traite tous les cas possible pour une demande de déplacement vers le bas du personnage
         * Si le personage est dans les cabines elle ne le déplace pas et renvoi le feedback approprié
         * sinon recupere la cabine associée au toit  l'affecte au personnage
         * @return feedback
         */
        public String executer(){
            String feed;
            if (ancienEmplacement instanceof Toit) {
                executeur.changerEmplacement(((Toit) ancienEmplacement).getCabine());
                feed = executeur.getSurnom() + " est descendu à l'interieur de la cabine";
            } else {
                feed = executeur.getSurnom() + " vous êtes deja à l'interieur";
            }

            return feed;
        }
    }


}
