package controllers;

import objects.GameObject;

import java.awt.*;

/**
 * Created by Luke on 20/12/2017.
 */
public class Patrol implements Controller {
    public Action action;
    public Point[] points;
    public int index;
    public int[] directions = {0, 3, 1, 2};
    public double step;
    public boolean goBack;
    public GameObject object;

    public Patrol(Point... points) {
        action = new Action();
        this.points = new Point[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
        index = 0;
        step = 0;
    }

    public Action action() {
        Point point = points[index];
        if (object.y != point.y) {
            if (object.y > point.y) { action.direction = 0; }
            else { action.direction = 1; }
        }
        else if (object.x != point.x){
            if (object.x > point.x) { action.direction = 2; }
            else { action.direction = 3; }
        }
        else {
            action.stop = true;
            step += 0.0625;
            action.direction = directions[(int) step % 4];
            if (step == 4) {
                step = 0;
                action.stop = false;
                if (goBack) { index--;}
                else { index++; }
            }
        }
        if (goBack && index == -1) {
            goBack = false;
            index = 1;
        }
        if (!goBack && index == points.length) {
            goBack = true;
            index = points.length - 2;
        }
        return action;
    }
}
