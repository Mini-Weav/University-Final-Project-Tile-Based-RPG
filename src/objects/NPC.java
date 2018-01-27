package objects;

import controllers.Action;
import controllers.Controller;
import controllers.Patrol;
import controllers.RandomMovement;
import game.Activity;
import game.Game;
import game.TileMapView;
import lessons.Lesson;
import utilities.*;
import utilities.Menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static game.Game.GAME;

/**
 * Created by lmweav on 25/11/2017.
 */
public class NPC extends GameObject{
    public int id, defaultDirection, defaultX, defaultY;
    public String name;

    public static TreeMap<Integer, List<Tile>> tiles = new TreeMap<>();
    public static TreeMap<Integer, String[]> text = new TreeMap<>();
    public static String[] names, lessons;

    static {
        tiles.put(0, CharacterTileSet.readTileSet("resources/tilesets/friend_athlete.png", KEY));
        tiles.put(1, CharacterTileSet.readTileSet("resources/tilesets/friend_classmate.png", KEY));
        tiles.put(2, CharacterTileSet.readTileSet("resources/tilesets/friend_nerd.png", KEY));
        tiles.put(3, CharacterTileSet.readTileSet("resources/tilesets/friend_delinquent.png", KEY));
        tiles.put(4, CharacterTileSet.readTileSet("resources/tilesets/friend_tutee.png", KEY));
        tiles.put(5, CharacterTileSet.readTileSet("resources/tilesets/teacher_dt.png", KEY));
        tiles.put(6, CharacterTileSet.readTileSet("resources/tilesets/teacher_ft.png", KEY));
        tiles.put(7, CharacterTileSet.readTileSet("resources/tilesets/teacher_pe.png", KEY));
        tiles.put(8, CharacterTileSet.readTileSet("resources/tilesets/teacher_science.png", KEY));
        tiles.put(9, CharacterTileSet.readTileSet("resources/tilesets/teacher_ict.png", KEY));
        tiles.put(10, CharacterTileSet.readTileSet("resources/tilesets/student_boy.png", KEY));
        tiles.put(20, CharacterTileSet.readTileSet("resources/tilesets/student_girl.png", KEY));
        tiles.put(30, CharacterTileSet.readTileSet("resources/tilesets/dinner_lady.png", KEY));

        names = new String[] { "Jack", "Emily", "Alexander", "Nathan", "Frankie", "Mr Hardman", "Ms Mason", "Mr Rodgers", "Mr Burgess", "Ms McCarthy" };
        lessons = new String[] { "DT", "food tech", "PE", "chemistry", "ICT" };

        /* Friend students*/
        text.put(0, FileReader.jackStrings);
        text.put(1, FileReader.emilyStrings);
        text.put(2, FileReader.alexanderStrings);
        text.put(3, FileReader.nathanStrings);
        text.put(4, FileReader.frankieStrings);
        /* Generic boy students */
        text.put(10, FileReader.boyStrings);
        /* Generic girl students */
        text.put(20, FileReader.girlStrings);
        text.put(30, FileReader.lunchStrings);
    }


    public NPC(int x, int y, int id, int direction, Controller ctrl) {
        super(tiles.get(id).get(direction), x, y);
        defaultDirection = direction;
        defaultX = x;
        defaultY = y;
        this.id = id;
        name = names[id];
        setSprites(id);
        this.ctrl = ctrl;
        if (ctrl instanceof Patrol) { ((Patrol) ctrl).object = this; }
        flip = true;
    }

    public NPC(int x, int y, int id, int subId, int direction, Controller ctrl) {
        super(tiles.get(id).get(direction), x, y);
        defaultDirection = direction;
        defaultX = x;
        defaultY = y;
        this.id = id + subId;
        name = "";
        setSprites(id);
        this.ctrl = ctrl;
        if (ctrl instanceof Patrol) { ((Patrol) ctrl).object = this; }
        flip = true;
    }

    public void setSprites(int id) {
        upSprites1 = Arrays.asList(tiles.get(id).get(5).img, tiles.get(id).get(6).img, tiles.get(id).get(6).img, tiles.get(id).get(5).img);
        upSprites2 = Arrays.asList(tiles.get(id).get(5).img, tiles.get(id).get(7).img, tiles.get(id).get(7).img, tiles.get(id).get(5).img);
        downSprites1 = Arrays.asList(tiles.get(id).get(0).img, tiles.get(id).get(1).img);
        downSprites2 = Arrays.asList(tiles.get(id).get(0).img, tiles.get(id).get(2).img);
        leftSprites = Arrays.asList(tiles.get(id).get(8).img, tiles.get(id).get(9).img, tiles.get(id).get(9).img, tiles.get(id).get(8).img);
        rightSprites = Arrays.asList(tiles.get(id).get(3).img, tiles.get(id).get(4).img);
    }

