package game;

import objects.NPC;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.TextBox;
import utilities.TileMapLoader;

import static game.Game.GAME;

/**
 * Manages game flow when in an 'Activity' state.
 */
public class Activity {
    private int id;
    private boolean started;
    private String duringText;
    private String afterText;

    /**
     * Class constructor.
     *
     * @param id the identifier for the Activity type (0 = track club, 1 = chemistry club, 2 = tutoring)
     */
    private Activity(int id) {
        this.id = id;

        if (id == 1 && GAME.getGradeValue(3) > 29 && !GAME.hasStinkBomb() && !GAME.hasQuestions()) {
            duringText = FileReader.getActivityString(6);
            GAME.giveStinkBomb();
            GameAudio.playSfx(GameAudio.sfx_item);
        }
        else { duringText = FileReader.getActivityString(id); }
        afterText = FileReader.getActivityString(id + 3);
        started = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public void setStarted(boolean started) { this.started = started; }

    String getAfterText() { return afterText; }
    public boolean isStarted() { return started; }

    /**
     * Starts an Activity game state.
     * Sets the player's position and displays a TextBox.
     *
     * @param id the identifier for the Activity type (0 = track club, 2 = chemistry club, 4 = tutoring)
     */
    public static void startActivity(int id) {
        int activityId;
        switch (id) {
            case 0:
                activityId = 0;
                break;
            case 2:
                activityId = 1;
                break;
            case 4:
                activityId = 2;
                break;
            default:
                return;
        }
        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.getMapId());
        try {
            for (int i = 0; i < currentMap.getNumberOfNPCs(GAME.getTime()); i++) {
                NPC npc = currentMap.getNPCs(GAME.getTime()).get(i);
                npc.reset();
            }
        } catch (NullPointerException e) { /* Do nothing */ }
        GAME.setTime(9);
        for (int i = 0; i < currentMap.getNumberOfNPCs(9); i++) {
            NPC npc = currentMap.getNPCs(9).get(i);
            npc.setEmotion(new Emotion(2));
        }
        GAME.getPlayer().setEmotion(new Emotion(2));
        GAME.setActivity(new Activity(activityId));
        GAME.setMenu(null);
        GAME.setTextBox(new TextBox(0, GAME.getActivity().duringText));
    }

    void finish() { GAME.setAfterActivity(true); }
}
