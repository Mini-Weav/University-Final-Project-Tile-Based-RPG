import java.awt.*;
import java.util.List;

/**
 * Created by Luke on 25/10/2017.
 */
public class Player extends GameObject {

    private static final char KEY = 'P';
    public static final List<Tile> TILES = CharacterTileSet.readTileSet("resources/tilesets/character.png", KEY);

    public Controller ctrl;
    public long lastStep = System.currentTimeMillis();
    public boolean moving;

    public Player(Tile tile, int x, int y, Controller ctrl) {
        super(tile, x, y);
        this.ctrl = ctrl;
    }

    public void update() {
        Action action = ctrl.action();
        if (System.currentTimeMillis() - lastStep > 1000/4 || !moving) {
            if (action.up && this.y > 0 &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y-1).get(this.x)).collision) {this.y--; }
            if (action.down && this.y < TileMap.MAX_Y &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y+1).get(this.x)).collision) { this.y++; }
            if (action.left && this.x > 0 &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x-1)).collision) { this.x--; }
            if (action.right && this.x < TileMap.MAX_X &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x+1)).collision) { this.x++; }
            lastStep = System.currentTimeMillis();
        }
        moving = action.up || action.down || action.left || action.right;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(TILES.get(0).img,x*32,y*32,32,32,null);
    }



}