    public void interaction(int time) {
        switch (id) {
            case 0:
            case 2:
            case 4:
                if (GAME.time == 2 && GAME.friendValues[id] > 0) { activityTextBox();}
                else { friendTextBox(); }
                break;
            case 1:
                if (GAME.hasFood() && GAME.friendValues[id] > 0) {
                    GAME.textBox = new TextBox(3, "You have an item you can#give to " + name + ".");
                    GAME.menu = new Menu(5);
                }
                else { friendTextBox(); }
                break;
            case 3:
                if (GAME.hasCraft() && GAME.friendValues[id] > 0) {
                    GAME.textBox = new TextBox(3, "You have an item you can#give to " + name + ".");
                    GAME.menu = new Menu(5);
                }
                else if (GAME.friendValues[id] >= 30 && !GAME.givenDrink) {
                    freeDrink();
                }
                else { friendTextBox(); }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                GAME.textBox = new TextBox(3, "Should I attend " + lessons[id - 5] + "?");
                GAME.menu = new Menu(5);
                break;
            case 30:
                if (GAME.player.condition == 0 && time == 1) {
                    GAME.textBox = new TextBox(3, "Should I eat lunch?");
                    GAME.menu = new Menu(5);
                }
                else { GAME.textBox = new TextBox(NPC.text.get(id)[time], this, true); }
                break;
            default:
                GAME.textBox = new TextBox(NPC.text.get((id / 10) * 10)[id % 10], this, true);
                break;
        }
    }

