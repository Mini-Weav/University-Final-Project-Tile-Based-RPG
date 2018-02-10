package controllers;
import game.Emotion;
import utilities.FileReader;
import utilities.Menu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static game.Game.GAME;

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
                action.direction = 0;
                break;
            case KeyEvent.VK_S:
                action.direction = 1;
                break;
            case KeyEvent.VK_A:
                action.direction = 2;
                break;
            case KeyEvent.VK_D:
                action.direction = 3;
                break;
            case KeyEvent.VK_SHIFT:
                action.stop = true;
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
            case KeyEvent.VK_ESCAPE:
                GAME.switchScreen();
                break;
            case KeyEvent.VK_0:
                GAME.goHome(true);
                break;
            case KeyEvent.VK_1:
                GAME.time = 0;
                break;
            case KeyEvent.VK_2:
                GAME.time = 1;
                break;
            case KeyEvent.VK_3:
                GAME.time = 2;
                break;
            case KeyEvent.VK_9:
                GAME.startHeist();
                break;
            case KeyEvent.VK_8:
                GAME.time = 11;
                break;
            case KeyEvent.VK_7:
                GAME.player.emotion = new Emotion(2);
                break;
            case KeyEvent.VK_6:
                GAME.items[0][1] = 1;
                GAME.items[1][3] = 1;
                GAME.gradeValues[4] = 30;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.direction = -1;
                break;
            case KeyEvent.VK_S:
                action.direction = -1;
                break;
            case KeyEvent.VK_A:
                action.direction = -1;
                break;
            case KeyEvent.VK_D:
                action.direction = -1;
                break;
            case KeyEvent.VK_SHIFT:
                action.stop = false;
                break;
        }
    }
}
