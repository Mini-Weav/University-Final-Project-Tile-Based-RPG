import java.util.List;

/**
 * Created by lmweav on 26/10/2017.
 */
public class Camera {
    public static int sizeX = TileMap.FRAME_WIDTH/32, sizeY = TileMap.FRAME_WIDTH/32;
    public int x, y, maxX, maxY;

    public Camera(int x, int y, List<List<Character>> matrix) {
        this.x = x;
        this.y = y;
        this.maxX = matrix.get(0).size()-sizeX;
        this.maxY = matrix.size()-sizeY;
    }

}
