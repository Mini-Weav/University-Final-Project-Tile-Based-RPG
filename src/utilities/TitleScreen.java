package utilities;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static game.Game.GAME;

/**
 * 15/12/2017.
 */
public class TitleScreen extends JComponent implements MouseListener, MouseMotionListener {
    private Menu menu;
    private TextBox textBox;

    public TitleScreen() {
        menu = new Menu(12);
        textBox = new TextBox(0, FileReader.getMenuString(63));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public Menu getMenu() { return menu; }

    public void setMenu(Menu menu) { this.menu = menu; }

    public void setTextBox(TextBox textBox) { this.textBox = textBox; }

    public void paintComponent(Graphics g) {
        g.drawImage(Menu.getTitleImg(), 0, 0, null);
        menu.paintComponent(g);
        textBox.paintComponent(g);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (curX > Game.getWidth() - 164 && curX < Game.getWidth() - 36 && curY > 32 && curY < 48) {
                GameAudio.playSfx(GameAudio.sfx_click);
                GAME.newGame();
            }
            if (curX > Game.getWidth() - 164 && curX < Game.getWidth() - 36 && curY > 64 && curY < 80) {
                GameAudio.playSfx(GameAudio.sfx_click);
                GAME.load();
            }
            if (curX > Game.getWidth() - 164 && curX < Game.getWidth() - 100 && curY > 96 && curY < 116) {
                Game.setRunning(false);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        boolean click = false;
        try {
            int curX = e.getX();
            int curY = e.getY();

            click = curX > Game.getWidth() - 164 && curX < Game.getWidth() - 36 && curY > 32 && curY < 48 ||
                    curX > Game.getWidth() - 164 && curX < Game.getWidth() - 36 && curY > 64 && curY < 80 ||
                    curX > Game.getWidth() - 164 && curX < Game.getWidth() - 100 && curY > 96 && curY < 116;

        } catch (NullPointerException e1) { /* Mouse is out of frame */ }

        if (click) { this.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        else { this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); }
    }

    public Dimension getPreferredSize() { return new Dimension(Game.getWidth(), Game.getHeight()); }
}
