import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmweav on 23/10/2017.
 */
public class TileMapEngine {

    public static List<List<Character>> readMap(File txtFile) {
        String line;
        List<List<Character>> matrix = new ArrayList<>();

        FileReader fr;
        try {
            fr = new FileReader(txtFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("cannot find file");
            e.printStackTrace();
            return null;
        }
        BufferedReader br = new BufferedReader(fr);

        try {
            while ( (line = br.readLine()) != null ) {
                List<Character> row = new ArrayList<>();

                for (char ch : line.toCharArray()) {
                    row.add(ch);
                }
                matrix.add(row);

            }
        }
        catch (IOException e) {
            System.out.println("cannot read line");
            e.printStackTrace();
        }

        return matrix;
    }

}
