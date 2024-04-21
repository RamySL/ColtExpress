package Vue.Bouttons;

import modele.Direction;

import javax.swing.*;
import java.awt.*;

/**
 * Regroupe tous les bouttons utilisé dans le jeu
 */
public abstract class Bouttons extends JButton {

    public Bouttons(String txt){
        super(txt);
        this.setBackground(new Color(0xFDB531));
        this.setFont(new Font("MV Boli", Font.BOLD, 15));
        this.setForeground(Color.BLACK);
        this.setFocusable(false);

    }

    public static class BouttonTir extends Bouttons {
        Direction direction;
        public BouttonTir(Direction direction, String txt){
            super(txt);
            this.direction = direction;

        }

        public Direction getDirection() {
            return this.direction;
        }

    }

    public static class BouttonDeplacement extends Bouttons {
        Direction direction;
        public BouttonDeplacement( Direction direction, String txt){
            super(txt);
            this.direction = direction;
        }

        public Direction getDirection() {
            return this.direction;
        }

    }

    public static class BouttonBraquage extends Bouttons {
        public BouttonBraquage(String txt){
            super(txt);
        }

    }

    public static class BouttonAction extends Bouttons {
        public BouttonAction( String txt){
            super(txt);
        }

    }

    /**
     * bouttons utilisé hors deroulement de la partie
     */
    public static class BouttonHorsJeu extends Bouttons{
        public BouttonHorsJeu( String txt){
            super(txt);
        }
    }





}
