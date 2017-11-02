package utilities;

import java.awt.image.BufferedImage;

/**
 * Created by lmweav on 23/10/2017.
 */
public class Tile {
    public BufferedImage img;
    public static final int WIDTH=16, HEIGHT=16;
    public boolean collision;
    public char key;

    public Tile(BufferedImage img, boolean collision, char key) {
        this.img = img;
        this.collision = collision;
        this.key = key;
    }

}
