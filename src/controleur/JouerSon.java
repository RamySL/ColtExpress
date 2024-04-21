package controleur;

import javax.sound.sampled.*;
import java.io.File;

/**
 * permet de jouer des sons, n'accepte pas les mp3 mais .wav
 */
public class JouerSon {
    private Clip clip;

    /**
     * recupere le flux auidio à partir du fichier et le met dans clip
     * @param cheminAudio chemins vers l'audio à jouer
     */
    public JouerSon(String cheminAudio) {
        try {
            File audPath = new File(cheminAudio);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audPath);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * déclenche le son depuis le debut
     * @param rejouer specifie si le son est rejoué après sa fin
     */
    public void jouer(boolean rejouer) {

        if (clip != null && !clip.isRunning()){
            clip.setFramePosition(0);
            clip.start();
        }
        if (rejouer) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * arrête le son
     */
    public void arreter() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }


}

