package game;

import controllers.Keys;
import lessons.Lesson;
import objects.GameObject;
import objects.NPC;
import objects.Player;
import utilities.*;
import utilities.Menu;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 23/10/2017.
 */



public class Game {
    public final static Game GAME = new Game();

    public Player player;
    public List<GameObject> objects;
    public JFrame frame;
    public Keys ctrl;
    public int time, day = 1, points;
    public TileMapView map;
    public Camera camera;
    public TextBox textBox;
    public Menu menu;
    public StatusMenu statusMenu;
    public TitleScreen titleScreen;
    public Lesson lesson;
    public Activity activity;
    public char[][] tileMatrix;
    public GameObject[][] objectMatrix;
    public int[] friendValues, gradeValues;
    public int[][] items;
    public boolean transition, isNewGame, isNewDay, isAfterActivity, givenDrink, fullScreen, isTitle = true;
    public long transitionTime;
    public String newDayText;
    public Clip music;

    public static int height, width, cameraHeight, cameraWidth;

    public final static String[] TIME_PERIODS = new String[] { "MORNING", "LUNCH", "AFT SCH", "DT", "FOOD", "PE", "CHEM", "ICT", "NIGHT", "ACTIVITY" },
            CONDITIONS = new String[] { "NORMAL", "GREAT", "UNWELL"};

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



    public void update() {
        tileMatrix = new char[map.matrix.size()][map.matrix.get(0).size()];
        objectMatrix = new GameObject[map.matrix.size()][map.matrix.get(0).size()];

        if (lesson == null) {
            statusMenu.currentId = 0;
            StatusMenu.setUp(statusMenu.currentId);
        }
        else {
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
            else {
                if (lesson.finished) { lesson.finish(); }
                else { textBox = new TextBox(0, lesson.questionText); }
            }
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
        for (GameObject object : objects) { object.update(); }
        camera.update();
        if (transition && System.currentTimeMillis() - transitionTime > 1000 / 5) { transition = false; }

        synchronized (Game.class) {
            objects.clear();
            objects.addAll(TileMapLoader.tileMaps.get(map.currentId).NPCs.get(time));
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

    public boolean hasStinkBomb() {
        return items[0][1] == 0;
    }

    public boolean hasLockPick() {
        return items[1][4] == 0;
    }

    public boolean hasSuperCake() {
        return items[2][4] == 0;
    }

    public void doTransition() {
        transition = true;
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
    }

    public void newDayFeedback(int... id) {
        GameAudio.playSfx(GameAudio.sfx_click);
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
        }
        textBox = null;
        menu = null;
    }

    public void newGame() {
        player = new Player(Player.TILES.get(Constants.UP), Constants.START_X, Constants.START_Y, ctrl);

        items = new int[3][];
        items[0] = new int[2];
        items[1] = new int[4];
        items[2] = new int[4];

        friendValues = new int[5];

        gradeValues = new int[5];

        points = 0;

        isNewGame = true;
        isTitle = false;
    }

    public void load() {
        player = new Player(Player.TILES.get(5), Constants.START_X, Constants.START_Y, ctrl);

        day = 15;

        /* start of testing values */
        items = new int[3][];
        items[0] = new int[2];
        items[1] = new int[4];
        items[2] = new int[4];
        items[0][0] = 5;
        items[1][0] = 1;
        items[2][0] = 0;
        items[2][1] = 0;
        items[2][2] = 0;

        friendValues = new int[5];
        friendValues[0] = 1;
        friendValues[1] = 9;
        friendValues[2]= 22;
        friendValues[3] = 30;
        friendValues[4] = 30;

        gradeValues = new int[5];
        gradeValues[0] = 30;
        gradeValues[1] = 18;
        gradeValues[2] = 22;
        gradeValues[3] = 35;
        gradeValues[4] = 30;
        /* end of testing values */

        isTitle = false;
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
        fullScreen = false;
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
        fullScreen = true;
    }

    public void switchScreen() {
        if (fullScreen) { windowScreen(); }
        else { fullScreen(); }

        camera.x = player.x - (Game.width / 64);
        camera.y = player.y - (Game.height / 64);
        camera.gX = camera.x * 32;
        camera.gY = camera.y * 32;
    }

    public void increasePoints(int id, int index, int increase) {
        switch(id) {
            case 0:
                points += 2 * (Math.pow((increase * ((friendValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 1:
                points += 2 * (Math.pow((increase * ((gradeValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
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

        GameAudio.startMusic(GameAudio.music_title);

        while (GAME.isTitle) { GAME.titleScreen.repaint(); }

        GAME.frame.remove(GAME.titleScreen);
        GAME.time = 0;
        GAME.map = new TileMapView(TileMapLoader.tileMaps.get(0));

        GAME.camera = new Camera(GAME.player.x - (cameraWidth / 2), GAME.player.y - (cameraHeight / 2),
                GAME.map.matrix);

        GAME.statusMenu = new StatusMenu(0);
        GAME.frame.getContentPane().add(GAME.map);
        GAME.frame.addKeyListener(GAME.ctrl);
        GAME.frame.revalidate();

        /*Game loop*/
        while (true) {
            GAME.update();
            GAME.map.repaint();
            Thread.sleep(70);
        }
    }
}
