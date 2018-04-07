package controllers;

import game.Constants;
import utilities.GameAudio;
import utilities.Menu;
import utilities.TileMapLoader;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static game.Game.GAME;

/**
 * Handles player input.
 */
public class Keys extends KeyAdapter implements Controller {
    private static final int[] konamiCode = { KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN,
            KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_B, KeyEvent.VK_A,
            KeyEvent.VK_ENTER };

    private int currentKonami;
    private Action action;

    /**
     * Class constructor.
     */
    public Keys() { action = new Action(); }

    public Action action() { return action; }

    /**
     * Handles player pressing a key.
     *
     * @param e the key pressed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!GAME.isDebug()) { konamiCode(key); }
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
            case KeyEvent.VK_1:
                if (GAME.isDebug()) {
                    GAME.loadMap(TileMapLoader.tileMaps.get(0));
                    GAME.getPlayer().setLocation(Constants.START_X, Constants.START_Y);
                    GAME.setTime(0);
                    GameAudio.startMusic(GameAudio.music_school);
                }
                break;
            case KeyEvent.VK_2:
                if (GAME.isDebug()) {
                    GAME.loadMap(TileMapLoader.tileMaps.get(0));
                    GAME.getPlayer().setLocation(Constants.START_X, Constants.START_Y);
                    GAME.setTime(1);
                    GameAudio.startMusic(GameAudio.music_school);
                }
                break;
            case KeyEvent.VK_3:
                if (GAME.isDebug()) {
                    GAME.loadMap(TileMapLoader.tileMaps.get(0));
                    GAME.getPlayer().setLocation(Constants.START_X, Constants.START_Y);
                    GAME.setTime(2);
                    GameAudio.startMusic(GameAudio.music_school);
                }
                break;
            case KeyEvent.VK_4:
                if (GAME.isDebug()) { GAME.goHome(true); }
                break;
            case KeyEvent.VK_5:
                if (GAME.isDebug()) {
                    GAME.startHeist();
                }
                break;
            case KeyEvent.VK_6:
                if (GAME.isDebug()) {
                    GAME.setDay(30);
                    GAME.setDaysLeft(1);
                    GAME.goHome(true);
                }
                break;
            case KeyEvent.VK_7:
                if (GAME.isDebug()) {
                    GAME.giveItem(0, 1);
                    GAME.giveItem(1, 3);
                    GAME.increaseGradeValue(4, 30);
                }
                break;
            case KeyEvent.VK_8:
                if (GAME.isDebug()) {
                    GAME.increaseGradeValue(2, 10);
                    GAME.increaseGradeValue(3, 10);
                    GAME.increaseGradeValue(4, 10);
                }
                break;
        }
    }

    /**
     * Handles player releasing a pressed key.
     *
     * @param e the key pressed
     */
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

    /**
     * Enables debug mode via the 'Konami code'.
     *
     * @param keyPressed the current key pressed in a sequence
     */
    private void konamiCode(int keyPressed) {
        if (keyPressed == konamiCode[currentKonami]) {
            currentKonami++;
            if (currentKonami == konamiCode.length) {
                currentKonami = 0;
                GAME.setDebug();
            }
        } else { currentKonami = 0; }
    }


}
