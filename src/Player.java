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


    public Player(Tile tile, int x, int y, Controller ctrl) {
        super(tile, x, y);
        this.ctrl = ctrl;
    }

    public void update() {
        Action action = ctrl.action();
        if (System.currentTimeMillis() - lastStep > 1000/4 || !moving) {
            if (action.up && this.y > 0 &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y-1).get(this.x)).collision) {
                Game.camera.y--;
                this.y--;
            }
            if (action.down && this.y < TileMap.maxY &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y+1).get(this.x)).collision) {
                Game.camera.y++;
                this.y++;
            }
            if (action.left && this.x > 0 &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x-1)).collision) {
                Game.camera.x--;
                this.x--;
            }
            if (action.right && this.x < TileMap.maxX &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x+1)).collision) {
                Game.camera.x++;
                this.x++;
            }
            this.lastStep = System.currentTimeMillis();
        }
        moving = action.up || action.down || action.left || action.right;

        if (TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x)) instanceof DoorTile) {
            Point doorPoints = DoorTile.points.get(new Point(this.x,this.y));
            this.x = doorPoints.x;
            this.y = doorPoints.y;
            Game.camera.x = this.x-(TileMap.FRAME_WIDTH/64);
            Game.camera.y = this.y-(TileMap.FRAME_HEIGHT/64);
        }

    }

    public void paintComponent(Graphics g) {
        g.drawImage(TILES.get(0).img,(this.x-Game.camera.x)*32,(this.y-Game.camera.y)*32,32,32,null);
    }



}
