package lessons;

import game.Game;
import utilities.TextBox;

import java.util.Scanner;

/**
 * Created by Luke on 14/11/2017.
 */
public class Lesson {
    static int grade, numberOfDrinks;

    public static void startLesson(int id) {
        Game.textBox = new TextBox(3, "Look at the command line.");
        grade = (Game.gradeValues[id] % 10) + 1;
        numberOfDrinks = Game.items[0][0];
        switch (id) {
            case 0:
            case 1:
                lesson2(grade);
                break;
            case 2:
                lesson3(grade);
                break;
            case 3:
                lesson1(0, grade);
                break;
            case 4:
                lesson1(1, grade);
                break;
        }
        Game.items[0][0] = numberOfDrinks;
        Game.textBox = null;
        Game.menu = null;
        Game.time++;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Which Lesson?\n1 Chemistry\n2 ICT\n3 DT\n4 Food Tech\n5 PE\n6 Quit");
            System.out.print(">>");

            int i = input.nextInt();
            if (i == 6) {
                System.out.println("Bye");
                return;
            }

            while (true) {
                System.out.println("Player's grade is...\n1 D\n2 C\n3 B\n4 A");
                System.out.print(">>");
                grade = input.nextInt();
                if (grade > 4 || grade < 1) {
                    System.out.println("invalid");
                }
                else {
                    break;
                }
            }

            System.out.println("Number of energy drinks is...");
            System.out.print(">>");
            numberOfDrinks = input.nextInt();

            switch (i) {
                case 1:
                    System.out.println("Running Chemistry method...");
                    lesson1(0, grade);
                    break;
                case 2:
                    System.out.println("Running ICT method...");
                    lesson1(1, grade);
                    break;
                case 3:
                    System.out.println("Running DT method...");
                    lesson2(grade);
                    break;
                case 4:
                    System.out.println("Running Food Tech method...");
                    lesson2(grade);
                    break;
                case 5:
                    System.out.println("Running PE method...");
                    lesson3(grade);
                    break;

            }
        }


    }

    public static void lesson1(int id, int grade) {
        double lv1, lv2, score = 0;
        int startingConcentration = setStart(grade);
        int numberOfQuestions = setNumberOfQuestions(grade);
        int questionsLeft = numberOfQuestions;
        int attentionSpan = 5;
        boolean toilet = false;
        Scanner input = new Scanner(System.in);

        if (id == 0 ) {
            lv1 = 0.2;
            lv2 = 0.7;
        }
        else {
            lv1 = 0.3;
            lv2 = 0.6;
        }

        while (questionsLeft > 0) {
            boolean answered = false;
            int concentration = startingConcentration;
            System.out.println("#########################################");
            int questionId = generateQuestion(id, lv1, lv2);
            while (!answered) {
                System.out.println(questionsLeft + " questions left, "+ numberOfDrinks + " drinks left\n"
                        + "Attention span: "+ attentionSpan + " Concentration: " + concentration);
                System.out.println("1 Write answer\n2 Concentrate\n3 Drink energy drink\n4 Bathroom break");
                System.out.print(">>");
                int action = input.nextInt();
                switch (action) {
                    case 1:
                        score += answer(concentration, questionId);
                        answered = true;
                        break;
                    case 2:
                        if (attentionSpan > 0) {
                            attentionSpan--;
                            concentration++;
                            System.out.println("You stop and think...\nYour concentration has improved!");
                        }
                        else { System.out.println("You struggle to concentrate!"); }
                        break;
                    case 3:
                        if (numberOfDrinks > 0) {
                            attentionSpan++;
                            concentration++;
                            numberOfDrinks--;
                            System.out.println("You drink a powerful energy drink...\nYour attention and concentration has improved!");
                        }
                        else { System.out.println("You're out of energy drinks!"); }
                        break;
                    case 4:
                        if (!toilet) {
                            attentionSpan++;
                            toilet = true;
                            answered = true;
                            System.out.println("You collect your thoughts as you take a break...\nYour attention has improved!");
                            System.out.println("...However, you missed the last question!");
                        }
                        else { System.out.println("You've already been to the toilet! The teacher won't let you go again :("); }
                        break;
                }
            }
            questionsLeft--;
        }
        System.out.println("The lesson is over!");
        System.out.println("You got a score of "+((score/numberOfQuestions)*100));
    }

    public static void lesson2(int grade) {
        double score = 0;
        int numberOfTasks = setNumberOfQuestions(grade);
        int tasksLeft = numberOfTasks;
        int time = 60;
        Scanner input = new Scanner(System.in);

        while (tasksLeft > 0 && time > 0) {
            boolean reread = false;
            boolean completed = false;
            double result;
            System.out.println("#########################################");
            int questionId = generateInstruction(grade);
            while (!completed) {
                System.out.println(tasksLeft + " tasks left\n"
                        + "Time left: "+ time + " minutes");
                System.out.println("1 Do task\n2 Do task meticulously\n3 Reread instructions");
                System.out.print(">>");
                int action = input.nextInt();
                switch (action) {
                    case 1:
                        result = doTask(false, reread, questionId);
                        score += result;
                        if (result > 0) {
                            completed = true;

                        }
                        time -= 5;
                        break;
                    case 2:
                        if (time > 5) {
                            result = doTask(true, reread, questionId);
                            score += result;
                            if (result > 0) {
                                completed = true;
                            }
                            time -= 10;
                        }
                        else {
                            System.out.println("You failed to complete the task in time.");
                            time -= 5;

                        }
                        break;
                    case 3:
                        if (!reread) {
                            reread = true;
                            System.out.println("You reread the instructions...\nYour understanding has improved!");
                            time -= 5;
                        }
                        else {
                            System.out.println("You've already reread the instructions!");
                        }
                        break;
                }

                if (time == 0) { break; }
            }
            if (completed) { tasksLeft--; }
        }
        if (tasksLeft == 0) { System.out.println("You finished every task!"); }
        else { System.out.println("You're out of time!"); }
        System.out.println("You got a score of "+((score/numberOfTasks)*50));

    }

    public static void lesson3(int grade) {
        double score = 0, bonusScore = 0, consecutiveRun = 0;
        int energy = setStart(grade);
        int time = 60;
        boolean started = false;
        Scanner input = new Scanner(System.in);

        while (time > 0) {
            System.out.println("#########################################");
            if (!started) {
                System.out.println("Time left: "+ time + " minutes, "+ numberOfDrinks + " drinks left\nEnergy: "+energy);
                System.out.println("You haven't started running yet...");
                System.out.println("1 Warm up\n2 Start running\n3 Drink energy drink");
                System.out.print(">>");
                int action = input.nextInt();
                switch (action) {
                    case 1:
                        energy++;
                        System.out.println("You warm up...\nYour energy has increased!");
                        time -= 5;
                        break;
                    case 2:
                        System.out.println("You start running...");
                        started = true;
                        break;
                    case 3:
                        if (numberOfDrinks > 0) {
                            energy++;
                            numberOfDrinks--;
                            System.out.println("You drink a powerful energy drink...\nYour energy has increased!");
                        }
                        else { System.out.println("You're out of energy drinks!"); }
                        break;
                }
            }
            else {
                System.out.println("Laps run: "+score+"\n"
                        + "Time left: "+ time + " minutes, "+ numberOfDrinks + " drinks left\nEnergy: "+energy);
                System.out.println("1 Jog\n2 Run\n3 Sprint\n4 Rest\n5 Drink energy drink");
                System.out.print(">>");
                int action = input.nextInt();
                switch (action) {
                    case 1:
                        score += 0.25;
                        consecutiveRun = 0;
                        time -= 5;
                        break;
                    case 2:
                        if (energy > 0) {
                            score += 0.5;
                            energy--;
                            if (consecutiveRun > 0) {
                                bonusScore+= consecutiveRun;
                                System.out.println("The teacher noticed your effort...\nYou got extra credit!");
                            }
                            consecutiveRun++;
                        }
                        else { System.out.println("You try to run...\nYou don't have enough energy!"); }
                        time -= 5;
                        break;
                    case 3:

                        if (energy > 1) {
                            score++;
                            energy -= 2;
                            if (consecutiveRun > 0) {
                                bonusScore+= consecutiveRun*2;
                                System.out.println("The teacher noticed your effort...\nYou got a lot of extra credit!");
                            }
                            consecutiveRun++;

                        }
                        else { System.out.println("You try to sprint...\nYou don't have enough energy!"); }

                        time -= 5;
                        break;
                    case 4:
                        System.out.println("You take a moment to rest...\nYou've regained some energy!");
                        energy++;
                        consecutiveRun = 0;
                        time -= 5;
                        break;
                    case 5:
                        if (numberOfDrinks > 0) {
                            energy++;
                            numberOfDrinks--;
                            System.out.println("You drink a powerful energy drink...\nYour energy has increased!");
                            consecutiveRun = 0;
                        }
                        else { System.out.println("You're out of energy drinks!"); }
                        break;
                }
            }
        }
        System.out.println("The lesson is over!");
        System.out.println("You got a score of "+((score+bonusScore)*10));
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
                return 10;
            case 4:
                return 12;
        }
        return 5;
    }

    public static int generateQuestion(int lessonId, double lv1, double lv2) {
        double r = Math.random();
        if (r < lv1) {
            if (lessonId == 0 || lessonId == 1) { System.out.println("It is a multiple choice question..."); }
            else { System.out.println("This instruction seems pretty simple..."); }
            return 0;
        }
        else if (r < lv2) {
            if (lessonId == 0) { System.out.println("I need to use an equation to answer this..."); }
            else if (lessonId == 1) { System.out.println("I need to use an algorithm to answer this..."); }
            else { System.out.println("You think you understand this instruction..."); }
            return 1;
        }
        else {
            if (lessonId == 0) { System.out.println("I need write an essay to answer this..."); }
            else if (lessonId == 1) { System.out.println("I need write a program to answer this..."); }
            else { System.out.println("You don't really understand this instruction..."); }
            return 2;
        }
    }

    public static int generateInstruction(int grade) {
        double lv1 = 0, lv2 = 0;
        switch (grade) {
            case 1:
                lv1 = 0.2;
                lv2 = 0.7;
                break;
            case 2:
                lv1 = 0.2;
                lv2 = 0.8;
                break;
            case 3:
                lv1 = 0.3;
                lv2 = 0.9;
                break;
            case 4:
                lv1 = 0.4;
                lv2 = 1;
        }
        return generateQuestion(3, lv1, lv2);
    }

    public static double answer(int concentration, int questionId) {
        switch (questionId) {
            case 0:
                if (concentration > 0) {
                    System.out.println("You have a good feeling about your answer...");
                    return 1;
                }
                else {
                    System.out.println("You're not very confident with your answer...");
                    return 0;
                }
            case 1:
                if (concentration == 2) {
                    System.out.println("You think you did ok...");
                    return 0.5;
                }
                else if (concentration > 2) {
                    System.out.println("You have a good feeling about your answer...");
                    return 1;
                }
                else {
                    System.out.println("You're not very confident with your answer...");
                    return 0;
                }
            case 2:
                if (concentration == 3) {
                    System.out.println("You think you did ok...");
                    return 0.5;
                }
                else if (concentration > 3) {
                    System.out.println("You have a good feeling about your answer...");
                    return 1;
                }
                else {
                    System.out.println("You're not very confident with your answer...");
                    return 0;
                }
        }
        return 0;
    }

    public static double doTask(boolean meticulous, boolean reread, int questionId) {
        switch (questionId) {
            case 0:
                if (meticulous) {
                    System.out.println("You completed the task to an excellent standard!");
                    return 2;
                }
                else {
                    System.out.println("You completed the task to a good standard");
                    return 1;
                }
            case 1:
                if (meticulous) {
                    if (reread) {
                        System.out.println("You completed the task to an excellent standard!");
                        return 2;
                    }
                    else {
                        System.out.println("You completed the task to a good standard");
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        System.out.println("You completed the task to a good standard!");
                        return 1;
                    }
                    else {
                        System.out.println("You completed the task to a satisfactory standard");
                        return 0.5;
                    }
                }
            case 2:
                if (meticulous) {
                    if (reread) {
                        System.out.println("You completed the task to a good standard!");
                        return 2;
                    }
                    else {
                        System.out.println("You completed the task to a satisfactory standard");
                        return 1;
                    }
                }
                else {
                    if (reread) {
                        System.out.println("You completed the task to a satisfactory standard!");
                        return 0.5;
                    }
                    else {
                        System.out.println("You didn't complete the task!");
                        return 0;
                    }
                }
        }
        return 0;
    }
}
