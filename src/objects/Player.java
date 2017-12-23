package objects;

import controllers.Action;
import controllers.Controller;
import game.Game;
import game.TileMap;
import game.TileMapView;
import lessons.LessonTypeC;
import utilities.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.Game.GAME;

/**
 * Created by Luke on 25/10/2017.
 */
public class Player extends GameObject {
    public Point direction;

    public static final List<Tile> TILES = CharacterTileSet.readTileSet("resources/tilesets/player.png", KEY);

    public Player(Tile tile, int x, int y, Controller ctrl) {
        super(tile, x, y);
        upSprites1 = Arrays.asList(TILES.get(5).img, TILES.get(6).img, TILES.get(6).img, TILES.get(5).img);
        upSprites2 = Arrays.asList(TILES.get(5).img, TILES.get(7).img, TILES.get(7).img, TILES.get(5).img);
        downSprites1 = Arrays.asList(TILES.get(0).img, TILES.get(1).img);
        downSprites2 = Arrays.asList(TILES.get(0).img, TILES.get(2).img);
        leftSprites = Arrays.asList(TILES.get(8).img, TILES.get(9).img, TILES.get(9).img, TILES.get(8).img);
        rightSprites = Arrays.asList(TILES.get(3).img, TILES.get(4).img);
        flip = true;
        direction = new Point(this.x, this.y + 1);
        this.ctrl = ctrl;
    }

    public void rotate(int direction) {
        switch (direction) {
            case 0:
                tile.img = upSprites1.get(0);
                this.direction.setLocation(x, y - 1);
                break;
            case 1:
                tile.img = downSprites1.get(0);
                this.direction.setLocation(x, y + 1);
                break;
            case 2:
                tile.img = leftSprites.get(0);
                this.direction.setLocation(x - 1, y);
                break;
            case 3:
                tile.img = rightSprites.get(0);
                this.direction.setLocation(x + 1, y);
                break;
        }
    }

    public void move() {
        int i;
        if (up) {
            GAME.camera.gY -= 8;
            gY -= 8;
            i = (gY % 32) / 8;
            walkAnimation(0, i);
            if (gY % 32 == 0 || TileMapView.tiles.get(GAME.tileMatrix[y - 1][x]) instanceof DoorTile) {
                GAME.camera.y--;
                y--;
                up = false;
                flip = !flip;
                direction.setLocation(x, y - 1);
            }
        }
        if (down) {
            GAME.camera.gY += 8;
            gY += 8;
            i = (gY % 32) / 16;
            walkAnimation(1, i);
            if (gY % 32 == 0 || TileMapView.tiles.get(GAME.tileMatrix[y + 1][x]) instanceof DoorTile) {
                GAME.camera.y++;
                y++;
                down = false;
                flip = !flip;
                direction.setLocation(x, y + 1);

            }
        }
        if (left) {
            GAME.camera.gX -= 8;
            gX -= 8;
            i = (gX % 32) / 8;
            walkAnimation(2, i);
            if (gX % 32 == 0) {
                GAME.camera.x--;
                x--;
                left = false;
                direction.setLocation(x - 1, y);
            }
        }
        if (right) {
            GAME.camera.gX += 8;
            gX += 8;
            i = (gX % 32)/16;
            walkAnimation(3, i);
            if (gX % 32 == 0) {
                GAME.camera.x++;
                x++;
                right = false;
                direction.setLocation(x + 1, y);
            }
        }
    }

    public void transition() {
        GAME.doTransition();

        Point doorPoints = TileMapLoader.tileMaps.get(GAME.map.currentId).doorPoints.get(new Point(x, y)).s;
        //if (GAME.tileMatrix.get(this.y).get(this.x) =='^') { this.tile.img = downSprites1.get(0); }
        //if (GAME.tileMatrix.get(this.y).get(this.x) =='v') { this.tile.img = downSprites1.get(0); }

        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.map.currentId);
        for (NPC npc : currentMap.NPCs.get(GAME.time)) {
            npc.reset();
        }
        int nextId = currentMap.doorPoints.get(new Point(x, y)).t;
        GAME.map.loadMap(TileMapLoader.tileMaps.get(nextId));

        x = doorPoints.x;
        y = doorPoints.y;
        gX = x * 32;
        gY = y * 32;

        GAME.camera.x = x-(Game.width / 64);
        GAME.camera.y = y-(Game.height / 64);
        GAME.camera.gX = GAME.camera.x * 32;
        GAME.camera.gY = GAME.camera.y * 32;
    }

    public void update() {
        inPlay = !GAME.transition && GAME.textBox == null && GAME.menu == null;
        moving = up || down || left || right;
        Action action = ctrl.action();

        if (GAME.lesson != null) {
            boolean isScript = false;
            switch (GAME.time) {
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
                    if (!((LessonTypeC) GAME.lesson).started) {
                        x = 24;
                        y = 16;
                        rotate(1);
                    }
                    else if (!GAME.lesson.finished) {
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
                GAME.camera.x = x-(Game.width / 64);
                GAME.camera.y = y-(Game.height / 64);
                GAME.camera.gX = GAME.camera.x * 32;
                GAME.camera.gY = GAME.camera.y * 32;
            }
        }

        if (!moving && inPlay && !action.stop) {
            if (action.direction == 0 && y > 0 &&
                    GAME.objectMatrix[y - 1][x] == null &&
                    !TileMapView.tiles.get(GAME.tileMatrix[y - 1][x]).collision) { up = true; }

            if (action.direction == 1 && y < GAME.map.maxY &&
                    GAME.objectMatrix[y + 1][x] == null &&
                    !TileMapView.tiles.get(GAME.tileMatrix[y + 1][x]).collision) { down = true; }

            if (action.direction == 2 && x > 0 &&
                    GAME.objectMatrix[y][x - 1] == null &&
                    !TileMapView.tiles.get(GAME.tileMatrix[y][x - 1]).collision) { left = true; }

            if (action.direction == 3 && x < GAME.map.maxX &&
                    GAME.objectMatrix[y][x + 1] == null &&
                    !TileMapView.tiles.get(GAME.tileMatrix[y][x + 1]).collision) { right = true; }
        }
        move();

        if (!this.moving && inPlay) {
            if (action.direction >= 0) { rotate(action.direction); }
        }

        if (TileMapView.tiles.get(GAME.tileMatrix[y][x]) instanceof DoorTile) {
            if (TileMapView.tiles.get(GAME.tileMatrix[y][x]).key == '£') {
                y--;
                gY -= 8;
                GAME.camera.y--;
                GAME.camera.gY -= 8;
                if (GAME.time == 2) {
                    GAME.textBox = new TextBox(3, "Should I go home for today?");
                    GAME.menu = new utilities.Menu(13);
                }
                else { GAME.textBox = new TextBox(0, "School isn't over yet!"); }
            }
            else { transition(); }
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(tile.img, gX - GAME.camera.gX, gY - GAME.camera.gY, 32, 32, null);
    }
}
