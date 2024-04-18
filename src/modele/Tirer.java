package modele;
public class Tirer extends Action {

    private Direction direction;

    public Tirer(Personnage tireur, Direction direction) {
        super(tireur);
        this.direction = direction;

    }

    public String toString(){
        return " Tir en direction " + this.direction;
    }

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

    private class TirerAdroite{
        ComposanteTrain emplacement;
        public TirerAdroite(){
            this.emplacement = executeur.getEmplacement();
        }

        public String executer(){
            String feed;
            // on regarde que si on est ni dans la locomotive ni sur son toit
            if (this.emplacement instanceof Locomotive ||
                    (this.emplacement instanceof Toit) &&
                            ((Toit) this.emplacement).getCabine() instanceof Locomotive) {

                if (!(this.emplacement instanceof Toit))
                    feed = executeur.getSurnom() + " à tirer à droite dans le vide dans la locmotive ";
                else
                    feed = executeur.getSurnom() + " à tirer à droite dans le vide  sur le toit de la locomotive ";


            } else {
                // si on était dans les cabines

                if (this.emplacement instanceof Interieur) {
                    feed = tireDirectionInterieur(Direction.Droite, executeur);

                } else {// toit

                    // il faut parcourir tout les wagons à droite jusqu'a trouver un avec un bandit dedans
                    // et après mm principe
                    Toit toitCourant = ((Toit) (this.emplacement).getVoisin(Direction.Droite)); // on recup le toit de droite
                    // tant ya pas de bandit sur le toit de coté et tant que on est pas encore arrivé à la locmotive
                    while (toitCourant.getBanditListSauf(executeur).isEmpty() && !(toitCourant.getCabine() instanceof Locomotive)) {
                        toitCourant = (Toit) toitCourant.getVoisin(Direction.Droite);
                    }

                    if (!toitCourant.getBanditListSauf(executeur).isEmpty()) {
                        Bandit banditBlesse = toucherBandit(toitCourant,executeur);
                        feed = executeur.getSurnom() + " à tirer à droite sur le toit sur " + banditBlesse;
                    }else{
                        feed = executeur.getSurnom() + " à tirer à droite sur le toit dans le vide";
                    }
                }
            }
            return feed;
        }

    }

    private class TirerAGauche{

        ComposanteTrain emplacement;
        public TirerAGauche(){
            this.emplacement = executeur.getEmplacement();
        }
        public String executer(){
            String feed;
            // on regarde que si on est ni dans le dernier wagon ni sur son toit
            if (this.emplacement instanceof DernierWagon ||
                    (this.emplacement instanceof Toit) &&
                            ((Toit) this.emplacement).getCabine() instanceof DernierWagon) {

                if (!(this.emplacement instanceof Toit))
                    feed = executeur.getSurnom() + " Vous êtes dans le dernier wagon, pas de tire à gauche";
                else
                    feed = executeur.getSurnom() + " Vous êtes sur le toit du dernier wagon, pas de tire à gauche";


            } else {
                // si on était dans les cabines

                if (this.emplacement instanceof Interieur) {
                    feed = tireDirectionInterieur(Direction.Gauche, executeur);

                } else {

                    // il faut parcourir tout les wagons à droite jusqu'a trouver un avec un bandit dedans
                    // et après mm principe
                    Toit toitCourant = ((Toit) (this.emplacement).getVoisin(Direction.Gauche)); // on recup le toit de droite
                    while (toitCourant.getBanditListSauf(executeur).isEmpty() && !(toitCourant.getCabine() instanceof DernierWagon)) {
                        toitCourant = (Toit) toitCourant.getVoisin(Direction.Gauche);
                    }

                    if (!toitCourant.getBanditListSauf(executeur).isEmpty()) {
                        Bandit banditBlesse = toucherBandit(toitCourant,executeur);
                        feed = executeur.getSurnom() + " à tirer à gauche sur le toit sur " + banditBlesse;
                    }else{
                        feed = executeur.getSurnom() + " à tirer à gauche sur le toit dans le vide";
                    }
                }
            }

            return feed;
        }

    }

    private class TirerHaut{
        ComposanteTrain emplacement;
        public TirerHaut(){
            this.emplacement = executeur.getEmplacement();
        }

        public String executer(){
            String feed;
            if (this.emplacement instanceof Toit) {

                Toit toit = (Toit) (this.emplacement);
                if (!toit.getBanditListSauf(executeur).isEmpty()) {

                    Bandit banditBlesse = toucherBandit(toit,executeur);
                    feed = executeur.getSurnom() + " à tirer dans sa position sur le toit et a touché " + banditBlesse;

                }else{
                    feed = executeur.getSurnom() + " à tirer dans sa position dans le vide";
                }

            } else {

                Toit toitDuWagon = ((Interieur) this.emplacement).getToit();
                if (!toitDuWagon.getBanditListSauf(executeur).isEmpty()) {

                    Bandit banditBlesse = toucherBandit(toitDuWagon,executeur);
                    feed = executeur.getSurnom() + " à tirer vers le toit et a touché " + banditBlesse;

                }else{
                    feed = executeur.getSurnom() + " à tirer vers le toit dans le vide";

                }

            }
            return feed;
        }

    }

    private class TirerBas{
        ComposanteTrain emplacement;
        public TirerBas(){
            this.emplacement = executeur.getEmplacement();
        }

        public String executer(){
            String feed;
            if (this.emplacement instanceof Toit) {

                Interieur cabine = ((Toit) this.emplacement).getCabine();

                if (!cabine.getBanditListSauf(executeur).isEmpty()) {
                    Bandit banditBlesse = toucherBandit(cabine,executeur);
                    feed = executeur.getSurnom() + " à tirer vers l'interieur et a touché " + banditBlesse;
                }else {
                    feed = executeur.getSurnom() + " à tirer vers l'interieur dans le vide";
                }

            } else {

                Interieur cabine = ((Interieur) this.emplacement);
                if (!cabine.getBanditListSauf(executeur).isEmpty()) {
                    Bandit banditBlesse = toucherBandit(cabine,executeur);
                    feed = executeur.getSurnom() + " à tirer vers la cabine et a touché " + banditBlesse;

                }else {
                    feed = executeur.getSurnom() + " à tirer dans sa position dans les cabines dans le vide";
                }



            }
            return feed;
        }
    }
}