package Vue.Bouttons;

import modele.Direction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Regroupe tous les bouttons utilisé dans le jeu
 */
public abstract class Bouttons extends JButton {
    Border border = new CompoundBorder(
            new LineBorder(new Color(0), 2),
            new EmptyBorder(5, 15, 5, 15));
    public Bouttons(String txt){
        super(txt);
        this.setBackground(new Color(0xFDB531));
        this.setFont(new Font("MV Boli", Font.BOLD, 15));
        this.setForeground(Color.BLACK);
        this.setFocusable(false);
        this.setBorder(border);

    }

    /**
     * bouttons utilisé hors deroulement de la partie
     */
    public static class BouttonHorsJeu extends Bouttons{
        public BouttonHorsJeu(String txt) {
            super(txt);
            this.addHoverEffect();

        }

        private void addHoverEffect() {

            Color originalBackground = this.getBackground();
            Border originalBorder = this.getBorder();
            Color originalForeground = this.getForeground();

            Color hoverBackground = new Color(0x8A5701);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    BouttonHorsJeu.this.setBackground(hoverBackground);
                    BouttonHorsJeu.this.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    BouttonHorsJeu.this.setBackground(originalBackground);
                    BouttonHorsJeu.this.setBorder(originalBorder);
                    BouttonHorsJeu.this.setForeground(originalForeground);
                }
            });
        }
    }





}
