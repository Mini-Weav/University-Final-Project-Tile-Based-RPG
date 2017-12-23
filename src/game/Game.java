package game;

import controllers.Keys;
import lessons.Lesson;
import objects.GameObject;
import objects.NPC;
import objects.Player;
import utilities.*;
import utilities.Menu;

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
    public int time, condition, day = 1;
    public TileMapView map;
    public Camera camera;
    public TextBox textBox;
    public Menu menu;
    public StatusMenu statusMenu;
    public TitleScreen titleScreen;
    public Lesson lesson;
    public char[][] tileMatrix;
    public GameObject[][] objectMatrix;
    public int[] friendValues, gradeValues;
    public int[][] items;
    public boolean transition, newGame, fullScreen, isTitle = true;
    public long transitionTime;

    public static int height, width, cameraHeight, cameraWidth;

    public final static String[] TIME_PERIODS = new String[] { "MORNING", "LUNCH", "AFT SCH", "DT", "FOOD", "PE", "CHEM", "ICT" },
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
        frame.setResizable(false);
        frame.add(titleScreen, BorderLayout.CENTER);
        //frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
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

    public void goHome(boolean yes) {
        if (yes) {
            doTransition();
            time = 0;
            condition = 0;
            TileMap nextMap = TileMapLoader.tileMaps.get(0);
            for (NPC npc : nextMap.NPCs.get(GAME.time)) { npc.reset(); }
            player.rotate(0);
            map.loadMap(nextMap);
            day++;
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

        newGame = true;
        isTitle = false;
    }

    public void load() {
        player = new Player(Player.TILES.get(5), Constants.START_X, Constants.START_Y, ctrl);

        /* start of testing values */
        items = new int[3][];
        items[0] = new int[2];
        items[1] = new int[4];
        items[2] = new int[4];
        items[0][0] = 4;
        items[1][0] = 1;
        items[2][0] = 3;
        items[2][1] = 2;
        items[2][2] = 1;

        friendValues = new int[5];
        friendValues[0] = 21;
        friendValues[1] = 19;
        friendValues[2]= 1;
        friendValues[3] = 0;
        friendValues[4] = 1;

        gradeValues = new int[5];
        gradeValues[0] = 10;
        gradeValues[1] = 20;
        gradeValues[2] = 0;
        gradeValues[3] = 30;
        gradeValues[4] = 0;
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
        frame.setSize(width + Constants.DEC_WIDTH, height + Constants.DEC_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        frame.setSize(width, height);
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

    public static void main(String[] args) throws Exception {
        FileReader.readFiles();
        TextBox.loadImages();
        Menu.loadImages();
        TileMapLoader.loadMaps();
        GameFont.loadFont();
        GAME.titleScreen = new TitleScreen();
        GAME.createFrame();

        while (GAME.isTitle) { GAME.titleScreen.repaint(); }
        GAME.frame.remove(GAME.titleScreen);
        GAME.time = 0;
        GAME.map = new TileMapView(TileMapLoader.tileMaps.get(0));

        GAME.camera = new Camera(GAME.player.x - (cameraWidth / 2), GAME.player.y - (cameraHeight / 2),
                GAME.map.matrix);

        GAME.statusMenu = new StatusMenu(0);
        GAME.frame.add(GAME.map, BorderLayout.CENTER);
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
