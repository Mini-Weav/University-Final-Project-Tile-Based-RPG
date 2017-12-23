package game;

import objects.DoorTile;
import objects.InteractiveTile;
import objects.NPC;
import utilities.NPCLoader;
import utilities.Pair;
import objects.Tile;
import utilities.TextBox;
import utilities.TileMapLoader;

import java.awt.*;
import java.util.List;
import java.io.File;
import java.util.*;

/**
 * Created by lmweav on 02/11/2017.
 */
public class TileMap {
    public int id, tileSetID, minimapId;
    public File txtFile;
    public String tileFile;
    public TreeMap<Character, Tile> tiles;
    public HashMap<Point, Pair<Integer, Point>> doorPoints;
    public HashMap<Point, TextBox> interactivePoints;
    public Point iconPoint;
    public List<List<NPC>> NPCs;

    public TileMap(int id, int tileSetID, String txtFile, String tileFile, int minimapId, int x, int y) {
        this.id = id;
        this.tileSetID = tileSetID;
        this.txtFile = new File("resources/maps/"+txtFile+".txt");
        this.tileFile = "resources/tilesets/"+tileFile+".png";
        tiles = TileMapLoader.readTileSet(this.tileSetID, this.tileFile);
        doorPoints = DoorTile.initialisePoints(id);
        interactivePoints = InteractiveTile.initialisePoints(id);
        this.minimapId = minimapId;
        iconPoint = new Point(x, y);
        NPCs = NPCLoader.NPCs.get(id);
    }
}
