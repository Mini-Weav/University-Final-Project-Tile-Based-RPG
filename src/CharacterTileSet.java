import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Luke on 25/10/2017.
 */
public class CharacterTileSet extends TileSet {
    private static final int ROWS = 2, COLS = 5;

    public static List<Tile> readTileSet(String fname, char key) {
        List<BufferedImage> images = TileSet.readTileSet(ROWS,COLS,fname);

        List<Tile> tiles = new ArrayList<>();
        Tile tile;
        try {
            for (int i = 0; i<images.size(); i++) {
                switch (i) {
                    //Down0
                    case 0: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Up0
                    case 1: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Left0
                    case 2: tile = new Tile(images.get(i),false,key);
                        tiles.add(tile);
                        break;
                    //Right0
                    case 3: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Down1
                    case 4: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Down2
                    case 5: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Up1
                    case 6: tile = new Tile(images.get(i),false,key);
                        tiles.add(tile);
                        break;
                    //Up2
                    case 7: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Left1
                    case 8: tile = new Tile(images.get(i),true,key);
                        tiles.add(tile);
                        break;
                    //Right1
                    case 9: tile = new Tile(images.get(i),true,'P');
                        tiles.add(tile);
                        break;
                }
            }
            return tiles;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Unable to read tileset");
            return null;
        }
    }

}
