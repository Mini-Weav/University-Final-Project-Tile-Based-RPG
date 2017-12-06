package objects;

import controllers.Action;
import controllers.Controller;
import game.Constants;
import game.Game;
import game.TileMap;
import game.TileMapView;
import lessons.LessonTypeC;
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
    public static final char KEY = 'U';
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
        flip = true;
        direction = new Point(this.x, this.y + 1);
    }

    public void walkAnimation(int direction, int index) {
        switch (direction) {
            case 0:
                if (flip) { tile.img = UP_SPRITES1.get(index); }
                else { tile.img = UP_SPRITES2.get(index); }
                break;
            case 1:
                if (flip) { tile.img = DOWN_SPRITES2.get(index); }
                else { tile.img = DOWN_SPRITES1.get(index); }
                break;
            case 2:
                tile.img = LEFT_SPRITES.get(index);
                break;
            case 3:
                tile.img = RIGHT_SPRITES.get(index);
                break;
        }
    }

    public void rotate(int direction) {
        switch (direction) {
            case 0:
                tile.img = UP_SPRITES1.get(0);
                this.direction.setLocation(x, y - 1);
                break;
            case 1:
                tile.img = DOWN_SPRITES1.get(0);
                this.direction.setLocation(x, y + 1);
                break;
            case 2:
                tile.img = LEFT_SPRITES.get(0);
                this.direction.setLocation(x - 1, y);
                break;
            case 3:
                tile.img = RIGHT_SPRITES.get(0);
                this.direction.setLocation(x + 1, y);
                break;
        }
    }

    public void move() {
        int i;
        if (up) {
            Game.camera.gY -= 8;
            gY -= 8;
            i = (gY % 32) / 8;
            walkAnimation(0, i);
            if (gY % 32 == 0 || TileMapView.tiles.get(Game.tileMatrix[y - 1][x]) instanceof DoorTile) {
                Game.camera.y--;
                y--;
                up = false;
                flip = !flip;
                direction.setLocation(x, y - 1);
            }
        }
        if (down) {
            Game.camera.gY += 8;
            gY += 8;
            i = (gY % 32) / 16;
            walkAnimation(1, i);
            if (gY % 32 == 0 || TileMapView.tiles.get(Game.tileMatrix[y + 1][x]) instanceof DoorTile) {
                Game.camera.y++;
                y++;
                down = false;
                flip = !flip;
                direction.setLocation(x, y + 1);

            }
        }
        if (left) {
            Game.camera.gX -= 8;
            gX -= 8;
            i = (gX % 32) / 8;
            walkAnimation(2, i);
            if (gX % 32 == 0) {
                Game.camera.x--;
                x--;
                left = false;
                direction.setLocation(x - 1, y);
            }
        }
        if (right) {
            Game.camera.gX += 8;
            gX += 8;
            i = (gX % 32)/16;
            walkAnimation(3, i);
            if (gX % 32 == 0) {
                Game.camera.x++;
                x++;
                right = false;
                direction.setLocation(x + 1, y);
            }
        }
    }

    public void transition() {
        Game.doTransition();

        Point doorPoints = TileMapLoader.tileMaps.get(Game.map.currentId).doorPoints.get(new Point(x, y)).s;
        //if (Game.tileMatrix.get(this.y).get(this.x) =='^') { this.tile.img = downSprites1.get(0); }
        //if (Game.tileMatrix.get(this.y).get(this.x) =='v') { this.tile.img = downSprites1.get(0); }

        TileMap currentMap = TileMapLoader.tileMaps.get(Game.map.currentId);
        for (NPC npc : currentMap.NPCs.get(Game.time)) {
            npc.resetDirection(npc.defaultDirection);
        }
        int nextId = currentMap.doorPoints.get(new Point(x, y)).t;
        Game.map.loadMap(TileMapLoader.tileMaps.get(nextId));

        x = doorPoints.x;
        y = doorPoints.y;
        gX = x * 32;
        gY = y * 32;

        Game.camera.x = x-(Constants.FRAME_WIDTH / 64);
        Game.camera.y = y-(Constants.FRAME_HEIGHT / 64);
        Game.camera.gX = Game.camera.x * 32;
        Game.camera.gY = Game.camera.y * 32;
    }

    public void update() {
        moving = up || down || left || right;
        Action action = ctrl.action();

        if (Game.isLesson) {
            boolean isScript = false;
            switch (Game.time) {
                case 3:
                    x = 4;
                    y = 6;
                    rotate(1);
                    break;
                case 4:
                    x = 14;
                    y = 7;
                    rotate(0);
                    break;
                case 5:
                    if (!((LessonTypeC) Game.lesson).started) {
                        x = 19;
                        y = 11;
                        rotate(1);
                    }
                    else if (!Game.lesson.finished) {
                        LessonTypeC.movingScript(this);
                        isScript = true;
                    }
                    break;
                case 6:
                    x = 6;
                    y = 7;
                    rotate(0);
                    break;
                case 7:
                    x = 14;
                    y = 7;
                    rotate(0);
                    break;
            }
            if (!isScript) {
                gX = x * 32;
                gY = y * 32;
                Game.camera.x = x-(Constants.FRAME_WIDTH / 64);
                Game.camera.y = y-(Constants.FRAME_HEIGHT / 64);
                Game.camera.gX = Game.camera.x * 32;
                Game.camera.gY = Game.camera.y * 32;
            }
        }

        if (!moving && !Game.transition && Game.textBox == null && Game.menu == null) {
            if (action.up && y > 0 &&
                    Game.objectMatrix[y - 1][x] == null &&
                    !TileMapView.tiles.get(Game.tileMatrix[y - 1][x]).collision) { up = true; }

            if (action.down && y < Game.map.maxY &&
                    Game.objectMatrix[y + 1][x] == null &&
                    !TileMapView.tiles.get(Game.tileMatrix[y + 1][x]).collision) { down = true; }

            if (action.left && x > 0 &&
                    Game.objectMatrix[y][x - 1] == null &&
                    !TileMapView.tiles.get(Game.tileMatrix[y][x - 1]).collision) { left = true; }

            if (action.right && x < Game.map.maxX &&
                    Game.objectMatrix[y][x + 1] == null &&
                    !TileMapView.tiles.get(Game.tileMatrix[y][x + 1]).collision) { right = true; }
        }
        move();

        if (!this.moving && !Game.transition && Game.textBox == null && Game.menu == null) {
            if (action.up) { rotate(0); }
            if (action.down) { rotate(1); }
            if (action.left) { rotate(2); }
            if (action.right) { rotate(3); }
        }

        if (TileMapView.tiles.get(Game.tileMatrix[y][x]) instanceof DoorTile) { transition(); }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(tile.img, gX - Game.camera.gX, gY - Game.camera.gY, 32, 32, null);
    }
}
