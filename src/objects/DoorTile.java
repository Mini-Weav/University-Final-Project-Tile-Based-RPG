package objects;

import utilities.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Stores each of the game map's door points.
 */
public class DoorTile extends Tile {

    /**
     * Class constructor.
     *
     * @param img the image of the DoorTile
     * @param key the key of the DoorTile (for when stored in a matrix)
     */
    public DoorTile(BufferedImage img, char key) {
        super(img, false, key);
    }

    /**
     * Initialises the co-ordinates of a map's door points.
     *
     * @param mapID the identifier of the map
     * @return the map of door points
     */
    public static HashMap<Point,Pair<Integer, Point>> initialisePoints(int mapID) {
        HashMap<Point, Pair<Integer, Point>> points = new HashMap<>();

        switch (mapID) {

            /* School Hall G */
            case 0:
                points.put(new Point(10, 1), new Pair<>(6, new Point(30, 28)));
                points.put(new Point(3, 5), new Pair<>(2, new Point(13, 9)));
                points.put(new Point(17, 5), new Pair<>(3, new Point(5, 9)));
                points.put(new Point(5, 17), new Pair<>(5, new Point(3, 9)));
                points.put(new Point(9, 17), new Pair<>(1, new Point(9, 12)));
                points.put(new Point(10, 17), new Pair<>(1, new Point(10, 12)));
                points.put(new Point(15, 17), new Pair<>(5, new Point(22, 9)));
                break;

            /* School Hall 1F */
            case 1:
                points.put(new Point(4, 10), new Pair<>(4, new Point(2, 8)));
                points.put(new Point(9, 13), new Pair<>(0, new Point(9, 18)));
                points.put(new Point(10, 13), new Pair<>(0, new Point(10, 18)));
                points.put(new Point(15, 10), new Pair<>(4, new Point(18, 8)));
                points.put(new Point(9, 1), new Pair<>(8, new Point(14, 16)));
                points.put(new Point(10, 1), new Pair<>(8, new Point(15, 16)));
                break;

            /* Design Tech Classroom */
            case 2:
                points.put(new Point(12, 10), new Pair<>(0, new Point(3, 6)));
                points.put(new Point(13, 10), new Pair<>(0, new Point(3, 6)));
                break;

            /* Food Tech Classroom */
            case 3:
                points.put(new Point(5, 10), new Pair<>(0, new Point(17, 6)));
                points.put(new Point(6, 10), new Pair<>(0, new Point(17, 6)));
                break;

            /* 1F Classrooms */
            case 4:
                points.put(new Point(2, 9), new Pair<>(1, new Point(4, 11)));
                points.put(new Point(3, 9), new Pair<>(1, new Point(4, 11)));
                points.put(new Point(18, 9), new Pair<>(1, new Point(15, 11)));
                points.put(new Point(19, 9), new Pair<>(1, new Point(15, 11)));
                break;

            /* Canteen */
            case 5:
                points.put(new Point(3, 10), new Pair<>(0, new Point(5, 18)));
                points.put(new Point(4, 10), new Pair<>(0, new Point(5, 18)));
                points.put(new Point(21, 10), new Pair<>(0, new Point(15, 18)));
                points.put(new Point(22, 10), new Pair<>(0, new Point(15, 18)));
                break;

            /* Yard */
            case 6:
                points.put(new Point(30, 29), new Pair<>(0, new Point(10, 2)));
                points.put(new Point(31, 29), new Pair<>(0, new Point(10, 2)));
                break;

            /* Staffroom */
            case 8:
                points.put(new Point(14, 17), new Pair<>(1, new Point(9, 2)));
                points.put(new Point(15, 17), new Pair<>(1, new Point(10, 2)));
        }
        return points;
    }
}
