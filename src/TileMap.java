import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by lmweav on 23/10/2017.
 */
public class TileMap extends JComponent {
    private Game game;

    public static final int FRAME_WIDTH = 480, FRAME_HEIGHT = 480;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT );
    public static int MAX_X, MAX_Y;
    public static TreeMap<Character, Tile> tiles;

    public File txtFile;
    public List<List<Character>> matrix;

    public TileMap(Game game, String txtFile, String tileFile) {
        this.game = game;
        this.txtFile = new File("resources/"+txtFile+".txt");
        matrix = TileMapEngine.readMap(this.txtFile);
        MAX_X = matrix.get(0).size()-1;
        MAX_Y = matrix.size()-1;
        tiles = MapTileSet.readTileSet("resources/tilesets/"+tileFile+".png");
    }

    public void paintComponent(Graphics g) {
        for (int j = 0; j<matrix.size(); j++) {
            for (int i = 0; i<matrix.get(0).size(); i++) {
                Tile tile = tiles.get(matrix.get(j).get(i));
                g.drawImage(tile.img,i*32,j*32,32,32,null);
            }
        }
        for (GameObject object : game.objects) {
            object.paintComponent(g);
        }
    }

    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
