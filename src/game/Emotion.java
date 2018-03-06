package game;

import objects.GameObject;
import utilities.TileSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static game.Game.GAME;

/**
 * Displays an emoticon-like symbol above GameObjects for visual feedback.
 */
public class Emotion {
    private static ArrayList<BufferedImage> images;

    private int lifetime;
    private BufferedImage image;

    static {
        try {
            images = new ArrayList<>(Objects.requireNonNull(
                    TileSet.readTileSet(3, 3, "resources/tilesets/emotions.png")));
        } catch (NullPointerException e) { System.out.println("Unable to read tileset"); }
    }

    /**
     * Class constructor.
     *
     * @param id the identifier for the Emotion type ( 0 = !, 1 = ?, 2 = very happy, 3 = sick, 4 = love, 5 = happy,
     *           6 = neutral, 7 = unhappy)
     */
    public Emotion(int id) {
        lifetime = 30;
        try {
            image = images.get(id);
        } catch (NullPointerException e) {
            image = null;
        }
    }

    public int getLifetime() { return lifetime; }
    public void decreaseLifetime() { lifetime--; }

    /**
     * Renders the Emotion one tile above the specified GameObject.
     *
     * @param g the graphics
     * @param character the GameObject that will be drawn above
     */
    public void paintComponent(Graphics g, GameObject character) {
        g.drawImage(image, character.getGX() - GAME.getCamera().getGX(),
                character.getGY() - GAME.getCamera().getGY() - 32, 32, 32, null);
    }
}
