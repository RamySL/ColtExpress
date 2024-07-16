package Vue.ComposantsPerso;

import javax.swing.*;
import java.awt.*;

/**
 * Pour créer des panels avec des composants centrés
 */
public  class CenteredPanel {
    /**
     *
     * @param panel
     */
    public static void centerArrangement(JPanel panel){

        panel.setLayout(new BorderLayout());

        // C'est pour centerer le menu d'option
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(200,100));
        westPanel.setPreferredSize(new Dimension(200,100));
        northPanel.setPreferredSize(new Dimension(100,70));
        southPanel.setPreferredSize(new Dimension(100,70));
        eastPanel.setOpaque(false);
        westPanel.setOpaque(false);
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);

        panel.add(eastPanel,BorderLayout.EAST);
        panel.add(westPanel,BorderLayout.WEST);
        panel.add(southPanel,BorderLayout.SOUTH);
        panel.add(northPanel,BorderLayout.NORTH);
    }

    /**
     * Arrangement centrale et ajout du boutton passé en paramètre tout en haut à gauche
     * @param panel
     * @param button généralement le boutton de retour
     */
    public static void centerArrangement(JPanel panel,JButton button){

        panel.setLayout(new BorderLayout());

        // C'est pour centerer le menu d'option
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(200,100));
        westPanel.setPreferredSize(new Dimension(200,100));
        northPanel.setPreferredSize(new Dimension(100,70));
        southPanel.setPreferredSize(new Dimension(100,70));
        eastPanel.setOpaque(false);
        westPanel.setOpaque(false);
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);

        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(button);

        panel.add(eastPanel,BorderLayout.EAST);
        panel.add(westPanel,BorderLayout.WEST);
        panel.add(southPanel,BorderLayout.SOUTH);
        panel.add(northPanel,BorderLayout.NORTH);
    }

}
