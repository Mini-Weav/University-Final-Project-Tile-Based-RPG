package lessons;

import objects.Player;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Created by Luke on 06/12/2017.
 */
public class LessonTypeC extends Lesson {
    public int time, energy;
    public double score = 0;
    double bonusScore = 0, consecutiveRun = 0;
    public boolean started;

    public LessonTypeC(int grade) {
        super(grade);
        energy = setStart(grade);
        if (GAME.condition == 1) { energy += 2; }
        if (GAME.condition == 2) {
            if (energy > 1) { energy -= 2; }
            if (energy == 1) { energy--; }
        }
        time = 60;
        questionText = "What will you do?";

        GAME.menu = new Menu(10);
    }

    public void doAction(int action) {
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
                        score += 0.25;
                        consecutiveRun = 0;
                        feedbackText = "You jogged a quarter of a#lap!";
                        time -= 5;
                        break;
                    case 1:
                        if (energy > 0) {
                            score += 0.5;
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
                        break;
                    case 2:
                        if (energy > 1) {
                            score++;
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
                        break;
                    case 3:
                        GAME.doTransition();
                        feedbackText = "You take a moment to#rest... You've regained#some energy!";
                        energy++;
                        consecutiveRun = 0;
                        time -= 5;
                        break;
                    case 4:
                        if (GAME.items[0][0] > 0) {
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
            feedbackText = "The lesson is over!#You got a score of " + ((score + bonusScore) * 10) + "!";
            finished = true;
        }
    }

    public static void movingScript(Player player) {
        int lowX = 24, highX = 36, lowY = 13, highY = 20;
        if (player.x == lowX && player.y < highY) {
            player.down = true;
            player.move();
        }
        if (player.x == highX && player.y > lowY) {
            player.up = true;
            player.move();
        }
        if (player.y == lowY && player.x > lowX) {
            player.left = true;
            player.move();
        }
        if (player.y== highY && player.x < highX) {
            player.right = true;
            player.move();
        }

    }


}
