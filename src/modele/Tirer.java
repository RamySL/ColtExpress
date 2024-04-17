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
            Bandit banditExecuteur = (Bandit) this.executeur;

            if (banditExecuteur.getNbBalles() <= 0) {
                feed = banditExecuteur.getSurnom() + " n'a plus de balles";
            } else {


                banditExecuteur.diminueBalle();

                switch (this.direction) {
                    case Droite:
                        // on regarde que si on est ni dans la locomotive ni sur son toit
                        if (banditExecuteur.getEmplacement() instanceof Locomotive ||
                                (banditExecuteur.getEmplacement() instanceof Toit) &&
                                        ((Toit) banditExecuteur.getEmplacement()).getCabine() instanceof Locomotive) {

                            if (!(banditExecuteur.getEmplacement() instanceof Toit))
                                feed = banditExecuteur.getSurnom() + " à tirer à droite dans le vide dans la locmotive ( nombres balles : " + banditExecuteur.getNbBalles();
                            else
                                feed = banditExecuteur.getSurnom() + " à tirer à droite dans le vide  sur le toit de la locomotive ( nombres balles : " + banditExecuteur.getNbBalles();


                        } else {
                            // si on était dans les cabines

                            if (banditExecuteur.getEmplacement() instanceof Interieur) {
                                feed = tireDirectionInterieur(Direction.Droite, banditExecuteur);

                            } else {// toit

                                // il faut parcourir tout les wagons à droite jusqu'a trouver un avec un bandit dedans
                                // et après mm principe
                                Toit toitCourant = ((Toit) (banditExecuteur.getEmplacement()).getVoisin(Direction.Droite)); // on recup le toit de droite
                                // tant ya pas de bandit sur le toit de coté et tant que on est pas encore arrivé à la locmotive
                                while (toitCourant.getBanditListSauf(banditExecuteur).isEmpty() && !(toitCourant.getCabine() instanceof Locomotive)) {
                                    toitCourant = (Toit) toitCourant.getVoisin(Direction.Droite);
                                }

                                if (!toitCourant.getBanditListSauf(banditExecuteur).isEmpty()) {
                                    Bandit banditBlesse = this.toucherBandit(toitCourant,banditExecuteur);
                                    feed = banditExecuteur.getSurnom() + " à tirer à droite sur le toit sur " + banditBlesse;
                                }else{
                                    feed = banditExecuteur.getSurnom() + " à tirer à droite sur le toit dans le vide";
                                }
                            }
                        }
                        break;

                    case Gauche:
                        // on regarde que si on est ni dans le dernier wagon ni sur son toit
                        if (banditExecuteur.getEmplacement() instanceof DernierWagon ||
                                (banditExecuteur.getEmplacement() instanceof Toit) &&
                                        ((Toit) banditExecuteur.getEmplacement()).getCabine() instanceof DernierWagon) {

                            if (!(banditExecuteur.getEmplacement() instanceof Toit))
                                feed = banditExecuteur.getSurnom() + " Vous êtes dans le dernier wagon, pas de tire à gauche";
                            else
                                feed = banditExecuteur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de tire à gauche";


                        } else {
                            // si on était dans les cabines

                            if (banditExecuteur.getEmplacement() instanceof Interieur) {
                                feed = tireDirectionInterieur(Direction.Gauche, banditExecuteur);

                            } else {

                                // il faut parcourir tout les wagons à droite jusqu'a trouver un avec un bandit dedans
                                // et après mm principe
                                Toit toitCourant = ((Toit) (banditExecuteur.getEmplacement()).getVoisin(Direction.Gauche)); // on recup le toit de droite
                                while (toitCourant.getBanditListSauf(banditExecuteur).isEmpty() && !(toitCourant.getCabine() instanceof DernierWagon)) {
                                    toitCourant = (Toit) toitCourant.getVoisin(Direction.Gauche);
                                }

                                if (!toitCourant.getBanditListSauf(banditExecuteur).isEmpty()) {
                                    Bandit banditBlesse = this.toucherBandit(toitCourant,banditExecuteur);
                                    feed = banditExecuteur.getSurnom() + " à tirer à gauche sur le toit sur " + banditBlesse;
                                }else{
                                    feed = banditExecuteur.getSurnom() + " à tirer à gauche sur le toit dans le vide";
                                }
                            }
                        }

                        break;


                    case Haut:
                        if (banditExecuteur.getEmplacement() instanceof Toit) {

                            Toit toit = (Toit) (banditExecuteur.getEmplacement());
                            if (!toit.getBanditListSauf(banditExecuteur).isEmpty()) {

                                Bandit banditBlesse = this.toucherBandit(toit,banditExecuteur);
                                feed = banditExecuteur.getSurnom() + " à tirer dans sa position sur le toit et a touché " + banditBlesse;

                            }else{
                                feed = banditExecuteur.getSurnom() + " à tirer dans sa position dans le vide";
                            }

                        } else {

                            Toit toitDuWagon = ((Interieur) banditExecuteur.getEmplacement()).getToit();
                            if (!toitDuWagon.getBanditListSauf(banditExecuteur).isEmpty()) {

                                Bandit banditBlesse = this.toucherBandit(toitDuWagon,banditExecuteur);
                                feed = banditExecuteur.getSurnom() + " à tirer vers le toit et a touché " + banditBlesse;

                            }else{
                                feed = banditExecuteur.getSurnom() + " à tirer vers le toit dans le vide";

                            }

                        }

                        break;

                    case Bas:
                        if (banditExecuteur.getEmplacement() instanceof Toit) {

                            Interieur cabine = ((Toit) banditExecuteur.getEmplacement()).getCabine();

                            if (!cabine.getBanditListSauf(banditExecuteur).isEmpty()) {
                                Bandit banditBlesse = this.toucherBandit(cabine,banditExecuteur);
                                feed = banditExecuteur.getSurnom() + " à tirer vers l'interieur et a touché " + banditBlesse;
                            }else {
                                feed = banditExecuteur.getSurnom() + " à tirer vers l'interieur dans le vide";
                            }

                        } else {

                            Interieur cabine = ((Interieur) banditExecuteur.getEmplacement());
                            if (!cabine.getBanditListSauf(banditExecuteur).isEmpty()) {
                                Bandit banditBlesse = this.toucherBandit(cabine,banditExecuteur);
                                feed = banditExecuteur.getSurnom() + " à tirer vers la cabine et a touché " + banditBlesse;

                            }else {
                                feed = banditExecuteur.getSurnom() + " à tirer dans sa position dans les cabines dans le vide";
                            }



                        }
                        break;
                }
            }

        }
        return feed;
    }

}
