package game;


import utilities.FileReader;
import utilities.TextBox;

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
        duringText = FileReader.activityStrings[id];
        afterText = FileReader.activityStrings[id + 3];
        started = false;
    }

    public static void startActivity(int id) {
        GAME.time = 9;
        GAME.activity = new Activity(id);
        GAME.menu = null;
        GAME.textBox = new TextBox(0, GAME.activity.duringText);
    }

    public void finish() {
        GAME.isAfterActivity = true;
    }

}
