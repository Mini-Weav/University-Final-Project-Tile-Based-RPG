package game;

import objects.DoorTile;
import objects.InteractiveTile;
import utilities.Pair;
import objects.Tile;
import utilities.TileMapLoader;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by lmweav on 02/11/2017.
 */
public class TileMap {
    public int id, tileSetID;
    public File txtFile;
    public String tileFile;
    public TreeMap<Character, Tile> tiles;
    public HashMap<Point, Pair<Integer, Point>> doorPoints;
    public HashMap<Point,String> interactivePoints;

    public TileMap(int id, int tileSetID, String txtFile, String tileFile) {
        this.id = id;
        this.tileSetID = tileSetID;
        this.txtFile = new File("resources/maps/"+txtFile+".txt");
        this.tileFile = "resources/tilesets/"+tileFile+".png";
        this.tiles = TileMapLoader.readTileSet(this.tileSetID, this.tileFile);
        this.doorPoints = DoorTile.initialisePoints(id);
        this.interactivePoints = InteractiveTile.initialisePoints(id);
    }


}
