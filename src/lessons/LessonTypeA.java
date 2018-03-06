package lessons;

import game.Emotion;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Manages game flow when in a 'Chemistry/ICT Lesson' state.
 */
public class LessonTypeA extends Lesson {
    private int attentionSpan;
    private int concentration;
    private int questionsLeft;
    private int id;
    private int startingConcentration;
    private int questionId;
    private double lv1;
    private double lv2;
    private boolean toilet;

    /**
     * Class constructor.
     *
     * @param id the type of lesson (0 = chemistry, 1 = ICT)
     * @param grade the player's respective grade
     */
    LessonTypeA(int id, int grade) {
        super(grade);

        this.id = id;

        startingConcentration = setStart(grade);
        if (GAME.getPlayer().getCondition() == 1) { startingConcentration++; }
        if (GAME.getPlayer().getCondition() == 2 && startingConcentration > 0) { startingConcentration--; }
        concentration = startingConcentration;

        setRounds(setNumberOfQuestions(grade));
        questionsLeft = getRounds();
        attentionSpan = 5;
        toilet = false;

        questionId = generateQuestion(id, lv1, lv2);

        if (id == 0 ) {
            lv1 = 0.2;
            lv2 = 0.7;
        } else {
            lv1 = 0.3;
            lv2 = 0.6;
        }

        GAME.setMenu(new Menu(8));
    }

    public int getAttentionSpan() { return attentionSpan; }

    public int getConcentration() { return concentration; }

    public int getQuestionsLeft() { return questionsLeft; }

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
                questionsLeft--;
                concentration = startingConcentration;
                questionId = generateQuestion(id, lv1, lv2);
                if (questionsLeft == 0) {
                    setFinished();
                    GAME.getPlayer().setEmotion(null);
                    setFeedbackText(FileReader.getLessonString(5) + FileReader.getLessonString(6) +
                            (int)((getScore() / getRounds()) * 100) + "!");
                }
                break;
            case 1:
                if (attentionSpan > 0) {
                    attentionSpan--;
                    concentration++;
                    setFeedbackText(FileReader.getLessonString(7));
                }
                else {
                    setFeedbackText(FileReader.getLessonString(8));
                    GAME.getPlayer().setEmotion(new Emotion(3));
                }
                break;
            case 2:
                if (GAME.hasEnergyDrink()) {
                    GameAudio.playSfx(GameAudio.sfx_buff);
                    attentionSpan++;
                    concentration++;
                    GAME.drinkEnergyDrink();
                    setFeedbackText(FileReader.getLessonString(9) + FileReader.getLessonString(10));
                    GAME.getPlayer().setEmotion(new Emotion(5));
                }
                else {
                    setFeedbackText(FileReader.getLessonString(11));
                    GAME.getPlayer().setEmotion(new Emotion(6));
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
                    setFeedbackText(FileReader.getLessonString(12));
                    GAME.getPlayer().setEmotion(new Emotion(5));
                }
                else if (toilet){
                    setFeedbackText(FileReader.getLessonString(13));
                    GAME.getPlayer().setEmotion(new Emotion(6));
                }
                else {
                    setFeedbackText(FileReader.getLessonString(14));
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
        switch (questionId) {
            case 0:
                if (concentration > 0) {
                    setFeedbackText(FileReader.getLessonString(15));
                    GAME.getPlayer().setEmotion(new Emotion(2));
                    return 1;
                }
                else {
                    setFeedbackText(FileReader.getLessonString(17));
                    GAME.getPlayer().setEmotion(new Emotion(7));
                    return 0;
                }
            case 1:
                if (concentration == 2) {
                    setFeedbackText(FileReader.getLessonString(16));
                    GAME.getPlayer().setEmotion(new Emotion(5));
                    return 0.5;
                }
                else if (concentration > 2) {
                    setFeedbackText(FileReader.getLessonString(15));
                    GAME.getPlayer().setEmotion(new Emotion(2));
                    return 1;
                }
                else {
                    setFeedbackText(FileReader.getLessonString(17));
                    GAME.getPlayer().setEmotion(new Emotion(7));
                    return 0;
                }
            case 2:
                if (concentration == 3) {
                    setFeedbackText(FileReader.getLessonString(16));
                    GAME.getPlayer().setEmotion(new Emotion(5));
                    return 0.5;
                }
                else if (concentration > 3) {
                    setFeedbackText(FileReader.getLessonString(15));
                    GAME.getPlayer().setEmotion(new Emotion(2));
                    return 1;
                }
                else {
                    setFeedbackText(FileReader.getLessonString(17));
                    GAME.getPlayer().setEmotion(new Emotion(7));
                    return 0;
                }
        }
        return 0;
    }
}
