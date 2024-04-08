package modele;

public class Braquer extends Action {
    // braque doit savoir quel wagon, mais elle connait le joueur qui lui connait le wagon

    // CETTE CLASSE A LE DROIT D'eTRE UTLSE QUE PAR LES BANDIT
    public Braquer(Personnage braqueur) {
        super(braqueur);
    }

    public void executer() {
        ComposanteTrain banditPos = this.executeur.getEmplacement();
        Buttin buttinBraque = banditPos.EnleverButinAlea();

        if(buttinBraque != null){
            ((Bandit) this.executeur).ajouterButtin(buttinBraque); // executeur n'est pas un bandit

            System.out.println(this.executeur.getSurnom() + " Vient de braquer un passager et a récupéré : " + buttinBraque);
        }


    }
}
