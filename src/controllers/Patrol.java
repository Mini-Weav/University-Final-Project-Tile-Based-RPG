package controllers;

import objects.GameObject;

import java.awt.*;

/**
 * Handles patrolling NPC steering behaviour.
 */
public class Patrol implements Controller {
    private int index;
    private double step;
    private Action action;
    private GameObject object;

    private Point[] points;
    private int[] directions = {0, 3, 1, 2};

    /**
     * Class constructor.
     *
     * @param points the list of co-ordinates that the NPC goes to
     */
    public Patrol(Point... points) {
        action = new Action();
        this.points = new Point[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
        index = 0;
        step = 0;
    }

    public void setObject(GameObject object) { this.object = object; }

    /**
     * Modifies the GameObject's actions according to the steering behaviour.
     * The GameObject's direction is set according to its position in relation to the destination point.
     * When the GameObject's position is equal to the destination point, the GameObject rotates in each direction
     * for 8 ticks (a total of 32 ticks) and then moves to the next point. When the GameObject has finished
     * rotating on the last point, it returns to the first point.
     *
     * @return the modified action
     */
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
