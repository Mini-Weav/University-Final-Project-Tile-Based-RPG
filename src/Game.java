import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 23/10/2017.
 */
public class Game {
    public Player player;
    public TileMap map;
    public List<GameObject> objects;
    public static List<List<Character>> gameMatrix;
    public Keys ctrl;

    public Game() {
        objects = new ArrayList<>();
        ctrl = new Keys();
        player = new Player(Player.TILES.get(0),11,3,ctrl);
        objects.add(player);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        TileMap test = new TileMap(game,"test","classroom");
        game.map = test;
        gameMatrix = new ArrayList<>(game.map.matrix.size());

        new JEasyFrame(test,"test").addKeyListener(game.ctrl);

        while (true) {
            game.update();
            //System.out.println(game.player.x+","+game.player.y);
            test.repaint();
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

    }
}
