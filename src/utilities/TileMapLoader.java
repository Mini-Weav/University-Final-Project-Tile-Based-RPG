package utilities;

import game.TileMap;
import objects.DoorTile;
import objects.InteractiveTile;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 02/11/2017.
 */
public class TileMapLoader {
    public static ArrayList<TileMap> tileMaps;

    /**
     * Pre-loads all the TileMaps used in the game.
     */
    public static void loadMaps() {
        tileMaps = new ArrayList<>();
        tileMaps.add(new TileMap(0, 0,"hall_g","school_hall", 0, 126, 132));
        tileMaps.add(new TileMap(1, 0,"hall_1f","school_hall", 1, 128, 180));
        tileMaps.add(new TileMap(2, 1,"classroom_dt","school_classroom", 0, 88, 132));
        tileMaps.add(new TileMap(3, 1,"classroom_ft","school_classroom", 0, 164, 132));
        tileMaps.add(new TileMap(4, 1,"classroom_1f","school_classroom", 1, 128, 160));
        tileMaps.add(new TileMap(5, 2,"canteen","school_canteen", 0, 126, 176));
        tileMaps.add(new TileMap(6, 3,"yard","school_yard", 0, 126, 80));
        tileMaps.add(new TileMap(7, 4, "bedroom", "bedroom", 2, 0, 0));
        tileMaps.add(new TileMap(8, 5, "staff_room", "school_staffroom", 1, 128, 90));
    }

