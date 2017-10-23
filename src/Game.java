import java.io.File;
import java.util.List;

/**
 * Created by Luke on 23/10/2017.
 */
public class Game {
    public static void main(String[] args) {
//        List<List<Character>> matrix = TileMapEngine.readMap(new File("resources/test.txt"));
//        matrix.forEach(System.out::println);
        new JEasyFrame(new TileMap("test","classroom"),"test");

    }
}
