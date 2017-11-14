package utilities;

import game.Constants;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lmweav on 01/11/2017.
 */
public class TextBox {
    public String text;
    public static BufferedImage img;

    public TextBox(String text) {
        loadImage();
        this.text = text;
    }

    public static void loadImage() {
        try { img = ImageIO.read(new File("resources/textbox.png")); }
        catch (IOException e) {
            System.out.println("Cannot find textbox image.");
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, Constants.FRAME_HEIGHT - 136, img.getWidth() * 2, img.getHeight() * 3, null);
        g.setFont(GameFont.font);
        int lineIndex = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, 24, (Constants.FRAME_HEIGHT - 90 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
            lineIndex++;
        }
    }
}
