package controllers;


/**
 * 20/12/2017.
 */
public class RandomMovement implements Controller {
    private double chance;
    private Action action;

    public RandomMovement(boolean stop) {
        action = new Action();
        if (stop) { chance = 0.05; }
        else { chance = 0.01; }
        action.setStop(stop);
    }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public Action action() {
        action.setDirection(-1);
        if (Math.random() < chance) { action.setDirection((int) (Math.random() * 4)); }
        return action;
    }
}
