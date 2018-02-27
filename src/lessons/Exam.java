package lessons;

import game.Emotion;
import game.TileMap;
import objects.NPC;
import utilities.*;

import java.util.ArrayList;
import java.util.Arrays;

import static game.Game.GAME;

/**
 * Created by lmweav on 16/02/2018.
 */
public class Exam extends Lesson {
    public int attentionSpan, concentration, questionsLeft, timeLeft;
    int id, startingConcentration, questionId;
    boolean toilet;

    ArrayList<Integer> questions = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3));

    public static void startExam(int id) {
        GAME.day++;
        GAME.daysLeft = 0;
        GAME.isExams = true;
        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.map.currentId);
        try { for (NPC npc : currentMap.NPCs.get(GAME.time)) { npc.reset(); } }
        catch (NullPointerException e) {}
        int grade = (GAME.gradeValues[id] / 10) + 1;
        if (grade > 4) { grade = 4; }
        GAME.time = id + 12;
        GameAudio.startMusic(GameAudio.music_exam);

        int mapId = -1;
        switch (id) {
            case 0:
                mapId = 2;
                break;
            case 1:
                mapId = 3;
                break;
            case 2:
            case 3:
            case 4:
                mapId = 4;
                break;
        }

        TileMap nextMap = TileMapLoader.tileMaps.get(mapId);
        GAME.map.loadMap(nextMap);

        GAME.exam = new Exam(id, grade);
        GAME.exam.id = id;
    }

    public void finish() {
        int increase = (int)((score / rounds) * 5);
        GAME.examScores[id] = increase;
        GAME.increaseValues(2, id, increase);

        GAME.examsLeft--;
        GAME.textBox = null;
        GAME.menu = null;
        GAME.exam = null;
        GAME.player.condition = 0;
        if (GAME.examsLeft > 0) { GAME.goHome(true); }
        else { GAME.finishGame(); }
    }

    public Exam(int id, int grade) {
        super(grade);
        this.id = id;
        this.grade = grade;
        startingConcentration = setStart(grade);
        if (GAME.player.condition == 1) { startingConcentration++; }
        if (GAME.items[0][2] > 0) { startingConcentration++; }
        if (GAME.player.condition == 2 && startingConcentration > 0) { startingConcentration--; }
        concentration = startingConcentration;
        rounds = 12;
        questionsLeft = rounds;
        timeLeft = 90;
        attentionSpan = 5;
        toilet = false;

        questionId = generateQuestion();

        GAME.menu = new Menu(8);

    }

    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (action) {
            case 0:
                score += answer(concentration, questionId);
                int i = (int) (score / 4);
                if (i > 2) { i = 2; }
                questionsLeft--;
                timeLeft -= 5;
                concentration = startingConcentration;
                if (timeLeft == 0) {
                    finished = true;
                    feedbackText = FileReader.lessonStrings[46] + FileReader.lessonStrings[47 + i];
                }
                else if (questionsLeft == 0) {
                    score += (grade * 0.25);
                    finished = true;
                    feedbackText = FileReader.lessonStrings[50] + FileReader.lessonStrings[46] + FileReader.lessonStrings[47 + i];
                }
                if (!finished) { questionId = generateQuestion(); }
                else {
                    switch (i) {
                        case 0:
                            GAME.player.emotion = new Emotion(7);
                            break;
                        case 1:
                            GAME.player.emotion = new Emotion(5);
                            break;
                        case 2:
                            GAME.player.emotion = new Emotion(2);
                            break;
                    }
                }
                break;
            case 1:
                timeLeft -= 5;
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
                feedbackText = FileReader.lessonStrings[51];
                GAME.player.emotion = new Emotion(6);
                break;
            case 3:
                if (!toilet && (timeLeft > 15 && timeLeft <= 75)) {
                    GameAudio.playSfx(GameAudio.sfx_buff);
                    GAME.doTransition();
                    attentionSpan++;
                    toilet = true;
                    timeLeft -= 10;
                    feedbackText = FileReader.lessonStrings[56];
                    GAME.player.emotion = new Emotion(5);
                }
                else if (toilet){
                    feedbackText = FileReader.lessonStrings[13];
                    GAME.player.emotion = new Emotion(6);
                }
                else {
                    feedbackText = FileReader.lessonStrings[52];
                    GAME.player.emotion = new Emotion(6);
                }
                break;
            case 4:
                rules = true;
                return;
        }
        feedback = true;
    }

    public double answer(int concentration, int questionId) {
            if (concentration < questionId + 1) {
                feedbackText = FileReader.lessonStrings[17];
                GAME.player.emotion = new Emotion(7);
                return 0;
            }
            else if (concentration == questionId + 1) {
                feedbackText = FileReader.lessonStrings[16];
                GAME.player.emotion = new Emotion(5);
                return 0.5;
            }
            else {
                feedbackText = FileReader.lessonStrings[15];
                GAME.player.emotion = new Emotion(2);
                return 1;
            }
    }

    public int generateQuestion() {
        int r = (int) (Math.random() * questions.size());
        int questionId = questions.get(r);
        questions.remove(r);
        switch (questionId) {
            case 0:
                questionText = FileReader.lessonStrings[0];
                break;
            case 1:
                questionText = FileReader.lessonStrings[53];
                break;
            case 2:
                questionText = FileReader.lessonStrings[54];
                break;
            case 3 :
                questionText = FileReader.lessonStrings[55];
                break;
        }
        return questionId;
    }

    public TextBox showRules() {
        return new TextBox(1, FileReader.interactiveStrings[38]);
    }
}
