package game;

import controllers.Keys;
import lessons.Lesson;
import objects.GameObject;
import objects.InteractiveTile;
import objects.NPC;
import objects.Player;
import utilities.*;
import utilities.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 23/10/2017.
 */



public class Game {
    public Player player;
    public List<GameObject> objects;
    public Keys ctrl;
    public static int time;
    public final static String[] timePeriods = new String[] { "MORNING\n", "LUNCH\n", "AFTER\nSCHOOL", "DT", "FOOD", "PE", "CHEM", "ICT" };
    public static TileMapView map;
    public static Camera camera;
    public static TextBox textBox;
    public static Menu menu;
    public static Lesson lesson;
    public static char[][] tileMatrix;
    public static Object[][] objectMatrix;
    public static int[] friendValues, gradeValues;
    public static int[][] items;
    public static boolean transition, isLesson;
    public static long transitionTime;

    public Game() {
        objects = new ArrayList<>();
        ctrl = new Keys();
        player = new Player(Player.TILES.get(0), Constants.START_X, Constants.START_Y, ctrl);
        objects.add(player);

    }

    public void createFrame() {
        JFrame frame = new JFrame("Game");
        frame.setResizable(false);
        frame.add(map, BorderLayout.CENTER);
        frame.addKeyListener(this.ctrl);
        //frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



    public void update() {

        tileMatrix = new char[map.matrix.size()][map.matrix.get(0).size()];
        objectMatrix = new Object[map.matrix.size()][map.matrix.get(0).size()];

        for (int i = 0; i < map.matrix.size(); i++) {
            List<Character> line = map.matrix.get(i);
            for (int j = 0; j < map.matrix.get(0).size(); j++) {
                tileMatrix[i][j] = line.get(j);
            }
        }
        for (GameObject object : objects) {
            object.update();
            tileMatrix[object.y][object.x] = object.tile.key;
            objectMatrix[object.y][object.x]  = object;
        }
        camera.update();
        if (Game.transition && System.currentTimeMillis() - Game.transitionTime > 1000 / 5) { Game.transition = false; }
        synchronized (Game.class) {
            objects.clear();
            objects.addAll(TileMapLoader.tileMaps.get(map.currentId).NPCs.get(time));
            objects.add(player);
        }
    }

    public static boolean hasCraft() {
        for (int i = 0; i < items[1].length - 1; i++) {
            if (items[1][i] != 0) { return true;}
        }
        return false;
    }

    public static boolean hasFood() {
        for (int i = 0; i < items[2].length - 1; i++) {
            if (items[2][i] != 0) { return true;}
        }
        return false;
    }

    public static boolean hasStinkBomb() {
        return items[0][1] == 0;
    }

    public static boolean hasLockPick() {
        return items[1][4] == 0;
    }

    public static boolean hasSuperCake() {
        return items[2][4] == 0;
    }

    public static void doTransition() {
        Game.transition = true;
        Game.transitionTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        time = 0;

        FileReader.readFiles();
        TextBox.loadImages();
        Menu.loadImages();
        TileMapLoader.loadMaps();
        map = new TileMapView(game, TileMapLoader.tileMaps.get(0));

        camera = new Camera(game.player.x - (Constants.FRAME_WIDTH / 64), game.player.y - (Constants.FRAME_HEIGHT / 64),
                map.matrix);

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

        GameFont.loadFont();
        game.createFrame();

        /*Game loop*/
        while (true) {
            game.update();
            map.repaint();
            Thread.sleep(70);
        }
    }
}
