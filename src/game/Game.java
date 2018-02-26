package game;

import controllers.Keys;
import lessons.Exam;
import lessons.Lesson;
import objects.*;
import utilities.*;
import utilities.FileReader;
import utilities.Menu;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luke on 23/10/2017.
 */



public class Game implements Serializable {
    public final static Game GAME = new Game();

    public transient Player player;
    public transient List<GameObject> objects;
    public JFrame frame;
    public transient Keys ctrl;
    public int time, day = 1, daysLeft = 30, points, timeBeforeHeist, examsLeft = 5, playerX, playerY, mapId;
    public transient TileMapView map;
    public transient Camera camera;
    public transient TextBox textBox;
    public transient Menu menu;
    public transient StatusMenu statusMenu;
    public transient TitleScreen titleScreen;
    public transient Lesson lesson;
    public transient Activity activity;
    public transient Exam exam;
    public char[][] tileMatrix;
    public transient GameObject[][] objectMatrix;
    public int[] friendValues, gradeValues, examScores, daysSince;
    public int[][] items;
    public boolean isTransition, isNewGame, isNewDay, isAfterActivity, givenDrink, isHeist, gotAnswers, isSpotted,
            isResult, isFinish, hasLostHeist, hasWonHeist, isSuspended, isExams, preExam, emilyCrush, isFullScreen,
            isTitle = true;
    public long transitionTime;
    public String newDayText;
    public transient Clip music;

    public static int height, width, cameraHeight, cameraWidth;

    public static String[] timePeriods, conditions;

    private Game() {
        objects = new ArrayList<>();
        ctrl = new Keys();
        height = Constants.FRAME_HEIGHT;
        width = Constants.FRAME_WIDTH;
        cameraHeight = height / 32;
        cameraWidth = width / 32;
    }

