package VuePlus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EcranLancementPlus extends JPanel  {
    private FenetrePlus fenetre;
    private Image imageFond;




    public EcranLancementPlus(FenetrePlus fenetre) {
        this.fenetre = fenetre;
        this.setLayout(new BorderLayout());
        // Charger l'image de fond
        this.imageFond = new ImageIcon("src/assets/images/colt_express-banner.jpg").getImage();

        // le conteneur qui va etre ajouté dans le south de EcranLacement
        JPanel southContainer = new JPanel(new BorderLayout());
        JPanel northContainer = new JPanel(new BorderLayout());

        // Icone de saclay
        ImageIcon saclayIcon = new ImageIcon("src/assets/images/saclay1.png");
        // On redimensionne l'image pour qu'elle remplisse le label
        Image imageSaclay = saclayIcon.getImage();
        Image scaledSaclay = imageSaclay.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        saclayIcon = new ImageIcon(scaledSaclay);


        // Saclay icone label
        JLabel saclayLabel = new JLabel(saclayIcon);
        saclayLabel.setPreferredSize(new Dimension(150, 100));
        saclayLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //saclayLabel.setBorder(new LineBorder(Color.GREEN,2));




        JLabel copyRights = new JLabel("Réalisé par RAMDANI Kelia & SAIL Ramy");
        JLabel anne = new JLabel(" POGL 2023/2024");

        copyRights.setForeground(Color.WHITE);
        copyRights.setFont(new Font("MV Boli", Font.BOLD, 20));
        //copyRights.setBorder(new LineBorder(Color.GREEN,2));
        anne.setForeground(Color.WHITE);
        anne.setFont(new Font("MV Boli", Font.BOLD, 20));
        //anne.setBorder(new LineBorder(Color.GREEN,2));

        southContainer.setBackground(new Color(0x0FFFFFF, true)); // transparent
        southContainer.add(copyRights, BorderLayout.WEST);
        southContainer.add(anne, BorderLayout.EAST);

        northContainer.setBackground(new Color(0x0FFFFFF, true)); // transparent
        northContainer.add(saclayLabel, BorderLayout.WEST);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                EcranLancementPlus.this.fenetre.changerFenetre(EcranLancementPlus.this.fenetre.getAccueilId());}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        JButton appyerCommencer = new JButton("Appuyer sur une touche pour commencer");
        appyerCommencer.setBackground(new Color(0x0FFFFFF, true));
        appyerCommencer.setBorder(null);
        appyerCommencer.setFocusable(true);
        appyerCommencer.setForeground(Color.BLACK);
        appyerCommencer.setFont(new Font("MV Boli", Font.BOLD, 20));
        // Pour le clignotement
        Timer timer = new Timer(800, new ActionListener() {
            boolean visible = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                appyerCommencer.setVisible(visible);
                visible = !visible;
            }
        });
        timer.start();

        this.setFocusable(true);
        this.add(appyerCommencer, BorderLayout.EAST);
        this.add(southContainer, BorderLayout.SOUTH);
        this.add(northContainer, BorderLayout.NORTH);


    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(this.imageFond,0,-20,this.fenetre.getWidth(),this.fenetre.getHeight(),this);
    }

}