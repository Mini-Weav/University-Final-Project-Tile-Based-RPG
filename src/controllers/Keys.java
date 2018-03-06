package controllers;
import utilities.Menu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static game.Game.GAME;

/**
 * 25/10/2017.
 */
public class Keys extends KeyAdapter implements Controller {
    private Action action;

    public Keys() { action = new Action(); }
    public Action action() { return action; }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.setDirection(0);
                break;
            case KeyEvent.VK_S:
                action.setDirection(1);
                break;
            case KeyEvent.VK_A:
                action.setDirection(2);
                break;
            case KeyEvent.VK_D:
                action.setDirection(3);
                break;
            case KeyEvent.VK_SHIFT:
                action.setStop(true);
                break;
            case KeyEvent.VK_M:
                Menu.hotKeyAccess(1);
                break;
            case KeyEvent.VK_F:
                Menu.hotKeyAccess(2);
                break;
            case KeyEvent.VK_E:
                Menu.hotKeyAccess(3);
                break;
            case KeyEvent.VK_C:
                Menu.hotKeyAccess(4);
                break;
            case KeyEvent.VK_0:
                GAME.goHome(true);
                break;
            case KeyEvent.VK_1:
                GAME.setTime(0);
                break;
            case KeyEvent.VK_2:
                GAME.setTime(1);
                break;
            case KeyEvent.VK_3:
                GAME.setTime(2);
                break;
            case KeyEvent.VK_8:
                GAME.setTime(2);
                GAME.setDay(30);
                GAME.setDaysLeft(1);
                break;
            case KeyEvent.VK_9:
                GAME.startHeist();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.setDirection(-1);
                break;
            case KeyEvent.VK_S:
                action.setDirection(-1);
                break;
            case KeyEvent.VK_A:
                action.setDirection(-1);
                break;
            case KeyEvent.VK_D:
                action.setDirection(-1);
                break;
            case KeyEvent.VK_SHIFT:
                action.setStop(false);
                break;
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
