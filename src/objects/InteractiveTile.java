package objects;

import utilities.TextBox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by lmweav on 01/11/2017.
 */
public class InteractiveTile extends Tile {
    public static HashMap<Point, TextBox> points;
    public static String rules1 = "1 Answer questions in this\n  class.\n" +
            "2 You need to have a certain\n  concentration to answer\n  questions to a good standard.\n" +
            "3 Different types of questions\n  require different levels\n  of concentration.\n" +
            "4 Improve your grade to\n  increase your base\n  concentration level.\n" +
            "5 Concentrating reduces your\n  attention span.\n" +
            "6 Answering a question resets\n  your concentration.\n" +
            "7 Have a break to raise your\n  attention span.\n" +
            "8 However, you are only allowed\n  one break in a lesson... And\n  you will miss a question if\n  you take a break!\n" +
            "9 Drinking an energy drink\n  increases your concentration\n  and attention span!\n",
            rules2 = "1 Complete tasks in this class.\n" +
                    "2 You only have 60 minutes to\n  complete the task sheet.\n" +
                    "3 Rereading instructions and\n  doing a task meticulously\n  improves the standard of\n  your work... but doing a task\n" +
                    "  meticulously takes longer!\n" +
                    "4 Improve your grade to\n  increase the chance you\n  understand the  instruction.\n",
            rules3 = "1 Run laps in PE.\n" +
                    "2 Try and run as many laps in\n  60 minutes.\n" +
                    "3 Warming up and resting\n  increases your energy.\n" +
                    "4 Drinking an energy drink\n  increases your energy and\n  takes no time!\n" +
                    "5 Running and sprinting uses\n  energy.\n" +
                    "6 Sprinting uses more energy\n  than running.\n" +
                    "7 Run or sprint consecutively\n  to get extra credit!\n";

    public InteractiveTile(BufferedImage img, boolean collision, char key) {
        super(img, collision, key);
    }

