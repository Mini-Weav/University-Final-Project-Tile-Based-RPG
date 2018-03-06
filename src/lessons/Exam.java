package lessons;

import game.Emotion;
import game.TileMap;
import utilities.*;

import java.util.ArrayList;
import java.util.Arrays;

import static game.Game.GAME;

/**
 * Manages game flow when in an 'Exam Lesson' state.
 */
public class Exam extends Lesson {
    private int attentionSpan;
    private int concentration;
    private int questionsLeft;
    private int timeLeft;
    private int id;
    private int startingConcentration;
    private int questionId;
    private boolean toilet;

    private ArrayList<Integer> questions = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3));

    /**
     * Class constructor.
     *
     * @param id the identifier for the exam subject (0 = DT, 1 = food tech, 2 = PE, 3 = chemistry, 4 = ICT)
     * @param grade the player's current points in the exam subject
     */
    private Exam(int id, int grade) {
        super(grade);
        this.id = id;
        this.setGrade(grade);
        startingConcentration = setStart(grade);
        if (GAME.getPlayer().getCondition() == 1) { startingConcentration++; }
        if (GAME.hasQuestions()) { startingConcentration++; }
        if (GAME.getPlayer().getCondition() == 2 && startingConcentration > 0) { startingConcentration--; }
        concentration = startingConcentration;
        setRounds(12);
        questionsLeft = getRounds();
        timeLeft = 90;
        attentionSpan = 5;
        toilet = false;

        questionId = generateQuestion();

        GAME.setMenu(new Menu(8));
    }

    public int getAttentionSpan() { return attentionSpan; }

    public int getConcentration() { return concentration; }

    public int getQuestionsLeft() { return questionsLeft; }

    public int getTimeLeft() { return timeLeft; }

    /**
     * Starts an Exam game state.
     * Sets the player's position and displays a TextBox and Menu.
     *
     * @param id the identifier for the exam subject (0 = DT, 1 = food tech, 2 = PE, 3 = chemistry, 4 = ICT)
     */
    public static void startExam(int id) {
        GAME.setDay(GAME.getDay() + 1);
        GAME.setDaysLeft(0);
        GAME.setExams(true);
        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.getMapId());
        currentMap.resetNPCs();
        int grade = (GAME.getGradeValue(id) / 10) + 1;
        if (grade > 4) { grade = 4; }
        GAME.setTime(id + 12);
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
        GAME.loadMap(nextMap);

        GAME.setExam(new Exam(id, grade));
        GAME.getExam().id = id;
    }

    /**
     * Ends the Exam state.
     * Increments the respective exam score and game points.
     * Closes the Exam shell screens and either ends the game or makes the player go home.
     */
    public void finish() {
        int increase = (int)((getScore() / getRounds()) * 5);
        GAME.setExamScore(id, increase);
        GAME.increasePoints(2, id, increase);

        GAME.setExamsLeft(GAME.getExamsLeft() - 1);
        GAME.setTextBox(null);
        GAME.setMenu(null);
        GAME.setExam(null);
        GAME.getPlayer().setCondition(0);
        if (GAME.getExamsLeft() > 0) { GAME.goHome(true); }
        else { GAME.finishGame(); }
    }

    /**
     * Handles the result of the supplied action.
     *
     * @param action the action to be done (0 = answer, 1 = think, 2 = drink, 3 = toilet, 4 = rules)
     */
    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (action) {
            case 0:
                setScore(getScore() + answer(concentration, questionId));
                int i = (int) (getScore() / 4);
                if (i > 2) { i = 2; }
                questionsLeft--;
                timeLeft -= 5;
                concentration = startingConcentration;
                if (timeLeft == 0) {
                    setFinished();
                    setFeedbackText(FileReader.getLessonString(46) + FileReader.getLessonString(47 + i));
                }
                else if (questionsLeft == 0) {
                    setScore(getScore() + (getGrade() * 0.25));
                    setFinished();
                    setFeedbackText(FileReader.getLessonString(50) + FileReader.getLessonString(46) +
                            FileReader.getLessonString(47 + i));
                }
                if (isNotFinished()) { questionId = generateQuestion(); }
                else {
                    switch (i) {
                        case 0:
                            GAME.getPlayer().setEmotion(new Emotion(7));
                            break;
                        case 1:
                            GAME.getPlayer().setEmotion(new Emotion(5));
                            break;
                        case 2:
                            GAME.getPlayer().setEmotion(new Emotion(2));
                            break;
                    }
                }
                break;
            case 1:
                if (attentionSpan > 0) {
                    timeLeft -= 5;
                    attentionSpan--;
                    concentration++;
                    if (timeLeft == 0) {
                        i = (int) (getScore() / 4);
                        if (i > 2) { i = 2; }
                        setFinished();
                        setFeedbackText(FileReader.getLessonString(46) + FileReader.getLessonString(47 + i));
                    }
                    else {
                        setFeedbackText(FileReader.getLessonString(7));
                    }
                }
                else {
                    setFeedbackText(FileReader.getLessonString(8));
                    GAME.getPlayer().setEmotion(new Emotion(3));
                }
                break;
            case 2:
                setFeedbackText(FileReader.getLessonString(51));
                GAME.getPlayer().setEmotion(new Emotion(6));
                break;
            case 3:
                if (!toilet && (timeLeft > 15 && timeLeft <= 75)) {
                    GameAudio.playSfx(GameAudio.sfx_buff);
                    GAME.doTransition();
                    attentionSpan++;
                    toilet = true;
                    timeLeft -= 10;
                    setFeedbackText(FileReader.getLessonString(56));
                    GAME.getPlayer().setEmotion(new Emotion(5));
                }
                else if (toilet){
                    setFeedbackText(FileReader.getLessonString(13));
                    GAME.getPlayer().setEmotion(new Emotion(6));
                }
                else {
                    setFeedbackText(FileReader.getLessonString(52));
                    GAME.getPlayer().setEmotion(new Emotion(6));
                }
                break;
            case 4:
                setRules(true);
                return;
        }
        setFeedback(true);
    }

    /**
     * Determines the score the player receives for their answer.
     *
     * @param concentration the player's current concentration level
     * @param questionId the type of question
     * @return the points gained from the player's answer
     */
    private double answer(int concentration, int questionId) {
            if (concentration < questionId + 1) {
                setFeedbackText(FileReader.getLessonString(17));
                GAME.getPlayer().setEmotion(new Emotion(7));
                return 0;
            }
            else if (concentration == questionId + 1) {
                setFeedbackText(FileReader.getLessonString(16));
                GAME.getPlayer().setEmotion(new Emotion(5));
                return 0.5;
            }
            else {
                setFeedbackText(FileReader.getLessonString(15));
                GAME.getPlayer().setEmotion(new Emotion(2));
                return 1;
            }
    }

    /**
     * Determine which type of question is next, from the values left in the questions ArrayList.
     *
     * @return the type of question's id
     */
    private int generateQuestion() {
        int r = (int) (Math.random() * questions.size());
        int questionId = questions.get(r);
        questions.remove(r);
        switch (questionId) {
            case 0:
                setQuestionText(FileReader.getLessonString(0));
                break;
            case 1:
                setQuestionText(FileReader.getLessonString(53));
                break;
            case 2:
                setQuestionText(FileReader.getLessonString(54));
                break;
            case 3 :
                setQuestionText(FileReader.getLessonString(55));
                break;
        }
        return questionId;
    }
    public TextBox showRules() { return new TextBox(1, FileReader.getInteractiveString(38)); }

}
