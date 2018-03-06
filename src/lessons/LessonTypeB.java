package lessons;

import game.Emotion;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.Menu;

import static game.Game.GAME;

/**
 * 06/12/2017.
 */
public class LessonTypeB extends Lesson {
    private int tasksLeft;
    private int timeLeft;
    private int id;
    private int questionId;
    private boolean reread;

    LessonTypeB(int id, int grade) {
        super(grade);

        this.id = id;

        if (GAME.getPlayer().getCondition() == 1) { grade++; }
        if (GAME.getPlayer().getCondition() == 2 && grade > 1) { grade--; }

        setRounds(setNumberOfQuestions(grade));
        tasksLeft = getRounds();
        timeLeft = 60;

        questionId = generateInstruction(grade);

        GAME.setMenu(new Menu(9));
    }

    public int getTasksLeft() { return tasksLeft; }

    public int getTimeLeft() { return timeLeft; }

    public boolean isReread() { return reread; }

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
                if (GAME.hasItem(id, 3)) { GAME.giveItem(id, 2); }
                else { GAME.giveItem(id, getGrade() - 1); }
            }
            else {
                setFeedbackText(FileReader.getLessonString(25));
                GAME.getPlayer().setEmotion(new Emotion(6));
            }
            setFeedbackText(getFeedbackText() + FileReader.getLessonString(6) +(int)((getScore() / getRounds()) * 100) + "!");
            setFinished();
        }
    }

    private double doTask(boolean meticulous, boolean reread, int questionId) {
        switch (questionId) {
            case 0:
                return succeedTask(meticulous);
            case 1:
                if (meticulous) {
                    return succeedTask(reread);
                }
                else {
                    if (reread) {
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
            case 2:
                if (meticulous) {
                    if (reread) {
                        setFeedbackText(FileReader.getLessonString(27));
                        GAME.getPlayer().setEmotion(new Emotion(5));
                        return 2;
                    }
                    else {
                        setFeedbackText(FileReader.getLessonString(28));
                        GAME.getPlayer().setEmotion(new Emotion(6));
                        return 1;
                    }
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

    private double succeedTask(boolean excellentStandard) {
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
        }
        return generateQuestion(3, lv1, lv2);
    }

    private void completeTask() {
        if (GAME.getPlayer().getCondition() == 1) { questionId = generateInstruction(getGrade() + 1); }
        else if (GAME.getPlayer().getCondition() == 2 && getGrade() > 0) { questionId = generateInstruction(getGrade() - 1); }
        else { questionId = generateInstruction(getGrade()); }
        tasksLeft--;
        reread = false;
    }
}
