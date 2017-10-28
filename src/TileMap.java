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
    public static int maxX, maxY;
    public static TreeMap<Character, Tile> tiles;

    public File txtFile;
    public List<List<Character>> matrix;

    public TileMap(Game game, String txtFile, String tileFile) {
        this.game = game;
        this.txtFile = new File("resources/"+txtFile+".txt");
        matrix = TileMapEngine.readMap(this.txtFile);
        maxX = matrix.get(0).size()-1;
        maxY = matrix.size()-1;
        tiles = MapTileSet.readTileSet("resources/tilesets/"+tileFile+".png");
    }

    public void paintComponent(Graphics g) {
        for (int j = 0; j< Camera.sizeY; j++) {
            for (int i = 0; i<Camera.sizeX; i++) {
                try {
                    Tile tile = tiles.get(matrix.get(Game.camera.y+j).get(Game.camera.x+i));
                    g.drawImage(tile.img,i*32,j*32,32,32,null);
                } catch (IndexOutOfBoundsException e) {
                    Tile tile = tiles.get('B');
                    g.drawImage(tile.img,i*32,j*32,32,32,null);
                }

            }
        }
        for (GameObject object : game.objects) {
            if (object.x >= Game.camera.x && object.x <= Game.camera.x + Camera.sizeX &&
                    object.y >= Game.camera.y && object.y <= Game.camera.y + Camera.sizeY) {
                object.paintComponent(g);
            }
        }
    }

    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
