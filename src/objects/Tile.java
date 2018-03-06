package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 23/10/2017.
 */
public class Tile {
    private char key;
    private boolean collision;

    private BufferedImage img;

    public Tile(BufferedImage img, boolean collision, char key) {
        this.img = img;
        this.collision = collision;
        this.key = key;
    }

    public BufferedImage getImg() { return img; }
    void setImg(BufferedImage img) { this.img = img; }

    public boolean isCollision() { return collision; }

    public char getKey() { return key; }
    public void setKey(char key) { this.key = key; }

    public Tile(Tile tile) {
        img = new BufferedImage(tile.img.getWidth(), tile.img.getHeight(), tile.img.getType());
        Graphics2D g = img.createGraphics();
        g.drawImage(tile.img, 0, 0, null);
        g.dispose();
        collision = tile.collision;
        key = tile.key;
    }
}
