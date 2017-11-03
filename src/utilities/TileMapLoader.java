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
 * Created by lmweav on 02/11/2017.
 */
public class TileMapLoader {
    public static ArrayList<TileMap> tileMaps;

    public static void loadMaps() {
        tileMaps = new ArrayList<>();
        tileMaps.add(new TileMap(0, 0,"hall_g","school_hall"));
        tileMaps.add(new TileMap(1, 0,"hall_1f","school_hall"));
        tileMaps.add(new TileMap(2, 1,"classroom_dt","school_classroom"));
        tileMaps.add(new TileMap(3, 1,"classroom_ft","school_classroom"));
        tileMaps.add(new TileMap(4, 1,"classroom_1f","school_classroom"));
        tileMaps.add(new TileMap(5, 2,"canteen","school_canteen"));
        tileMaps.add(new TileMap(6, 3,"yard","school_yard"));
    }

    public static TreeMap<Character, Tile> readTileSet(int id, String fname) {
        List<BufferedImage> images;
        TreeMap<Character, Tile> tiles = new TreeMap<>();
        Tile tile;
        try {
            switch (id) {

                /*School Halls*/
                case 0:
                    images = TileSet.readTileSet(4, 3, fname);
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {

                            /*Black Space*/
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.key, tile);
                                break;

                            /*Wall*/
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.key, tile);
                                break;

                            /*Window*/
                            case 2:
                                tile = new InteractiveTile(images.get(i), true, 'w');
                                tiles.put(tile.key, tile);
                                break;

                            /*Floor*/
                            case 3:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat L*/
                            case 4:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat R*/
                            case 5:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.key, tile);
                                break;

                            /*Door 1*/
                            case 6:
                                tile = new DoorTile(images.get(i), 'D');
                                tiles.put(tile.key, tile);
                                break;

                            /*Door 2*/
                            case 7:
                                tile = new DoorTile(images.get(i), 'd');
                                tiles.put(tile.key, tile);
                                break;

                            /*Up Staircase*/
                            case 8:
                                tile = new DoorTile(images.get(i), '^');
                                tiles.put(tile.key, tile);
                                break;

                            /*Down Staircase*/
                            case 9:
                                tile = new DoorTile(images.get(i), 'v');
                                tiles.put(tile.key, tile);
                                break;

                            /*Sign*/
                            case 10:
                                tile = new InteractiveTile(images.get(i), true, '?');
                                tiles.put(tile.key, tile);
                                break;

                            /*Locked Door*/
                            case 11:
                                tile = new Tile(images.get(i), true, '$');
                                tiles.put(tile.key, tile);
                                break;

                        }
                    }
                    break;

                /*Classrooms*/
                case 1:
                    images = TileSet.readTileSet(10, 3, fname);
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {

                            /*Black Space*/
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.key, tile);
                                break;

                            /*Wall*/
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.key, tile);
                                break;

                            /*Whiteboard 1*/
                            case 2:
                                tile = new InteractiveTile(images.get(i), true, '[');
                                tiles.put(tile.key, tile);
                                break;

                            /*Whiteboard 2*/
                            case 3:
                                tile = new InteractiveTile(images.get(i), true, '=');
                                tiles.put(tile.key, tile);
                                break;

                            /*Whiteboard 3*/
                            case 4:
                                tile = new InteractiveTile(images.get(i), true, ']');
                                tiles.put(tile.key, tile);
                                break;

                            /*Floor*/
                            case 5:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.key, tile);
                                break;

                            /*Chair*/
                            case 6:
                                tile = new Tile(images.get(i), false, 'n');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat L*/
                            case 7:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat R*/
                            case 8:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.key, tile);
                                break;

                            /*Invisible Door*/
                            case 9:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.key, tile);
                                break;

                            /*Desk*/
                            case 10:
                                tile = new Tile(images.get(i), true, 'D');
                                tiles.put(tile.key, tile);
                                break;

                            /*Bookcase Top*/
                            case 11:
                                tile = new Tile(images.get(i), true, 'h');
                                tiles.put(tile.key, tile);
                                break;

                            /*Bookcase Bottom*/
                            case 12:
                                tile = new InteractiveTile(images.get(i), true, 'H');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fridge Top*/
                            case 13:
                                tile = new Tile(images.get(i), true, 'f');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fridge Bottom*/
                            case 14:
                                tile = new InteractiveTile(images.get(i), true, 'F');
                                tiles.put(tile.key, tile);
                                break;

                            /*Sink*/
                            case 15:
                                tile = new InteractiveTile(images.get(i), true, 'S');
                                tiles.put(tile.key, tile);
                                break;

                            /*Oven*/
                            case 16:
                                tile = new InteractiveTile(images.get(i), true, 'O');
                                tiles.put(tile.key, tile);
                                break;

                            /*Tool Shelf Top L*/
                            case 17:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.key, tile);
                                break;

                            /*Tool Shelf Top R*/
                            case 18:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.key, tile);
                                break;

                            /*Tool Shelf Bottom L*/
                            case 19:
                                tile = new InteractiveTile(images.get(i), true, '3');
                                tiles.put(tile.key, tile);
                                break;

                            /*Tool Shelf Bottom R*/
                            case 20:
                                tile = new InteractiveTile(images.get(i), true, '4');
                                tiles.put(tile.key, tile);
                                break;

                            /*Work Bench Top L*/
                            case 21:
                                tile = new InteractiveTile(images.get(i), true, '5');
                                tiles.put(tile.key, tile);
                                break;

                            /*Work Bench Top R*/
                            case 22:
                                tile = new InteractiveTile(images.get(i), true, '6');
                                tiles.put(tile.key, tile);
                                break;

                            /*Work Bench Bottom L*/
                            case 23:
                                tile = new InteractiveTile(images.get(i), true, '7');
                                tiles.put(tile.key, tile);
                                break;

                            /*Work Bench Bottom R*/
                            case 24:
                                tile = new InteractiveTile(images.get(i), true, '8');
                                tiles.put(tile.key, tile);
                                break;

                            /*Computer Desk Top L*/
                            case 25:
                                tile = new InteractiveTile(images.get(i), true, 'p');
                                tiles.put(tile.key, tile);
                                break;

                            /*Computer Desk Top R*/
                            case 26:
                                tile = new InteractiveTile(images.get(i), true, 'c');
                                tiles.put(tile.key, tile);
                                break;

                            /*Computer Desk Bottom L*/
                            case 27:
                                tile = new Tile(images.get(i), false, 'P');
                                tiles.put(tile.key, tile);
                                break;

                            /*Computer Desk Bottom R*/
                            case 28:
                                tile = new Tile(images.get(i), false, 'C');
                                tiles.put(tile.key, tile);
                                break;

                        }
                    }
                    break;

                /*Canteen*/
                case 2:
                    images = TileSet.readTileSet(7, 3, fname);
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {

                            /*Black Space*/
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.key, tile);
                                break;

                            /*Wall*/
                            case 1:
                                tile = new Tile(images.get(i), true, 'W');
                                tiles.put(tile.key, tile);
                                break;

                            /*Floor*/
                            case 2:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.key, tile);
                                break;

                            /*Chair*/
                            case 3:
                                tile = new Tile(images.get(i), false, 'n');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat L*/
                            case 4:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat R*/
                            case 5:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.key, tile);
                                break;

                            /*Invisible Door*/
                            case 6:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.key, tile);
                                break;

                            /*Counter 1*/
                            case 7:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.key, tile);
                                break;

                            /*Counter 2*/
                            case 8:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.key, tile);
                                break;

                            /*Counter 3*/
                            case 9:
                                tile = new Tile(images.get(i), true, '3');
                                tiles.put(tile.key, tile);
                                break;

                            /*Counter 4*/
                            case 10:
                                tile = new Tile(images.get(i), true, '4');
                                tiles.put(tile.key, tile);
                                break;

                            /*Table Top L*/
                            case 11:
                                tile = new Tile(images.get(i), true, '5');
                                tiles.put(tile.key, tile);
                                break;

                            /*Table Top R*/
                            case 12:
                                tile = new Tile(images.get(i), true, '6');
                                tiles.put(tile.key, tile);
                                break;

                            /*Table Middle L*/
                            case 13:
                                tile = new Tile(images.get(i), true, '7');
                                tiles.put(tile.key, tile);
                                break;

                            /*Table Middle R*/
                            case 14:
                                tile = new Tile(images.get(i), true, '8');
                                tiles.put(tile.key, tile);
                                break;

                            /*Table Bottom L*/
                            case 15:
                                tile = new Tile(images.get(i), true, '9');
                                tiles.put(tile.key, tile);
                                break;

                            /*Table Middle R*/
                            case 16:
                                tile = new Tile(images.get(i), true, '0');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fridge Top*/
                            case 17:
                                tile = new Tile(images.get(i), true, 'f');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fridge Bottom*/
                            case 18:
                                tile = new InteractiveTile(images.get(i), true, 'F');
                                tiles.put(tile.key, tile);
                                break;

                            /*Sink*/
                            case 19:
                                tile = new InteractiveTile(images.get(i), true, 'S');
                                tiles.put(tile.key, tile);
                                break;

                            /*Oven*/
                            case 20:
                                tile = new InteractiveTile(images.get(i), true, 'O');
                                tiles.put(tile.key, tile);
                                break;

                        }
                    }
                    break;

                /*Yard*/
                case 3:
                    images = TileSet.readTileSet(11, 3, fname);
                    for (int i = 0; i < images.size(); i++) {
                        switch (i) {

                            /*Black Space*/
                            case 0:
                                tile = new Tile(images.get(i), true, '#');
                                tiles.put(tile.key, tile);
                                break;

                            /*Tree Top*/
                            case 1:
                                tile = new Tile(images.get(i), true, 't');
                                tiles.put(tile.key, tile);
                                break;

                            /*Tree Bottom*/
                            case 2:
                                tile = new Tile(images.get(i), true, 'T');
                                tiles.put(tile.key, tile);
                                break;

                            /*Floor*/
                            case 3:
                                tile = new Tile(images.get(i), false, '-');
                                tiles.put(tile.key, tile);
                                break;

                            /*Running Track*/
                            case 4:
                                tile = new Tile(images.get(i), false, '~');
                                tiles.put(tile.key, tile);
                                break;

                            /*Grass*/
                            case 5:
                                tile = new Tile(images.get(i), false, 'G');
                                tiles.put(tile.key, tile);
                                break;


                            /*Doormat L*/
                            case 6:
                                tile = new Tile(images.get(i), false, 'L');
                                tiles.put(tile.key, tile);
                                break;

                            /*Doormat R*/
                            case 7:
                                tile = new Tile(images.get(i), false, 'R');
                                tiles.put(tile.key, tile);
                                break;

                            /*Invisible Door*/
                            case 8:
                                tile = new DoorTile(images.get(i), ' ');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fence Top L*/
                            case 9:
                                tile = new Tile(images.get(i), true, '[');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fence Top M*/
                            case 10:
                                tile = new Tile(images.get(i), true, '+');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fence Top R*/
                            case 11:
                                tile = new Tile(images.get(i), true, ']');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fence Down L*/
                            case 12:
                                tile = new Tile(images.get(i), true, '(');
                                tiles.put(tile.key, tile);
                                break;

                            /*Fence Down R*/
                            case 13:
                                tile = new Tile(images.get(i), true, ')');
                                tiles.put(tile.key, tile);
                                break;

                            /*Sign*/
                            case 14:
                                tile = new InteractiveTile(images.get(i), true, '?');
                                tiles.put(tile.key, tile);
                                break;

                            /*Roof Top L*/
                            case 15:
                                tile = new Tile(images.get(i), true, '<');
                                tiles.put(tile.key, tile);
                                break;

                            /*Roof Top M*/
                            case 16:
                                tile = new Tile(images.get(i), true, '=');
                                tiles.put(tile.key, tile);
                                break;

                            /*Roof Top R*/
                            case 17:
                                tile = new Tile(images.get(i), true, '>');
                                tiles.put(tile.key, tile);
                                break;

                            /*Roof Bottom L*/
                            case 18:
                                tile = new Tile(images.get(i), true, '/');
                                tiles.put(tile.key, tile);
                                break;

                            /*Roof Bottom C*/
                            case 19:
                                tile = new Tile(images.get(i), true, '_');
                                tiles.put(tile.key, tile);
                                break;

                            /*Roof Bottom R*/
                            case 20:
                                tile = new Tile(images.get(i), true, '\\');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Top L*/
                            case 21:
                                tile = new Tile(images.get(i), true, '1');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Top M*/
                            case 22:
                                tile = new Tile(images.get(i), true, '2');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Top R*/
                            case 23:
                                tile = new Tile(images.get(i), true, '3');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Middle L*/
                            case 24:
                                tile = new Tile(images.get(i), true, '4');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Middle M*/
                            case 25:
                                tile = new Tile(images.get(i), true, '5');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Middle R*/
                            case 26:
                                tile = new Tile(images.get(i), true, '6');
                                tiles.put(tile.key, tile);
                                break;


                            /*Building Bottom L*/
                            case 27:
                                tile = new Tile(images.get(i), true, '7');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Bottom M*/
                            case 28:
                                tile = new Tile(images.get(i), true, '8');
                                tiles.put(tile.key, tile);
                                break;

                            /*Building Bottom R*/
                            case 29:
                                tile = new Tile(images.get(i), true, '9');
                                tiles.put(tile.key, tile);
                                break;

                            /*Door*/
                            case 30:
                                tile = new Tile(images.get(i), true, 'D');
                                tiles.put(tile.key, tile);
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