    /**
     * Reads a supplied tileset image and creates a List of Tiles for a TileMap.
     *
     * @param id the identification of the TileMap
     * @param fname the filename of the tileset
     * @return the List of Tiles for a TileMap
     */
    public static TreeMap<Character, Tile> readTileSet(int id, String fname) {
        List<BufferedImage> images;
        TreeMap<Character, Tile> tiles = new TreeMap<>();
        Tile tile;
        try {
            switch (id) {
                /* School Halls */
                case 0:
                    images = TileSet.readTileSet(5, 3, fname);
                    assert images != null;
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {
                            /* Black Space */
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Wall */
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Window */
                            case 2:
                                tile = new InteractiveTile(images.get(i), true, 'w', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Floor */
                            case 3:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat L */
                            case 4:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat R */
                            case 5:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Door 1 */
                            case 6:
                                tile = new DoorTile(images.get(i), 'D');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Door 2 */
                            case 7:
                                tile = new DoorTile(images.get(i), 'd');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Up Staircase */
                            case 8:
                                tile = new DoorTile(images.get(i), '^');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Down Staircase */
                            case 9:
                                tile = new DoorTile(images.get(i), 'v');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Sign */
                            case 10:
                                tile = new InteractiveTile(images.get(i), true, '?', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Locked Door */
                            case 11:
                                tile = new DoorTile(images.get(i), '$');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Air Vent */
                            case 12:
                                tile = new InteractiveTile(images.get(i), true, 'A', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Go home door */
                            case 13:
                                tile = new DoorTile(images.get(i), '£');
                                tiles.put(tile.getKey(), tile);
                                break;
                        }
                    }
                    break;
                /* Classrooms */
                case 1:
                    images = TileSet.readTileSet(10, 3, fname);
                    assert images != null;
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {
                            /* Black Space */
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Wall */
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Whiteboard 1 */
                            case 2:
                                tile = new InteractiveTile(images.get(i), true, '[', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Whiteboard 2 */
                            case 3:
                                tile = new InteractiveTile(images.get(i), true, '=', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Whiteboard 3 */
                            case 4:
                                tile = new InteractiveTile(images.get(i), true, ']', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Floor */
                            case 5:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Chair */
                            case 6:
                                tile = new Tile(images.get(i), false, 'n');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat L */
                            case 7:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat R */
                            case 8:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Invisible Door */
                            case 9:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Desk */
                            case 10:
                                tile = new Tile(images.get(i), true, 'D');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bookcase Top */
                            case 11:
                                tile = new Tile(images.get(i), true, 'h');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bookcase Bottom */
                            case 12:
                                tile = new InteractiveTile(images.get(i), true, 'H', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fridge Top */
                            case 13:
                                tile = new Tile(images.get(i), true, 'f');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fridge Bottom */
                            case 14:
                                tile = new InteractiveTile(images.get(i), true, 'F', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Sink */
                            case 15:
                                tile = new InteractiveTile(images.get(i), true, 'S', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Oven */
                            case 16:
                                tile = new InteractiveTile(images.get(i), true, 'O', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Tool Shelf Top L */
                            case 17:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Tool Shelf Top R */
                            case 18:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Tool Shelf Bottom L */
                            case 19:
                                tile = new InteractiveTile(images.get(i), true, '3', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Tool Shelf Bottom R */
                            case 20:
                                tile = new InteractiveTile(images.get(i), true, '4', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Work Bench Top L */
                            case 21:
                                tile = new InteractiveTile(images.get(i), true, '5', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Work Bench Top R */
                            case 22:
                                tile = new InteractiveTile(images.get(i), true, '6', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Work Bench Bottom L */
                            case 23:
                                tile = new InteractiveTile(images.get(i), true, '7', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Work Bench Bottom R */
                            case 24:
                                tile = new InteractiveTile(images.get(i), true, '8', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Desk Top L */
                            case 25:
                                tile = new InteractiveTile(images.get(i), true, 'p', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Desk Top R */
                            case 26:
                                tile = new InteractiveTile(images.get(i), true, 'c', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Desk Bottom L */
                            case 27:
                                tile = new Tile(images.get(i), false, 'P');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Desk Bottom R */
                            case 28:
                                tile = new Tile(images.get(i), false, 'C');
                                tiles.put(tile.getKey(), tile);
                                break;
                        }
                    }
                    break;
                /* Canteen */
                case 2:
                    images = TileSet.readTileSet(8, 3, fname);
                    assert images != null;
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {
                            /* Black Space */
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Wall */
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Floor */
                            case 2:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Chair */
                            case 3:
                                tile = new Tile(images.get(i), false, 'n');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat L */
                            case 4:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat R */
                            case 5:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Invisible Door */
                            case 6:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Counter 1 */
                            case 7:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Counter 2 */
                            case 8:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Counter 3 */
                            case 9:
                                tile = new Tile(images.get(i), true, '3');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Counter 4 */
                            case 10:
                                tile = new Tile(images.get(i), true, '4');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table Top L */
                            case 11:
                                tile = new Tile(images.get(i), true, '5');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table Top R */
                            case 12:
                                tile = new Tile(images.get(i), true, '6');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table Middle L */
                            case 13:
                                tile = new Tile(images.get(i), true, '7');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table Middle R */
                            case 14:
                                tile = new Tile(images.get(i), true, '8');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table Bottom L */
                            case 15:
                                tile = new Tile(images.get(i), true, '9');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table Middle R */
                            case 16:
                                tile = new Tile(images.get(i), true, '0');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fridge Top */
                            case 17:
                                tile = new Tile(images.get(i), true, 'f');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fridge Bottom */
                            case 18:
                                tile = new InteractiveTile(images.get(i), true, 'F', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Sink */
                            case 19:
                                tile = new InteractiveTile(images.get(i), true, 'S', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Oven */
                            case 20:
                                tile = new InteractiveTile(images.get(i), true, 'O', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Counter 5 */
                            case 21:
                                tile = new Tile(images.get(i), true, '!');
                                tiles.put(tile.getKey(), tile);
                                break;
                        }
                    }
                    break;
                /* Yard */
                case 3:
                    images = TileSet.readTileSet(11, 3, fname);
                    assert images != null;
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {
                            /* Black Space */
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Tree Top */
                            case 1:
                                tile = new Tile(images.get(i), true, 't');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Tree Bottom */
                            case 2:
                                tile = new Tile(images.get(i), true, 'T');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Floor */
                            case 3:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Running Track */
                            case 4:
                                tile = new Tile(images.get(i), false, '~');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Grass */
                            case 5:
                                tile = new Tile(images.get(i), false, 'G');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat L */
                            case 6:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat R */
                            case 7:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Invisible Door */
                            case 8:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fence Top L */
                            case 9:
                                tile = new Tile(images.get(i), true, '[');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fence Top M */
                            case 10:
                                tile = new Tile(images.get(i), true, '+');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fence Top R */
                            case 11:
                                tile = new Tile(images.get(i), true, ']');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fence Down L */
                            case 12:
                                tile = new Tile(images.get(i), true, '(');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fence Down R */
                            case 13:
                                tile = new Tile(images.get(i), true, ')');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Sign */
                            case 14:
                                tile = new InteractiveTile(images.get(i), true, '?', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Roof Top L */
                            case 15:
                                tile = new Tile(images.get(i), true, '<');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Roof Top M */
                            case 16:
                                tile = new Tile(images.get(i), true, '=');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Roof Top R */
                            case 17:
                                tile = new Tile(images.get(i), true, '>');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Roof Bottom L */
                            case 18:
                                tile = new Tile(images.get(i), true, '/');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Roof Bottom C */
                            case 19:
                                tile = new Tile(images.get(i), true, '_');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Roof Bottom R */
                            case 20:
                                tile = new Tile(images.get(i), true, '\\');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Top L */
                            case 21:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Top M */
                            case 22:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Top R */
                            case 23:
                                tile = new Tile(images.get(i), true, '3');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Middle L */
                            case 24:
                                tile = new Tile(images.get(i), true, '4');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Middle M */
                            case 25:
                                tile = new Tile(images.get(i), true, '5');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Middle R */
                            case 26:
                                tile = new Tile(images.get(i), true, '6');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Bottom L */
                            case 27:
                                tile = new Tile(images.get(i), true, '7');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Bottom M */
                            case 28:
                                tile = new Tile(images.get(i), true, '8');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Building Bottom R */
                            case 29:
                                tile = new Tile(images.get(i), true, '9');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Door */
                            case 30:
                                tile = new Tile(images.get(i), true, 'D');
                                tiles.put(tile.getKey(), tile);
                                break;
                        }
                    }
                    break;
                /* Bedroom */
                case 4:
                    images = TileSet.readTileSet(5, 3, fname);
                    assert images != null;
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {
                            /* Black Space */
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Wall */
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Top */
                            case 2:
                                tile = new Tile(images.get(i), true, 'P');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* TV top */
                            case 3:
                                tile = new Tile(images.get(i), true, 'T');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bookcase Top */
                            case 4:
                                tile = new Tile(images.get(i), true, 'B');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Floor */
                            case 5:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Bottom */
                            case 6:
                                tile = new InteractiveTile(images.get(i), true, 'C', true);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Chair */
                            case 7:
                                tile = new Tile(images.get(i), false, 'h');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Game */
                            case 8:
                                tile = new InteractiveTile(images.get(i), true, 'G', true);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* TV Bottom */
                            case 9:
                                tile = new Tile(images.get(i), true, 'V');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bookcase Bottom */
                            case 10:
                                tile = new InteractiveTile(images.get(i), true, 'c', true);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Door */
                            case 11:
                                tile = new InteractiveTile(images.get(i), true, 'D', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bed Top */
                            case 12:
                                tile = new InteractiveTile(images.get(i), true, 'n', true);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bed Bottom */
                            case 13:
                                tile = new InteractiveTile(images.get(i), true, 'U', true);
                                tiles.put(tile.getKey(), tile);
                                break;
                        }
                    }
                    break;
                case 5:
                    images = TileSet.readTileSet(13, 3, fname);
                    assert images != null;
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {
                            /* Black Space */
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Wall */
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Top */
                            case 2:
                                tile = new Tile(images.get(i), true, 'P');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* TV top */
                            case 3:
                                tile = new Tile(images.get(i), true, 'T');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bookcase Top */
                            case 4:
                                tile = new Tile(images.get(i), true, 'h');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fridge Top */
                            case 5:
                                tile = new Tile(images.get(i), true, 'f');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Room Divider bottom */
                            case 6:
                                tile = new Tile(images.get(i), true, '8');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Room Divider Top */
                            case 7:
                                tile = new Tile(images.get(i), true, '7');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Window */
                            case 8:
                                tile = new InteractiveTile(images.get(i), true, 'w', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Floor */
                            case 9:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat L */
                            case 10:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Doormat R */
                            case 11:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Chair */
                            case 12:
                                tile = new Tile(images.get(i), false, 'n');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Computer Bottom */
                            case 13:
                                tile = new InteractiveTile(images.get(i), true, 'C', true);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* TV Bottom */
                            case 14:
                                tile = new InteractiveTile(images.get(i), true, 'V', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Bookcase Bottom */
                            case 15:
                                tile = new InteractiveTile(images.get(i), true, 'H', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Fridge Bottom */
                            case 16:
                                tile = new InteractiveTile(images.get(i), true, 'F', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Sink */
                            case 17:
                                tile = new InteractiveTile(images.get(i), true, 'S', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Chest */
                            case 18:
                                tile = new InteractiveTile(images.get(i), true, 'B', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Stool */
                            case 19:
                                tile = new Tile(images.get(i), true, 'o');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Oven */
                            case 20:
                                tile = new InteractiveTile(images.get(i), true, 'O', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table top L */
                            case 21:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table top R */
                            case 22:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table middle L */
                            case 23:
                                tile = new Tile(images.get(i), true, '3');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table middle R */
                            case 24:
                                tile = new Tile(images.get(i), true, '4');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table bottom L */
                            case 25:
                                tile = new Tile(images.get(i), true, '5');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Table bottom R */
                            case 26:
                                tile = new Tile(images.get(i), true, '6');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Desk top L */
                            case 27:
                                tile = new Tile(images.get(i), true, '<');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Desk top R */
                            case 28:
                                tile = new Tile(images.get(i), true, '>');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Desk bottom L */
                            case 29:
                                tile = new Tile(images.get(i), false, '\\');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Desk bottom R */
                            case 30:
                                tile = new Tile(images.get(i), false, '/');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Plant top */
                            case 31:
                                tile = new Tile(images.get(i), true, '^');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Plant bottom */
                            case 32:
                                tile = new Tile(images.get(i), true, 'X');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Invisible Door */
                            case 33:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Sign */
                            case 34:
                                tile = new InteractiveTile(images.get(i), true, '?', false);
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Couch left */
                            case 35:
                                tile = new Tile(images.get(i), true, '(');
                                tiles.put(tile.getKey(), tile);
                                break;
                            /* Couch right */
                            case 36:
                                tile = new Tile(images.get(i), true, ')');
                                tiles.put(tile.getKey(), tile);
                                break;
                        }
                    }
                    break;
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
