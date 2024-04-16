package modele;

import java.util.ArrayList;
import java.util.Random;
// Tout est bon !
/**
 * La classe la plus haute dans la hiearchie des classes composant le train regroupe tout ce qui
 * constitue le train, peut possedée un nombre de buttins et de Personnage
 */
public abstract class ComposanteTrain {
    private Train train;
    protected ArrayList<Personnage> persoList = new ArrayList<>();
    protected ArrayList<Butin> butins = new ArrayList<>();

    public ComposanteTrain(Train train) {
        this.train = train;
    }

    /**
     * Va permettre  de retourner le voisin dans la direction d si d est approprié sinon de retourner this
     * @param d pointe vers la composante voisine à récuperer
     * @return la composante voisine dans la direction de d
     */
    public abstract ComposanteTrain getVoisin(Direction d);

    /**
     * retourne tous les bandits présents sauf celui spécifié en paramètre
     * @param courant Personnage présent dans la composante courante
     * @return liste bandits présents dans la composante courante sans "courant"
     */
    public ArrayList<Bandit> getBanditListSauf(Personnage courant){
        ArrayList<Bandit>banditList = new ArrayList<>();
        for(Personnage b : this.persoList){
            if (b instanceof Bandit) {
                if (!b.equals(courant)) {
                    banditList.add((Bandit)b);
                }
            }
        }
        return banditList;
    }

    /**
     * retourne un bandit aléatoire parmis ceux présent dans la composante (sauf celui précisé en parametre)
     * ! Précondition :  il existe un autre bandit à part courant dans la composante
     * @param courant
     * @return un bandit != courant
     */
    public Bandit getBanditAlea(Personnage courant) {
        Random rnd = new Random();
        return this.getBanditListSauf(courant).get(rnd.nextInt(0, getBanditListSauf(courant).size()));
    }

    /**
     * retourne un buttin aléatoire et l'enleve de la composante
     * @return Buttin ou null
     */
    public Butin EnleverButinAlea(){
        if (!this.butins.isEmpty()){
            Random rnd = new Random();
            Butin butinBraque = this.butins.get(rnd.nextInt(0,this.butins.size()));
            this.butins.remove(butinBraque);
            return butinBraque;
        }

        return null;
    }
    /**
     * ajoute p à la composante du train
     * @param p
     */
    public void ajouterPersonnage (Personnage p){
        this.persoList.add(p);
    }
    public void ajouterButin(Butin b) { this.butins.add(b);}
    public Train getTrain() {
        return this.train;
    }

    public ArrayList<Personnage> getPersoList(){return this.persoList;}

    public ArrayList<Butin> getButtins(){return this.butins;}
}
