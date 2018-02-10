package lessons;

import game.Emotion;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Created by Luke on 05/12/2017.
 */
public class LessonTypeA extends Lesson {
    public int attentionSpan, concentration, questionsLeft;
    int id, startingConcentration, questionId;
    double lv1, lv2;
    boolean toilet;

    public LessonTypeA(int id, int grade) {
        super(grade);
        this.id = id;
        startingConcentration = setStart(grade);
        if (GAME.player.condition == 1) { startingConcentration++; }
        if (GAME.player.condition == 2 && startingConcentration > 0) { startingConcentration--; }
        concentration = startingConcentration;
        rounds = setNumberOfQuestions(grade);
        questionsLeft = rounds;
        attentionSpan = 5;
        toilet = false;

        questionId = generateQuestion(id, lv1, lv2);

        if (id == 0 ) {
            lv1 = 0.2;
            lv2 = 0.7;
        }
        else {
            lv1 = 0.3;
            lv2 = 0.6;
        }

        GAME.menu = new Menu(8);

    }

    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (action) {
            case 0:
                score += answer(concentration, questionId);
                questionsLeft--;
                concentration = startingConcentration;
                questionId = generateQuestion(id, lv1, lv2);
                if (questionsLeft == 0) {
                    finished = true;
                    feedbackText = FileReader.lessonStrings[5] + FileReader.lessonStrings[6] + (int)((score / rounds) * 100) + "!";
                }
                break;
            case 1:
                if (attentionSpan > 0) {
                    attentionSpan--;
                    concentration++;
                    feedbackText = FileReader.lessonStrings[7];
                }
                else {
                    feedbackText = FileReader.lessonStrings[8];
                    GAME.player.emotion = new Emotion(3);
                }
                break;
            case 2:
                if (GAME.items[0][0] > 0) {
                    GameAudio.playSfx(GameAudio.sfx_buff);
                    attentionSpan++;
                    concentration++;
                    GAME.items[0][0]--;
                    feedbackText = FileReader.lessonStrings[9] + FileReader.lessonStrings[10];
                    GAME.player.emotion = new Emotion(5);
                }
                else {
                    feedbackText = FileReader.lessonStrings[11];
                    GAME.player.emotion = new Emotion(6);
                }
                break;
            case 3:
                if (!toilet && questionsLeft > 1) {
                    GameAudio.playSfx(GameAudio.sfx_buff);
                    GAME.doTransition();
                    attentionSpan++;
                    toilet = true;
                    questionId = generateQuestion(id, lv1, lv2);
                    questionsLeft--;
                    feedbackText = FileReader.lessonStrings[12];
                    GAME.player.emotion = new Emotion(5);
                }
                else if (toilet){
                    feedbackText = FileReader.lessonStrings[13];
                    GAME.player.emotion = new Emotion(6);
                }
                else {
                    feedbackText = FileReader.lessonStrings[14];
                    GAME.player.emotion = new Emotion(6);
                }
                break;
        }
        feedback = true;
    }

    public double answer(int concentration, int questionId) {
        switch (questionId) {
            case 0:
                if (concentration > 0) {
                    feedbackText = FileReader.lessonStrings[15];
                    GAME.player.emotion = new Emotion(2);
                    return 1;
                }
                else {
                    feedbackText = FileReader.lessonStrings[17];
                    GAME.player.emotion = new Emotion(7);
                    return 0;
                }
            case 1:
                if (concentration == 2) {
                    feedbackText = FileReader.lessonStrings[16];
                    GAME.player.emotion = new Emotion(5);
                    return 0.5;
                }
                else if (concentration > 2) {
                    feedbackText = FileReader.lessonStrings[15];
                    GAME.player.emotion = new Emotion(2);
                    return 1;
                }
                else {
                    feedbackText = FileReader.lessonStrings[17];
                    GAME.player.emotion = new Emotion(7);
                    return 0;
                }
            case 2:
                if (concentration == 3) {
                    feedbackText = FileReader.lessonStrings[16];
                    GAME.player.emotion = new Emotion(5);
                    return 0.5;
                }
                else if (concentration > 3) {
                    feedbackText = FileReader.lessonStrings[15];
                    GAME.player.emotion = new Emotion(2);
                    return 1;
                }
                else {
                    feedbackText = FileReader.lessonStrings[17];
                    GAME.player.emotion = new Emotion(7);
                    return 0;
                }
        }
        return 0;
    }
}
