package lessons;

import game.TileMap;
import objects.NPC;
import utilities.FileReader;
import utilities.GameAudio;
import utilities.TileMapLoader;

import static game.Game.GAME;

/**
 * Created by Luke on 14/11/2017.
 */
public abstract class Lesson {
    int grade, rounds, id;
    public static int oldTime;
    double score;
    public boolean feedback, finished;
    public String questionText, feedbackText;

    public Lesson(int grade) {
        this.grade = grade;
    }

    public abstract void doAction(int action);

    public static void startLesson(int id) {
        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.map.currentId);
        try { for (NPC npc : currentMap.NPCs.get(GAME.time)) { npc.reset(); } }
        catch (NullPointerException e) {}
        int grade = (GAME.gradeValues[id] / 10) + 1;
        oldTime = GAME.time;
        GAME.time = id + 3;
        GameAudio.startMusic(GameAudio.music_lesson);

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
        GAME.lesson.id = id;

    }

    public void finish() {
        int increase = (int)((score / rounds) * 5);
        GAME.gradeValues[id] += increase;
        GAME.increaseValues(1, id, increase);
        GAME.time = oldTime;

        GAME.time++;
        GAME.textBox = null;
        GAME.menu = null;
        GAME.lesson = null;
        GAME.player.condition = 0;
        GAME.doTransition();
        GameAudio.startMusic(GameAudio.music_school);
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
            if (lessonId == 0 || lessonId == 1) { questionText = FileReader.lessonStrings[0]; }
            else { questionText = FileReader.lessonStrings[18]; }
            return 0;
        }
        else if (r < lv2) {
            if (lessonId == 0) { questionText = FileReader.lessonStrings[1]; }
            else if (lessonId == 1) { questionText = FileReader.lessonStrings[2]; }
            else { questionText = FileReader.lessonStrings[19]; }
            return 1;
        }
        else {
            if (lessonId == 0) { questionText = FileReader.lessonStrings[3]; }
            else if (lessonId == 1) { questionText = FileReader.lessonStrings[4]; }
            else { questionText = FileReader.lessonStrings[20]; }
            return 2;
        }
    }
}
