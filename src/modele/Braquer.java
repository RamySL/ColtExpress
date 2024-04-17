package modele;
// !! Tout est bon
/**
 * action de braquage de passager sur le train par les bandits
 */
public class Braquer extends Action {

    public Braquer(Bandit braqueur) {
        super(braqueur);
    }

    /**
     * retire un butin aléatoire de la position (si il en sxiste) de l'executeur et lui ajoute ce butin
     * @return feedback
     */
    public String executer() {
        ComposanteTrain banditPos = this.executeur.getEmplacement();
        Butin butinBraque = banditPos.EnleverButinAlea();
        String feed;
        if(butinBraque != null){
            ((Bandit) this.executeur).ajouterButtin(butinBraque);
            feed = this.executeur.getSurnom() + " Vient de braquer un passager et a récupéré un : " + butinBraque;
        }else{
            feed = this.executeur.getSurnom() + " a rien rien récupéré dans le braquage";
        }

        return feed;


    }

    public String toString(){
        return " Braquage ";
    }
}
