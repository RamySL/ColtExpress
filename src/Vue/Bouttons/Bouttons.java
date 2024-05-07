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

    /**
     * bouttons utilisé hors deroulement de la partie
     */
    public static class BouttonHorsJeu extends Bouttons{
        public BouttonHorsJeu( String txt){
            super(txt);
        }
    }





}
