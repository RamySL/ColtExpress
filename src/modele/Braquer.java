package modele;

public class Braquer extends Action {
    // braque doit savoir quel wagon, mais elle connait le joueur qui lui connait le wagon

    // CETTE CLASSE A LE DROIT D'eTRE UTLSE QUE PAR LES BANDIT
    public Braquer(Personnage braqueur) {
        super(braqueur);
    }

    public String executer() {
        ComposanteTrain banditPos = this.executeur.getEmplacement();
        Butin butinBraque = banditPos.EnleverButinAlea();
        String feed;
        if(butinBraque != null){
            ((Bandit) this.executeur).ajouterButtin(butinBraque); // executeur n'est pas un bandit
            feed = this.executeur.getSurnom() + " Vient de braquer un passager et a récupéré : " + butinBraque;
        }else{
            feed = this.executeur.getSurnom() + "a rien braqué ";
        }

        return feed;


    }

    public String toString(){
        return " Braquage ";
    }
}
