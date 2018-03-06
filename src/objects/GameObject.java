package objects;

import controllers.Controller;
import game.Emotion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * Has common methods and fields for different types of GameObjects.
 */
public abstract class GameObject {
    private static final char KEY = '*';
    private static boolean inPlay;

    private int x;
    private int y;
    private int gX;
    private int gY;
    private boolean moving;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean flip;
    private boolean spotted;
    private Tile tile;
    private Controller ctrl;
    private Emotion emotion;

    private List<BufferedImage> upSprites1;
    private List<BufferedImage> upSprites2;
    private List<BufferedImage> downSprites1;
    private List<BufferedImage> downSprites2;
    private List<BufferedImage> leftSprites;
    private List<BufferedImage> rightSprites;

    /**
     * Class constructor.
     *
     * @param tile the GameObject's tile (for image and key)
     * @param x the starting x co-ordinate
     * @param y the starting y co-ordinate
     */
    GameObject(Tile tile, int x, int y) {
        this.tile = new Tile(tile);
        this.x = x;
        this.y = y;
        gX = x * 32;
        gY = y * 32;
        moving = false;
    }

    static char getKEY() { return KEY; }

    static boolean isInPlay() { return inPlay; }
    static void setInPlay(boolean inPlay) { GameObject.inPlay = inPlay; }

    public int getX() { return x; }
    void setX(int x) { this.x = x; }

    public int getY() { return y; }
    void setY(int y) { this.y = y; }

    public int getGX() { return gX; }
    void setGX(int gX) { this.gX = gX; }

    public int getGY() { return gY; }
    void setGY(int gY) { this.gY = gY; }

    boolean isNotMoving() { return !moving; }
    void setMoving(boolean moving) { this.moving = moving; }

    boolean isUp() { return up; }
    public void setUp(boolean up) { this.up = up; }

    boolean isDown() { return down; }
    public void setDown(boolean down) { this.down = down; }

    boolean isLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }

    boolean isRight() { return right; }
    public void setRight(boolean right) { this.right = right; }

    boolean isNotFlip() { return !flip; }
    void setFlip(boolean flip) { this.flip = flip; }

    boolean isSpotted() { return spotted; }
    public void setSpotted(boolean spotted) { this.spotted = spotted; }

    public Tile getTile() { return tile; }

    public void setTile(Tile tile) { this.tile = tile; }

    Controller getCtrl() { return ctrl; }
    void setCtrl(Controller ctrl) { this.ctrl = ctrl; }

    Emotion getEmotion() { return emotion; }
    public void setEmotion(Emotion emotion) { this.emotion = emotion; }

    BufferedImage getUpSprite() { return upSprites1.get(0); }
    void setUpSprites1(List<Tile> tiles) { upSprites1 = Arrays.asList(tiles.get(5).getImg(), tiles.get(6).getImg(),
            tiles.get(6).getImg(), tiles.get(5).getImg()); }
    void setUpSprites2(List<Tile> tiles) { upSprites2 = Arrays.asList(tiles.get(5).getImg(), tiles.get(7).getImg(),
            tiles.get(7).getImg(), tiles.get(5).getImg()); }

    BufferedImage getDownSprite() { return downSprites1.get(0); }
    void setDownSprites1(List<Tile> tiles) { downSprites1 = Arrays.asList(tiles.get(0).getImg(),
            tiles.get(1).getImg()); }
    void setDownSprites2(List<Tile> tiles) { downSprites2 = Arrays.asList(tiles.get(0).getImg(),
            tiles.get(2).getImg()); }

    BufferedImage getLeftSprite() { return leftSprites.get(0); }
    void setLeftSprites(List<Tile> tiles) { leftSprites = Arrays.asList(tiles.get(8).getImg(),
            tiles.get(9).getImg(), tiles.get(9).getImg(), tiles.get(8).getImg()); }

    BufferedImage getRightSprite() { return rightSprites.get(0); }
    void setRightSprites(List<Tile> tiles) { rightSprites = Arrays.asList(tiles.get(3).getImg(),
            tiles.get(4).getImg()); }

    /**
     * Changes the GameObject's tile image for a walking animation.
     *
     * @param direction the direction the GameObject is walking
     * @param index the current index of the tile image in the tileset
     */
    void walkAnimation(int direction, int index) {
        switch (direction) {
            case 0:
                if (flip) { tile.setImg(upSprites1.get(index)); }
                else { tile.setImg(upSprites2.get(index)); }
                break;
            case 1:
                if (flip) { tile.setImg(downSprites2.get(index)); }
                else { tile.setImg(downSprites2.get(index)); }
                break;
            case 2:
                tile.setImg(leftSprites.get(index));
                break;
            case 3:
                tile.setImg(rightSprites.get(index));
                break;
        }
    }

    /**
     * Creates an Emotion object above the GameObject.
     */
    void displayEmotion() {
        if (emotion.getLifetime() == 0) { emotion = null; }
        else { emotion.decreaseLifetime(); }
    }

    public abstract void move();
    public abstract void rotate(int direction);
    public abstract void update() throws NullPointerException;
    public abstract void paintComponent(Graphics g);
}
