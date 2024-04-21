package modele.actions;
//!! tout est bon

import modele.*;
import modele.butins.Butin;
import modele.personnages.Bandit;
import modele.personnages.Personnage;
import modele.trainEtComposantes.*;

/**
 * un tir possede une direction Haut, Bas, Gauche, Droite
 */
public class Tirer extends Action {

    private Direction direction;

    public Tirer(Personnage tireur, Direction direction) {
        super(tireur);
        this.direction = direction;

    }

    public String toString(){
        return " Tir en direction " + this.direction;
    }

    /**
     * execute le tir pour le bandit qu'il l'a déclenché si il a assez de balles
     * et pour le marshall dans tout les cas
     * @return feedback
     */
    public String executer() {
        String feed;

        if (executeur instanceof Bandit && ((Bandit)executeur).getNbBalles() > 0) ((Bandit) executeur).diminueBalle();
        else if (executeur instanceof Bandit) {
            return executeur.getSurnom() + " n'a plus de balles";
        }
        feed = switch (this.direction) {
                case Droite -> new TirerAdroite().executer();
                case Gauche -> new TirerAGauche().executer();
                case Haut -> new TirerHaut().executer();
                case Bas -> new TirerBas().executer();
            };

        return feed;
    }

    /**
     * execute un tir lorsque le personnage est à l'interieur du train
     * @param d direction du tir
     * @param tireur
     * @return feedback
     */
    private String tireDansCabine(Direction d, Personnage tireur){
        // utilisé pour les tirs dans les cabines
        String feed ;
        ComposanteTrain voisin = tireur.getEmplacement().getVoisin(d);
        if (!voisin.getBanditListSauf(tireur).isEmpty()) {

            Bandit banditBlesse =  this.toucherBandit(voisin, tireur);
            feed = tireur.getSurnom() + " à tirer vers " + d + " dans les cabines et a touché : " + banditBlesse.getSurnom();
        }else {
            feed = tireur.getSurnom() + " à tirer vers " + d + " dans les cabines dans le vide ";
        }
        return feed;

    }

    /**
     * renvoie un bandit aleatoire present dans cmp en lui enlevant un buttin aleatoire et le mettant dans cmp
     * ! précondition : cmp possede un bandit dedans
     * @param cmp composante du train vers laquelle pointe la direction du tir
     * @param tireur
     * @return bandit touché par le tir
     */
    private Bandit toucherBandit (ComposanteTrain cmp, Personnage tireur){

        Bandit banditBlesse = cmp.getBanditAlea(tireur);
        if (!banditBlesse.getButtins().isEmpty()) {
            Butin butinPerdu = banditBlesse.retirerButtin();
            cmp.ajouterButin(butinPerdu);

        }
        return banditBlesse;

    }

    /**
     * Factorise differentes duplications constatées entre les tires dans les différentes directions
     * ou on constate deux à traiter pour le tir soit un tir se fait sur le toit soit à l'interieur du train
     */
    private abstract class TirerVersDirection {
        protected ComposanteTrain emplacement;

        public TirerVersDirection() {
            this.emplacement = executeur.getEmplacement();
        }

        /**
         * declenche la methode appropriée selon qu'on soit sur le toit ou pas
         * @return feedback
         */
        public String executer() {
            if (emplacement instanceof Toit) {
                return TireSurToit((Toit) emplacement);
            } else {
                return TireDansCabine((Interieur) emplacement);
            }
        }

        /**
         * un tir sur le toit vers la gauche ou la droite traverse tout les toits jusqu'au premier toit
         *  qui contient des bandits et touche un d'entre eux aleatoirement
         * @param toit
         * @return feedback
         */
        protected abstract String TireSurToit(Toit toit);
        protected abstract String TireDansCabine(Interieur cabine);
    }

    private class TirerAdroite extends TirerVersDirection {

