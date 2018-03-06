package lessons;

import game.Emotion;
import objects.GameObject;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Manages game flow when in a 'PE Lesson' state.
 */
public class LessonTypeC extends Lesson {
    private int time;
    private int energy;
    private double laps = 0;
    private double bonusScore = 0;
    private double consecutiveRun = 0;
    private boolean started;
    private boolean rested;

    /**
     * Class constructor.
     *
     * @param grade the player's respective grade
     */
    LessonTypeC(int grade) {
        super(grade);
        energy = setStart(grade);
        if (GAME.getPlayer().getCondition() == 1) { energy += 2; }
        if (GAME.getPlayer().getCondition() == 2) {
            if (energy > 1) { energy -= 2; }
            else if (energy == 1) { energy--; }
        }
        time = 60;
        setRounds(10);
        setQuestionText(FileReader.getLessonString(30));
        rested = false;

        GAME.setMenu(new Menu(10));
    }

    public int getTime() { return time; }

    public int getEnergy() { return energy; }

    public double getLaps() { return laps; }

    public boolean isStarted() { return started; }

    /**
     * Handles the result of the supplied action.
     *
     * @param action the action to be done (0 = answer, 1 = think, 2 = drink, 3 = toilet, 4 = rules)
     */
    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (!started) {
            switch (action) {
                case 0:
                    energy++;
                    setFeedbackText(FileReader.getLessonString(31));
                    time -= 5;
                    break;
                case 1:
                    GAME.doTransition();
                    setFeedbackText(FileReader.getLessonString(32));
                    GAME.setMenu(new Menu(11));
                    started = true;
                    break;
                case 2:
                    if (GAME.hasEnergyDrink()) {
                        GameAudio.playSfx(GameAudio.sfx_buff);
                        energy++;
                        GAME.drinkEnergyDrink();
                        setFeedbackText(FileReader.getLessonString(9) + FileReader.getLessonString(33));
                        GAME.getPlayer().setEmotion(new Emotion(5));
                    }
                    else {
                        setFeedbackText(FileReader.getLessonString(11));
                        GAME.getPlayer().setEmotion(new Emotion(6));
                    }
                    break;
                case 3:
                    setRules(true);
                    return;
            }
        }
        else {
            switch (action) {
                    case 0:
                        laps += 0.25;
                        consecutiveRun = 0;
                        setFeedbackText(FileReader.getLessonString(34));
                        time -= 5;
                        rested = false;
                        break;
                    case 1:
                        if (energy > 0) {
                            laps += 0.5;
                            energy--;
                            setFeedbackText(FileReader.getLessonString(35));
                            if (consecutiveRun > 0) {
                                bonusScore += consecutiveRun;
                                setFeedbackText(FileReader.getLessonString(37) + FileReader.getLessonString(38));
                                GAME.getPlayer().setEmotion(new Emotion(5));
                            }
                            consecutiveRun++;
                        }
                        else {
                            setFeedbackText(FileReader.getLessonString(40) + FileReader.getLessonString(42));
                            GAME.getPlayer().setEmotion(new Emotion(3));
                        }
                        time -= 5;
                        rested = false;
                        break;
                    case 2:
                        if (energy > 1) {
                            laps++;
                            energy -= 2;
                            setFeedbackText(FileReader.getLessonString(36));
                            if (consecutiveRun > 0) {
                                bonusScore += consecutiveRun * 2;
                                setFeedbackText(FileReader.getLessonString(37) + FileReader.getLessonString(39));
                                GAME.getPlayer().setEmotion(new Emotion(2));
                            }
                            consecutiveRun++;

                        }
                        else {
                            setFeedbackText(FileReader.getLessonString(41) + FileReader.getLessonString(42));
                            GAME.getPlayer().setEmotion(new Emotion(3));
                        }
                        time -= 5;
                        rested = false;
                        break;
                    case 3:
                        if (!rested) {
                            GameAudio.playSfx(GameAudio.sfx_buff);
                            GAME.doTransition();
                            setFeedbackText(FileReader.getLessonString(43));
                            energy++;
                            consecutiveRun = 0;
                            time -= 5;
                            rested = true;
                            GAME.getPlayer().setEmotion(new Emotion(5));
                        } else {
                            setFeedbackText(FileReader.getLessonString(44));
                            GAME.getPlayer().setEmotion(new Emotion(6));
                        }
                        break;
                    case 4:
                        if (GAME.hasEnergyDrink()) {
                            GameAudio.playSfx(GameAudio.sfx_buff);
                            energy++;
                            GAME.drinkEnergyDrink();
                            setFeedbackText(FileReader.getLessonString(9) + FileReader.getLessonString(33));
                            consecutiveRun = 0;
                            GAME.getPlayer().setEmotion(new Emotion(5));
                        }
                        else {
                            setFeedbackText(FileReader.getLessonString(11));
                            GAME.getPlayer().setEmotion(new Emotion(6));
                        }
                        break;
                case 5:
                    setRules(true);
                    return;
            }
        }
        setFeedback(true);
        if (time == 0) {
            GAME.getPlayer().setEmotion(null);
            setScore((laps + bonusScore) / 2);
            setFeedbackText(FileReader.getLessonString(5) + FileReader.getLessonString(6) +
                    ((laps + bonusScore) * 5) + "!");
            setFinished();
        }
    }

    /**
     * A script that moves the player around the running track
     *
     * @param obj the GameObject to apply the script to (typically the player)
     */
    public static void movingScript(GameObject obj) {
        int lowX = 24, highX = 36, lowY = 13, highY = 20;
        if (obj.getX() == lowX && obj.getY() < highY) {
            obj.setDown(true);
            obj.move();
        }
        if (obj.getX() == highX && obj.getY() > lowY) {
            obj.setUp(true);
            obj.move();
        }
        if (obj.getY() == lowY && obj.getX() > lowX) {
            obj.setLeft(true);
            obj.move();
        }
        if (obj.getY() == highY && obj.getX() < highX) {
            obj.setRight(true);
            obj.move();
        }
    }
}
