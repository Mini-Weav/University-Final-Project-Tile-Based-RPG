package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by lmweav on 29/11/2017.
 */
public class FileReader {
    public static String[] interactiveStrings, npcStrings, newDayStrings,
            jackStrings = new String[5], emilyStrings = new String[5], alexanderStrings = new String[5],
            nathanStrings = new String[5], frankieStrings = new String[5], boyStrings = new String[5],
            girlStrings = new String[5], lunchStrings = new String[3];

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
        npcStrings = readFile("npcs");
        for (int i = 0; i < 5; i++) {
            jackStrings[i] = npcStrings[i];
            emilyStrings[i] = npcStrings[i + 6];
            alexanderStrings[i] = npcStrings[i + 12];
            nathanStrings[i] = npcStrings[i + 18];
            frankieStrings[i] = npcStrings[i + 24];
            boyStrings[i] = npcStrings[i + 30];
            girlStrings[i] = npcStrings[i + 36];
        }
        System.arraycopy(npcStrings, 42, lunchStrings, 0, 3);
        newDayStrings = readFile("new_day_feedback");
    }
}
