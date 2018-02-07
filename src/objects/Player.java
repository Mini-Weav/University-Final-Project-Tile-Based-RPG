package objects;

import controllers.Action;
import controllers.Controller;
import game.Game;
import game.TileMap;
import game.TileMapView;
import lessons.LessonTypeC;
import utilities.*;
import utilities.Menu;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static game.Game.GAME;

/**
 * Created by Luke on 25/10/2017.
 */
public class Player extends GameObject {
    public Point direction;
    public int condition;

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
        GameAudio.playSfx(GameAudio.sfx_door);

        Point doorPoints = TileMapLoader.tileMaps.get(GAME.map.currentId).doorPoints.get(new Point(x, y)).s;

        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.map.currentId);
        try { for (NPC npc : currentMap.NPCs.get(GAME.time)) { npc.reset(); } }
        catch (NullPointerException e) {}
        int nextId = currentMap.doorPoints.get(new Point(x, y)).t;
        GAME.map.loadMap(TileMapLoader.tileMaps.get(nextId));

        setLocation(doorPoints.x, doorPoints.y);
        direction.setLocation(x, y - 1);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        gX = x * 32;
        gY = y * 32;
        GAME.camera.x = x - (Game.width / 64);
        GAME.camera.y = y - (Game.height / 64);
        GAME.camera.gX = GAME.camera.x * 32;
        GAME.camera.gY = GAME.camera.y * 32;
    }

    public void sleep() {
        condition = 0;
        GAME.newDayFeedback(2);
    }

    public void study() {
        GAME.textBox = null;
        GAME.menu = new Menu(15);
    }

    public void game() {
        double r = Math.random();
        if (r < 0.75) {
            GameAudio.playSfx(GameAudio.sfx_buff);
            condition =  1;
            GAME.newDayFeedback(1, 0);
        }
        else {
            GameAudio.playSfx(GameAudio.sfx_debuff);
            condition = 2;
            GAME.newDayFeedback(1, 1);
        }
    }

    public void hack() {

    }

    public void door() {
        char key = TileMapView.tiles.get(GAME.tileMatrix[y][x]).key;
        if (GAME.time == 10 && key != '$' && key != ' ' && key != '^' && key != 'v') {
            if (key == '£') {
                y--;
                gY -= 8;
                GAME.camera.y--;
                GAME.camera.gY -= 8;
            }
            else {
                y++;
                gY += 8;
                GAME.camera.y++;
                GAME.camera.gY += 8;
            }
            GAME.textBox = new TextBox(0, FileReader.menuStrings[47]);
        }
        else if (key == '£') {
            y--;
            gY -= 8;
            GAME.camera.y--;
            GAME.camera.gY -= 8;
            if (GAME.time == 2) {
                GAME.textBox = new TextBox(3, FileReader.menuStrings[48]);
                GAME.menu = new utilities.Menu(13);
            }
            else { GAME.textBox = new TextBox(0, FileReader.menuStrings[49]); }
        }
        else if (GAME.time != 10 && key == '$') {
            y++;
            gY += 8;
            GAME.camera.y++;
            GAME.camera.gY += 8;
        }
        else { transition(); }
    }

    public void update() {
        inPlay = !GAME.transition && GAME.textBox == null && GAME.menu == null && !spotted;
        moving = up || down || left || right;
        Action action = ctrl.action();

        if (GAME.lesson != null) {
            switch (GAME.time) {
                case 3:
                    setLocation(4, 6);
                    rotate(1);
                    break;
                case 4:
                    setLocation(14, 7);
                    rotate(0);
                    break;
                case 5:
                    if (!((LessonTypeC) GAME.lesson).started) {
                        setLocation(24, 16);
                        rotate(1);
                    }
                    else if (!GAME.lesson.finished) {
                        LessonTypeC.movingScript(this);
                    }
                    break;
                case 6:
                    setLocation(6, 7);
                    rotate(0);
                    break;
                case 7:
                    setLocation(14, 7);
                    rotate(0);
                    break;
            }
        }

        if (GAME.activity != null) {
            switch (GAME.activity.id) {
                case 0:
                    if (!GAME.activity.started) {
                        setLocation(24, 16);
                        rotate(1);
                        GAME.activity.started = true;
                    }
                    else if (!GAME.isAfterActivity){ LessonTypeC.movingScript(this); }
                    break;
                case 1:
                    setLocation(7, 7);
                    rotate(0);
                    break;
                case 2:
                    setLocation(17, 5);
                    rotate(0);
                    break;
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

        if (TileMapView.tiles.get(GAME.tileMatrix[y][x]) instanceof DoorTile) { door(); }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(tile.img, gX - GAME.camera.gX, gY - GAME.camera.gY, 32, 32, null);
    }
}
