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

import static game.Game.GAME;

/**
 * 02/11/2017.
 */
public class TileMap {
    private int id;
    private int miniMapId;
    private File txtFile;
    private Point iconPoint;

    private TreeMap<Character, Tile> tiles;
    private TreeMap<Integer, List<NPC>> NPCs;
    private HashMap<Point, Pair<Integer, Point>> doorPoints;
    private HashMap<Point, TextBox> interactivePoints;

    public TileMap(int id, int tileSetID, String txtFile, String tileFile, int miniMapId, int x, int y) {
        this.id = id;
        this.txtFile = new File("resources/maps/"+txtFile+".txt");
        String tileFile1 = "resources/tilesets/" + tileFile + ".png";
        tiles = TileMapLoader.readTileSet(tileSetID, tileFile1);
        doorPoints = DoorTile.initialisePoints(id);
        interactivePoints = InteractiveTile.initialisePoints(id);
        this.miniMapId = miniMapId;
        iconPoint = new Point(x, y);
        try { NPCs = NPCLoader.getNPCs(id); }
        catch (NullPointerException e) { /* No NPCs in map */}
    }

    public int getId() { return id; }

    int getMiniMapId() { return miniMapId; }

    File getTxtFile() { return txtFile; }

    Point getIconPoint() { return iconPoint; }

    TreeMap<Character, Tile> copyTiles() { return new TreeMap<>(tiles); }
    Tile getTile(Character key) { return tiles.get(key); }
    void putTile(Character key, Tile tile) { tiles.put(key, tile); }

    public List<NPC> getNPCs(Integer key) { return NPCs.get(key); }
    public int getNumberOfNPCs(Integer key) { return NPCs.get(key).size(); }
    public void resetNPCs() {
        try {
            for (int i = 0; i < getNumberOfNPCs(GAME.getTime()); i++) {
                NPC npc = getNPCs(GAME.getTime()).get(i);
                npc.reset();
            }
        } catch (NullPointerException e) { /* Do nothing */ }
    }

    public Pair<Integer, Point> getDoorPoint(Point key) { return doorPoints.get(key); }

    TextBox getInteractivePoint(Point key) { return interactivePoints.get(key); }
    void putPoint(Point key, TextBox textBox) { interactivePoints.put(key, textBox); }
}
