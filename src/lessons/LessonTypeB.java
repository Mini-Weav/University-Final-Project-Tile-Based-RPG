package lessons;

import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

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
        if (GAME.player.condition == 1) { grade++; }
        if (GAME.player.condition == 2 && grade > 0) { grade--; }
        questionId = generateInstruction(grade);

        GAME.menu = new Menu(9);

    }

    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (action) {
            case 0:
                result = doTask(false, reread, questionId);
                score += (result / 2);
                if (result > 0) {
                    if (GAME.player.condition == 1) { questionId = generateInstruction(grade + 1); }
                    else if (GAME.player.condition == 2 && grade > 0) { questionId = generateInstruction(grade - 1); }
                    else { questionId = generateInstruction(grade); }
                    tasksLeft--;
                    reread = false;
                }
                time -= 5;
                break;
            case 1:
                if (time > 5) {
                    result = doTask(true, reread, questionId);
                    score += (result / 2);
                    if (result > 0) {
                        if (GAME.player.condition == 1) { questionId = generateInstruction(grade + 1); }
                        else if (GAME.player.condition == 2 && grade > 0) { questionId = generateInstruction(grade - 1); }
                        else { questionId = generateInstruction(grade); }
                        tasksLeft--;
                        reread = false;
                    }
                    time -= 10;
                }
                else {
                    feedbackText = FileReader.lessonStrings[21];
                    time -= 5;

                }
                break;
            case 2:
                if (!reread) {
                    reread = true;
                    feedbackText = FileReader.lessonStrings[22];
                    time -= 5;
                }
                else {
                    feedbackText = FileReader.lessonStrings[23];
                }
                break;
        }
        feedback = true;
        if (time == 0 || tasksLeft == 0) {
            if (tasksLeft == 0) {
                GameAudio.playSfx(GameAudio.sfx_item);
                feedbackText = FileReader.lessonStrings[24];
                if (GAME.items[id][3] > 0) { GAME.items[id][2]++; }
                else { GAME.items[id][grade - 1]++;}
            }
            else { feedbackText = FileReader.lessonStrings[25]; }
            feedbackText += FileReader.lessonStrings[6] +(int)((score / rounds) * 100) + "!";
            finished = true;
        }
    }

    public double doTask(boolean meticulous, boolean reread, int questionId) {
        switch (questionId) {
            case 0:
                if (meticulous) {
                    feedbackText = FileReader.lessonStrings[26];
                    return 2;
                }
                else {
                    feedbackText = FileReader.lessonStrings[27];
                    return 1;
                }
            case 1:
                if (meticulous) {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[26];
                        return 2;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[27];
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[27];
                        return 1;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[28];
                        return 0.5;
                    }
                }
            case 2:
                if (meticulous) {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[27];
                        return 2;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[28];
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[28];
                        return 0.5;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[29];
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
                break;
            case 5:
                lv1 = 0.6;
                lv2 = 1.0;
        }
        return generateQuestion(3, lv1, lv2);
    }
}
