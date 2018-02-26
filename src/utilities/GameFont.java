package utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by lmweav on 01/11/2017.
 */
public class GameFont {
    public static Font bigFont, tinyFont, medFont, smallFont, titleFont;

    public static void loadFont() {
        try {
            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            e.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("resources/Pokemon GB.ttf")));
            bigFont = new Font("Pokemon GB", 0, 16);
            tinyFont = new Font("Pokemon GB", 0, 11);
            medFont = new Font("Pokemon GB", 0, 14);
            smallFont = new Font("Pokemon GB", 0, 12);
            titleFont = new Font("Pokemon GB", 0, 28);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
