package controllers;

import objects.GameObject;

import java.awt.*;

/**
 * 20/12/2017.
 */
public class Patrol implements Controller {
    private int index;
    private double step;
    private Action action;
    private GameObject object;

    private Point[] points;
    private int[] directions = {0, 3, 1, 2};

    public Patrol(Point... points) {
        action = new Action();
        this.points = new Point[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
        index = 0;
        step = 0;
    }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public void setObject(GameObject object) { this.object = object; }

    public Action action() {
        Point point = points[index];
        if (object.getY() != point.y) {
            action.setStop(false);
            if (object.getY() > point.y) { action.setDirection(0); }
            else { action.setDirection(1); }
        }
        else if (object.getX() != point.x){
            action.setStop(false);
            if (object.getX() > point.x) { action.setDirection(2); }
            else { action.setDirection(3); }
        }
        else {
            action.setStop(true);
            step += 0.0625;
            action.setDirection(directions[(int) step % 4]);
            if (step == 4) {
                step = 0;
                index++;
            }
        }
        if (index == points.length) { index = 0; }
        return action;
    }

    public void reset() {
        index = 0;
        step = 0;
    }
}
