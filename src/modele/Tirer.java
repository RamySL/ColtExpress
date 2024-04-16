package modele;

import java.util.Random;

/* Pour tirer, on a besoin d'un tireur et d'une direction.
Lorsqu'un bandit tire son nbr de balle diminue.
Tandis que le nbr de balle du Marshall ne diminue pas.
 */
public class Tirer extends Action {

    private Direction direction;
    private Direction[] directionTable = Direction.values(); // on recupere ici une liste pour optimisation avec l'utilisation
    // de tirer avec le marshall

    public Tirer(Personnage tireur, Direction direction) {

        super(tireur);
        this.direction = direction;

    }

    private String tireDirectionInterieur(Direction d, Personnage tireur){
        // utilisé pour les tirs dans les cabines
        String feed ;
        ComposanteTrain voisin = tireur.getEmplacement().getVoisin(d);
        if (!voisin.getBanditListSauf(tireur).isEmpty()) {

            Bandit banditBlesse =  this.toucherBandit(voisin, tireur);
            feed = tireur.getSurnom() + " à tirer dans les cabines et a touché : " + banditBlesse;
        }else {
            feed = tireur.getSurnom() + " à tirer dans le vide ";
        }
        return feed;

    }

    private Bandit toucherBandit (ComposanteTrain cmp, Personnage tireur){
        //doit etre utilsée en s'assurant que cmp possede des bandit sur lesquelles tirer'
        Bandit banditBlesse = cmp.getBanditAlea(tireur);
        if (!banditBlesse.getButtins().isEmpty()) {
            Butin butinPerdu = banditBlesse.retirerButtin();
            cmp.ajouterButin(butinPerdu);

        }
        return banditBlesse;

    }

    public String toString(){
        return " Tir en direction " + this.direction;
    }

