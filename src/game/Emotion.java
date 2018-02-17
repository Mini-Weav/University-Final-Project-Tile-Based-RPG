package game;

import objects.GameObject;
import utilities.TileSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static game.Game.GAME;

/**
 * Created by lmweav on 07/02/2018.
 */
public class Emotion {
    public int lifetime;
    public BufferedImage image;
    public static ArrayList<BufferedImage> images;

    static {
        try {
            images = new ArrayList<>(TileSet.readTileSet(3, 3, "resources/tilesets/emotions.png"));
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

    public void paintComponent(Graphics g, GameObject character) {
        g.drawImage(image, character.gX - GAME.camera.gX, character.gY - GAME.camera.gY - 32, 32, 32, null);
    }
}
