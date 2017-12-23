package objects;

import controllers.Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Luke on 25/10/2017.
 */
public abstract class GameObject {
    public int x, y;
    public int gX, gY;
    public boolean moving, up, down, left, right, flip;
    public Tile tile;
    public Controller ctrl;

    public List<BufferedImage> upSprites1;
    public List<BufferedImage> upSprites2;
    public List<BufferedImage> downSprites1;
    public List<BufferedImage> downSprites2;
    public List<BufferedImage> leftSprites;
    public List<BufferedImage> rightSprites;

    public static boolean inPlay;
    public static final char KEY = '*';

    public GameObject(Tile tile, int x, int y) {
        this.tile = new Tile(tile);
        this.x = x;
        this.y = y;
        gX = x * 32;
        gY = y * 32;
        moving = false;
    }

    public void walkAnimation(int direction, int index) {
        switch (direction) {
            case 0:
                if (flip) { tile.img = upSprites1.get(index); }
                else { tile.img = upSprites2.get(index); }
                break;
            case 1:
                if (flip) { tile.img = downSprites2.get(index); }
                else { tile.img = downSprites2.get(index); }
                break;
            case 2:
                tile.img = leftSprites.get(index);
                break;
            case 3:
                tile.img = rightSprites.get(index);
                break;
        }
    }

    public abstract void move();
    public abstract void rotate(int direction);
    public abstract void update();
    public abstract void paintComponent(Graphics g);


}
