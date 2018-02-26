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
    public static String text, subText;
    public static BufferedImage img;

    public StatusMenu(int id) {
        this.currentId = id;
        setUp(id);
    }

    public static void setUp(int id) {
        switch (id) {
            case 0:
                img = Menu.imgs[0];
                text = GAME.daysLeft + FileReader.menuStrings[32] + "#" + Game.timePeriods[GAME.time] + "#"
                        + Game.conditions[GAME.player.condition] + "#" + GAME.points;
                subText = FileReader.menuStrings[64];
                break;
            case 1:
                img = Menu.imgs[7];
                text = FileReader.menuStrings[33] + ((LessonTypeB) GAME.lesson).tasksLeft +
                        FileReader.menuStrings[34] + ((LessonTypeB) GAME.lesson).timeLeft +
                        FileReader.menuStrings[35] + ((LessonTypeB) GAME.lesson).reread;
                subText = null;
                break;
            case 2:
                img = Menu.imgs[5];
                text = FileReader.menuStrings[36] + ((LessonTypeC) GAME.lesson).laps +
                        FileReader.menuStrings[34] + ((LessonTypeC) GAME.lesson).time +
                        FileReader.menuStrings[37] + GAME.items[0][0] +
                        FileReader.menuStrings[38] + ((LessonTypeC) GAME.lesson).energy;
                subText = null;
                break;
            case 3:
                img = Menu.imgs[5];
                text = FileReader.menuStrings[39] + ((LessonTypeA) GAME.lesson).questionsLeft +
                        FileReader.menuStrings[37] + GAME.items[0][0] +
                        FileReader.menuStrings[40] + ((LessonTypeA) GAME.lesson).attentionSpan +
                        FileReader.menuStrings[41] + ((LessonTypeA) GAME.lesson).concentration;
                subText = null;
                break;
            case 4:
                img = Menu.imgs[5];
                text = FileReader.menuStrings[39] + GAME.exam.questionsLeft +
                        FileReader.menuStrings[34] + GAME.exam.timeLeft +
                        FileReader.menuStrings[40] + GAME.exam.attentionSpan +
                        FileReader.menuStrings[41] + GAME.exam.concentration;
                subText = null;
                break;
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, 16, 16, img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.bigFont);
        int height = g.getFontMetrics().getHeight();
        int lineIndex = 0;
        for (String line : text.split("#")) {
            g.drawString(line, 26, 48 + (height + 16) * lineIndex);
            lineIndex++;
        }
        if (subText != null) {
            g.setFont(GameFont.smallFont);
            lineIndex = 0;
            for (String line : subText.split("#")) {
                g.drawString(line, 26, 62 + (height + 16) * lineIndex);
                lineIndex++;
            }
        }
    }
}
