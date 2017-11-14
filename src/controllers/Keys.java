package controllers;

import game.Game;
import utilities.Menu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by lmweav on 25/10/2017.
 */
public class Keys extends KeyAdapter implements Controller {
    public Action action;

    public Keys() { action = new Action(); }

    public Action action() { return action; }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.up = true;
                action.down = false;
                action.left = false;
                action.right = false;
                break;
            case KeyEvent.VK_S:
                action.up = false;
                action.down = true;
                action.left = false;
                action.right = false;
                break;
            case KeyEvent.VK_A:
                action.up = false;
                action.down = false;
                action.left = true;
                action.right = false;
                break;
            case KeyEvent.VK_D:
                action.up = false;
                action.down = false;
                action.left = false;
                action.right = true;
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
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.up = false;
                break;
            case KeyEvent.VK_S:
                action.down = false;
                break;
            case KeyEvent.VK_A:
                action.left = false;
                break;
            case KeyEvent.VK_D:
                action.right = false;
                break;
        }
    }
}
