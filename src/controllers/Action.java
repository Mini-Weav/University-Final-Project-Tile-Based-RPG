package controllers;

/**
 * 25/10/2017.
 */
public class Action {
    private int direction = -1;
    private boolean stop;

    public int getDirection() { return direction; }
    void setDirection(int direction) { this.direction = direction; }

    public boolean isMoving() { return !stop; }
    void setStop(boolean stop) { this.stop = stop; }
}
