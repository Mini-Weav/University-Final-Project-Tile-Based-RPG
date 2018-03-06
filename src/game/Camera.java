package game;


/**
 * 26/10/2017.
 */
public class Camera {
    private int x;
    private int y;
    private int gX;
    private int gY;
    private int diffX = gX - (x * 32);
    private int diffY = gY-(y * 32);


    Camera(int x, int y) {
        this.x = x;
        this.y = y;
        this.gX = x * 32;
        this.gY = y * 32;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getGX() { return gX; }
    public void setGX(int gX) { this.gX = gX; }

    public int getGY() { return gY; }
    public void setGY(int gY) { this.gY = gY; }

    int getDiffX() { return diffX; }

    int getDiffY() { return diffY; }

    void update() {
        diffX = gX - (x * 32);
        diffY = gY - (y * 32);
    }
}
