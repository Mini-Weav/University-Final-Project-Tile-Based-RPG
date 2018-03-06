package controllers;

/**
 * Handles random movement NPC steering behaviour.
 */
public class RandomMovement implements Controller {
    private double chance;
    private Action action;

    /**
     * Class constructor.
     *
     * @param stop is the GameObject stationary
     */
    public RandomMovement(boolean stop) {
        action = new Action();
        if (stop) { chance = 0.05; }
        else { chance = 0.01; }
        action.setStop(stop);
    }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    /**
     * Modifies the GameObject's actions according to the steering behaviour.
     * If a RNG is lower than a pre-determined value, the GameObject will move/rotate in a random direction.
     *
     * @return the modified action
     */
    public Action action() {
        action.setDirection(-1);
        if (Math.random() < chance) { action.setDirection((int) (Math.random() * 4)); }
        return action;
    }
}
