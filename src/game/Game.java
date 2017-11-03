package game;

import controllers.Keys;
import objects.GameObject;
import objects.InteractiveTile;
import objects.Player;
import utilities.GameFont;
import utilities.TextBox;
import utilities.TileMapLoader;

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
    public Keys ctrl;
    public static TileMapView map;
    public static Camera camera;
    public static TextBox textBox;
    public static List<List<Character>> gameMatrix;
    public static boolean transition;
    public static long transitionTime;

    public Game() {
        objects = new ArrayList<>();
        ctrl = new Keys();
        player = new Player(Player.TILES.get(0), Constants.START_X, Constants.START_Y,ctrl);
        objects.add(player);
    }

    public void createFrame() {
        JFrame frame = new JFrame("Game");
        frame.setResizable(false);
        frame.add(map, BorderLayout.CENTER);
        frame.addKeyListener(this.ctrl);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void update() {
        gameMatrix.clear();
        gameMatrix = new ArrayList<>(map.matrix.size());

        for (int i = 0; i < map.matrix.size(); i++) {
            List<Character> line = map.matrix.get(i);
            gameMatrix.add(new ArrayList<>(line.size()));
            for (Character aLine : line) { gameMatrix.get(i).add(aLine); }
        }
        for (GameObject object : objects) {
            gameMatrix.get(object.y).set(object.x, object.tile.key);
            object.update();
        }
        camera.update();
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();

        TileMapLoader.loadMaps();
        map = new TileMapView(game, TileMapLoader.tileMaps.get(0));

        camera = new Camera(game.player.x - (Constants.FRAME_WIDTH / 64), game.player.y - (Constants.FRAME_HEIGHT / 64),
                map.matrix);

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
