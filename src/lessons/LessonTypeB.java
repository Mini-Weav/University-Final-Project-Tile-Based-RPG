package lessons;

import game.Emotion;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * Manages game flow when in a 'DT/Food Tech Lesson' state.
 */
public class LessonTypeB extends Lesson {
    private int tasksLeft;
    private int timeLeft;
    private int id;
    private int questionId;
    private boolean reread;

    /**
     * Class constructor.
     *
     * @param id the type of lesson (1 = DT, 2 = food tech)
     * @param grade the player's respective grade
     */
    LessonTypeB(int id, int grade) {
        super(grade);

        this.id = id;

        setRounds(setNumberOfQuestions(grade));
        tasksLeft = getRounds();
        timeLeft = 60;

        if (GAME.getPlayer().getCondition() == 1) { grade++; }
        if (GAME.getPlayer().getCondition() == 2 && grade > 1) { grade--; }

        questionId = generateInstruction(grade);

        GAME.setMenu(new Menu(9));
    }

    public int getTasksLeft() { return tasksLeft; }

    public int getTimeLeft() { return timeLeft; }

    public boolean isReread() { return reread; }

    /**
     * Handles the result of the supplied action.
     *
     * @param action the action to be done (0 = answer, 1 = think, 2 = drink, 3 = toilet, 4 = rules)
     */
    public void doAction(int action) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (action) {
            case 0:
                double result = doTask(false, reread, questionId);
                setScore(getScore() + (result / 2));
                if (result > 0) { completeTask(); }
                timeLeft -= 5;
                break;
            case 1:
                if (timeLeft > 5) {
                    result = doTask(true, reread, questionId);
                    setScore(getScore() + (result / 2));
                    if (result > 0) { completeTask(); }
                    timeLeft -= 10;
                }
                else {
                    setFeedbackText(FileReader.getLessonString(21));
                    GAME.getPlayer().setEmotion(new Emotion(3));
                    timeLeft -= 5;

                }
                break;
            case 2:
                if (!reread) {
                    reread = true;
                    setFeedbackText(FileReader.getLessonString(22));
                    timeLeft -= 5;
                    if (questionId > 0) { setQuestionText(FileReader.getLessonString(questionId + 17)); }
                }
                else { setFeedbackText(FileReader.getLessonString(23)); }
                break;
            case 3:
                setRules(true);
                return;
        }
        setFeedback(true);
        if (timeLeft == 0 || tasksLeft == 0) {
            if (tasksLeft == 0) {
                GameAudio.playSfx(GameAudio.sfx_item);
                setFeedbackText(FileReader.getLessonString(24));
                GAME.getPlayer().setEmotion(new Emotion(2));
                setScore(getScore() * 2);
                if (GAME.hasItem(id, 3)) { GAME.giveItem(id, 2); }
                else { GAME.giveItem(id, getGrade() - 1); }
            }
            else {
                setFeedbackText(FileReader.getLessonString(25));
                GAME.getPlayer().setEmotion(new Emotion(6));
            }
            setFeedbackText(getFeedbackText() + FileReader.getLessonString(6) +
                    (int)((getScore() / getRounds()) * 100) + "!");
            setFinished();
        }
    }

    /**
     * Determines the score the player receives for the task.
     *
     * @param meticulous whether or not the player did the task meticulously
     * @param reread whether or not the player reread the instructions
     * @param questionId the type of question
     * @return the points gained from the player's completed task
     */
    private double doTask(boolean meticulous, boolean reread, int questionId) {
        switch (questionId) {
            case 0:
                return goodScore(meticulous);
            case 1:
                if (meticulous) {
                    return goodScore(reread);
                }
                else {
                    return okScore(reread);
                }
            case 2:
                if (meticulous) {
                    return okScore(reread);
                }
                else {
                    if (reread) {
                        setFeedbackText(FileReader.getLessonString(28));
                        GAME.getPlayer().setEmotion(new Emotion(6));
                        return 0.5;
                    }
                    else {
                        setFeedbackText(FileReader.getLessonString(29));
                        GAME.getPlayer().setEmotion(new Emotion(3));
                        return 0;
                    }
                }
        }
        return 0;
    }

    /**
     * Determines the result of a task done to at least a nok standard.
     *
     * @param goodStandard whether or not the task is done to an excellent standard
     * @return the points gained from the player's completed task
     */
    private double okScore(boolean goodStandard) {
        if (goodStandard) {
            setFeedbackText(FileReader.getLessonString(27));
            GAME.getPlayer().setEmotion(new Emotion(5));
            return 1;
        }
        else {
            setFeedbackText(FileReader.getLessonString(28));
            GAME.getPlayer().setEmotion(new Emotion(6));
            return 0.5;
        }
    }

    /**
     * Determines the result of a task done to at least a good standard.
     *
     * @param excellentStandard whether or not the task is done to an excellent standard
     * @return the points gained from the player's completed task
     */
    private double goodScore(boolean excellentStandard) {
        if (excellentStandard) {
            setFeedbackText(FileReader.getLessonString(26));
            GAME.getPlayer().setEmotion(new Emotion(2));
            return 2;
        }
        else {
            setFeedbackText(FileReader.getLessonString(27));
            GAME.getPlayer().setEmotion(new Emotion(5));
            return 1;
        }
    }

    /**
     * Sets the odds for the task type depending on the player's respective grade.
     *
     * @param grade the player's respective grade
     * @return the type of task's id
     */
    private int generateInstruction(int grade) {
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
                break;
        }
        return generateQuestion(3, lv1, lv2);
    }

    /**
     * Generates the next task and resets values if the player is successful in completing a task.
     */
    private void completeTask() {
        if (GAME.getPlayer().getCondition() == 1) { questionId = generateInstruction(getGrade() + 1); }
        else if (GAME.getPlayer().getCondition() == 2 && getGrade() > 0) {
            questionId = generateInstruction(getGrade() - 1);
        } else { questionId = generateInstruction(getGrade()); }
        tasksLeft--;
        reread = false;
    }
}
