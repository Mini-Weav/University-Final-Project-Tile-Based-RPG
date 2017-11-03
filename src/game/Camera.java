package game;

import java.util.List;

/**
 * Created by lmweav on 26/10/2017.
 */
public class Camera {
    public int x, y;
    public int gX, gY;
    public int maxX, maxY;
    public int diffX = gX - (x * 32), diffY = gY-(y * 32);


    public Camera(int x, int y, List<List<Character>> matrix) {
        this.x = x;
        this.y = y;
        this.gX = x * 32;
        this.gY = y * 32;
        this.maxX = matrix.get(0).size() - Constants.CAMERA_SIZE_X;
        this.maxY = matrix.size() - Constants.CAMERA_SIZE_Y;
    }

    public void update() {
        diffX = gX - (x * 32);
        diffY = gY - (y * 32);
    }
}
