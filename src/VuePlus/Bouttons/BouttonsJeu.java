package VuePlus.Bouttons;

import controleur.ControleurPlus;
import modele.Direction;

import javax.swing.*;

public abstract class BouttonsJeu extends JButton {

    public BouttonsJeu(String txt){
        super(txt);

    }



    public static class BouttonTir extends BouttonsJeu{
        Direction direction;
        public BouttonTir(Direction direction, String txt){
            super(txt);
            this.direction = direction;

        }

        public Direction getDirection() {
            return this.direction;
        }

    }

    public static class BouttonDeplacement extends BouttonsJeu{
        Direction direction;
        public BouttonDeplacement( Direction direction, String txt){
            super(txt);
            this.direction = direction;
        }

        public Direction getDirection() {
            return this.direction;
        }

    }

    public static class BouttonAction extends BouttonsJeu{
        public BouttonAction( String txt){
            super(txt);
        }

    }

    public static class BouttonBraquage extends BouttonsJeu{
        public BouttonBraquage(String txt){
            super(txt);
        }

    }



}
