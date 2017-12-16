package objects;

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
    public int id, defaultDirection;
    public boolean flip;
    public static final char KEY = 'N';

    public static List<List<Tile>> tiles = new ArrayList<>();
    public static List<String> text = new ArrayList<>();
    public List<BufferedImage> upSprites1;
    public List<BufferedImage> upSprites2;
    public List<BufferedImage> downSprites1;
    public List<BufferedImage> downSprites2;
    public List<BufferedImage> leftSprites;
    public List<BufferedImage> rightSprites;

    public String name;
    public static String[] names;

    static {
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/friend_athlete.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/friend_classmate.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/friend_nerd.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/friend_delinquent.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/friend_tutee.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/teacher_dt.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/teacher_ft.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/teacher_pe.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/teacher_science.png", KEY));
        tiles.add(CharacterTileSet.readTileSet("resources/tilesets/teacher_ict.png", KEY));
        names = new String[] { "Jack", "Emily", "Alexander", "Nathan", "Frankie", "Mr Reeves", "Ms Welfle", "Mr Lees", "Mr Nickson", "Ms Williams" };
        text.add("Yo, what's up?");
        text.add("Hiya! Do you like your new\nschool?");
        text.add("... Do you want something?");
        text.add("What do you want?");
        text.add("Oh hey! How's it goin'?");
        text.add("GONNA STUDY DT?");
        text.add("Hello, are you joining us? ");
        text.add("Wanna do some runnin'?");
        text.add("You're late... So, are you\njoining us?");
        text.add("Are you attending this\nclass??");
    }


    public NPC(int x, int y, int id, int direction) {
        super(tiles.get(id).get(direction), x, y);
        defaultDirection = direction;
        setSprites(id);
        name = names[id];
        this.id = id;
        flip = true;
        //rotate(direction);
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
                GAME.textBox = new TextBox(NPC.text.get(id), this, true);
                break;
            case 1:
                if (GAME.hasFood()) {
                    GAME.textBox = new TextBox(3, "You have an item you can#give to " + name + ".");
                    GAME.menu = new Menu(5);
                }
                else { GAME.textBox = new TextBox(NPC.text.get(id), this, true); }
                break;
            case 3:
                if (GAME.hasCraft()) {
                    GAME.textBox = new TextBox(3, "You have an item you can#give to " + name + ".");
                    GAME.menu = new Menu(5);
                }
                else { GAME.textBox = new TextBox(NPC.text.get(id), this, true); }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                GAME.textBox = new TextBox(NPC.text.get(id), this, false);
                GAME.menu = new Menu(5);
        }
    }

    public void gift(boolean yes) {
        if (yes) {
            if (id == 1) { GAME.menu = new Menu(6); }
            if (id == 3) { GAME.menu = new Menu(7); }
        }
        else {
            GAME.menu = null;
            GAME.textBox = new TextBox(NPC.text.get(id), this, true);
        }
    }

    public void gift(int index) {
        System.out.println(name + "\nBefore FP = " + GAME.friendValues[id]);
        switch (id) {
            case 1:
                GAME.items[2][index]--;
                GAME.textBox = new TextBox("Wow thanks!", this, true);
                break;
            case 3:
                GAME.items[1][index]--;
                GAME.textBox = new TextBox("...Thanks. Have an energy\ndrink, on me.", this, true);
                GAME.items[0][0]++;
        }
        GAME.menu = null;
        GAME.friendValues[id] += (index + 1);
        System.out.println(name + "\nAfter FP = " + GAME.friendValues[id]);

    }

    public void lesson(boolean yes) {
        if (yes) {
            GAME.doTransition();
            switch (id - 5) {
                case 3:
                case 4:
                    GAME.menu = new Menu(8);

            }
            Lesson.startLesson(id - 5);
            GAME.isLesson = true;
        }
        else {
            GAME.menu = null;
            GAME.textBox = null;
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
            if (player.x < x) {
                tile.img = leftSprites.get(0);
            } else {
                tile.img = rightSprites.get(0);
            }
        }
    }

    public void resetDirection(int defaultDirection) {
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
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        g.drawImage(tile.img, gX - GAME.camera.gX, gY - GAME.camera.gY, 32, 32, null);
    }
}
