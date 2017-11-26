package lessons;

import objects.Player;

/**
 * Created by lmweav on 17/11/2017.
 */
public class PE {
    public static boolean running;

    public static void movingScript(Player player) {
        int lowX = 19, highX = 31, lowY = 8, highY = 15;
        if (player.x == lowX && player.y < highY) {
            player.down = true;
            player.move();
        }
        if (player.x == highX && player.y > lowY) {
            player.up = true;
            player.move();
        }
        if (player.y == lowY && player.x > lowX) {
            player.left = true;
            player.move();
        }
        if (player.y== highY && player.x < highX) {
            player.right = true;
            player.move();
        }

    }
}
