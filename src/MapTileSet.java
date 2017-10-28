import java.awt.image.BufferedImage;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Luke on 25/10/2017.
 */
public class MapTileSet extends TileSet {
    private static final int ROWS = 11, COLS = 7;

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
                    //Floor 1
                    case 1: tile = new Tile(images.get(i),false,'-');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackspace
                    case 2: tile = new Tile(images.get(i),true,'B');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackboard 1
                    case 3: tile = new Tile(images.get(i),true,'x');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackboard 2
                    case 4: tile = new Tile(images.get(i),true,'y');
                        tiles.put(tile.key,tile);
                        break;
                    //Blackboard 3
                    case 5: tile = new Tile(images.get(i),true,'z');
                        tiles.put(tile.key,tile);
                        break;
                    //Door
                    case 6: tile = new DoorTile(images.get(i),'#');
                        tiles.put(tile.key,tile);
                        break;
                    //Floor 2
                    case 7: tile = new Tile(images.get(i),false,'_');
                        tiles.put(tile.key,tile);
                        break;
                    //Chair
                    case 8: tile = new Tile(images.get(i),false,'C');
                        tiles.put(tile.key,tile);
                        break;
                    //Doormat 1
                    case 9: tile = new Tile(images.get(i),false,'L');
                        tiles.put(tile.key,tile);
                        break;
                    //Doormat 2
                    case 10: tile = new Tile(images.get(i),false,'R');
                        tiles.put(tile.key,tile);
                        break;
                    //Desk
                    case 11: tile = new Tile(images.get(i),true,'D');
                        tiles.put(tile.key,tile);
                        break;
                    //Bookcase top
                    case 12: tile = new Tile(images.get(i),true,'a');
                        tiles.put(tile.key,tile);
                        break;
                    //Bookcase bottom
                    case 13: tile = new Tile(images.get(i),true,'A');
                        tiles.put(tile.key,tile);
                        break;
                    //Up staircase
                    case 14: tile = new DoorTile(images.get(i),'/');
                        tiles.put(tile.key,tile);
                        break;
                    //Down staircase
                    case 15: tile = new DoorTile(images.get(i),'\\');
                        tiles.put(tile.key,tile);
                        break;
                    //Door 2
                    case 16: tile = new DoorTile(images.get(i),'@');
                        tiles.put(tile.key,tile);
                        break;
                    //Fridge top
                    case 17: tile = new Tile(images.get(i),true,'f');
                        tiles.put(tile.key,tile);
                        break;
                    //Fridge bottom
                    case 18: tile = new Tile(images.get(i),true,'F');
                        tiles.put(tile.key,tile);
                        break;
                    //Sink
                    case 19: tile = new Tile(images.get(i),true,'S');
                        tiles.put(tile.key,tile);
                        break;
                    //Oven
                    case 20: tile = new Tile(images.get(i),true,'O');
                        tiles.put(tile.key,tile);
                        break;
                    //Invisible door
                    case 21: tile = new DoorTile(images.get(i),'b');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool shelf top 1
                    case 22: tile = new Tile(images.get(i),true,'u');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool shelf top 2
                    case 23: tile = new Tile(images.get(i),true,'i');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool shelf bottom 1
                    case 24: tile = new Tile(images.get(i),true,'U');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool shelf bottom 2
                    case 25: tile = new Tile(images.get(i),true,'I');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool desk top 1
                    case 26: tile = new Tile(images.get(i),true,'h');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool desk top 2
                    case 27: tile = new Tile(images.get(i),true,'j');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool desk bottom 1
                    case 28: tile = new Tile(images.get(i),true,'H');
                        tiles.put(tile.key,tile);
                        break;
                    //Tool desk bottom 2
                    case 29: tile = new Tile(images.get(i),true,'J');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen desk bottom 1
                    case 30: tile = new Tile(images.get(i),true,'9');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen desk bottom 2
                    case 31: tile = new Tile(images.get(i),true,'0');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen desk middle 1
                    case 32: tile = new Tile(images.get(i),true,'7');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen desk middle 2
                    case 33: tile = new Tile(images.get(i),true,'8');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen chair
                    case 34: tile = new Tile(images.get(i),false,'c');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen desk top 1
                    case 35: tile = new Tile(images.get(i),true,'5');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen desk top 2
                    case 36: tile = new Tile(images.get(i),true,'6');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen counter 1
                    case 37: tile = new Tile(images.get(i),true,'4');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen counter 2
                    case 38: tile = new Tile(images.get(i),true,'3');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen counter 3
                    case 39: tile = new Tile(images.get(i),true,'2');
                        tiles.put(tile.key,tile);
                        break;
                    //Canteen counter 4
                    case 40: tile = new Tile(images.get(i),true,'1');
                        tiles.put(tile.key,tile);
                        break;
                    //Window
                    case 41: tile = new Tile(images.get(i),true,'w');
                        tiles.put(tile.key,tile);
                        break;
                    //Computer desk top 1
                    case 42: tile = new Tile(images.get(i),true,'p');
                        tiles.put(tile.key,tile);
                        break;
                    //Computer desk top 2
                    case 43: tile = new Tile(images.get(i),true,'v');
                        tiles.put(tile.key,tile);
                        break;
                    //Computer desk bottom 1
                    case 44: tile = new Tile(images.get(i),false,'P');
                        tiles.put(tile.key,tile);
                        break;
                    //Computer desk bottom 2
                    case 45: tile = new Tile(images.get(i),false,'V');
                        tiles.put(tile.key,tile);
                        break;
                    //Staff room door
                    case 46: tile = new Tile(images.get(i),true,'$');
                        tiles.put(tile.key,tile);
                        break;
                    //Gravel floor
                    case 47: tile = new Tile(images.get(i),false,'~');
                        tiles.put(tile.key,tile);
                        break;
                    //Sign
                    case 48: tile = new Tile(images.get(i),true,'?');
                        tiles.put(tile.key,tile);
                        break;
                    //Top left fence
                    case 49: tile = new Tile(images.get(i),true,'[');
                        tiles.put(tile.key,tile);
                        break;
                    //Fence
                    case 50: tile = new Tile(images.get(i),true,'!');
                        tiles.put(tile.key,tile);
                        break;
                    //Top right fence
                    case 51: tile = new Tile(images.get(i),true,']');
                        tiles.put(tile.key,tile);
                        break;
                    //Left fence
                    case 52: tile = new Tile(images.get(i),true,'{');
                        tiles.put(tile.key,tile);
                        break;
                    //Right fence
                    case 53: tile = new Tile(images.get(i),true,'}');
                        tiles.put(tile.key,tile);
                        break;
                    //Grass
                    case 54: tile = new Tile(images.get(i),false,'`');
                        tiles.put(tile.key,tile);
                        break;
                    //Top tree
                    case 55: tile = new Tile(images.get(i),true,'t');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom tree
                    case 56: tile = new Tile(images.get(i),true,'T');
                        tiles.put(tile.key,tile);
                        break;
                    //Top roof 1
                    case 57: tile = new Tile(images.get(i),true,'e');
                        tiles.put(tile.key,tile);
                        break;
                    //Top roof 2
                    case 58: tile = new Tile(images.get(i),true,'q');
                        tiles.put(tile.key,tile);
                        break;
                    //Top roof 3
                    case 59: tile = new Tile(images.get(i),true,'k');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom roof 1
                    case 60: tile = new Tile(images.get(i),true,'E');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom roof 2
                    case 61: tile = new Tile(images.get(i),true,'Q');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom roof 3
                    case 62: tile = new Tile(images.get(i),true,'K');
                        tiles.put(tile.key,tile);
                        break;
                    //Top building 1
                    case 63: tile = new Tile(images.get(i),true,'n');
                        tiles.put(tile.key,tile);
                        break;
                    //Top building 2
                    case 64: tile = new Tile(images.get(i),true,'m');
                        tiles.put(tile.key,tile);
                        break;
                    //Top building 3
                    case 65: tile = new Tile(images.get(i),true,'g');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom building 1
                    case 66: tile = new Tile(images.get(i),true,'N');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom building 2
                    case 67: tile = new Tile(images.get(i),true,'M');
                        tiles.put(tile.key,tile);
                        break;
                    //Bottom building 3
                    case 68: tile = new Tile(images.get(i),true,'G');
                        tiles.put(tile.key,tile);
                        break;
                    //Track
                    case 69: tile = new Tile(images.get(i),false,'*');
                        tiles.put(tile.key,tile);
                        break;
                    //Middle building 1
                    case 70: tile = new Tile(images.get(i),true,'(');
                        tiles.put(tile.key,tile);
                        break;
                    //Middle building 2
                    case 71: tile = new Tile(images.get(i),true,'+');
                        tiles.put(tile.key,tile);
                        break;
                    //Middle building 3
                    case 72: tile = new Tile(images.get(i),true,')');
                        tiles.put(tile.key,tile);
                        break;
                    //Invisible roof door
                    case 73: tile = new DoorTile(images.get(i),'=');
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
