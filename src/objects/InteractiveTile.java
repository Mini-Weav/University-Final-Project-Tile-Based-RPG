package objects;

import utilities.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by lmweav on 01/11/2017.
 */
public class InteractiveTile extends Tile {
    public static HashMap<Point,String> points;

    public InteractiveTile(BufferedImage img, boolean collision, char key) {
        super(img, collision, key);
    }

    public static void initialisePoints() {
        points = new HashMap<>();
        //Signs
        points.put(new Point(11,0), "The sign says 'SCHOOL YARD'.");
        points.put(new Point(4,4), "The sign says 'FOOD\nTECHNOLOGY'.");
        points.put(new Point(18,4), "The sign says 'DESIGN\nTECHNOLOGY'.");
        points.put(new Point(6,16), "The sign says 'CANTEEN'.");
        points.put(new Point(16,16), "The sign says 'CANTEEN'.");
        points.put(new Point(54,41), "The sign says 'STAFF ROOM - \nSTUDENTS NOT PERMITTED BEYOND\nTHIS POINT!'.");
        points.put(new Point(48,50), "The sign says 'SCIENCE LAB'.");
        points.put(new Point(59,50), "The sign says 'COMPUTER LAB'.");
        //Food tech
        points.put(new Point(39,2), "This bookcase is filled with \nrecipe books.");
        points.put(new Point(40,2), "This bookcase is filled with \nrecipe books.");
        points.put(new Point(43,1), "The whiteboard says 'The way\nto someone's heart\nis through their stomach!'.");
        points.put(new Point(44,1), "The whiteboard says 'The way\nto someone's heart\nis through their stomach!'.");
        points.put(new Point(45,1), "The whiteboard says 'The way\nto someone's heart\nis through their stomach!'.");
        points.put(new Point(52,2), "The fridge is full of\ningredients.");
        points.put(new Point(53,2), "The fridge is full of\ningredients.");
        points.put(new Point(50,4), "I should wash up after\ncooking.");
        points.put(new Point(53,4), "I should wash up after\ncooking.");
        points.put(new Point(50,6), "I should wash up after\ncooking.");
        points.put(new Point(53,6), "I should wash up after\ncooking.");
        points.put(new Point(50,8), "I should wash up after\ncooking.");
        points.put(new Point(53,8), "I should wash up after\ncooking.");
        points.put(new Point(51,4), "I can can cook here if I\nattend food tech.");
        points.put(new Point(52,4), "I can can cook here if I\nattend food tech.");
        points.put(new Point(51,6), "I can can cook here if I\nattend food tech.");
        points.put(new Point(52,6), "I can can cook here if I\nattend food tech.");
        points.put(new Point(51,8), "I can can cook here if I\nattend food tech.");
        points.put(new Point(52,8), "I can can cook here if I\nattend food tech.");
        //DT
        points.put(new Point(40,22), "There are various tools\non this shelf.");
        points.put(new Point(41,22), "There are various tools\non this shelf.");
    }
}
