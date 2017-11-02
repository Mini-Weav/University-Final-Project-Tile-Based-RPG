package objects;

import utilities.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by lmweav on 26/10/2017.
 */
public class DoorTile extends Tile {
    public static HashMap<Point,Point> points;

    public DoorTile(BufferedImage img, char key) {
        super(img, false, key);
    }

    public static void initialisePoints() {
        points = new HashMap<>();
        points.put(new Point(3,4), new Point(43,9));
        points.put(new Point(43,10), new Point(3,5));
        points.put(new Point(44,10), new Point(3,5));
        points.put(new Point(17,4), new Point(50,29));
        points.put(new Point(50,30), new Point(17,5));
        points.put(new Point(51,30), new Point(17,5));
        points.put(new Point(5,16), new Point(3,44));
        points.put(new Point(3,45), new Point(5,17));
        points.put(new Point(4,45), new Point(5,17));
        points.put(new Point(15,16), new Point(21,44));
        points.put(new Point(21,45), new Point(15,17));
        points.put(new Point(22,45), new Point(15,17));
        points.put(new Point(9,16), new Point(52,51));
        points.put(new Point(10,16), new Point(53,51));
        points.put(new Point(52,50), new Point(9,17));
        points.put(new Point(53,50), new Point(10,17));
        points.put(new Point(58,50), new Point(90,8));
        points.put(new Point(90,9), new Point(58,51));
        points.put(new Point(91,9), new Point(58,51));
        points.put(new Point(47,50), new Point(75,8));
        points.put(new Point(75,9), new Point(47,51));
        points.put(new Point(76,9), new Point(47,51));
        points.put(new Point(10,0), new Point(98,42));
        points.put(new Point(98,43), new Point(10,1));
        points.put(new Point(99,43), new Point(10,1));

    }
}
