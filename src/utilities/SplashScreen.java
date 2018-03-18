package utilities;

import game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Loading screen.
 */
public class SplashScreen extends JComponent {

    private final BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/loadingscreen.png"));

    public SplashScreen() throws IOException {
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public Dimension getPreferredSize() { return new Dimension(Game.getWidth(), Game.getHeight()); }
}
