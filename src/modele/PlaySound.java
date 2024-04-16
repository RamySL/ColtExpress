package modele;

import javax.sound.sampled.*;
import java.io.File;

public class PlaySound {
    private Clip clip;

    public PlaySound(String cheminAudio) {
        try {
            File audPath = new File(cheminAudio);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audPath); // obtention du flux audio à partir du fichier
            clip = AudioSystem.getClip(); // on crée un clip qui va nous permettre de lire notre audio
            clip.open(audioInputStream); // mets le contenu de notre fichier audio dans le clip
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jouer(boolean rejouer) {
        // le bool rejouer c'est pour relancer la musique après sa fin
        if (clip != null && !clip.isRunning()){
            clip.setFramePosition(0); // 0 c'est pour jouer le clip depuis le début
            clip.start(); // démamare la lecture du son
        }
        if (rejouer) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // pour jouer le son en boucle
        }
    }

    public void arreter() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }


}

