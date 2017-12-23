package controllers;


/**
 * Created by Luke on 20/12/2017.
 */
public class RandomMovement implements Controller {
    public Action action;
    public double chance;

    public RandomMovement(boolean stop) {
        action = new Action();
        if (stop) { chance = 0.05; }
        else { chance = 0.01; }
        action.stop = stop;
    }

    public Action action() {
        action.direction = -1;
        if (Math.random() < chance) { action.direction = (int) (Math.random() * 4); }
        return action;
    }

}
