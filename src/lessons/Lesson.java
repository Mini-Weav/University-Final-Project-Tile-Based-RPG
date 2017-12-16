package lessons;

import static game.Game.GAME;

/**
 * Created by Luke on 14/11/2017.
 */
public abstract class Lesson {
    int grade, rounds;
    public static int oldTime;
    double score;
    public boolean feedback, finished;
    public String questionText, feedbackText;

    public Lesson(int grade) {
        this.grade = grade;
    }

    public abstract void doAction(int action);

    public static void startLesson(int id) {
        int grade = (GAME.gradeValues[id] / 10) + 1;
        oldTime = GAME.time;
        GAME.time = id + 3;

        switch (id) {
            case 0:
            case 1:
                GAME.lesson = new LessonTypeB(id + 1, grade);
                break;
            case 2:
                GAME.lesson = new LessonTypeC(grade);
                break;
            case 3:
            case 4:
                GAME.lesson = new LessonTypeA(id - 3, grade);
                break;
        }

    }

    public void finish() {
        GAME.textBox = null;
        GAME.menu = null;
        GAME.isLesson = false;
        GAME.lesson = null;
        GAME.time = oldTime + 1;
        GAME.doTransition();
    }

    public static int setStart(int grade) {
        switch (grade) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
        }
        return 0;
    }

    public static int setNumberOfQuestions(int grade) {
        switch (grade) {
            case 1:
                return 5;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 12;
        }
        return 5;
    }

    public int generateQuestion(int lessonId, double lv1, double lv2) {
        double r = Math.random();
        if (r < lv1) {
            if (lessonId == 0 || lessonId == 1) {
                questionText = "It is a multiple choice#question...";
            } else { questionText = "This instruction seems#pretty simple..."; }
            return 0;
        }
        else if (r < lv2) {
            if (lessonId == 0) {
                questionText = "I need to use an equation#to answer this...";
            } else if (lessonId == 1) { questionText = "I need to use an algorithm#to answer this..."; }
            else { questionText = "You think you understand#this instruction..."; }
            return 1;
        }
        else {
            if (lessonId == 0) {
                questionText = "I need write an essay to#answer this...";
            }
            else if (lessonId == 1) { questionText = "I need write a program to#answer this..."; }
            else { questionText = "You don't really understand#this instruction..."; }
            return 2;
        }
    }
}
