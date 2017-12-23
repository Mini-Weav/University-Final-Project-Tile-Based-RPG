package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by lmweav on 23/10/2017.
 */
public class Tile {
    public BufferedImage img;
    public boolean collision;
    public char key;

    public Tile(BufferedImage img, boolean collision, char key) {
        this.img = img;
        this.collision = collision;
        this.key = key;
    }

    public Tile(Tile tile) {
        img = new BufferedImage(tile.img.getWidth(), tile.img.getHeight(), tile.img.getType());
        Graphics2D g = img.createGraphics();
        g.drawImage(tile.img, 0, 0, null);
        g.dispose();
        collision = tile.collision;
        key = tile.key;
    }

}