    public void createFrame() {
        frame = new JFrame("Brooklands Academy");
        frame.getContentPane().add(titleScreen);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void updateAirVent() {
        if (hasStinkBomb() && hasSuperKey() && gradeValues[4] > 29 && !hasQuestions()) {
            TileMapLoader.tileMaps.get(0).interactivePoints.put(new Point(7, 5),
                    new TextBox(0, FileReader.interactiveStrings[5] + FileReader.interactiveStrings[36]));
            Tile oldTile = TileMapLoader.tileMaps.get(0).tiles.get('A');
            Tile newTile = new InteractiveTile(oldTile.img, true, 'A', true);
            TileMapLoader.tileMaps.get(0).tiles.put('A', newTile);
        }
        else {
            TileMapLoader.tileMaps.get(0).interactivePoints.put(new Point(7, 5),
                    new TextBox(0, FileReader.interactiveStrings[5]));
            Tile oldTile = TileMapLoader.tileMaps.get(0).tiles.get('A');
            Tile newTile = new InteractiveTile(oldTile.img, true, 'A', false);
            TileMapLoader.tileMaps.get(0).tiles.put('A', newTile);
        }
    }

    public void update() {
        tileMatrix = new char[map.matrix.size()][map.matrix.get(0).size()];
        objectMatrix = new GameObject[map.matrix.size()][map.matrix.get(0).size()];

        if (lesson != null) {
            switch (time) {
                case 3:
                case 4:
                    statusMenu.currentId = 1;
                    break;
                case 5:
                    statusMenu.currentId = 2;
                    break;
                case 6:
                case 7:
                    statusMenu.currentId = 3;
                    break;
            }
            StatusMenu.setUp(statusMenu.currentId);
            if (lesson.feedback) { textBox = new TextBox(0, GAME.lesson.feedbackText); }
            else if (lesson.rules) { textBox = new TextBox(1, GAME.lesson.rulesText); }
            else {
                if (lesson.finished) { lesson.finish(); }
                else { textBox = new TextBox(0, lesson.questionText); }
            }
        }
        else if (exam != null) {
            statusMenu.currentId = 4;
            StatusMenu.setUp(statusMenu.currentId);
            if (exam.feedback) { textBox = new TextBox(0, GAME.exam.feedbackText); }
            else {
                if (exam.finished) { exam.finish(); }
                else { textBox = new TextBox(0, exam.questionText); }
            }
        }
        else {
            statusMenu.currentId = 0;
            StatusMenu.setUp(statusMenu.currentId);
        }

        for (int i = 0; i < map.matrix.size(); i++) {
            List<Character> line = map.matrix.get(i);
            for (int j = 0; j < map.matrix.get(0).size(); j++) {
                tileMatrix[i][j] = line.get(j);
            }
        }
        for (GameObject object : objects) {
            tileMatrix[object.y][object.x] = object.tile.key;
            objectMatrix[object.y][object.x]  = object;
        }
        for (GameObject object : objects) {  object.update();  }
        camera.update();
        if (isTransition && System.currentTimeMillis() - transitionTime > 1000 / 5) { isTransition = false; }

        synchronized (Game.class) {
            objects.clear();
            try { objects.addAll(TileMapLoader.tileMaps.get(map.currentId).NPCs.get(time)); }
            catch (NullPointerException e) {}
            objects.add(GAME.player);
        }
    }

    public boolean hasCraft() {
        for (int i = 0; i < items[1].length - 1; i++) {
            if (items[1][i] != 0) { return true;}
        }
        return false;
    }

    public boolean hasFood() {
        for (int i = 0; i < items[2].length - 1; i++) {
            if (items[2][i] != 0) { return true;}
        }
        return false;
    }

    public boolean hasStinkBomb() { return items[0][1] > 0; }

    public boolean hasSuperKey() { return items[1][3] > 0; }

    public boolean hasLovelyCake() { return items[2][3] > 0; }

    public boolean hasQuestions() { return items[0][2] > 0; }

    public void doTransition() {
        isTransition = true;
        transitionTime = System.currentTimeMillis();
    }

    public void newDay() {
        time = 0;
        givenDrink = false;
        TileMap nextMap = TileMapLoader.tileMaps.get(0);
        map.loadMap(nextMap);
        player.setLocation(Constants.START_X, Constants.START_Y);
        player.rotate(0);
        day++;
        daysLeft--;
        for (int i = 0; i < 3; i ++) {
            if (friendValues[i * 2] >= 10) {
                daysSince[i]++;
                if (daysSince[i] > 2) {
                    int decrease = daysSince[i] - 1;
                    friendValues[i * 2] -= decrease;
                    GAME.increaseValues(3, i *2, decrease);
                }
            }
        }
    }

    public void newDayFeedback(int... id) {
        GameAudio.playSfx(GameAudio.sfx_click);
        menu = null;
        isNewDay = true;
        switch (id[0]) {
            case 0:
                newDayText = FileReader.newDayStrings[id[1]];
                break;
            case 1:
                newDayText = FileReader.newDayStrings[id[1] + 5];
                break;
            case 2:
                newDayText = FileReader.newDayStrings[7];
                break;
            case 3:
                GameAudio.stopMusic();
                newDayText = FileReader.newDayStrings[8] + FileReader.newDayStrings[8 + examsLeft] +
                        FileReader.newDayStrings[14] + FileReader.newDayStrings[14 + examsLeft];
                preExam = true;
                break;
        }
    }

    public void goHome(boolean yes) {
        if (yes) {
            GameAudio.playSfx(GameAudio.sfx_click);
            doTransition();
            objects.clear();
            time = 8;
            TileMap nextMap = TileMapLoader.tileMaps.get(7);
            map.loadMap(nextMap);
            GameAudio.playSfx(GameAudio.sfx_door);
            GameAudio.startMusic(GameAudio.music_bedroom);
            objects.add(GAME.player);
            player.setLocation(6, 2);
            player.rotate(1);
            player.condition = 0;
            player.emotion = null;
            if (daysLeft < 2) { isExams = true; }
        }
        textBox = null;
        menu = null;
    }

    public void startHeist() {
        if (time < 2) {
            timeBeforeHeist = time;
            items[0][1] = 0;
            GameAudio.playSfx(GameAudio.sfx_click);
            GameAudio.playSfx(GameAudio.sfx_useStinkbomb);
            GameAudio.stopMusic();
            time = 10;
            isHeist = true;
            textBox = null;
        }
        else { textBox = new TextBox(0, FileReader.menuStrings[51]); }
        menu = null;
    }

    public void endHeist(int outcome) {
        GameAudio.stopMusic();
        switch (outcome) {
            case 0:
                hasWonHeist = true;
                time = timeBeforeHeist + 1;
                points *= 1.5;
                break;
            case 1:
                items[0][2] = 0;
                isSpotted = false;
                gotAnswers = false;
                hasLostHeist = true;
                isSuspended = true;
                objects.clear();
                time = timeBeforeHeist + 1;
                TileMap nextMap = TileMapLoader.tileMaps.get(1);
                map.loadMap(nextMap);
                objects.add(GAME.player);
                player.spotted = false;
                player.setLocation(9, 2);
                player.rotate(1);
                break;
        }
    }

    public void finishGame() {
        GameAudio.startMusic(GameAudio.music_result);
        isResult = true;
        int totalGradePoints = 0, totalFriendPoints = 0, totalExamPoints = 0;
        for (int i : gradeValues) { totalGradePoints += i; }
        for (int i : friendValues) { totalFriendPoints += i; }
        for (int i : examScores) { totalExamPoints += i; }
        textBox = new TextBox(5, FileReader.resultStrings[0] + FileReader.resultStrings[1] + totalFriendPoints + "#" +
                FileReader.resultStrings[2] + totalGradePoints + "#" + FileReader.resultStrings[3] + totalExamPoints + "#" +
                FileReader.resultStrings[4] + (items[0][2] > 0) + "#" + FileReader.resultStrings[5] + points);
    }

    public void newGame() {
        GameAudio.stopMusic();

        player = new Player(Player.TILES.get(Constants.UP), Constants.START_X, Constants.START_Y, ctrl);

        items = new int[3][];
        items[0] = new int[3];
        items[1] = new int[4];
        items[2] = new int[4];

        map = new TileMapView(TileMapLoader.tileMaps.get(0));

        camera = new Camera(player.x - (cameraWidth / 2), player.y - (cameraHeight / 2),
                map.matrix);

        time = 0;

        friendValues = new int[5];
        gradeValues = new int[5];
        examScores = new int[5];

        daysSince = new int[3];

        day = 1;
        daysLeft = 30;

        points = 0;
        examsLeft = 5;

        isNewGame = true;
        isTitle = false;

        isExams = false;
        isSuspended = false;
        givenDrink = false;
        emilyCrush = false;

    }

    public void load() {
        //load game

        Game data = null;

        try {
            FileInputStream saveData = new FileInputStream("sav/game.ser");
            ObjectInputStream in = new ObjectInputStream(saveData);
            data = (Game) in.readObject();
            in.close();
            saveData.close();
        } catch (IOException e) {
            titleScreen.textBox = new TextBox(0, FileReader.menuStrings[61]);
            return;
        } catch (ClassNotFoundException e) {
            titleScreen.textBox = new TextBox(0, FileReader.menuStrings[62]);
            return;
        }

        player = new Player(Player.TILES.get(0), data.playerX, data.playerY, ctrl);

        items = data.items;
        map = new TileMapView(TileMapLoader.tileMaps.get(data.mapId));
        time = data.time;

        switch (time) {
            case 8:
                GameAudio.startMusic(GameAudio.music_bedroom);
                break;
            case 10:
            case 11:
                GameAudio.startMusic(GameAudio.music_heist);
                break;
            default:
                GameAudio.startMusic(GameAudio.music_school);
                break;
        }

        camera = new Camera(player.x - (cameraWidth / 2), player.y - (cameraHeight / 2),
                map.matrix);

        friendValues = data.friendValues;
        gradeValues = data.gradeValues;
        examScores = data.examScores;

        daysSince = data.daysSince;

        day = data.day;
        daysLeft = data.daysLeft;

        points = data.points;
        examsLeft = data.examsLeft;

        isExams = data.isExams;
        isSuspended = data.isSuspended;
        givenDrink = data.givenDrink;
        emilyCrush = data.emilyCrush;

        isNewGame = false;
        isTitle = false;


//        day = 5;
//        daysLeft = 25;
//
//        points = 100;
//
//        items = new int[3][];
//        items[0] = new int[3];
//        items[1] = new int[4];
//        items[2] = new int[4];
//        items[0][0] = 3;
//        items[1][0] = 1;
//        items[2][0] = 1;
//
//        friendValues = new int[5];
//        friendValues[0] = 10;
//        friendValues[1] = 10;
//        friendValues[2]= 10;
//        friendValues[3] = 10;
//        friendValues[4] = 10;
//
//        gradeValues = new int[5];
//        gradeValues[0] = 10;
//        gradeValues[1] = 10;
//        gradeValues[2] = 10;
//        gradeValues[3] = 10;
//        gradeValues[4] = 10;
//
//        examScores = new int[5];
//
//        daysSince = new int[3];

        isTitle = false;
    }

    public void save() {
        GameAudio.playSfx(GameAudio.sfx_click);
        playerX = player.x;
        playerY = player.y;
        mapId = map.currentId;
        menu = null;
        try {
            FileOutputStream saveData = new FileOutputStream("sav/game.ser");
            ObjectOutputStream out = new ObjectOutputStream(saveData);
            out.writeObject(this);
            out.close();
            saveData.close();
            GameAudio.playSfx(GameAudio.sfx_save);
            textBox = new TextBox(0, FileReader.menuStrings[58]);
        } catch (IOException e) {
            textBox = new TextBox(0, FileReader.menuStrings[59]);
            e.printStackTrace();
        }
    }

    public void quit() {
        GameAudio.playSfx(GameAudio.sfx_click);
        textBox = new TextBox(3, FileReader.menuStrings[60]);
        menu = new Menu(17);
    }



    public void windowScreen() {
        frame.dispose();
        frame.setUndecorated(false);
        height = Constants.FRAME_HEIGHT;
        width = Constants.FRAME_WIDTH;
        cameraHeight = height / 32;
        cameraWidth = width / 32;
        frame.getContentPane().setSize(width, height);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        isFullScreen = false;
    }

    public void fullScreen() {
        frame.dispose();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        height = Constants.FULL_HEIGHT;
        width = Constants.FULL_WIDTH;
        cameraHeight = height / 32;
        cameraWidth = width / 32;
        frame.getContentPane().setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        isFullScreen = true;
    }

    public void switchScreen() {
        if (isFullScreen) { windowScreen(); }
        else { fullScreen(); }

        camera.x = player.x - (Game.width / 64);
        camera.y = player.y - (Game.height / 64);
        camera.gX = camera.x * 32;
        camera.gY = camera.y * 32;
    }

    public void increaseValues(int id, int index, int increase) {
        switch(id) {
            case 0:
                points += 2 * (Math.pow((increase * ((friendValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 1:
                points += 2 * (Math.pow((increase * ((gradeValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 2:
                points += 5 * (Math.pow((increase * ((examScores[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 3:
                points -= 2 * (Math.pow((increase * ((gradeValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        GameAudio.loadSounds();
        FileReader.readFiles();
        TextBox.loadImages();
        Menu.loadImages();
        TileMapLoader.loadMaps();
        GameFont.loadFont();
        GAME.titleScreen = new TitleScreen();
        GAME.createFrame();
        timePeriods = Arrays.copyOf(FileReader.statusStrings, 17);
        conditions = Arrays.copyOfRange(FileReader.statusStrings, 17, 20);

        while (true) {
            GameAudio.startMusic(GameAudio.music_title);

            while (GAME.isTitle) { GAME.titleScreen.repaint(); }

            GAME.frame.remove(GAME.titleScreen);

            GAME.statusMenu = new StatusMenu(0);
            GAME.frame.getContentPane().add(GAME.map);
            GAME.frame.addKeyListener(GAME.ctrl);
            GAME.frame.revalidate();

            /*Game loop*/
            while (!GAME.isFinish) {
                GAME.update();
                GAME.map.repaint();
                Thread.sleep(70);
            }
            GAME.isFinish = false;
            GAME.isTitle = true;
            GAME.titleScreen = new TitleScreen();
            GAME.frame.removeKeyListener(GAME.ctrl);
            GAME.frame.remove(GAME.map);
            GAME.frame.getContentPane().add(GAME.titleScreen);
            GAME.frame.revalidate();

        }


    }
}
