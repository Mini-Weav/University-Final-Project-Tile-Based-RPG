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
                text = "DAY " + GAME.day + "\n" + Game.TIME_PERIODS[GAME.time] + "\n" + Game.CONDITIONS[GAME.player.condition] + "\nPP: " + 0;
                break;
            case 1:
                img = Menu.imgs[7];
                text = "TASKS:        " + ((LessonTypeB) GAME.lesson).tasksLeft +
                        "\nTIME:         " + ((LessonTypeB) GAME.lesson).time +
                        "\nREREAD:    " + ((LessonTypeB) GAME.lesson).reread;
                break;
            case 2:
                img = Menu.imgs[5];
                text = "LAPS:       " + ((LessonTypeC) GAME.lesson).score +
                        "\nTIME:         " + ((LessonTypeC) GAME.lesson).time +
                        "\nDRINKS:       " + GAME.items[0][0] +
                        "\nENERGY:       " + ((LessonTypeC) GAME.lesson).energy;
                break;
            case 3:
                img = Menu.imgs[5];
                text = "QUESTIONS:    " + ((LessonTypeA) GAME.lesson).questionsLeft +
                        "\nDRINKS:       " + GAME.items[0][0] +
                        "\nATTENTION:    " + ((LessonTypeA) GAME.lesson).attentionSpan +
                        "\nCONCENTRATION:" + ((LessonTypeA) GAME.lesson).concentration;
                break;

        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, 16, 16, img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.bigFont);
        int lineIndex = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, 26, 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
            lineIndex++;
        }
    }
}
