package VuePlus.Bouttons;

import controleur.ControleurPlus;
import modele.Direction;

import javax.swing.*;

public abstract class BouttonsJeu extends JButton {

    ControleurPlus controleur;
    public BouttonsJeu(ControleurPlus controleur, String txt){
        super(txt);
        this.controleur = controleur;
        this.addActionListener(this.controleur);

    }

    public static class BouttonTir extends BouttonsJeu{
        Direction direction;
        public BouttonTir(ControleurPlus controleur, Direction direction, String txt){
            super(controleur, txt);
            this.direction = direction;
        }

        public Direction getDirection() {
            return this.direction;
        }

    }

    public static class BouttonDeplacement extends BouttonsJeu{
        Direction direction;
        public BouttonDeplacement(ControleurPlus controleur, Direction direction, String txt){
            super(controleur, txt);
            this.direction = direction;
        }

        public Direction getDirection() {
            return this.direction;
        }

    }

    public static class BouttonAction extends BouttonsJeu{
        public BouttonAction(ControleurPlus controleur, String txt){
            super(controleur, txt);
        }

    }

    public static class BouttonBraquage extends BouttonsJeu{
        public BouttonBraquage(ControleurPlus controleur, String txt){
            super(controleur, txt);
        }

    }



}
