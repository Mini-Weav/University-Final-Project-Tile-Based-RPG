package objects;

import utilities.FileReader;
import utilities.TextBox;
import utilities.TileMapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Created by lmweav on 01/11/2017.
 */
public class InteractiveTile extends Tile {
    public static HashMap<Point, TextBox> points;

    public boolean menu;

    public InteractiveTile(BufferedImage img, boolean collision, char key, boolean menu) {
        super(img, collision, key);
        this.menu = menu;
    }

    public static HashMap<Point, TextBox> initialisePoints(int mapID) {
        points = new HashMap<>();

        switch (mapID) {

            /*School Hall G*/
            case 0:
                points.put(new Point(11, 1), new TextBox(0, FileReader.interactiveStrings[3])); //4
                points.put(new Point(4, 5), new TextBox(0, FileReader.interactiveStrings[4])); //5
                points.put(new Point(7, 5), new TextBox(0, FileReader.interactiveStrings[5])); //6
                points.put(new Point(18, 5), new TextBox(0, FileReader.interactiveStrings[6])); //7
                points.put(new Point(6, 17), new TextBox(0, FileReader.interactiveStrings[7])); //8
                points.put(new Point(16, 17), new TextBox(0, FileReader.interactiveStrings[7])); //8
                break;

            /*School Hall 1F*/
            case 1:
                points.put(new Point(11, 1), new TextBox(0, FileReader.interactiveStrings[8])); //9
                points.put(new Point(2, 1), new TextBox(0, FileReader.interactiveStrings[9])); //10
                points.put(new Point(3, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(4, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(5, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(14, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(15, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(16, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(17, 1), new TextBox(0, FileReader.interactiveStrings[9]));
                points.put(new Point(5, 10), new TextBox(0, FileReader.interactiveStrings[10])); //11
                points.put(new Point(16, 10), new TextBox(0, FileReader.interactiveStrings[11]));//12
                break;

            /*Design Tech Classroom*/
            case 2:
                points.put(new Point(2, 2), new TextBox(0, FileReader.interactiveStrings[12]));//13
                points.put(new Point(3, 2), new TextBox(0, FileReader.interactiveStrings[12]));
                points.put(new Point(2, 4), new TextBox(0, FileReader.interactiveStrings[13]));//14
                points.put(new Point(3, 4), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(2, 5), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(3, 5), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(4, 7), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(5, 7), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(4, 8), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(5, 8), new TextBox(0, FileReader.interactiveStrings[13]));
                points.put(new Point(10, 1), new TextBox(6, FileReader.interactiveStrings[1]));//2
                points.put(new Point(11, 1), new TextBox(6, FileReader.interactiveStrings[1]));
                points.put(new Point(12, 1), new TextBox(6, FileReader.interactiveStrings[1]));
                points.put(new Point(15, 2), new TextBox(0, FileReader.interactiveStrings[14]));//15
                points.put(new Point(16, 2), new TextBox(0, FileReader.interactiveStrings[14]));
                break;

            /*Food Tech Classroom*/
            case 3:
                points.put(new Point(1, 2), new TextBox(0, FileReader.interactiveStrings[15]));//16
                points.put(new Point(2, 2), new TextBox(0, FileReader.interactiveStrings[15]));
                points.put(new Point(5, 1), new TextBox(6, FileReader.interactiveStrings[1]));//2
                points.put(new Point(6, 1), new TextBox(6, FileReader.interactiveStrings[1]));
                points.put(new Point(7, 1), new TextBox(6, FileReader.interactiveStrings[1]));
                points.put(new Point(14, 2), new TextBox(0, FileReader.interactiveStrings[16]));//17
                points.put(new Point(15, 2), new TextBox(0, FileReader.interactiveStrings[16]));
                points.put(new Point(12, 4), new TextBox(0, FileReader.interactiveStrings[17]));//18
                points.put(new Point(15, 4), new TextBox(0, FileReader.interactiveStrings[17]));
                points.put(new Point(12, 6), new TextBox(0, FileReader.interactiveStrings[17]));
                points.put(new Point(15, 6), new TextBox(0, FileReader.interactiveStrings[17]));
                points.put(new Point(12, 8), new TextBox(0, FileReader.interactiveStrings[17]));
                points.put(new Point(15, 8), new TextBox(0, FileReader.interactiveStrings[17]));
                points.put(new Point(13, 4), new TextBox(0, FileReader.interactiveStrings[18]));//19
                points.put(new Point(14, 4), new TextBox(0, FileReader.interactiveStrings[18]));
                points.put(new Point(13, 6), new TextBox(0, FileReader.interactiveStrings[18]));
                points.put(new Point(14, 6), new TextBox(0, FileReader.interactiveStrings[18]));
                points.put(new Point(13, 8), new TextBox(0, FileReader.interactiveStrings[18]));
                points.put(new Point(14, 8), new TextBox(0, FileReader.interactiveStrings[18]));
                break;

            /*1F Classrooms*/
            case 4:
                points.put(new Point(1, 2), new TextBox(0, FileReader.interactiveStrings[19]));//20
                points.put(new Point(2, 2), new TextBox(0, FileReader.interactiveStrings[19]));
                points.put(new Point(6, 1), new TextBox(1, FileReader.interactiveStrings[38]));//1
                points.put(new Point(7, 1), new TextBox(1, FileReader.interactiveStrings[0]));
                points.put(new Point(8, 1), new TextBox(1, FileReader.interactiveStrings[0]));
                points.put(new Point(5, 4), new TextBox(0, FileReader.interactiveStrings[20]));//21
                points.put(new Point(5, 6), new TextBox(0, FileReader.interactiveStrings[20]));
                points.put(new Point(13, 1), new TextBox(1, FileReader.interactiveStrings[0]));//1
                points.put(new Point(14, 1), new TextBox(1, FileReader.interactiveStrings[0]));
                points.put(new Point(15, 1), new TextBox(1, FileReader.interactiveStrings[0]));
                points.put(new Point(19, 2), new TextBox(0, FileReader.interactiveStrings[21]));//22
                points.put(new Point(20, 2), new TextBox(0, FileReader.interactiveStrings[21]));
                points.put(new Point(12, 4), new TextBox(0, FileReader.interactiveStrings[22]));//23
                points.put(new Point(13, 4), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(14, 4), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(15, 4), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(16, 4), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(17, 4), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(12, 6), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(13, 6), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(14, 6), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(15, 6), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(16, 6), new TextBox(0, FileReader.interactiveStrings[22]));
                points.put(new Point(17, 6), new TextBox(0, FileReader.interactiveStrings[22]));
                break;

            /*Canteen*/
            case 5:

                break;

            /*Yard*/
            case 6:
                points.put(new Point(26, 22), new TextBox(7, FileReader.interactiveStrings[2]));//3
                break;

            /*Bedroom*/
            case 7:
                points.put(new Point(1, 2), new TextBox(3, FileReader.interactiveStrings[23]));
                points.put(new Point(3, 3), new TextBox(3, FileReader.interactiveStrings[24]));
                points.put(new Point(1, 4), new TextBox(3, FileReader.interactiveStrings[25]));
                points.put(new Point(1, 5), new TextBox(3, FileReader.interactiveStrings[25]));
                points.put(new Point(4, 2), new TextBox(0, FileReader.interactiveStrings[26]));
                points.put(new Point(6, 1), new TextBox(0, FileReader.interactiveStrings[27]));
                break;
            case 8:
                points.put(new Point(5, 1), new TextBox(0, FileReader.interactiveStrings[28]));
                points.put(new Point(6, 2), new TextBox(3, FileReader.interactiveStrings[29]));
                points.put(new Point(13, 8), new TextBox(0, FileReader.interactiveStrings[30]));
                points.put(new Point(1, 2), new TextBox(0, FileReader.interactiveStrings[31]));
                points.put(new Point(2, 2), new TextBox(0, FileReader.interactiveStrings[31]));
                points.put(new Point(14, 8), new TextBox(0, FileReader.interactiveStrings[31]));
                points.put(new Point(7, 13), new TextBox(0, FileReader.interactiveStrings[32]));
                points.put(new Point(5, 13), new TextBox(0, FileReader.interactiveStrings[33]));
                points.put(new Point(6, 13), new TextBox(0, FileReader.interactiveStrings[34]));
                points.put(new Point(2, 7), new TextBox(0, FileReader.interactiveStrings[35]));
        }
        return points;
    }
}
