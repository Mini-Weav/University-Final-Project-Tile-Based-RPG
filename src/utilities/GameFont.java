package utilities;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmweav on 01/11/2017.
 */
public class GameFont {
    public static Font bigFont, tinyFont, medFont, smallFont, smallUnderline, titleFont;

    public static void loadFont() {
        Map<TextAttribute, Integer> textAttibutes = new HashMap<>();
        textAttibutes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL);
        try {
            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            e.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("resources/Pokemon GB.ttf")));
            bigFont = new Font("Pokemon GB", 0, 16);
            tinyFont = new Font("Pokemon GB", 0, 11);
            medFont = new Font("Pokemon GB", 0, 14);
            smallFont = new Font("Pokemon GB", 0, 12);
            titleFont = new Font("Pokemon GB", 0, 28);
            smallUnderline = new Font("Pokemon GB", 0, 12).deriveFont(textAttibutes);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
