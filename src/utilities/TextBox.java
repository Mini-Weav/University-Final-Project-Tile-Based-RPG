package utilities;

import game.TileMapView;
import utilities.GameFont;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lmweav on 01/11/2017.
 */
public class TextBox {
    public static BufferedImage img;
    public String text;

    public TextBox(String text) {
        loadImage();
        this.text = text;
    }

    public static void loadImage() {
        try {
            img = ImageIO.read(new File("resources/textbox.png"));
        } catch (IOException e) {
            System.out.println("cannot find file");
            e.printStackTrace();
            return;
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img,4, TileMapView.FRAME_HEIGHT-88,null);
        g.setFont(GameFont.font);
        int lineIndex = 0;
        for (String line : text.split("\n")) {
            g.drawString(line,12,(TileMapView.FRAME_HEIGHT-64+(g.getFontMetrics().getHeight()+2)*lineIndex));
            lineIndex++;
        }
    }
}
