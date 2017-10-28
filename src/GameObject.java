import java.awt.*;

/**
 * Created by Luke on 25/10/2017.
 */
public abstract class GameObject {
    public int x, y, pX, pY;
    public boolean moving;
    public Tile tile;

    public GameObject(Tile tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.pX = this.x*32;
        this.pY = this.y*32;
    }

    public abstract void update();
    public abstract void paintComponent(Graphics g);


}
