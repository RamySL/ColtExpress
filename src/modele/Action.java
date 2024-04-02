package modele;

import java.util.ArrayList;
import java.util.Random;

public abstract class Action {

    protected Personnage executeur;

    public Action (Personnage executeur){
        this.executeur = executeur;
    }
    public abstract void executer();
}

/* Pour tirer, on a besoin d'un tireur et d'une direction.
Lorsqu'un bandit tire son nbr de balle diminue.
Tandis que le nbr de balle du Marshall ne diminue pas.
 */
class Tirer extends Action {

    private Direction direction;

    public Tirer (Personnage tireur, Direction direction){

        super(tireur);
        this.direction = direction;
    }

    public void executer() {
        if( this.executeur instanceof Marshall ){}
        //dans le cas du bandit
        else{
            switch (this.direction) {
                case Droite:
                    // on regarde que si on est ni dans la locomotive ni sur son toit
                    if (this.executeur.getEmplacement() instanceof Locomotive || (this.executeur.getEmplacement() instanceof Toit) && ((Toit) this.executeur.getEmplacement()).getCabine() instanceof Locomotive){
                        if (!(this.executeur.getEmplacement() instanceof Toit))
                            System.out.println(this.executeur.getSurnom() + " Vous êtes dans la locomotive, pas de tire à droite");
                        else
                            System.out.println(this.executeur.getSurnom() + " Vous êtes sur le toit de la locomotive, pas de tire à droite");


                    } else {
                        // si on était dans les cabines
                        ((Bandit)this.executeur).diminueBalle();
                        if (this.executeur.getEmplacement() instanceof Interieur) {
                            System.out.println(this.executeur.getSurnom() + "a tiré à droite");
                            ComposanteTrain voisinDroite = this.executeur.getEmplacement().getVoisin(Direction.Droite);
                            if(!voisinDroite.getBanditList((Bandit) this.executeur).isEmpty()){
                                Bandit banditBlesse =voisinDroite.getBanditAlea((Bandit) this.executeur);
                                if(!banditBlesse.getButtins().isEmpty()){
                                    Buttin buttinPerdu = banditBlesse.retirerButtin();
                                    voisinDroite.ajouterButin(buttinPerdu);
                                }
                            }


                        }
                        //else {
                            // si on etait sur le toit on recuperere d'abord la cabine à droite puis son toit
                            // on est passé de ça : ((Cabine) src.getTrain().getComposantes()[src.getPosition() + 1]).getToit()
                            //this.executeur.setWagon(src.getVoisin(Direction.Droite));
                            //System.out.println(this.executeur.getSurnom() + " s'est déplacé à droite sur le toit");
                        //}
                    }
                    break;

                case Gauche:
                    // on regarde que si on est ni dans le dernier wagon ni sur son toit
                    if (this.executeur.getEmplacement() instanceof DernierWagon || (this.executeur.getEmplacement() instanceof Toit) && ((Toit) this.executeur.getEmplacement()).getCabine() instanceof DernierWagon){
                        if (!(this.executeur.getEmplacement() instanceof Toit))
                            System.out.println(this.executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de tire à gauche");
                        else
                            System.out.println(this.executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de tire à gauche");


                    } else {
                        // si on était dans les cabines
                        ((Bandit)this.executeur).diminueBalle();
                        if (this.executeur.getEmplacement() instanceof Interieur) {
                            System.out.println(this.executeur.getSurnom() + "a tiré à gauche");
                            ComposanteTrain voisinGauche = this.executeur.getEmplacement().getVoisin(Direction.Gauche);
                            if(!voisinGauche.getBanditList((Bandit) this.executeur).isEmpty()){
                                Bandit banditBlesse =voisinGauche.getBanditAlea((Bandit) this.executeur);
                                if(!banditBlesse.getButtins().isEmpty()){
                                    Buttin buttinPerdu = banditBlesse.retirerButtin();
                                    voisinGauche.ajouterButin(buttinPerdu);
                                }
                            }


                        }
                        //else {
                            // si on etait sur le toit on recurere d'abord la cabine à droite puis son toit
                            // on est passé de ça : ((Cabine) src.getTrain().getComposantes()[src.getPosition() + 1]).getToit()
                            //this.executeur.setWagon(src.getVoisin(Direction.Droite));
                            //System.out.println(this.executeur.getSurnom() + " s'est déplacé à droite sur le toit");
                        //}
                    }
                    break;


                case Haut:
                    if (this.executeur.getEmplacement() instanceof Toit){
                        System.out.println(this.executeur.getSurnom() + "a tiré sur le toit ");
                        Toit toit = (Toit)this.executeur.getEmplacement();
                        if(!toit.getBanditList((Bandit) this.executeur).isEmpty()){
                            Bandit banditBlesse =toit.getBanditAlea((Bandit) this.executeur);
                            if(!banditBlesse.getButtins().isEmpty()){
                                Buttin buttinPerdu = banditBlesse.retirerButtin();
                                toit.ajouterButin(buttinPerdu);
                            }
                        }

                    } else {
                        System.out.println(this.executeur.getSurnom() + " a tiré vers le toit");
                            Toit toitDuWagon = ((Interieur) this.executeur.getEmplacement()).getToit();
                            if(!toitDuWagon.getBanditList((Bandit) this.executeur).isEmpty()){
                                Bandit banditBlesse =toitDuWagon.getBanditAlea((Bandit) this.executeur);
                                if(!banditBlesse.getButtins().isEmpty()){
                                    Buttin buttinPerdu = banditBlesse.retirerButtin();
                                    toitDuWagon.ajouterButin(buttinPerdu);
                                }
                            }

                        }

                    break;

                case Bas:
                    if (this.executeur.getEmplacement() instanceof Toit){
                        System.out.println(this.executeur.getSurnom() + "a tiré en direction de l'intérieur");
                        Interieur cabine =((Toit) this.executeur.getEmplacement()).getCabine();
                        if(!cabine.getBanditList((Bandit) this.executeur).isEmpty()){
                            Bandit banditBlesse =cabine.getBanditAlea((Bandit) this.executeur);
                            if(!banditBlesse.getButtins().isEmpty()){
                                Buttin buttinPerdu = banditBlesse.retirerButtin();
                                cabine.ajouterButin(buttinPerdu);
                            }
                        }

                    } else {
                        System.out.println(this.executeur.getSurnom() + " a tiré dans le wagon");
                        Interieur cabine = ((Interieur) this.executeur.getEmplacement());
                        if(!cabine.getBanditList((Bandit) this.executeur).isEmpty()){
                            Bandit banditBlesse =cabine.getBanditAlea((Bandit) this.executeur);
                            if(!banditBlesse.getButtins().isEmpty()){
                                Buttin buttinPerdu = banditBlesse.retirerButtin();
                                cabine.ajouterButin(buttinPerdu);
                            }
                        }

                    }
                    break;
            }
        }

    }



}


