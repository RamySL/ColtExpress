package Vue;

public class AccueilClient extends Accueil{
    public AccueilClient(Fenetre fenetre){
        super(fenetre);
        this.optionsJeu.saisieNbActions.setEnabled(false);
        this.optionsJeu.saisieNbBalles.setEnabled((false));
        this.optionsJeu.saisieNbManches.setEnabled((false));
        this.optionsJeu.saisieNbWagon.setEnabled((false));
        this.optionsJeu.selectNervositePanel.setEnabled(false);
    }

}
