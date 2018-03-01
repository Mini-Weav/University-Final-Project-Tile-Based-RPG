package lessons;

import game.Emotion;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Created by Luke on 06/12/2017.
 */
public class LessonTypeB extends Lesson {
    public int tasksLeft, timeLeft;
    int id, questionId;
    public boolean reread;
    double result;

    public LessonTypeB(int id, int grade) {
        super(grade);
        rounds = setNumberOfQuestions(grade);
        tasksLeft = rounds;
        timeLeft = 60;

        this.id = id;
        if (GAME.player.condition == 1) { grade++; }
        if (GAME.player.condition == 2 && grade > 1) { grade--; }
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
                timeLeft -= 5;
                break;
            case 1:
                if (timeLeft > 5) {
                    result = doTask(true, reread, questionId);
                    score += (result / 2);
                    if (result > 0) {
                        if (GAME.player.condition == 1) { questionId = generateInstruction(grade + 1); }
                        else if (GAME.player.condition == 2 && grade > 0) { questionId = generateInstruction(grade - 1); }
                        else { questionId = generateInstruction(grade); }
                        tasksLeft--;
                        reread = false;
                    }
                    timeLeft -= 10;
                }
                else {
                    feedbackText = FileReader.lessonStrings[21];
                    GAME.player.emotion = new Emotion(3);
                    timeLeft -= 5;

                }
                break;
            case 2:
                if (!reread) {
                    reread = true;
                    feedbackText = FileReader.lessonStrings[22];
                    timeLeft -= 5;
                    if (questionId > 0) { questionText = FileReader.lessonStrings[questionId + 17]; }
                }
                else { feedbackText = FileReader.lessonStrings[23]; }
                break;
            case 3:
                rules = true;
                return;
        }
        feedback = true;
        if (timeLeft == 0 || tasksLeft == 0) {
            if (tasksLeft == 0) {
                GameAudio.playSfx(GameAudio.sfx_item);
                feedbackText = FileReader.lessonStrings[24];
                GAME.player.emotion = new Emotion(2);
                if (GAME.items[id][3] > 0) { GAME.items[id][2]++; }
                else { GAME.items[id][grade - 1]++;}
            }
            else {
                feedbackText = FileReader.lessonStrings[25];
                GAME.player.emotion = new Emotion(6);
            }
            feedbackText += FileReader.lessonStrings[6] +(int)((score / rounds) * 100) + "!";
            finished = true;
        }
    }

    public double doTask(boolean meticulous, boolean reread, int questionId) {
        switch (questionId) {
            case 0:
                if (meticulous) {
                    feedbackText = FileReader.lessonStrings[26];
                    GAME.player.emotion = new Emotion(2);
                    return 2;
                }
                else {
                    feedbackText = FileReader.lessonStrings[27];
                    GAME.player.emotion = new Emotion(5);
                    return 1;
                }
            case 1:
                if (meticulous) {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[26];
                        GAME.player.emotion = new Emotion(2);
                        return 2;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[27];
                        GAME.player.emotion = new Emotion(5);
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[27];
                        GAME.player.emotion = new Emotion(5);
                        return 1;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[28];
                        GAME.player.emotion = new Emotion(6);
                        return 0.5;
                    }
                }
            case 2:
                if (meticulous) {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[27];
                        GAME.player.emotion = new Emotion(5);
                        return 2;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[28];
                        GAME.player.emotion = new Emotion(6);
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        feedbackText = FileReader.lessonStrings[28];
                        GAME.player.emotion = new Emotion(6);
                        return 0.5;
                    }
                    else {
                        feedbackText = FileReader.lessonStrings[29];
                        GAME.player.emotion = new Emotion(3);
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
