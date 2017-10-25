import java.awt.image.BufferedImage;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Luke on 25/10/2017.
 */
public class MapTileSet extends TileSet {
    private static final int ROWS = 2, COLS = 6;

    public static TreeMap<Character, Tile> readTileSet(String fname) {
        List<BufferedImage> images = TileSet.readTileSet(ROWS,COLS,fname);

        TreeMap<Character, Tile> tiles = new TreeMap<>();
        Tile tile;
        try {
            for (int i = 0; i<images.size(); i++) {
                switch (i) {
                    //Wall
                    case 0: tile = new Tile(images.get(i),true,'W');
                        tiles.put(tile.key,tile);
                        break;
                    //Floor
                    case 1: tile = new Tile(images.get(i),false,'-');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackspace
                    case 2: tile = new Tile(images.get(i),true,'B');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackboard1
                    case 3: tile = new Tile(images.get(i),true,'x');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackboard2
                    case 4: tile = new Tile(images.get(i),true,'y');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackboard3
                    case 5: tile = new Tile(images.get(i),true,'z');
                        tiles.put(tile.key,tile);
                        break;
                    //Chair
                    case 6: tile = new Tile(images.get(i),false,'C');
                        tiles.put(tile.key,tile);
                        break;
                    //Desk
                    case 9: tile = new Tile(images.get(i),true,'D');
                        tiles.put(tile.key,tile);
                        break;
                    //Bookcase top
                    case 10: tile = new Tile(images.get(i),true,'a');
                        tiles.put(tile.key,tile);
                        break;
                    //Bookcase bottom
                    case 11: tile = new Tile(images.get(i),true,'A');
                        tiles.put(tile.key,tile);
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
