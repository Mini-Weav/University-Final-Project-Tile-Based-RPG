package lessons;

import game.Game;
import utilities.Menu;

/**
 * Created by Luke on 06/12/2017.
 */
public class LessonTypeB extends Lesson {
    public int tasksLeft, time;
    int id, questionId;
    public boolean reread;
    double result;

    public LessonTypeB(int id, int grade) {
        super(grade);
        rounds = setNumberOfQuestions(grade);
        tasksLeft = rounds;
        time = 60;

        this.id = id;
        questionId = generateInstruction(grade);

        Game.menu = new Menu(9);

    }

    public void doAction(int action) {
        switch (action) {
            case 0:
                result = doTask(false, reread, questionId);
                score += result;
                if (result > 0) {
                    questionId = generateInstruction(grade);
                    tasksLeft--;
                }
                time -= 5;
                break;
            case 1:
                if (time > 5) {
                    result = doTask(true, reread, questionId);
                    score += result;
                    if (result > 0) {
                        questionId = generateInstruction(grade);
                        tasksLeft--;
                    }
                    time -= 10;
                }
                else {
                    feedbackText = "You failed to complete the#task in time.";
                    time -= 5;

                }
                break;
            case 2:
                if (!reread) {
                    reread = true;
                    feedbackText = "You reread the#instructions... Your#understanding has improved!";
                    time -= 5;
                }
                else {
                    feedbackText = "You've already reread#the instructions!";
                }
                break;
        }
        feedback = true;
        if (time == 0 || tasksLeft == 0) {
            if (tasksLeft == 0) {
                feedbackText = "You finished every task#and made an item!";
                Game.items[id][grade - 1]++;
            }
            else { feedbackText = "You're out of time!"; }
            feedbackText += "#You got a score of "+(int)((score/rounds)*50) + "!";
            finished = true;
        }
    }

    public double doTask(boolean meticulous, boolean reread, int questionId) {
        switch (questionId) {
            case 0:
                if (meticulous) {
                    feedbackText = "You completed the task to#an excellent standard!";
                    return 2;
                }
                else {
                    feedbackText = "You completed the task to#a good standard!";
                    return 1;
                }
            case 1:
                if (meticulous) {
                    if (reread) {
                        feedbackText = "You completed the task to#an excellent standard!";
                        return 2;
                    }
                    else {
                        feedbackText = "You completed the task to#a good standard!";
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        feedbackText = "You completed the task to a#good standard!";
                        return 1;
                    }
                    else {
                        feedbackText = "You completed the task to a#satisfactory standard.";
                        return 0.5;
                    }
                }
            case 2:
                if (meticulous) {
                    if (reread) {
                        feedbackText = "You completed the task to a#good standard!";
                        return 2;
                    }
                    else {
                        feedbackText = "You completed the task to a#satisfactory standard.";
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        feedbackText = "You completed the task to a#satisfactory standard.";
                        return 0.5;
                    }
                    else {
                        feedbackText = "You didn't complete the#task.";
                        return 0;
                    }
                }
        }
        return 0;
    }

    public int generateInstruction(int grade) {
        double lv1 = 0, lv2 = 0;
        switch (grade) {
            case 1:
                lv1 = 0.2;
                lv2 = 0.7;
                break;
            case 2:
                lv1 = 0.3;
                lv2 = 0.8;
                break;
            case 3:
                lv1 = 0.4;
                lv2 = 0.8;
                break;
            case 4:
                lv1 = 0.5;
                lv2 = 0.9;
        }
        return generateQuestion(3, lv1, lv2);
    }
}
