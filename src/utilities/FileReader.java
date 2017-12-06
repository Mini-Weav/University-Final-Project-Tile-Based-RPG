package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by lmweav on 29/11/2017.
 */
public class FileReader {
    public static String[] interactiveStrings;

    public static String[] readFile(String file) {
        String s;
        try {
            Scanner in = new Scanner(new File("resources/texts/" + file + ".txt"));

            ArrayList<String> temp = new ArrayList<>();
            while (in.hasNext()) {
                s = in.nextLine();
                temp.add(s);
            }
            in.close();
            return temp.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Cannot open text file");
            return null;
        }
    }

    public static void readFiles() {
        interactiveStrings = readFile("interactive_tiles");
    }
}
