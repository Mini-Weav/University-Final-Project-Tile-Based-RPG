package game;

import objects.GameObject;
import utilities.TileSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static game.Game.GAME;

/**
 * 07/02/2018.
 */
public class Emotion {
    private static ArrayList<BufferedImage> images;

    private int lifetime;
    private BufferedImage image;

    static {
        try {
            images = new ArrayList<>(Objects.requireNonNull(TileSet.readTileSet(3, 3, "resources/tilesets/emotions.png")));
        } catch (NullPointerException e) {
            System.out.println("Unable to read tileset");
        }
    }

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


    public void paintComponent(Graphics g, GameObject character) {
        g.drawImage(image, character.getGX() - GAME.getCamera().getGX(), character.getGY() - GAME.getCamera().getGY() - 32, 32, 32, null);
    }
}
