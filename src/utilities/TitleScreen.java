package utilities;

import game.Constants;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static game.Game.GAME;

/**
 * Created by Luke on 15/12/2017.
 */
public class TitleScreen extends JComponent implements MouseListener, MouseMotionListener {
    Menu menu;
    TextBox textBox;

    public TitleScreen() {
        menu = new Menu(12);
        textBox = new TextBox(0, "WELCOME TO BROOKLANDS#ACADEMY.");
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(Menu.titleImg, 0, 0, null);
        menu.paintComponent(g);
        textBox.paintComponent(g);
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (curX > Game.width - 164 && curX < Game.width - 36 && curY > 32 && curY < 48) {
                GameAudio.playSfx(GameAudio.sfx_click);
                GAME.newGame();
                GameAudio.stopMusic();
            }
            if (curX > Game.width - 164 && curX < Game.width - 36 && curY > 64 && curY < 80) {
                GameAudio.playSfx(GameAudio.sfx_click);
                GAME.load();
                GameAudio.startMusic(GameAudio.music_school);
            }
            if (curX > Game.width - 164 && curX < Game.width - 100 && curY > 96 && curY < 116) { System.exit(0); }
        }
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        boolean click = false;
        try {
            int curX = e.getX();
            int curY = e.getY();

            click = curX > Game.width - 164 && curX < Game.width - 36 && curY > 32 && curY < 48 ||
                    curX > Game.width - 164 && curX < Game.width - 36 && curY > 64 && curY < 80 ||
                    curX > Game.width - 164 && curX < Game.width - 100 && curY > 96 && curY < 116;

        } catch (NullPointerException e1) {
            //out of frame
        }

        if (click) { this.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        else { this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); }
    }

    public Dimension getPreferredSize() { return new Dimension(Game.width, Game.height); }
}
