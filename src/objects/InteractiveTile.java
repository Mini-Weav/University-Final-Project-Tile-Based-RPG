package objects;

import utilities.FileReader;
import utilities.TextBox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * 01/11/2017.
 */
public class InteractiveTile extends Tile {
    private boolean menu;

    public InteractiveTile(BufferedImage img, boolean collision, char key, boolean menu) {
        super(img, collision, key);
        this.menu = menu;
    }

    public boolean isMenu() { return menu; }

    public static HashMap<Point, TextBox> initialisePoints(int mapID) {
        HashMap<Point, TextBox> points = new HashMap<>();

        switch (mapID) {

            /*School Hall G*/
            case 0:
                points.put(new Point(11, 1), new TextBox(0, FileReader.getInteractiveString(3))); //4
                points.put(new Point(4, 5), new TextBox(0, FileReader.getInteractiveString(4))); //5
                points.put(new Point(7, 5), new TextBox(0, FileReader.getInteractiveString(5))); //6
                points.put(new Point(18, 5), new TextBox(0, FileReader.getInteractiveString(6))); //7
                points.put(new Point(6, 17), new TextBox(0, FileReader.getInteractiveString(7))); //8
                points.put(new Point(16, 17), new TextBox(0, FileReader.getInteractiveString(7))); //8
                break;

            /*School Hall 1F*/
            case 1:
                points.put(new Point(11, 1), new TextBox(0, FileReader.getInteractiveString(8))); //9
                points.put(new Point(2, 1), new TextBox(0, FileReader.getInteractiveString(9))); //10
                points.put(new Point(3, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(4, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(5, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(14, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(15, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(16, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(17, 1), new TextBox(0, FileReader.getInteractiveString(9)));
                points.put(new Point(5, 10), new TextBox(0, FileReader.getInteractiveString(10))); //11
                points.put(new Point(16, 10), new TextBox(0, FileReader.getInteractiveString(11)));//12
                break;

            /*Design Tech Classroom*/
            case 2:
                points.put(new Point(2, 2), new TextBox(0, FileReader.getInteractiveString(12)));//13
                points.put(new Point(3, 2), new TextBox(0, FileReader.getInteractiveString(12)));
                points.put(new Point(2, 4), new TextBox(0, FileReader.getInteractiveString(13)));//14
                points.put(new Point(3, 4), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(2, 5), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(3, 5), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(4, 7), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(5, 7), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(4, 8), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(5, 8), new TextBox(0, FileReader.getInteractiveString(13)));
                points.put(new Point(10, 1), new TextBox(6, FileReader.getInteractiveString(1)));//2
                points.put(new Point(11, 1), new TextBox(6, FileReader.getInteractiveString(1)));
                points.put(new Point(12, 1), new TextBox(6, FileReader.getInteractiveString(1)));
                points.put(new Point(15, 2), new TextBox(0, FileReader.getInteractiveString(14)));//15
                points.put(new Point(16, 2), new TextBox(0, FileReader.getInteractiveString(14)));
                break;

            /*Food Tech Classroom*/
            case 3:
                points.put(new Point(1, 2), new TextBox(0, FileReader.getInteractiveString(15)));//16
                points.put(new Point(2, 2), new TextBox(0, FileReader.getInteractiveString(15)));
                points.put(new Point(5, 1), new TextBox(6, FileReader.getInteractiveString(1)));//2
                points.put(new Point(6, 1), new TextBox(6, FileReader.getInteractiveString(1)));
                points.put(new Point(7, 1), new TextBox(6, FileReader.getInteractiveString(1)));
                points.put(new Point(14, 2), new TextBox(0, FileReader.getInteractiveString(16)));//17
                points.put(new Point(15, 2), new TextBox(0, FileReader.getInteractiveString(16)));
                points.put(new Point(12, 4), new TextBox(0, FileReader.getInteractiveString(17)));//18
                points.put(new Point(15, 4), new TextBox(0, FileReader.getInteractiveString(17)));
                points.put(new Point(12, 6), new TextBox(0, FileReader.getInteractiveString(17)));
                points.put(new Point(15, 6), new TextBox(0, FileReader.getInteractiveString(17)));
                points.put(new Point(12, 8), new TextBox(0, FileReader.getInteractiveString(17)));
                points.put(new Point(15, 8), new TextBox(0, FileReader.getInteractiveString(17)));
                points.put(new Point(13, 4), new TextBox(0, FileReader.getInteractiveString(18)));//19
                points.put(new Point(14, 4), new TextBox(0, FileReader.getInteractiveString(18)));
                points.put(new Point(13, 6), new TextBox(0, FileReader.getInteractiveString(18)));
                points.put(new Point(14, 6), new TextBox(0, FileReader.getInteractiveString(18)));
                points.put(new Point(13, 8), new TextBox(0, FileReader.getInteractiveString(18)));
                points.put(new Point(14, 8), new TextBox(0, FileReader.getInteractiveString(18)));
                break;

            /*1F Classrooms*/
            case 4:
                points.put(new Point(1, 2), new TextBox(0, FileReader.getInteractiveString(19)));//20
                points.put(new Point(2, 2), new TextBox(0, FileReader.getInteractiveString(19)));
                points.put(new Point(6, 1), new TextBox(1, FileReader.getInteractiveString(38)));//1
                points.put(new Point(7, 1), new TextBox(1, FileReader.getInteractiveString(0)));
                points.put(new Point(8, 1), new TextBox(1, FileReader.getInteractiveString(0)));
                points.put(new Point(5, 4), new TextBox(0, FileReader.getInteractiveString(20)));//21
                points.put(new Point(5, 6), new TextBox(0, FileReader.getInteractiveString(20)));
                points.put(new Point(13, 1), new TextBox(1, FileReader.getInteractiveString(0)));//1
                points.put(new Point(14, 1), new TextBox(1, FileReader.getInteractiveString(0)));
                points.put(new Point(15, 1), new TextBox(1, FileReader.getInteractiveString(0)));
                points.put(new Point(19, 2), new TextBox(0, FileReader.getInteractiveString(21)));//22
                points.put(new Point(20, 2), new TextBox(0, FileReader.getInteractiveString(21)));
                points.put(new Point(12, 4), new TextBox(0, FileReader.getInteractiveString(22)));//23
                points.put(new Point(13, 4), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(14, 4), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(15, 4), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(16, 4), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(17, 4), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(12, 6), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(13, 6), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(14, 6), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(15, 6), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(16, 6), new TextBox(0, FileReader.getInteractiveString(22)));
                points.put(new Point(17, 6), new TextBox(0, FileReader.getInteractiveString(22)));
                break;

            /*Canteen*/
            case 5:

                break;

            /*Yard*/
            case 6:
                points.put(new Point(26, 22), new TextBox(7, FileReader.getInteractiveString(2)));//3
                break;

            /*Bedroom*/
            case 7:
                points.put(new Point(1, 2), new TextBox(3, FileReader.getInteractiveString(23)));
                points.put(new Point(3, 3), new TextBox(3, FileReader.getInteractiveString(24)));
                points.put(new Point(1, 4), new TextBox(3, FileReader.getInteractiveString(25)));
                points.put(new Point(1, 5), new TextBox(3, FileReader.getInteractiveString(25)));
                points.put(new Point(4, 2), new TextBox(0, FileReader.getInteractiveString(26)));
                points.put(new Point(6, 1), new TextBox(0, FileReader.getInteractiveString(27)));
                break;
            case 8:
                points.put(new Point(5, 1), new TextBox(0, FileReader.getInteractiveString(28)));
                points.put(new Point(6, 2), new TextBox(3, FileReader.getInteractiveString(29)));
                points.put(new Point(13, 8), new TextBox(0, FileReader.getInteractiveString(30)));
                points.put(new Point(1, 2), new TextBox(0, FileReader.getInteractiveString(31)));
                points.put(new Point(2, 2), new TextBox(0, FileReader.getInteractiveString(31)));
                points.put(new Point(14, 8), new TextBox(0, FileReader.getInteractiveString(31)));
                points.put(new Point(7, 13), new TextBox(0, FileReader.getInteractiveString(32)));
                points.put(new Point(5, 13), new TextBox(0, FileReader.getInteractiveString(33)));
                points.put(new Point(6, 13), new TextBox(0, FileReader.getInteractiveString(34)));
                points.put(new Point(2, 7), new TextBox(0, FileReader.getInteractiveString(35)));
        }
        return points;
    }
}
