package game;

import controllers.Keys;
import objects.DoorTile;
import objects.GameObject;
import objects.InteractiveTile;
import objects.Player;
import utilities.GameFont;
import utilities.TextBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 23/10/2017.
 */
public class Game {
    public Player player;
    public List<GameObject> objects;
    public static TileMapView map;
    public static Camera camera;
    public static TextBox textBox;
    public static List<List<Character>> gameMatrix;
    public static boolean transition;
    public static long transitionTime;
    public Keys ctrl;

    public Game() {
        objects = new ArrayList<>();
        ctrl = new Keys();
        player = new Player(Player.TILES.get(0),9,22,ctrl);
        objects.add(player);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        TileMapView test = new TileMapView(game,"map","school");
        map = test;
        gameMatrix = new ArrayList<>(map.matrix.size());
        camera = new Camera(game.player.x-(TileMapView.FRAME_WIDTH/64),game.player.y-(TileMapView.FRAME_HEIGHT/64), map.matrix);
        DoorTile.initialisePoints();
        InteractiveTile.initialisePoints();
        GameFont.loadFont();

        JFrame frame = new JFrame("game.Game");
        frame.add(test, BorderLayout.CENTER);
        frame.addKeyListener(game.ctrl);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(TileMapView.FRAME_SIZE );


        while (true) {
            game.update();
            test.repaint();
            Thread.sleep(70);
        }

    }

    public void update() {
        gameMatrix.clear();
        gameMatrix = new ArrayList<>(map.matrix.size());
        for (int i=0; i<map.matrix.size(); i++) {
            List<Character> line = map.matrix.get(i);
            gameMatrix.add(new ArrayList<>(line.size()));
            for (Character aLine : line) {
                gameMatrix.get(i).add(aLine);
            }
        }



        for (GameObject object : objects) {
            object.update();
            gameMatrix.get(object.y).set(object.x,object.tile.key);
        }

        camera.update();

    }
}
