package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

/**
 * Reads text files for all in-game text.
 */
public class FileReader {
    private static String[] interactiveStrings;
    private static String[] npcStrings;
    private static String[] newDayStrings;
    private static String[] activityStrings;
    private static String[] lessonStrings;
    private static String[] menuStrings;
    private static String[] statusStrings;
    private static String[] resultStrings;
    private static String[] jackStrings = new String[3];
    private static String[] emilyStrings = new String[5];
    private static String[] alexanderStrings = new String[3];
    private static String[] nathanStrings = new String[3];
    private static String[] frankieStrings = new String[3];
    private static String[] boyStrings = new String[5];
    private static String[] girlStrings = new String[5];
    private static String[] lunchStrings = new String[3];

    public static String getInteractiveString(int index) { return interactiveStrings[index]; }

    public static String getNpcString(int index) { return npcStrings[index]; }

    public static String getNewDayString(int index) { return newDayStrings[index]; }

    public static String getActivityString(int index) { return activityStrings[index]; }

    public static String getLessonString(int index) { return lessonStrings[index]; }

    public static String getMenuString(int index) { return menuStrings[index]; }

    public static String[] getTimeStrings() { return Arrays.copyOf(statusStrings, 17); }
    public static String[] getConditionStrings() { return Arrays.copyOfRange(statusStrings, 17, 20); }

    public static String getResultString(int index) { return resultStrings[index]; }

    public static String[] getJackStrings() { return Arrays.copyOf(jackStrings, jackStrings.length); }

    public static String[] getEmilyStrings() { return Arrays.copyOf(emilyStrings, emilyStrings.length); }

    public static String[] getAlexanderStrings() { return Arrays.copyOf(alexanderStrings, alexanderStrings.length); }

    public static String[] getNathanStrings() { return Arrays.copyOf(nathanStrings, nathanStrings.length); }

    public static String[] getFrankieStrings() { return Arrays.copyOf(frankieStrings, frankieStrings.length); }

    public static String[] getBoyStrings() { return Arrays.copyOf(boyStrings, boyStrings.length); }

    public static String[] getGirlStrings() { return Arrays.copyOf(girlStrings, girlStrings.length); }

    public static String[] getLunchStrings() { return Arrays.copyOf(lunchStrings, lunchStrings.length); }

    /**
     * Reads the supplied file and puts the content into an array of Strings.
     *
     * @param file the name of the file to be read
     * @return the array created from the file
     */
    private static String[] readFile(String file) {
        String s;
        Scanner in = new Scanner(FileReader.class.getResourceAsStream("/texts/" + file + ".txt"));

        ArrayList<String> temp = new ArrayList<>();
        while (in.hasNext()) {
            s = in.nextLine();
            temp.add(s);
        }
        in.close();
        return temp.toArray(new String[0]);
    }

    /**
     * Reads all the text files used in the games and put the content into arrays.
     */
    public static void readFiles(CyclicBarrier barrier) {
        try {
            interactiveStrings = readFile("interactive_tiles");
            npcStrings = readFile("npcs");
            for (int i = 0; i < 3; i++) {
                jackStrings[i] = npcStrings[i];
                emilyStrings[i] = npcStrings[i + 4];
                alexanderStrings[i] = npcStrings[i + 8];
                nathanStrings[i] = npcStrings[i + 12];
                frankieStrings[i] = npcStrings[i + 16];
            }
            for (int i = 0; i < 5; i++) {
                boyStrings[i] = npcStrings[i + 20];
                girlStrings[i] = npcStrings[i + 26];
            }
            emilyStrings[3] = npcStrings[37];
            emilyStrings[4] = npcStrings[38];
            System.arraycopy(npcStrings, 32, lunchStrings, 0, 3);
            newDayStrings = readFile("new_day_feedback");
            activityStrings = readFile("activities");
            lessonStrings = readFile("lessons");
            menuStrings = readFile("menu");
            statusStrings = readFile("status");
            resultStrings = readFile("results");
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
