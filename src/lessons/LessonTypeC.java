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
            feedback = true;
        }
        else {
            switch (action) {
                    case 0:
                        score += 0.25;
                        consecutiveRun = 0;
                        time -= 5;
                        break;
                    case 1:
                        if (energy > 0) {
                            score += 0.5;
                            energy--;
                            if (consecutiveRun > 0) {
                                bonusScore+= consecutiveRun;
                                feedbackText = "The teacher noticed your#effort... You got extra#credit!";
                                feedback = true;
                            }
                            consecutiveRun++;
                        }
                        else {
                            feedbackText = "You try to run...#You don't have enough#energy!";
                            feedback = true;}
                        time -= 5;
                        break;
                    case 2:
                        if (energy > 1) {
                            score++;
                            energy -= 2;
                            if (consecutiveRun > 0) {
                                bonusScore+= consecutiveRun*2;
                                feedbackText = "The teacher noticed your#effort... You got a lot of#extra credit!";
                                feedback = true;
                            }
                            consecutiveRun++;

                        }
                        else {
                            feedbackText = "You try to sprint...#You don't have enough#energy!";
                            feedback = true;
                        }

                        time -= 5;
                        break;
                    case 3:
                        GAME.doTransition();
                        feedbackText = "You take a moment to#rest... You've regained#some energy!";
                        energy++;
                        consecutiveRun = 0;
                        time -= 5;
                        feedback = true;
                        break;
                    case 4:
                        if (GAME.items[0][0] > 0) {
                            energy++;
                            GAME.items[0][0]--;
                            feedbackText = "You drink a powerful energy#drink... Your energy has#increased!";
                            consecutiveRun = 0;
                        }
                        else { feedbackText = "You're out of energy#drinks!"; }
                        feedback = true;
                        break;
            }
        }
        if (time == 0) {
            feedbackText = "The lesson is over!#You got a score of " + ((score + bonusScore) * 10) + "!";
            feedback = true;
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