    public void gift(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            if (id == 1) { GAME.menu = new Menu(6); }
            if (id == 3) { GAME.menu = new Menu(7); }
        }
        else {
            GAME.menu = null;
            if (id == 3 && GAME.friendValues[id] >= 30 && !GAME.givenDrink) { freeDrink(); }
            else { friendTextBox(); }
        }
    }

    public void giftTextBox(int index) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (id) {
            case 1:
                GAME.items[2][index]--;
                GAME.textBox = new TextBox(text.get(1)[2], this, true);
                break;
            case 3:
                GAME.items[1][index]--;
                GAME.textBox = new TextBox(text.get(3)[2], this, true);
                GAME.items[0][0]++;
                GameAudio.playSfx(GameAudio.sfx_item);
        }
        GAME.menu = null;
        GAME.friendValues[id] += (index + 1);
        GAME.increasePoints(0, id, index + 1);
    }

    public void freeDrink() {
        GAME.textBox = new TextBox(text.get(3)[2], this, true);
        GAME.items[0][0]++;
        GAME.givenDrink = true;
        GameAudio.playSfx(GameAudio.sfx_item);
    }

    public void lesson(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            GAME.doTransition();
            Lesson.startLesson(id - 5);
        }
        else {
            GAME.menu = null;
            GAME.textBox = null;
        }
    }

    public void lunch(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            double r = Math.random();
            if (r < 0.7) {
                GameAudio.playSfx(GameAudio.sfx_buff);
                GAME.player.condition = 1;
                GAME.textBox = new TextBox(0, "The lunch is good! You feel #recharged and ready for the #next class!");
            } else {
                GameAudio.playSfx(GameAudio.sfx_debuff);
                GAME.player.condition = 2;
                GAME.textBox = new TextBox(0, "The lunch is bad! You don't #feel very well...");
            }
        }
        else { GAME.textBox = null; }
        GAME.menu = null;
    }

    public void rotate(int direction) {
        switch (direction) {
            case 0:
                tile.img = upSprites1.get(0);
                break;
            case 1:
                tile.img = downSprites1.get(0);
                break;
            case 2:
                tile.img = leftSprites.get(0);
                break;
            case 3:
                tile.img = rightSprites.get(0);
                break;
        }
    }

    public void rotate(Player player) {
        if (player.x == x) {
            if (player.y < y) {
                tile.img = upSprites1.get(0);
            } else {
                tile.img = downSprites1.get(0);
            }
        }
        if (player.y == y) {
            if (player.x < x) { tile.img = leftSprites.get(0); }
            else { tile.img = rightSprites.get(0); }
        }
    }

    public void move() {
        int i;
        if (up) {
            if (y * 32 == gY) { y--; }
            gY -= 8;
            i = (gY % 32) / 8;
            walkAnimation(0, i);
            if (gY % 32 == 0) {
                up = false;
                flip = !flip;
            }
        }
        if (down) {
            if (y * 32 == gY) { y++; }
            gY += 8;
            i = (gY % 32) / 16;
            walkAnimation(1, i);
            if (gY % 32 == 0) {
                down = false;
                flip = !flip;

            }
        }
        if (left) {
            if (x * 32 == gX) { x--; }
            gX -= 8;
            i = (gX % 32) / 8;
            walkAnimation(2, i);
            if (gX % 32 == 0) {
                left = false;
            }
        }
        if (right) {
            if (x * 32 == gX) { x++; }
            gX += 8;
            i = (gX % 32)/16;
            walkAnimation(3, i);
            if (gX % 32 == 0) {
                right = false;
            }
        }
    }

    public void reset() {
        x = defaultX;
        y = defaultY;
        this.gX = x * 32;
        this.gY = y * 32;
        switch (defaultDirection) {
            case 0:
                tile.img = downSprites1.get(0);
                break;
            case 3:
                tile.img = rightSprites.get(0);
                break;
            case 5:
                tile.img = upSprites1.get(0);
                break;
            case 8:
                tile.img = leftSprites.get(0);
                break;
        }
        if (this.ctrl instanceof Patrol) { ((Patrol) this.ctrl).reset(); }
    }

    public void friendTextBox() {
        int fp = 0;
        if (GAME.friendValues[id] > 0) { fp = 1; }
        else {
            GAME.friendValues[id]++;
            GAME.increasePoints(0, id, 1);
        }
        GAME.textBox = new TextBox(NPC.text.get(id)[fp], this, true);
    }

    public void activityTextBox() {
        switch (id) {
            case 0:
                if (GAME.gradeValues[2] > 9) {
                    GAME.textBox = new TextBox(text.get(0)[2], this, false);
                    GAME.menu = new Menu(5);
                }
                else { friendTextBox(); }
                break;
            case 2:
                if (GAME.gradeValues[3] > 9) {
                    GAME.textBox = new TextBox(text.get(2)[2], this, false);
                    GAME.menu = new Menu(5);
                }
                else { friendTextBox(); }
                break;
            case 4:
                if (GAME.gradeValues[4] > 9) {
                    GAME.textBox = new TextBox(text.get(4)[2], this, false);
                    GAME.menu = new Menu(5);
                }
                else { friendTextBox(); }
                break;
            default:
                friendTextBox();
                break;
        }
    }

    public void activity(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            int activityId;
            int gradeId;
            GAME.doTransition();
            switch (id) {
                case 0:
                    activityId = 0;
                    gradeId = 2;
                    break;
                case 2:
                    activityId = 1;
                    gradeId = 3;
                    break;
                case 4:
                    activityId = 2;
                    gradeId = 4;
                    break;
                default:
                    GAME.textBox = null;
                    GAME.menu = null;
                    return;
            }
            int increase = (GAME.gradeValues[gradeId] / 10) + 1;
            GAME.friendValues[id] += increase;
            GAME.increasePoints(0, id, increase);
            Activity.startActivity(activityId);
        }
        else {
            GAME.textBox = null;
            GAME.menu = null;
        }

    }

    public void update() {
        inPlay = !GAME.transition && GAME.textBox == null && GAME.menu == null;
        moving = up || down || left || right;
        if (ctrl != null && inPlay) {
            Action action = ctrl.action();
            rotate(action.direction);
            if (!moving && !action.stop) {
                if (action.direction == 0 && y > 0 &&
                        GAME.objectMatrix[y - 1][x] == null &&
                        !TileMapView.tiles.get(GAME.tileMatrix[y - 1][x]).collision &&
                        !(TileMapView.tiles.get(GAME.tileMatrix[y - 1][x]) instanceof DoorTile)){ up = true; }

                if (action.direction == 1 && y < GAME.map.maxY &&
                        GAME.objectMatrix[y + 1][x] == null &&
                        !TileMapView.tiles.get(GAME.tileMatrix[y + 1][x]).collision &&
                        !(TileMapView.tiles.get(GAME.tileMatrix[y + 1][x]) instanceof DoorTile)){ down = true; }

                if (action.direction == 2 && x > 0 &&
                        GAME.objectMatrix[y][x - 1] == null &&
                        !TileMapView.tiles.get(GAME.tileMatrix[y][x - 1]).collision &&
                        !(TileMapView.tiles.get(GAME.tileMatrix[y][x - 1]) instanceof DoorTile)){ left = true; }

                if (action.direction == 3 && x < GAME.map.maxX &&
                        GAME.objectMatrix[y][x + 1] == null &&
                        !TileMapView.tiles.get(GAME.tileMatrix[y][x + 1]).collision &&
                        !(TileMapView.tiles.get(GAME.tileMatrix[y][x + 1]) instanceof DoorTile)){ right = true; }
            }
            move();
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(tile.img, gX - GAME.camera.gX, gY - GAME.camera.gY, 32, 32, null);
    }
}
