package game;

import java.awt.*;

/**
 * The Constants Class provides the constant values used in the game.
 * Created by lmweav
 */
public class Constants {
    public static final int START_X = 9, START_Y = 24;
    public static final int DEC_WIDTH = 6, DEC_HEIGHT = 29;
    public static final int FRAME_WIDTH = 480, FRAME_HEIGHT = 480;
    public static final Rectangle SCREEN = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration().getBounds();
    public static final int FULL_WIDTH = SCREEN.width, FULL_HEIGHT = SCREEN.height;
    public static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;
    public static final Dimension FRAME_SIZE = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT );
    public static final Dimension FULL_SIZE = new Dimension(Constants.FULL_WIDTH, Constants.FULL_HEIGHT );
    public static final int UP = 5, DOWN = 0, LEFT = 8, RIGHT = 3;
}
