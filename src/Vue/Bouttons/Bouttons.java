package Vue.Bouttons;

import modele.Direction;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
        this.setBorder(new CompoundBorder(
                new LineBorder(new Color(0), 2),
                new EmptyBorder(5, 15, 5, 15)));

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
