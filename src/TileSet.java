import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by lmweav on 23/10/2017.
 */
public class TileSet {

    public static TreeMap<Character, Tile> readTileSet(int rows, int cols, String fname) {
        List<BufferedImage> images = new ArrayList<>();
        BufferedImage ts;
        try {
            ts = ImageIO.read(new File(fname));
        } catch (IOException e) {
            System.out.println("cannot find file");
            e.printStackTrace();
            return null;
        }

        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                images.add((i*cols)+j,ts.getSubimage(j*Tile.WIDTH, i*Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT));
            }
        }

        TreeMap<Character, Tile> tiles = new TreeMap<>();
        for (int i = 0; i<images.size(); i++) {
            switch (i) {
                case 0: tiles.put('W',new Tile(images.get(i),true)) ;
                    break;
                case 1: tiles.put('0',new Tile(images.get(i),true));
                    break;
                case 2: tiles.put('B',new Tile(images.get(i),true));
                    break;
            }
        }

        return tiles;
    }

}
