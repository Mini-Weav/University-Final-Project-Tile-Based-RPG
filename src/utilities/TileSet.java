package utilities;

import game.Constants;
import objects.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 23/10/2017.
 */
public class TileSet {
    public static List<BufferedImage> readTileSet(int rows, int cols, String fname) {
        List<BufferedImage> images = new ArrayList<>();
        BufferedImage ts;
        try { ts = ImageIO.read(new File(fname)); }
        catch (IOException e) {
            System.out.println("Cannot find TileSet file.");
            e.printStackTrace();
            return null;
        }
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                images.add((i * cols) + j, ts.getSubimage(j * Constants.TILE_WIDTH, i * Constants.TILE_WIDTH,
                        Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
            }
        }
        return images;
    }
}
