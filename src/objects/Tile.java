package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Stores information about a game co-ordinate
 */
public class Tile {
    private char key;
    private boolean collision;

    private BufferedImage img;

    /**
     * Class constructor.
     *
     * @param img the image of the Tile
     * @param collision whether or not GameObjects can move on top of the tile
     * @param key the key of the Tile (for when stored in a matrix)
     */
    public Tile(BufferedImage img, boolean collision, char key) {
        this.img = img;
        this.collision = collision;
        this.key = key;
    }

    /**
     * Class constructor.
     * Creates a deep copy of the supplied Tile.
     *
     * @param tile the Tile to copy
     */
    public Tile(Tile tile) {
        img = new BufferedImage(tile.img.getWidth(), tile.img.getHeight(), tile.img.getType());
        Graphics2D g = img.createGraphics();
        g.drawImage(tile.img, 0, 0, null);
        g.dispose();
        collision = tile.collision;
        key = tile.key;
    }

    public BufferedImage getImg() { return img; }
    void setImg(BufferedImage img) { this.img = img; }

    public boolean isCollision() { return collision; }

    public char getKey() { return key; }
    public void setKey(char key) { this.key = key; }
}
