package game;


import objects.NPC;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.TextBox;
import utilities.TileMapLoader;

import static game.Game.GAME;

/**
 * Created by Luke on 22/01/2018.
 */
public class Activity {
    public int id;
    public String duringText, afterText;
    public boolean started;

    public Activity(int id) {
        this.id = id;

        if (id == 1 && GAME.gradeValues[3] >= 30 && GAME.items[0][1] == 0) {
            duringText = FileReader.activityStrings[6];
            GAME.items[0][1] = 1;
            GameAudio.playSfx(GameAudio.sfx_item);
        }
        else { duringText = FileReader.activityStrings[id]; }
        afterText = FileReader.activityStrings[id + 3];
        started = false;
    }

    public static void startActivity(int id) {
        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.map.currentId);
        try { for (NPC npc : currentMap.NPCs.get(GAME.time)) { npc.reset(); } }
        catch (NullPointerException e) {}
        GAME.time = 9;
        GAME.activity = new Activity(id);
        GAME.menu = null;
        GAME.textBox = new TextBox(0, GAME.activity.duringText);
    }

    public void finish() {
        GAME.isAfterActivity = true;
    }

}
