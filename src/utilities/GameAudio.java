package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static game.Game.GAME;

/**
 * 26/01/2018.
 */
public class GameAudio {
    public final static Clip music_bedroom = getClip("bedroom");
    public final static Clip music_exam = getClip("exam");
    public final static Clip music_heist = getClip("heist");
    public final static Clip music_lesson = getClip("lesson");
    public final static Clip music_school = getClip("school");
    public final static Clip music_title = getClip("title");
    public final static Clip music_result = getClip("results");

    public final static Clip sfx_buff = getClip("buff");
    public final static Clip sfx_click = getClip("click");
    public final static Clip sfx_debuff = getClip("debuff");
    public final static Clip sfx_door = getClip("door");
    public final static Clip sfx_gotAnswers = getClip("got_answers");
    public final static Clip sfx_item = getClip("item");
    public final static Clip sfx_menu = getClip("menu");
    public final static Clip sfx_paAnnouncement = getClip("pa_announcement");
    public final static Clip sfx_pcHack = getClip("pc_hack");
    public final static Clip sfx_save = getClip("save");
    public final static Clip sfx_useStinkBomb = getClip("use_stinkbomb");

    public static void loadSounds() {
        try {
            music_bedroom.open();
            music_exam.open();
            music_heist.open();
            music_lesson.open();
            music_school.open();
            music_title.open();
            music_result.open();

            sfx_buff.open();
            sfx_click.open();
            sfx_debuff.open();
            sfx_door.open();
            sfx_gotAnswers.open();
            sfx_item.open();
            sfx_menu.open();
            sfx_paAnnouncement.open();
            sfx_pcHack.open();
            sfx_save.open();
            sfx_useStinkBomb.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Clip getClip(String name) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(
                    "resources/sounds/" + name + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }
    public static void startMusic(Clip clip) {
        if (GAME.getMusic() != null) { GAME.getMusic().stop(); }
        GAME.setMusic(clip);
        GAME.getMusic().setFramePosition(0);
        GAME.getMusic().loop(-1);
    }
    public static void stopMusic() {
        GAME.getMusic().stop();
    }

    public static void playSfx(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }
}
