package lessons;

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
        switch (action) {
            case 0:
                score += answer(concentration, questionId);
                questionsLeft--;
                concentration = startingConcentration;
                questionId = generateQuestion(id, lv1, lv2);
                if (questionsLeft == 0) {
                    finished = true;
                    feedbackText = "The lesson is over!#You got a score of " + (int)((score / rounds) * 100) + "!";
                }
                break;
            case 1:
                if (attentionSpan > 0) {
                    attentionSpan--;
                    concentration++;
                    feedbackText = "You stop and think...#Your concentration has#improved!";
                }
                else { feedbackText = "You struggle to#concentrate!"; }
                break;
            case 2:
                if (GAME.items[0][0] > 0) {
                    attentionSpan++;
                    concentration++;
                    GAME.items[0][0]--;
                    feedbackText = "You drink a powerful energy#drink...#Your attention and#concentration has improved!";
                }
                else { feedbackText = "You're out of energy#drinks!"; }
                break;
            case 3:
                if (!toilet) {
                    GAME.doTransition();
                    attentionSpan++;
                    toilet = true;
                    questionId = generateQuestion(id, lv1, lv2);
                    questionsLeft--;
                    feedbackText = "You take a break... Your#attention has improved!#...However, you missed the#last question!";
                }
                else { feedbackText = "You've already been to the#toilet! The teacher won't#let you go again."; }
                break;
        }
        feedback = true;
    }

    public double answer(int concentration, int questionId) {
        switch (questionId) {
            case 0:
                if (concentration > 0) {
                    feedbackText = "You have a good feeling#about your answer...";
                    return 1;
                }
                else {
                    feedbackText = "You're not very confident#with your answer...";
                    return 0;
                }
            case 1:
                if (concentration == 2) {
                    feedbackText = "You think you did ok...";
                    return 0.5;
                }
                else if (concentration > 2) {
                    feedbackText = "You have a good feeling#about your answer...";
                    return 1;
                }
                else {
                    feedbackText = "You're not very confident#with your answer...";
                    return 0;
                }
            case 2:
                if (concentration == 3) {
                    feedbackText = "You think you did ok...";
                    return 0.5;
                }
                else if (concentration > 3) {
                    feedbackText = "You have a good feeling#about your answer...";
                    return 1;
                }
                else {
                    feedbackText = "You're not very confident#with your answer...";
                    return 0;
                }
        }
        return 0;
    }
}
