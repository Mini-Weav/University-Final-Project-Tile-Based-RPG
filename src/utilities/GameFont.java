package utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by lmweav on 01/11/2017.
 */
public class GameFont {
    public static Font font, smallFont;

    public static void loadFont() {
        try {
            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            e.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("resources/Pokemon GB.ttf")));
            font = new Font("Pokemon GB", 0, 16);
            smallFont = new Font("Pokemon GB", 0, 10);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