    public static HashMap<Point, TextBox> initialisePoints(int mapID) {
        points = new HashMap<>();

        switch (mapID) {

            /*School Hall G*/
            case 0:
                points.put(new Point(11, 1), new TextBox(0, "The sign says 'SCHOOL\nYARD'."));
                points.put(new Point(4, 5), new TextBox(0, "The sign says 'DESIGN\nTECHNOLOGY'."));
                points.put(new Point(7, 5), new TextBox(0, "It's an air vent..."));
                points.put(new Point(18, 6), new TextBox(0, "The sign says 'FOOD\nTECHNOLOGY'."));
                points.put(new Point(6, 17), new TextBox(0, "The sign says 'CANTEEN'."));
                points.put(new Point(16, 17), new TextBox(0, "The sign says 'CANTEEN'."));
                break;

            /*School Hall 1F*/
            case 1:
                points.put(new Point(11, 1), new TextBox(0, "The sign says 'STAFF ROOM -\nSTUDENTS NOT PERMITTED\nBEYOND THIS POINT!'."));
                points.put(new Point(2, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(3, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(4, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(5, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(14, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(15, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(16, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(17, 1), new TextBox(0, "I can see the school yard\nfrom here."));
                points.put(new Point(5, 10), new TextBox(0, "The sign says 'SCIENCE\nLAB'."));
                points.put(new Point(16, 10), new TextBox(0, "The sign says 'COMPUTER\nLAB'."));
                break;

            /*Design Tech Classroom*/
            case 2:
                points.put(new Point(2, 2), new TextBox(0, "There are various tools on\nthis shelf."));
                points.put(new Point(3, 2), new TextBox(0, "There are various tools on\nthis shelf."));
                points.put(new Point(2, 4), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(3, 4), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(2, 5), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(3, 5), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(4, 7), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(5, 7), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(4, 8), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(5, 8), new TextBox(0, "I can craft items here if I\nattend DT."));
                points.put(new Point(10, 1), new TextBox(1, rules2));
                points.put(new Point(11, 1), new TextBox(1, rules2));
                points.put(new Point(12, 1), new TextBox(1, rules2));
                points.put(new Point(15, 2), new TextBox(0, "This bookcase is filled\nwith construction manuals."));
                points.put(new Point(16, 2), new TextBox(0, "This bookcase is filled\nwith construction manuals."));
                break;

            /*Food Tech Classroom*/
            case 3:
                points.put(new Point(1, 2), new TextBox(0, "This bookcase is filled\nwith recipe books."));
                points.put(new Point(2, 2), new TextBox(0, "This bookcase is filled\nwith recipe books."));
                points.put(new Point(5, 1), new TextBox(1, rules2));
                points.put(new Point(6, 1), new TextBox(1, rules2));
                points.put(new Point(7, 1), new TextBox(1, rules2));
                points.put(new Point(14, 2), new TextBox(0, "The fridge is full of\ningredients."));
                points.put(new Point(15, 2), new TextBox(0, "The fridge is full of\ningredients."));
                points.put(new Point(12, 4), new TextBox(0, "I should wash up after\ncooking."));
                points.put(new Point(15, 4), new TextBox(0, "I should wash up after\ncooking."));
                points.put(new Point(12, 6), new TextBox(0, "I should wash up after\ncooking."));
                points.put(new Point(15, 6), new TextBox(0, "I should wash up after\ncooking."));
                points.put(new Point(12, 8), new TextBox(0, "I should wash up after\ncooking."));
                points.put(new Point(15, 8), new TextBox(0, "I should wash up after\ncooking."));
                points.put(new Point(13, 4), new TextBox(0, "I can cook here if I attend\nfood tech."));
                points.put(new Point(14, 4), new TextBox(0, "I can cook here if I attend\nfood tech."));
                points.put(new Point(13, 6), new TextBox(0, "I can cook here if I attend\nfood tech."));
                points.put(new Point(14, 6), new TextBox(0, "I can cook here if I atten\nfood tech."));
                points.put(new Point(13, 8), new TextBox(0, "I can cook here if I attend\nfood tech."));
                points.put(new Point(14, 8), new TextBox(0, "I can cook here if I attend\nfood tech."));
                break;

            /*1F Classrooms*/
            case 4:
                points.put(new Point(1, 2), new TextBox(0, "This bookcase is filled\nwith science text books."));
                points.put(new Point(2, 2), new TextBox(0, "This bookcase is filled\nwith science text books."));
                points.put(new Point(6, 1), new TextBox(1, rules1));
                points.put(new Point(7, 1), new TextBox(1, rules1));
                points.put(new Point(8, 1), new TextBox(1, rules1));
                points.put(new Point(5, 4), new TextBox(0, "I should remember to wash\nmy hands after working with\nchemicals!"));
                points.put(new Point(5, 6), new TextBox(0, "I should remember to wash\nmy hands after working with\nchemicals!"));
                points.put(new Point(13, 1), new TextBox(1, rules1));
                points.put(new Point(14, 1), new TextBox(1, rules1));
                points.put(new Point(15, 1), new TextBox(1, rules1));
                points.put(new Point(19, 2), new TextBox(0, "This bookcase is filled\nwith user manuals for\nvarious software."));
                points.put(new Point(20, 2), new TextBox(0, "This bookcase is filled\nwith user manuals for\nvarious software."));
                points.put(new Point(12, 4), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(13, 4), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(14, 4), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(15, 4), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(16, 4), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(17, 4), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(12, 6), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(13, 6), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(14, 6), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(15, 6), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(16, 6), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));
                points.put(new Point(17, 6), new TextBox(0, "I can improve my computer\nproficiency if I attend\nICT."));

                break;

            /*Canteen*/
            case 5:

                break;

            /*Yard*/
            case 6:
                points.put(new Point(21, 17), new TextBox(1, rules3));
                break;
        }
        return points;

    }
}
