import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by lmweav on 23/10/2017.
 */
public class TileMap extends JComponent {
    public static final int FRAME_WIDTH = 480, FRAME_HEIGHT = 480;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT );


    public File txtFile;
    public List<List<Character>> matrix;
    public TreeMap<Character, Tile> tiles;

    public TileMap(String txtFile, String tileFile) {
        this.txtFile = new File("resources/"+txtFile+".txt");
        matrix = TileMapEngine.readMap(this.txtFile);
        tiles = TileSet.readTileSet(2,6,"resources/tilesets/"+tileFile+".png");

    }

    public void paintComponent(Graphics g) {
        for (int j = 0; j<matrix.size(); j++) {
            for (int i = 0; i<matrix.get(0).size(); i++) {
                Tile tile = tiles.get(matrix.get(j).get(i));
                g.drawImage(tile.img,i*32,j*32,32,32,null);
            }
        }
    }

    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
