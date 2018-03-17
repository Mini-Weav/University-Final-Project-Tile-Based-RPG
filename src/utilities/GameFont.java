package utilities;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

/**
 * Manages the fonts used in the game.
 */
public class GameFont {
    private static Font bigFont;
    private static Font tinyFont;
    private static Font medFont;
    private static Font smallFont;
    private static Font smallUnderline;

    static Font getBigFont() { return bigFont; }

    static Font getTinyFont() { return tinyFont; }

    static Font getMedFont() { return medFont; }

    static Font getSmallFont() { return smallFont; }

    static Font getSmallUnderline() { return smallUnderline; }

    /**
     * Pre-loads all the fonts used in the game.
     */
    public static void loadFont(CyclicBarrier barrier) {
        Map<TextAttribute, Integer> textAttibutes = new HashMap<>();
        textAttibutes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL);
        try {
            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            e.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("resources/Pokemon GB.ttf")));
            bigFont = new Font("Pokemon GB", 0, 16);
            tinyFont = new Font("Pokemon GB", 0, 11);
            medFont = new Font("Pokemon GB", 0, 14);
            smallFont = new Font("Pokemon GB", 0, 12);
            smallUnderline = new Font("Pokemon GB", 0, 12).deriveFont(textAttibutes);
            System.out.println("font loaded");
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
