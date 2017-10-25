import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Luke on 25/10/2017.
 */
public class Keys extends KeyAdapter implements Controller {
    public Action action;

    public Keys() { action = new Action(); }

    public Action action() { return action; }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.up = true;
                action.down = false;
                action.left = false;
                action.right = false;
                break;
            case KeyEvent.VK_DOWN:
                action.up = false;
                action.down = true;
                action.left = false;
                action.right = false;
                break;
            case KeyEvent.VK_LEFT:
                action.up = false;
                action.down = false;
                action.left = true;
                action.right = false;
                break;
            case KeyEvent.VK_RIGHT:
                action.up = false;
                action.down = false;
                action.left = false;
                action.right = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.up = false;
                break;
            case KeyEvent.VK_DOWN:
                action.down = false;
                break;
            case KeyEvent.VK_LEFT:
                action.left = false;
                break;
            case KeyEvent.VK_RIGHT:
                action.right = false;
                break;
        }
    }
}
