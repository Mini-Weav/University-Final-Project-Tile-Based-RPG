package objects;

import controllers.Action;
import controllers.Controller;
import game.Constants;
import game.Game;
import game.TileMap;
import game.TileMapView;
import utilities.CharacterTileSet;
import utilities.TileMapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luke on 25/10/2017.
 */
public class Player extends GameObject {
    public boolean flip;
    public Point direction;
    public Controller ctrl;
    public static final char KEY = 'P';
    public static final List<Tile> TILES = CharacterTileSet.readTileSet("resources/tilesets/player.png", KEY);
    public static final List<BufferedImage> UP_SPRITES1;
    public static final List<BufferedImage> UP_SPRITES2;
    public static final List<BufferedImage> DOWN_SPRITES1;
    public static final List<BufferedImage> DOWN_SPRITES2;
    public static final List<BufferedImage> LEFT_SPRITES;
    public static final List<BufferedImage> RIGHT_SPRITES;

    static {
        UP_SPRITES1 = Arrays.asList(TILES.get(5).img, TILES.get(6).img, TILES.get(6).img, TILES.get(5).img);
        UP_SPRITES2 = Arrays.asList(TILES.get(5).img, TILES.get(7).img, TILES.get(7).img, TILES.get(5).img);
        DOWN_SPRITES1 = Arrays.asList(TILES.get(0).img, TILES.get(1).img);
        DOWN_SPRITES2 = Arrays.asList(TILES.get(0).img, TILES.get(2).img);
        LEFT_SPRITES = Arrays.asList(TILES.get(8).img, TILES.get(9).img, TILES.get(9).img, TILES.get(8).img);
        RIGHT_SPRITES = Arrays.asList(TILES.get(3).img, TILES.get(4).img);
    }

    public Player(Tile tile, int x, int y, Controller ctrl) {
        super(tile, x, y);
        this.ctrl = ctrl;
        this.flip = true;
        this.direction = new Point(this.x, this.y + 1);
    }

    public void walkAnimation(int direction, int index) {
        switch (direction) {
            case 0:
                if (flip) { this.tile.img = UP_SPRITES1.get(index); }
                else { this.tile.img = UP_SPRITES2.get(index); }
                break;
            case 1:
                if (flip) { this.tile.img = DOWN_SPRITES2.get(index); }
                else { this.tile.img = DOWN_SPRITES1.get(index); }
                break;
            case 2:
                this.tile.img = LEFT_SPRITES.get(index);
                break;
            case 3:
                this.tile.img = RIGHT_SPRITES.get(index);
                break;
        }
    }

    public void rotate(int direction) {
        switch (direction) {
            case 0:
                this.tile.img = UP_SPRITES1.get(0);
                this.direction.setLocation(this.x,this.y-1);
                break;
            case 1:
                this.tile.img = DOWN_SPRITES1.get(0);
                this.direction.setLocation(this.x,this.y+1);
                break;
            case 2:
                this.tile.img = LEFT_SPRITES.get(0);
                this.direction.setLocation(this.x-1,this.y);
                break;
            case 3:
                this.tile.img = RIGHT_SPRITES.get(0);
                this.direction.setLocation(this.x+1,this.y);
                break;
        }
    }

    public void move() {
        int i;
        if (this.up) {
            Game.camera.gY -= 8;
            this.gY -= 8;
            i = (this.gY%32)/8;
            walkAnimation(0, i);
            if (this.gY%32 == 0 || TileMapView.tiles.get(Game.gameMatrix.get(this.y-1).get(this.x)) instanceof DoorTile) {
                Game.camera.y--;
                this.y--;
                this.up = false;
                flip = !flip;
                direction.setLocation(this.x,this.y-1);
            }
        }
        if (this.down) {
            Game.camera.gY += 8;
            this.gY += 8;
            i = (this.gY%32)/16;
            walkAnimation(1, i);
            if (this.gY%32 == 0 || TileMapView.tiles.get(Game.gameMatrix.get(this.y+1).get(this.x)) instanceof DoorTile) {
                Game.camera.y++;
                this.y++;
                this.down = false;
                flip = !flip;
                direction.setLocation(this.x,this.y+1);

            }
        }
        if (this.left) {
            Game.camera.gX -= 8;
            this.gX -= 8;
            i = (this.gX%32)/8;
            walkAnimation(2, i);
            if (this.gX%32 == 0) {
                Game.camera.x--;
                this.x--;
                this.left = false;
                direction.setLocation(this.x-1,this.y);
            }
        }
        if (this.right) {
            Game.camera.gX += 8;
            this.gX += 8;
            i = (this.gX%32)/16;
            walkAnimation(3, i);
            if (this.gX%32 == 0) {
                Game.camera.x++;
                this.x++;
                this.right = false;
                direction.setLocation(this.x+1,this.y);
            }
        }
    }

    public void transition() {
        Game.transition = true;
        Game.transitionTime = System.currentTimeMillis();

        Point doorPoints = TileMapLoader.tileMaps.get(Game.map.currentId).doorPoints.get(new Point(this.x, this.y)).s;
        //if (Game.gameMatrix.get(this.y).get(this.x) =='^') { this.tile.img = DOWN_SPRITES1.get(0); }
        //if (Game.gameMatrix.get(this.y).get(this.x) =='v') { this.tile.img = DOWN_SPRITES1.get(0); }

        TileMap currentMap = TileMapLoader.tileMaps.get(Game.map.currentId);
        int nextId = currentMap.doorPoints.get(new Point(this.x, this.y)).t;
        Game.map.loadMap(TileMapLoader.tileMaps.get(nextId));

        this.x = doorPoints.x;
        this.y = doorPoints.y;
        this.gX = x*32;
        this.gY = y*32;

        Game.camera.x = this.x-(Constants.FRAME_WIDTH/64);
        Game.camera.y = this.y-(Constants.FRAME_HEIGHT/64);
        Game.camera.gX = Game.camera.x*32;
        Game.camera.gY = Game.camera.y*32;
    }

    public void update() {
        moving = this.up || this.down || this.left || this.right;
        Action action = ctrl.action();

        if (!moving && !Game.transition && Game.textBox == null && Game.menu == null) {
            if (action.up && this.y > 0 &&
                    !TileMapView.tiles.get(Game.gameMatrix.get(this.y - 1).get(this.x)).collision) { this.up = true; }
            if (action.down && this.y < Game.map.maxY &&
                    !TileMapView.tiles.get(Game.gameMatrix.get(this.y + 1).get(this.x)).collision) { this.down = true; }
            if (action.left && this.x > 0 &&
                    !TileMapView.tiles.get(Game.gameMatrix.get(this.y).get(this. x- 1)).collision) { this.left = true; }
            if (action.right && this.x < Game.map.maxX &&
                    !TileMapView.tiles.get(Game.gameMatrix.get(this.y).get(this.x + 1)).collision) { this.right = true; }
        }
        this.move();

        if (!this.moving && !Game.transition && Game.textBox == null && Game.menu == null) {
            if (action.up) { rotate(0); }
            if (action.down) { rotate(1); }
            if (action.left) { rotate(2); }
            if (action.right) { rotate(3); }
        }

        if (TileMapView.tiles.get(Game.gameMatrix.get(this.y).get(this.x)) instanceof DoorTile) { this.transition(); }
        if (Game.transition && System.currentTimeMillis() - Game.transitionTime > 1000 / 5) { Game.transition = false; }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.tile.img, this.gX - Game.camera.gX, this.gY - Game.camera.gY, 32, 32, null);
    }



}
