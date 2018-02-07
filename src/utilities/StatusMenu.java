package utilities;

import game.Game;
import lessons.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import static game.Game.GAME;

/**
 * Created by Luke on 15/12/2017.
 */
public class StatusMenu {
    public int currentId;
    public static String text;
    public static BufferedImage img;

    public StatusMenu(int id) {
        this.currentId = id;
        setUp(id);
    }

    public static void setUp(int id) {
        switch (id) {
            case 0:
                img = Menu.imgs[0];
                text = FileReader.menuStrings[32] + GAME.day + "#" + Game.timePeriods[GAME.time] + "#"
                        + Game.conditions[GAME.player.condition] + "#" + GAME.points;
                break;
            case 1:
                img = Menu.imgs[7];
                text = FileReader.menuStrings[33] + ((LessonTypeB) GAME.lesson).tasksLeft +
                        FileReader.menuStrings[34] + ((LessonTypeB) GAME.lesson).time +
                        FileReader.menuStrings[35] + ((LessonTypeB) GAME.lesson).reread;
                break;
            case 2:
                img = Menu.imgs[5];
                text = FileReader.menuStrings[36] + ((LessonTypeC) GAME.lesson).laps +
                        FileReader.menuStrings[34] + ((LessonTypeC) GAME.lesson).time +
                        FileReader.menuStrings[37] + GAME.items[0][0] +
                        FileReader.menuStrings[38] + ((LessonTypeC) GAME.lesson).energy;
                break;
            case 3:
                img = Menu.imgs[5];
                text = FileReader.menuStrings[39] + ((LessonTypeA) GAME.lesson).questionsLeft +
                        FileReader.menuStrings[37] + GAME.items[0][0] +
                        FileReader.menuStrings[40] + ((LessonTypeA) GAME.lesson).attentionSpan +
                        FileReader.menuStrings[41] + ((LessonTypeA) GAME.lesson).concentration;
                break;
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, 16, 16, img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.bigFont);
        int lineIndex = 0;
        for (String line : text.split("#")) {
            g.drawString(line, 26, 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
            lineIndex++;
        }
    }
}
