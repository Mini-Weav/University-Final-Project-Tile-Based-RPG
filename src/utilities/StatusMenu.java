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

    public Color setColours(int lineIndex) {
        switch (GAME.statusMenu.currentId) {
            case 0:
                switch (lineIndex) {
                    case 0:
                        if (GAME.daysLeft < 2) { return Color.red; }
                        else { return Color.black; }
                    case 1:
                        if (GAME.time == 11) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (GAME.player.condition == 1) { return new Color(0, 128, 0); }
                        else if (GAME.player.condition == 2) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 1:
                switch (lineIndex) {
                    case 1:
                        if (((LessonTypeB) GAME.lesson).timeLeft < 15) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (((LessonTypeB) GAME.lesson).reread) { return new Color(0, 128, 0); }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 2:
                switch (lineIndex) {
                    case 1:
                        if (((LessonTypeC) GAME.lesson).time < 15) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (GAME.items[0][0] < 1) { return Color.red; }
                        else { return Color.black; }
                    case 3:
                        if (((LessonTypeC) GAME.lesson).energy < 1) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 3:
                switch (lineIndex) {
                    case 1:
                        if (GAME.items[0][0] < 1) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (((LessonTypeA) GAME.lesson).attentionSpan < 1) { return Color.red; }
                        else { return Color.black; }
                    case 3:
                        if (((LessonTypeA) GAME.lesson).concentration < 1) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 4:
                switch (lineIndex) {
                    case 1:
                        if (GAME.exam.timeLeft < 15) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (GAME.exam.attentionSpan < 1) { return Color.red; }
                        else { return Color.black; }
                    case 3:
                        if (GAME.exam.concentration < 1) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            default:
                return Color.black;
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, 16, 16, img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.bigFont);
        int height = g.getFontMetrics().getHeight();
        int lineIndex = 0;
        for (String line : text.split("#")) {
            g.setColor(setColours(lineIndex));
            g.drawString(line, 26, 48 + (height + 16) * lineIndex);
            lineIndex++;
        }
        if (subText != null) {
            g.setFont(GameFont.smallUnderline);
            lineIndex = 0;
            for (String line : subText.split("#")) {
                g.setColor(setColours(lineIndex));
                g.drawString(line, 26 + ((11 - line.length()) * 12), 62 + (height + 16) * lineIndex);
                lineIndex++;
            }
        }
    }
}
