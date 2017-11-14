package objects;

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

    public static HashMap<Point,String> initialisePoints(int mapID) {
        points = new HashMap<>();

        switch (mapID) {

            /*School Hall G*/
            case 0:
                points.put(new Point(11, 0), "The sign says 'SCHOOL\nYARD'.");
                points.put(new Point(4, 4), "The sign says 'DESIGN\nTECHNOLOGY'.");
                points.put(new Point(18, 4), "The sign says 'FOOD\nTECHNOLOGY'.");
                points.put(new Point(6, 16), "The sign says 'CANTEEN'.");
                points.put(new Point(16, 16), "The sign says 'CANTEEN'.");
                break;

            /*School Hall 1F*/
            case 1:
                points.put(new Point(11, 1), "The sign says 'STAFF ROOM -\nSTUDENTS NOT PERMITTED\nBEYOND THIS POINT!'.");
                points.put(new Point(2, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(3, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(4, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(5, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(14, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(15, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(16, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(17, 1), "I can see the school yard\nfrom here.");
                points.put(new Point(5, 10), "The sign says 'SCIENCE\nLAB'.");
                points.put(new Point(16, 10), "The sign says 'COMPUTER\nLAB'.");
                break;

            /*Design Tech Classroom*/
            case 2:
                points.put(new Point(2, 2), "There are various tools on\nthis shelf.");
                points.put(new Point(3, 2), "There are various tools on\nthis shelf.");
                points.put(new Point(2, 4), "I can craft items here if I\nattend DT.");
                points.put(new Point(3, 4), "I can craft items here if I\nattend DT.");
                points.put(new Point(2, 5), "I can craft items here if I\nattend DT.");
                points.put(new Point(3, 5), "I can craft items here if I\nattend DT.");
                points.put(new Point(4, 7), "I can craft items here if I\nattend DT.");
                points.put(new Point(5, 7), "I can craft items here if I\nattend DT.");
                points.put(new Point(4, 8), "I can craft items here if I\nattend DT.");
                points.put(new Point(5, 8), "I can craft items here if I\nattend DT.");
                points.put(new Point(10, 1), "The whiteboard says 'You\nwill make cool things if\nyou study Design\nTechnology!'.");
                points.put(new Point(11, 1), "The whiteboard says 'You\nwill make cool things if\nyou study Design\nTechnology!'.");
                points.put(new Point(12, 1), "The whiteboard says 'You\nwill make cool things if\nyou study Design\nTechnology!'.");
                points.put(new Point(15, 2), "This bookcase is filled\nwith construction manuals.");
                points.put(new Point(16, 2), "This bookcase is filled\nwith construction manuals.");
                break;

            /*Food Tech Classroom*/
            case 3:
                points.put(new Point(1, 2), "This bookcase is filled\nwith recipe books.");
                points.put(new Point(2, 2), "This bookcase is filled\nwith recipe books.");
                points.put(new Point(5, 1), "The whiteboard says 'Study\nFood Technology to cook\ntasty food!'.");
                points.put(new Point(6, 1), "The whiteboard says 'Study\nFood Technology to cook\ntasty food!'.");
                points.put(new Point(7, 1), "The whiteboard says 'Study\nFood Technology to cook\ntasty food!'.");
                points.put(new Point(14, 2), "The fridge is full of\ningredients.");
                points.put(new Point(15, 2), "The fridge is full of\ningredients.");
                points.put(new Point(12, 4), "I should wash up after\ncooking.");
                points.put(new Point(15, 4), "I should wash up after\ncooking.");
                points.put(new Point(12, 6), "I should wash up after\ncooking.");
                points.put(new Point(15, 6), "I should wash up after\ncooking.");
                points.put(new Point(12, 8), "I should wash up after\ncooking.");
                points.put(new Point(15, 8), "I should wash up after\ncooking.");
                points.put(new Point(13, 4), "I can cook here if I attend\nfood tech.");
                points.put(new Point(14, 4), "I can cook here if I attend\nfood tech.");
                points.put(new Point(13, 6), "I can cook here if I attend\nfood tech.");
                points.put(new Point(14, 6), "I can cook here if I atten\nfood tech.");
                points.put(new Point(13, 8), "I can cook here if I attend\nfood tech.");
                points.put(new Point(14, 8), "I can cook here if I attend\nfood tech.");
                break;

            /*1F Classrooms*/
            case 4:
                points.put(new Point(1, 2), "This bookcase is filled\nwith science text books.");
                points.put(new Point(2, 2), "This bookcase is filled\nwith science text books.");
                points.put(new Point(6, 1), "The whiteboard says 'Study\nScience and you will become\nsmarter!'.");
                points.put(new Point(7, 1), "The whiteboard says 'Study\nScience and you will become\nsmarter!'.");
                points.put(new Point(8, 1), "The whiteboard says 'Study\nScience and you will become\nsmarter!'.");
                points.put(new Point(5, 4), "I should remember to wash\nmy hands after working with\nchemicals!");
                points.put(new Point(5, 6), "I should remember to wash\nmy hands after working with\nchemicals!");
                points.put(new Point(13, 1), "The whiteboard says 'Become\na computer genius with\nICT!'.");
                points.put(new Point(14, 1), "The whiteboard says 'Become\na computer genius with\nICT!'.");
                points.put(new Point(15, 1), "The whiteboard says 'Become\na computer genius with\nICT!'.");
                points.put(new Point(19, 2), "This bookcase is filled\nwith user manuals for\nvarious software.");
                points.put(new Point(20, 2), "This bookcase is filled\nwith user manuals for\nvarious software.");
                points.put(new Point(12, 4), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(13, 4), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(14, 4), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(15, 4), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(16, 4), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(17, 4), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(12, 6), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(13, 6), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(14, 6), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(15, 6), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(16, 6), "I can improve my computer\nproficiency if I attend\nICT.");
                points.put(new Point(17, 6), "I can improve my computer\nproficiency if I attend\nICT.");

                break;

            /*Canteen*/
            case 5:

                break;

            /*Yard*/
            case 6:
                points.put(new Point(21, 17), "The sign says 'Come to PE\nto get good at sports!'.");
                break;
        }
        return points;

    }
}
