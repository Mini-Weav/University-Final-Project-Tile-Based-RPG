import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luke on 25/10/2017.
 */
public class Player extends GameObject {

    private static final char KEY = 'X';
    public static final List<Tile> TILES = CharacterTileSet.readTileSet("resources/tilesets/character.png", KEY);
    public static final List<BufferedImage> UP_SPRITES1 = Arrays.asList(TILES.get(5).img,TILES.get(6).img,TILES.get(6).img,TILES.get(5).img);
    public static final List<BufferedImage> UP_SPRITES2 = Arrays.asList(TILES.get(5).img,TILES.get(7).img,TILES.get(7).img,TILES.get(5).img);
    public static final List<BufferedImage> DOWN_SPRITES1 = Arrays.asList(TILES.get(0).img,TILES.get(1).img);
    public static final List<BufferedImage> DOWN_SPRITES2 = Arrays.asList(TILES.get(0).img,TILES.get(2).img);
    public static final List<BufferedImage> LEFT_SPRITES = Arrays.asList(TILES.get(8).img,TILES.get(9).img,TILES.get(9).img,TILES.get(8).img);
    public static final List<BufferedImage> RIGHT_SPRITES = Arrays.asList(TILES.get(3).img,TILES.get(4).img);

    public Controller ctrl;
    public boolean flip;



    public Player(Tile tile, int x, int y, Controller ctrl) {
        super(tile, x, y);
        this.ctrl = ctrl;
        this.flip = true;
    }

    public void update() {
        moving = this.up || this.down || this.left || this.right;
        Action action = ctrl.action();
        if (!moving && !Game.transition) {
            if (action.up && this.y > 0 &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y-1).get(this.x)).collision) {
                this.up = true;
            }
            if (action.down && this.y < TileMap.maxY &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y+1).get(this.x)).collision) {
                this.down = true;
            }
            if (action.left && this.x > 0 &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x-1)).collision) {
                this.left = true;
            }
            if (action.right && this.x < TileMap.maxX &&
                    !TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x+1)).collision) {
                this.right = true;
            }
        }

        int index;
        if (this.up) {
            Game.camera.gY -= 8;
            this.gY -= 8;
            index = (this.gY%32)/8;
            walkAnimation(0, index);
            if (this.gY%32 == 0 || TileMap.tiles.get(Game.gameMatrix.get(this.y-1).get(this.x)) instanceof DoorTile) {
                Game.camera.y--;
                this.y--;
                this.up = false;
                if (flip) { flip = false; }
                else { flip = true; }
            }
        }
        if (this.down) {
            Game.camera.gY += 8;
            this.gY += 8;
            index = (this.gY%32)/16;
            walkAnimation(1, index);
            if (this.gY%32 == 0 || TileMap.tiles.get(Game.gameMatrix.get(this.y+1).get(this.x)) instanceof DoorTile) {
                Game.camera.y++;
                this.y++;
                this.down = false;
                if (flip) { flip = false; }
                else { flip = true; }
            }
        }
        if (this.left) {
            Game.camera.gX -= 8;
            this.gX -= 8;
            index = (this.gX%32)/8;
            walkAnimation(2, index);
            if (this.gX%32 == 0) {
                Game.camera.x--;
                this.x--;
                this.left = false;
            }
        }
        if (this.right) {
            Game.camera.gX += 8;
            this.gX += 8;
            index = (this.gX%32)/16;
            walkAnimation(3, index);
            if (this.gX%32 == 0) {
                Game.camera.x++;
                this.x++;
                this.right = false;
            }
        }

        if (action.up && !this.moving && !Game.transition) {
            this.tile.img = UP_SPRITES1.get(0);
        }
        if (action.down && !this.moving && !Game.transition) {
            this.tile.img = DOWN_SPRITES1.get(0);
        }
        if (action.left && !this.moving && !Game.transition) {
            this.tile.img = LEFT_SPRITES.get(0);
        }
        if (action.right && !this.moving && !Game.transition) {
            this.tile.img = RIGHT_SPRITES.get(0);
        }

        if (Game.transition) {
            if (System.currentTimeMillis() - Game.transitionTime > 1000/5) {
                Game.transition = false;
            }
        }


        if (TileMap.tiles.get(Game.gameMatrix.get(this.y).get(this.x)) instanceof DoorTile) {
            Game.transition = true;
            Game.transitionTime = System.currentTimeMillis();
            Point doorPoints = DoorTile.points.get(new Point(this.x,this.y));
            if (Game.gameMatrix.get(this.y).get(this.x) =='/') { this.tile.img = DOWN_SPRITES1.get(0); }
            if (Game.gameMatrix.get(this.y).get(this.x) =='\\') { this.tile.img = DOWN_SPRITES1.get(0); }
            this.x = doorPoints.x;
            this.y = doorPoints.y;
            this.gX = x*32;
            this.gY = y*32;
            Game.camera.x = this.x-(TileMap.FRAME_WIDTH/64);
            Game.camera.y = this.y-(TileMap.FRAME_HEIGHT/64);
            Game.camera.gX = Game.camera.x*32;
            Game.camera.gY = Game.camera.y*32;
        }

    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.tile.img,this.gX-Game.camera.gX,this.gY-Game.camera.gY,32,32,null);
    }

    public void walkAnimation(int direction, int index) {
        switch (direction) {
            case 0:
                if (flip) {
                    this.tile.img = UP_SPRITES1.get(index);
                }
                else {
                    this.tile.img = UP_SPRITES2.get(index);
                }
                break;
            case 1:
                if (flip) {
                    this.tile.img = DOWN_SPRITES2.get(index);
                }
                else {
                    this.tile.img = DOWN_SPRITES1.get(index);
                }
                break;
            case 2:
                this.tile.img = LEFT_SPRITES.get(index);
                break;
            case 3:
                this.tile.img = RIGHT_SPRITES.get(index);
                break;
        }
    }



}