        public String TireSurToit(Toit toit) {
            if (toit.getCabine() instanceof Locomotive) {
                return executeur.getSurnom() + " à tirer à droite dans le vide  sur le toit de la locomotive ";
            } else {

                Toit toitCourant = (Toit) toit.getVoisin(Direction.Droite); // on recup le toit de droite

                while (toitCourant.getBanditListSauf(executeur).isEmpty() && !(toitCourant.getCabine() instanceof Locomotive)) {
                    toitCourant = (Toit) toitCourant.getVoisin(Direction.Droite);
                }

                if (!toitCourant.getBanditListSauf(executeur).isEmpty()) {
                    Bandit banditBlesse = toucherBandit(toitCourant, executeur);
                    return executeur.getSurnom() + " à tirer à droite sur le toit sur " + banditBlesse.getSurnom();
                } else {
                    return executeur.getSurnom() + " à tirer à droite sur le toit dans le vide";
                }
            }
        }


        public String TireDansCabine(Interieur cabine){
            if (cabine instanceof Locomotive){
                return  executeur.getSurnom() + " à tirer à droite dans le vide dans la locmotive ";
            }else {
                return tireDansCabine(Direction.Droite, executeur);
            }
        }
    }


    private class TirerAGauche extends TirerVersDirection{

        public String TireSurToit(Toit toit) {
            if (toit.getCabine() instanceof DernierWagon) {
                return executeur.getSurnom() + " a tiré dans le vide à gauche sur le toit du dernier wagon";
            } else {

                Toit toitCourant = (Toit) toit.getVoisin(Direction.Gauche); // on recup le toit de droite

                while (toitCourant.getBanditListSauf(executeur).isEmpty() && !(toitCourant.getCabine() instanceof Locomotive)) {
                    toitCourant = (Toit) toitCourant.getVoisin(Direction.Gauche);
                }

                if (!toitCourant.getBanditListSauf(executeur).isEmpty()) {
                    Bandit banditBlesse = toucherBandit(toitCourant, executeur);
                    return executeur.getSurnom() + " à tirer à gauche sur le toit sur " + banditBlesse.getSurnom();
                } else {
                    return executeur.getSurnom() + " à tirer à gauche sur le toit dans le vide";
                }
            }
        }


        public String TireDansCabine(Interieur cabine){
            if (cabine instanceof DernierWagon){
                return  executeur.getSurnom() + " à tirer à gauche dans le vide dans le dernier wagon ";
            }else {
                return tireDansCabine(Direction.Gauche, executeur);
            }
        }


    }

    private class TirerHaut extends TirerVersDirection{

        public String TireSurToit(Toit toit) {

                if (!toit.getBanditListSauf(executeur).isEmpty()) {
                    Bandit banditBlesse = toucherBandit(toit,executeur);
                    return executeur.getSurnom() + " à tirer dans sa position sur le toit et a touché " + banditBlesse.getSurnom();

                }else{
                    return executeur.getSurnom() + " à tirer dans sa position sur le toit dans le vide";
                }

        }



        public String TireDansCabine(Interieur cabine){
                Toit toitDuWagon = cabine.getToit();
                if (!toitDuWagon.getBanditListSauf(executeur).isEmpty()) {

                    Bandit banditBlesse = toucherBandit(toitDuWagon,executeur);
                    return executeur.getSurnom() + " à tirer vers le toit et a touché " + banditBlesse.getSurnom();

                }else{
                    return executeur.getSurnom() + " à tirer vers le toit dans le vide";

                }
        }


    }

    private class TirerBas extends TirerVersDirection {

        public String TireSurToit(Toit toit) {

            Interieur cabine = toit.getCabine();

            if (!cabine.getBanditListSauf(executeur).isEmpty()) {
                Bandit banditBlesse = toucherBandit(cabine, executeur);
                return executeur.getSurnom() + " à tirer vers l'interieur et a touché " + banditBlesse.getSurnom();
            } else {
                return executeur.getSurnom() + " à tirer vers l'interieur dans le vide";
            }
        }


        public String TireDansCabine(Interieur cabine) {

            if (!cabine.getBanditListSauf(executeur).isEmpty()) {
                Bandit banditBlesse = toucherBandit(cabine, executeur);
                return executeur.getSurnom() + " à tirer vers la cabine et a touché " + banditBlesse.getSurnom();

            } else {
                return executeur.getSurnom() + " à tirer dans sa position dans dans le vide";
            }
        }
    }


}