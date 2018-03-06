package utilities;

import game.Game;
import lessons.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import static game.Game.GAME;

/**
 * Manages creating and rendering StatusMenu shell screens.
 */
public class StatusMenu {
    private static String text;
    private static String subText;
    private static BufferedImage img;

    private int currentId;

    /**
     * Class constructor.
     *
     * @param id the identification of the StatusMenu to be created (0 = normal, 1 = DT/food tech, 2 = PE,
     *           3 = chemistry/ICT, 4 = exams)
     */
    public StatusMenu(int id) {
        this.currentId = id;
        setUp(id);
    }

    public int getCurrentId() { return currentId; }

    public void setCurrentId(int currentId) { this.currentId = currentId; }

    /**
     * Sets the StatusMenu images and text.
     *
     * @param id the identification of the StatusMenu type
     */
    public static void setUp(int id) {
        switch (id) {
            case 0:
                img = Menu.getImg(0);
                text = GAME.getDaysLeft() + FileReader.getMenuString(32) +
                        "#" + Game.getTimePeriods()[GAME.getTime()] +
                        "#" + Game.getConditions()[GAME.getPlayer().getCondition()] +
                        "#" + GAME.getPoints();
                subText = FileReader.getMenuString(64);
                break;
            case 1:
                img = Menu.getImg(7);
                text = FileReader.getMenuString(33) + ((LessonTypeB) GAME.getLesson()).getTasksLeft() +
                        FileReader.getMenuString(34) + ((LessonTypeB) GAME.getLesson()).getTimeLeft() +
                        FileReader.getMenuString(35) + ((LessonTypeB) GAME.getLesson()).isReread();
                subText = null;
                break;
            case 2:
                img = Menu.getImg(5);
                text = FileReader.getMenuString(36) + ((LessonTypeC) GAME.getLesson()).getLaps() +
                        FileReader.getMenuString(34) + ((LessonTypeC) GAME.getLesson()).getTime() +
                        FileReader.getMenuString(37) + GAME.getItem(0, 0) +
                        FileReader.getMenuString(38) + ((LessonTypeC) GAME.getLesson()).getEnergy();
                subText = null;
                break;
            case 3:
                img = Menu.getImg(5);
                text = FileReader.getMenuString(39) + ((LessonTypeA) GAME.getLesson()).getQuestionsLeft() +
                        FileReader.getMenuString(37) + GAME.getItem(0, 0) +
                        FileReader.getMenuString(40) + ((LessonTypeA) GAME.getLesson()).getAttentionSpan() +
                        FileReader.getMenuString(41) + ((LessonTypeA) GAME.getLesson()).getConcentration();
                subText = null;
                break;
            case 4:
                img = Menu.getImg(5);
                text = FileReader.getMenuString(39) + GAME.getExam().getQuestionsLeft() +
                        FileReader.getMenuString(34) + GAME.getExam().getTimeLeft() +
                        FileReader.getMenuString(40) + GAME.getExam().getAttentionSpan() +
                        FileReader.getMenuString(41) + GAME.getExam().getConcentration();
                subText = null;
                break;
        }
    }

    /**
     * Change certain lines of text to green or red to indicate a good or bad status, respectively.
     *
     * @param lineIndex the index of the line to change
     * @return the colour to write the text in
     */
    private Color setColours(int lineIndex) {
        switch (GAME.getStatusMenu().currentId) {
            case 0:
                switch (lineIndex) {
                    case 0:
                        if (GAME.getDaysLeft() < 2) { return Color.red; }
                        else { return Color.black; }
                    case 1:
                        if (GAME.getTime() == 11) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (GAME.getPlayer().getCondition() == 1) { return new Color(0, 128, 0); }
                        else if (GAME.getPlayer().getCondition() == 2) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 1:
                switch (lineIndex) {
                    case 1:
                        if (((LessonTypeB) GAME.getLesson()).getTimeLeft() < 15) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (((LessonTypeB) GAME.getLesson()).isReread()) { return new Color(0, 128, 0); }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 2:
                switch (lineIndex) {
                    case 1:
                        if (((LessonTypeC) GAME.getLesson()).getTime() < 15) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (!GAME.hasEnergyDrink()) { return Color.red; }
                        else { return Color.black; }
                    case 3:
                        if (((LessonTypeC) GAME.getLesson()).getEnergy() < 1) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 3:
                switch (lineIndex) {
                    case 1:
                        if (!GAME.hasEnergyDrink()) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (((LessonTypeA) GAME.getLesson()).getAttentionSpan() < 1) { return Color.red; }
                        else { return Color.black; }
                    case 3:
                        if (((LessonTypeA) GAME.getLesson()).getConcentration() < 1) { return Color.red; }
                        else { return Color.black; }
                    default:
                        return Color.black;
                }
            case 4:
                switch (lineIndex) {
                    case 1:
                        if (GAME.getExam().getTimeLeft() < 15) { return Color.red; }
                        else { return Color.black; }
                    case 2:
                        if (GAME.getExam().getAttentionSpan() < 1) { return Color.red; }
                        else { return Color.black; }
                    case 3:
                        if (GAME.getExam().getConcentration() < 1) { return Color.red; }
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
        g.setFont(GameFont.getBigFont());
        int height = g.getFontMetrics().getHeight();
        int lineIndex = 0;
        for (String line : text.split("#")) {
            g.setColor(setColours(lineIndex));
            g.drawString(line, 26, 48 + (height + 16) * lineIndex);
            lineIndex++;
        }
        if (subText != null) {
            g.setFont(GameFont.getSmallUnderline());
            lineIndex = 0;
            for (String line : subText.split("#")) {
                g.setColor(setColours(lineIndex));
                g.drawString(line, 26 + ((11 - line.length()) * 12), 62 + (height + 16) * lineIndex);
                lineIndex++;
            }
        }
    }
}