    public String executer() {
        String feed = "";
        if (this.executeur instanceof Marshall) {
            // le marshall tire dans une direction aleatoire entre droite et gauche
            Random rnd = new Random();
            int dirIntAlea = rnd.nextInt(2); // 0 pour gauche, 1 pour droite

            if (dirIntAlea == 0){
                feed = tireDirectionInterieur(Direction.Gauche, this.executeur);
            }else{
                feed = tireDirectionInterieur(Direction.Droite, this.executeur);
            }

        }
        //dans le cas du bandit
        else {
            if (this.executeur.getNbBalles() <= 0) {
                feed = this.executeur.getSurnom() + " n'a plus de balles";
            } else {


                ((Bandit) this.executeur).diminueBalle();

                switch (this.direction) {
                    case Droite:
                        // on regarde que si on est ni dans la locomotive ni sur son toit
                        if (this.executeur.getEmplacement() instanceof Locomotive ||
                                (this.executeur.getEmplacement() instanceof Toit) &&
                                        ((Toit) this.executeur.getEmplacement()).getCabine() instanceof Locomotive) {

                            if (!(this.executeur.getEmplacement() instanceof Toit))
                                feed = this.executeur.getSurnom() + " à tirer à droite dans le vide dans la locmotive ( nombres balles : " + this.executeur.getNbBalles();
                            else
                                feed = this.executeur.getSurnom() + " à tirer à droite dans le vide  sur le toit de la locomotive ( nombres balles : " + this.executeur.getNbBalles();


                        } else {
                            // si on était dans les cabines

                            if (this.executeur.getEmplacement() instanceof Interieur) {
                                feed = tireDirectionInterieur(Direction.Droite, this.executeur);

                            } else {// toit

                                // il faut parcourir tout les wagons à droite jusqu'a trouver un avec un bandit dedans
                                // et après mm principe
                                Toit toitCourant = ((Toit) (this.executeur.getEmplacement()).getVoisin(Direction.Droite)); // on recup le toit de droite
                                // tant ya pas de bandit sur le toit de coté et tant que on est pas encore arrivé à la locmotive
                                while (toitCourant.getBanditListSauf(this.executeur).isEmpty() && !(toitCourant.getCabine() instanceof Locomotive)) {
                                    toitCourant = (Toit) toitCourant.getVoisin(Direction.Droite);
                                }

                                if (!toitCourant.getBanditListSauf(this.executeur).isEmpty()) {
                                    Bandit banditBlesse = this.toucherBandit(toitCourant,this.executeur);
                                    feed = this.executeur.getSurnom() + " à tirer à droite sur le toit sur " + banditBlesse;
                                }else{
                                    feed = this.executeur.getSurnom() + " à tirer à droite sur le toit dans le vide";
                                }
                            }
                        }
                        break;

                    case Gauche:
                        // on regarde que si on est ni dans le dernier wagon ni sur son toit
                        if (this.executeur.getEmplacement() instanceof DernierWagon ||
                                (this.executeur.getEmplacement() instanceof Toit) &&
                                        ((Toit) this.executeur.getEmplacement()).getCabine() instanceof DernierWagon) {

                            if (!(this.executeur.getEmplacement() instanceof Toit))
                                feed = this.executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de tire à gauche";
                            else
                                feed = this.executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de tire à gauche";


                        } else {
                            // si on était dans les cabines

                            if (this.executeur.getEmplacement() instanceof Interieur) {
                                feed = tireDirectionInterieur(Direction.Gauche, this.executeur);

                            } else {

                                // il faut parcourir tout les wagons à droite jusqu'a trouver un avec un bandit dedans
                                // et après mm principe
                                Toit toitCourant = ((Toit) (this.executeur.getEmplacement()).getVoisin(Direction.Gauche)); // on recup le toit de droite
                                while (toitCourant.getBanditListSauf(this.executeur).isEmpty() && !(toitCourant.getCabine() instanceof DernierWagon)) {
                                    toitCourant = (Toit) toitCourant.getVoisin(Direction.Gauche);
                                }

                                if (!toitCourant.getBanditListSauf(this.executeur).isEmpty()) {
                                    Bandit banditBlesse = this.toucherBandit(toitCourant,this.executeur);
                                    feed = this.executeur.getSurnom() + " à tirer à gauche sur le toit sur " + banditBlesse;
                                }else{
                                    feed = this.executeur.getSurnom() + " à tirer à gauche sur le toit dans le vide";
                                }
                            }
                        }

                        break;


                    case Haut:
                        if (this.executeur.getEmplacement() instanceof Toit) {

                            Toit toit = (Toit) (this.executeur.getEmplacement());
                            if (!toit.getBanditListSauf(this.executeur).isEmpty()) {

                                Bandit banditBlesse = this.toucherBandit(toit,this.executeur);
                                feed = this.executeur.getSurnom() + " à tirer dans sa position sur le toit et a touché " + banditBlesse;

                            }else{
                                feed = this.executeur.getSurnom() + " à tirer dans sa position dans le vide";
                            }

                        } else {

                            Toit toitDuWagon = ((Interieur) this.executeur.getEmplacement()).getToit();
                            if (!toitDuWagon.getBanditListSauf(this.executeur).isEmpty()) {

                                Bandit banditBlesse = this.toucherBandit(toitDuWagon,this.executeur);
                                feed = this.executeur.getSurnom() + " à tirer vers le toit et a touché " + banditBlesse;

                            }else{
                                feed = this.executeur.getSurnom() + " à tirer vers le toit dans le vide";

                            }

                        }

                        break;

                    case Bas:
                        if (this.executeur.getEmplacement() instanceof Toit) {

                            Interieur cabine = ((Toit) this.executeur.getEmplacement()).getCabine();

                            if (!cabine.getBanditListSauf(this.executeur).isEmpty()) {
                                Bandit banditBlesse = this.toucherBandit(cabine,this.executeur);
                                feed = this.executeur.getSurnom() + " à tirer vers l'interieur et a touché " + banditBlesse;
                            }else {
                                feed = this.executeur.getSurnom() + " à tirer vers l'interieur dans le vide";
                            }

                        } else {

                            Interieur cabine = ((Interieur) this.executeur.getEmplacement());
                            if (!cabine.getBanditListSauf(this.executeur).isEmpty()) {
                                Bandit banditBlesse = this.toucherBandit(cabine,this.executeur);
                                feed = this.executeur.getSurnom() + " à tirer vers la cabine et a touché " + banditBlesse;

                            }else {
                                feed = this.executeur.getSurnom() + " à tirer dans sa position dans les cabines dans le vide";
                            }



                        }
                        break;
                }
            }

        }
        return feed;
    }

}
