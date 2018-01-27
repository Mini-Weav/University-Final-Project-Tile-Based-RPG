package lessons;

import objects.GameObject;
import objects.Player;
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
        questionText = "What will you do?";
        rested = false;

        GAME.menu = new Menu(10);
    }

    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (!started) {
            switch (action) {
                case 0:
                    energy++;
                    feedbackText = "You warm up...#Your energy has increased!";
                    time -= 5;
                    break;
                case 1:
                    GAME.doTransition();
                    feedbackText = "You start running...";
                    GAME.menu = new Menu(11);
                    started = true;
                    break;
                case 2:
                    if (GAME.items[0][0] > 0) {
                        GameAudio.playSfx(GameAudio.sfx_buff);
                        energy++;
                        GAME.items[0][0]--;
                        feedbackText = "You drink a powerful energy#drink... Your energy has#increased!";
                    }
                    else { feedbackText = "You're out of energy#drinks!"; }
                    break;
            }
        }
        else {
            switch (action) {
                    case 0:
                        laps += 0.25;
                        consecutiveRun = 0;
                        feedbackText = "You jogged a quarter of a#lap!";
                        time -= 5;
                        rested = false;
                        break;
                    case 1:
                        if (energy > 0) {
                            laps += 0.5;
                            energy--;
                            feedbackText = "You ran half a lap!";
                            if (consecutiveRun > 0) {
                                bonusScore += consecutiveRun;
                                feedbackText = "The teacher noticed your#effort... You got extra#credit!";
                            }
                            consecutiveRun++;
                        }
                        else { feedbackText = "You try to run...#You don't have enough#energy!"; }
                        time -= 5;
                        rested = false;
                        break;
                    case 2:
                        if (energy > 1) {
                            laps++;
                            energy -= 2;
                            feedbackText = "You sprinted a full lap!";
                            if (consecutiveRun > 0) {
                                bonusScore += consecutiveRun * 2;
                                feedbackText = "The teacher noticed your#effort... You got a lot of#extra credit!";
                            }
                            consecutiveRun++;

                        }
                        else { feedbackText = "You try to sprint...#You don't have enough#energy!"; }
                        time -= 5;
                        rested = false;
                        break;
                    case 3:
                        if (!rested) {
                            GameAudio.playSfx(GameAudio.sfx_buff);
                            GAME.doTransition();
                            feedbackText = "You take a moment to#rest... You've regained#some energy!";
                            energy++;
                            consecutiveRun = 0;
                            time -= 5;
                            rested = true;
                        } else { feedbackText = "You just rested a#moment ago! Get moving!"; }
                        break;
                    case 4:
                        if (GAME.items[0][0] > 0) {
                            GameAudio.playSfx(GameAudio.sfx_buff);
                            energy++;
                            GAME.items[0][0]--;
                            feedbackText = "You drink a powerful energy#drink... Your energy has#increased!";
                            consecutiveRun = 0;
                        }
                        else { feedbackText = "You're out of energy#drinks!"; }
                        break;
            }
        }
        feedback = true;
        if (time == 0) {
            score = (laps + bonusScore) / 2;
            feedbackText = "The lesson is over!#You got a score of " + ((laps + bonusScore) * 5) + "!";
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
