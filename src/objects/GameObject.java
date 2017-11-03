package objects;

import java.awt.*;

/**
 * Created by Luke on 25/10/2017.
 */
public abstract class GameObject {
    public int x, y;
    public int gX, gY;
    public boolean moving, up, down, left, right;
    public Tile tile;

    public GameObject(Tile tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.gX = x * 32;
        this.gY = y * 32;
        this.moving = false;
    }

    public abstract void update();
    public abstract void paintComponent(Graphics g);


}
