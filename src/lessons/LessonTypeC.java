package lessons;

import game.Emotion;
import objects.GameObject;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Created by Luke on 06/12/2017.
 */
public class LessonTypeC extends Lesson {
    public int time, energy;
    public double laps = 0;
    double bonusScore = 0, consecutiveRun = 0;
    public boolean started, rested;

    public LessonTypeC(int grade) {
        super(grade);
        energy = setStart(grade);
        if (GAME.player.condition == 1) { energy += 2; }
        if (GAME.player.condition == 2) {
            if (energy > 1) { energy -= 2; }
            if (energy == 1) { energy--; }
        }
        time = 60;
        rounds = 10;
        questionText = FileReader.lessonStrings[30];
        rested = false;

        GAME.menu = new Menu(10);
    }

    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (!started) {
            switch (action) {
                case 0:
                    energy++;
                    feedbackText = FileReader.lessonStrings[31];
                    time -= 5;
                    break;
                case 1:
                    GAME.doTransition();
                    feedbackText = FileReader.lessonStrings[32];
                    GAME.menu = new Menu(11);
                    started = true;
                    break;
                case 2:
                    if (GAME.items[0][0] > 0) {
                        GameAudio.playSfx(GameAudio.sfx_buff);
                        energy++;
                        GAME.items[0][0]--;
                        feedbackText = FileReader.lessonStrings[9] + FileReader.lessonStrings[33];
                        GAME.player.emotion = new Emotion(5);
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[11];
                        GAME.player.emotion = new Emotion(6);
                    }
                    break;
            }
        }
        else {
            switch (action) {
                    case 0:
                        laps += 0.25;
                        consecutiveRun = 0;
                        feedbackText = FileReader.lessonStrings[34];
                        time -= 5;
                        rested = false;
                        break;
                    case 1:
                        if (energy > 0) {
                            laps += 0.5;
                            energy--;
                            feedbackText = FileReader.lessonStrings[35];
                            if (consecutiveRun > 0) {
                                bonusScore += consecutiveRun;
                                feedbackText = FileReader.lessonStrings[37] + FileReader.lessonStrings[38];
                                GAME.player.emotion = new Emotion(5);
                            }
                            consecutiveRun++;
                        }
                        else {
                            feedbackText = FileReader.lessonStrings[40] + FileReader.lessonStrings[42];
                            GAME.player.emotion = new Emotion(3);
                        }
                        time -= 5;
                        rested = false;
                        break;
                    case 2:
                        if (energy > 1) {
                            laps++;
                            energy -= 2;
                            feedbackText = FileReader.lessonStrings[36];
                            if (consecutiveRun > 0) {
                                bonusScore += consecutiveRun * 2;
                                feedbackText = FileReader.lessonStrings[37] + FileReader.lessonStrings[39];
                                GAME.player.emotion = new Emotion(2);
                            }
                            consecutiveRun++;

                        }
                        else {
                            feedbackText = FileReader.lessonStrings[41] + FileReader.lessonStrings[42];
                            GAME.player.emotion = new Emotion(3);
                        }
                        time -= 5;
                        rested = false;
                        break;
                    case 3:
                        if (!rested) {
                            GameAudio.playSfx(GameAudio.sfx_buff);
                            GAME.doTransition();
                            feedbackText = FileReader.lessonStrings[43];
                            energy++;
                            consecutiveRun = 0;
                            time -= 5;
                            rested = true;
                            GAME.player.emotion = new Emotion(5);
                        } else {
                            feedbackText = FileReader.lessonStrings[44];
                            GAME.player.emotion = new Emotion(6);
                        }
                        break;
                    case 4:
                        if (GAME.items[0][0] > 0) {
                            GameAudio.playSfx(GameAudio.sfx_buff);
                            energy++;
                            GAME.items[0][0]--;
                            feedbackText = FileReader.lessonStrings[9] + FileReader.lessonStrings[33];
                            consecutiveRun = 0;
                            GAME.player.emotion = new Emotion(5);
                        }
                        else {
                            feedbackText = FileReader.lessonStrings[11];
                            GAME.player.emotion = new Emotion(6);
                        }
                        break;
            }
        }
        feedback = true;
        if (time == 0) {
            GAME.player.emotion = null;
            score = (laps + bonusScore) / 2;
            feedbackText = FileReader.lessonStrings[5] + FileReader.lessonStrings[6] + + ((laps + bonusScore) * 5) + "!";
            finished = true;
        }
    }

    public static void movingScript(GameObject obj) {
        int lowX = 24, highX = 36, lowY = 13, highY = 20;
        if (obj.x == lowX && obj.y < highY) {
            obj.down = true;
            obj.move();
        }
        if (obj.x == highX && obj.y > lowY) {
            obj.up = true;
            obj.move();
        }
        if (obj.y == lowY && obj.x > lowX) {
            obj.left = true;
            obj.move();
        }
        if (obj.y== highY && obj.x < highX) {
            obj.right = true;
            obj.move();
        }

    }


}
