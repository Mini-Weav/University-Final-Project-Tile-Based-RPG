package objects;

import controllers.Action;
import controllers.Controller;
import controllers.Patrol;
import game.*;
import lessons.Lesson;
import utilities.*;
import utilities.Menu;

import java.awt.*;
import java.util.*;
import java.util.List;

import static game.Game.GAME;

/**
 * 25/11/2017.
 */
public class NPC extends GameObject{
    private int id;
    private int defaultDirection;
    private int defaultX;
    private int defaultY;
    private boolean hostile;
    private String name;

    private static TreeMap<Integer, List<Tile>> tiles = new TreeMap<>();
    private static TreeMap<Integer, String[]> text = new TreeMap<>();
    private static String[] names;
    private static String[] lessons;

    static {
        tiles.put(0, CharacterTileSet.readTileSet("resources/tilesets/friend_athlete.png", getKEY()));
        tiles.put(1, CharacterTileSet.readTileSet("resources/tilesets/friend_classmate.png", getKEY()));
        tiles.put(2, CharacterTileSet.readTileSet("resources/tilesets/friend_nerd.png", getKEY()));
        tiles.put(3, CharacterTileSet.readTileSet("resources/tilesets/friend_delinquent.png", getKEY()));
        tiles.put(4, CharacterTileSet.readTileSet("resources/tilesets/friend_tutee.png", getKEY()));
        tiles.put(5, CharacterTileSet.readTileSet("resources/tilesets/teacher_dt.png", getKEY()));
        tiles.put(6, CharacterTileSet.readTileSet("resources/tilesets/teacher_ft.png", getKEY()));
        tiles.put(7, CharacterTileSet.readTileSet("resources/tilesets/teacher_pe.png", getKEY()));
        tiles.put(8, CharacterTileSet.readTileSet("resources/tilesets/teacher_science.png", getKEY()));
        tiles.put(9, CharacterTileSet.readTileSet("resources/tilesets/teacher_ict.png", getKEY()));
        tiles.put(10, CharacterTileSet.readTileSet("resources/tilesets/student_boy.png", getKEY()));
        tiles.put(20, CharacterTileSet.readTileSet("resources/tilesets/student_girl.png", getKEY()));
        tiles.put(30, CharacterTileSet.readTileSet("resources/tilesets/dinner_lady.png", getKEY()));

        names = new String[] { "Jack", "Emily", "Alexander", "Nathan", "Frankie", "Mr Hardman", "Ms Mason", "Mr Rodgers", "Mr Burgess", "Ms McCarthy" };
        lessons = new String[] { "DT", "food tech", "PE", "chemistry", "ICT" };

        /* Friend students*/
        text.put(0, FileReader.getJackStrings());
        text.put(1, FileReader.getEmilyStrings());
        text.put(2, FileReader.getAlexanderStrings());
        text.put(3, FileReader.getNathanStrings());
        text.put(4, FileReader.getFrankieStrings());
        /* Generic boy students */
        text.put(10, FileReader.getBoyStrings());
        /* Generic girl students */
        text.put(20, FileReader.getGirlStrings());
        text.put(30, FileReader.getLunchStrings());
    }


    public NPC(int x, int y, int id, int direction, boolean hostile, Controller ctrl) {
        super(tiles.get(id).get(direction), x, y);
        defaultDirection = direction;
        defaultX = x;
        defaultY = y;
        this.id = id;
        name = names[id];
        setSprites(id);
        this.hostile = hostile;
        this.setCtrl(ctrl);
        if (ctrl instanceof Patrol) { ((Patrol) ctrl).setObject(this); }
        setFlip(true);
    }

    public NPC(int x, int y, int id, int subId, int direction, boolean hostile, Controller ctrl) {
        super(tiles.get(id).get(direction), x, y);
        defaultDirection = direction;
        defaultX = x;
        defaultY = y;
        this.id = id + subId;
        name = "";
        setSprites(id);
        this.hostile = hostile;
        this.setCtrl(ctrl);
        if (ctrl instanceof Patrol) { ((Patrol) ctrl).setObject(this); }
        setFlip(true);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    private void setSprites(int id) {
        setUpSprites1(tiles.get(id));
        setUpSprites2(tiles.get(id));
        setDownSprites1(tiles.get(id));
        setDownSprites2(tiles.get(id));
        setLeftSprites(tiles.get(id));
        setRightSprites(tiles.get(id));
    }

    public void interaction(int time) {
        switch (id) {
            case 0:
            case 2:
            case 4:
                if (GAME.getTime() == 2 && GAME.getFriendValue(id) > 0 && !GAME.isSuspended()) { activityTextBox();}
                else { friendTextBox(); }
                break;
            case 1:
                if (GAME.hasLovelyCake() && GAME.getFriendValue(id) > 20) {
                    GAME.setTextBox(new TextBox(3, FileReader.getMenuString(42) + name + "."));
                    GAME.setMenu(new Menu(5));
                }
                else if (GAME.hasFood() && GAME.getFriendValue(id) > 0) {
                    GAME.setTextBox(new TextBox(3, FileReader.getMenuString(42) + name + "."));
                    GAME.setMenu(new Menu(5));
                }
                else if (GAME.isEmilyCrush()) {
                    setEmotion(new Emotion(4));
                    GAME.setTextBox(new TextBox(NPC.text.get(id)[4], this, true));
                }
                else { friendTextBox(); }
                break;
            case 3:
                if (GAME.hasCraft() && GAME.getFriendValue(id) > 0) {
                    GAME.setTextBox(new TextBox(3, FileReader.getMenuString(42) + name + "."));
                    GAME.setMenu(new Menu(5));
                }
                else if (GAME.getFriendValue(id) > 20 && GAME.canGetDrink()) {
                    freeDrink();
                }
                else { friendTextBox(); }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                if (GAME.getTime() != 11) {
                    GAME.setTextBox(new TextBox(3, FileReader.getMenuString(43) + lessons[id - 5] + "?"));
                    GAME.setMenu(new Menu(5));
                } else { GAME.setSpotted(true); }
                break;
            case 30:
                if (GAME.getPlayer().getCondition() == 0 && time == 1) {
                    GAME.setTextBox(new TextBox(3, FileReader.getMenuString(44)));
                    GAME.setMenu(new Menu(5));
                }
                else { GAME.setTextBox(new TextBox(NPC.text.get(id)[time], this, true)); }
                break;
            default:
                GAME.setTextBox(new TextBox(NPC.text.get((id / 10) * 10)[id % 10], this, true));
                break;
        }
    }

    public void gift(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            if (id == 1) {
                if (GAME.hasLovelyCake() && GAME.getFriendValue(id) >= 20) { GAME.setMenu(new Menu(16)); }
                else { GAME.setMenu(new Menu(6)); }
            }
            if (id == 3) { GAME.setMenu(new Menu(7)); }
        }
        else {
            GAME.setMenu(null);
            if (id == 3 && GAME.getFriendValue(id) >= 30 && GAME.canGetDrink()) { freeDrink(); }
            else { friendTextBox(); }
        }
    }

    public void giftTextBox(int index) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (id) {
            case 1:
                GAME.takeItem(2, index);
                if (index == 3 || GAME.isEmilyCrush()) {
                    if (!GAME.isEmilyCrush()) { GAME.setPoints((int) (GAME.getPoints() * 1.2));}
                    GAME.setEmilyCrush(true);
                    GAME.setTextBox(new TextBox(text.get(1)[3], this, true));
                    setEmotion(new Emotion(4));
                    GAME.increaseFriendValue(id, index + 1);
                }
                else {
                    GAME.setTextBox(new TextBox(text.get(1)[2], this, true));
                    setEmotion(new Emotion(2));
                }
                break;
            case 3:
                GAME.takeItem(1, index);
                GAME.setTextBox(new TextBox(text.get(3)[2], this, true));
                GAME.giveEnergyDrink();
                GAME.getPlayer().setEmotion(new Emotion(2));
                setEmotion(new Emotion(5));
                GameAudio.playSfx(GameAudio.sfx_item);
        }
        GAME.setMenu(null);
        GAME.increaseFriendValue(id, index + 1);
        GAME.increasePoints(0, id, index + 1);
    }

    private void freeDrink() {
        GAME.setTextBox(new TextBox(text.get(3)[2], this, true));
        GAME.giveEnergyDrink();
        GAME.setGivenDrink(true);
        GAME.getPlayer().setEmotion(new Emotion(2));
        GameAudio.playSfx(GameAudio.sfx_item);
    }

    public void lesson(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            GAME.doTransition();
            Lesson.startLesson(id - 5);
        }
        else {
            GAME.setMenu(null);
            GAME.setTextBox(null);
        }
    }

    public void lunch(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            double r = Math.random();
            if (r < 0.7) {
                GameAudio.playSfx(GameAudio.sfx_buff);
                GAME.getPlayer().setCondition(1);
                GAME.setTextBox(new TextBox(0, FileReader.getMenuString(45)));
                GAME.getPlayer().setEmotion(new Emotion(2));
            } else {
                GameAudio.playSfx(GameAudio.sfx_debuff);
                GAME.getPlayer().setCondition(2);
                GAME.setTextBox(new TextBox(0, FileReader.getMenuString(46)));
                GAME.getPlayer().setEmotion(new Emotion(3));
            }
        }
        else { GAME.setTextBox(null); }
        GAME.setMenu(null);
    }

    public void rotate(int direction) {
        switch (direction) {
            case 0:
                getTile().setImg(getUpSprite());
                break;
            case 1:
                getTile().setImg(getDownSprite());
                break;
            case 2:
                getTile().setImg(getLeftSprite());
                break;
            case 3:
                getTile().setImg(getRightSprite());
                break;
        }
    }

    public void rotate(Player player) {
        if (player.getX() == getX()) {
            if (player.getY() < getY()) {
                getTile().setImg(getUpSprite());
            } else {
                getTile().setImg(getDownSprite());
            }
        }
        if (player.getY() == getY()) {
            if (player.getX() < getX()) { getTile().setImg(getLeftSprite()); }
            else { getTile().setImg(getRightSprite()); }
        }
    }

    public void move() {
        int i;
        if (isUp()) {
            if (getY() * 32 == getGY()) { setY(getY() - 1); }
            setGY(getGY() - 8);
            i = (getGY() % 32) / 8;
            walkAnimation(0, i);
            if (getGY() % 32 == 0) {
                setUp(false);
                setFlip(isNotFlip());
            }
        }
        if (isDown()) {
            if (getY() * 32 == getGY()) { setY(getY() + 1); }
            setGY(getGY() + 8);
            i = (getGY() % 32) / 16;
            walkAnimation(1, i);
            if (getGY() % 32 == 0) {
                setDown(false);
                setFlip(isNotFlip());

            }
        }
        if (isLeft()) {
            if (getX() * 32 == getGX()) { setX(getX() - 1); }
            setGX(getGX() - 8);
            i = (getGX() % 32) / 8;
            walkAnimation(2, i);
            if (getGX() % 32 == 0) {
                setLeft(false);
            }
        }
        if (isRight()) {
            if (getX() * 32 == getGX()) { setX(getX() + 1); }
            setGX(getGX() + 8);
            i = (getGX() % 32)/16;
            walkAnimation(3, i);
            if (getGX() % 32 == 0) {
                setRight(false);
            }
        }
    }

    public void reset() {
        setX(defaultX);
        setY(defaultY);
        this.setGX(getX() * 32);
        this.setGY(getY() * 32);
        switch (defaultDirection) {
            case 0:
                getTile().setImg(getDownSprite());
                break;
            case 3:
                getTile().setImg(getRightSprite());
                break;
            case 5:
                getTile().setImg(getUpSprite());
                break;
            case 8:
                getTile().setImg(getLeftSprite());
                break;
        }
        if (this.getCtrl() instanceof Patrol) { ((Patrol) this.getCtrl()).reset(); }
    }

    private void friendTextBox() {
        int fp = 0;
        if (GAME.getFriendValue(id) > 0) { fp = 1; }
        else {
            GAME.increaseFriendValue(id, 1);
            GAME.increasePoints(0, id, 1);
        }
        GAME.setTextBox(new TextBox(NPC.text.get(id)[fp], this, true));
    }

    private void activityTextBox() {
        switch (id) {
            case 0:
                if (GAME.getGradeValue(2) > 9) {
                    GAME.setTextBox(new TextBox(text.get(0)[2], this, false));
                    GAME.setMenu(new Menu(5));
                }
                else { friendTextBox(); }
                break;
            case 2:
                if (GAME.getGradeValue(3) > 9) {
                    GAME.setTextBox(new TextBox(text.get(2)[2], this, false));
                    GAME.setMenu(new Menu(5));
                }
                else { friendTextBox(); }
                break;
            case 4:
                if (GAME.getGradeValue(4) > 9) {
                    GAME.setTextBox(new TextBox(text.get(4)[2], this, false));
                    GAME.setMenu(new Menu(5));
                }
                else { friendTextBox(); }
                break;
            default:
                friendTextBox();
                break;
        }
        GAME.resetDaysSince(id / 2);
    }

    public void activity(boolean yes) {
        GameAudio.playSfx(GameAudio.sfx_click);
        if (yes) {
            int gradeId;
            GAME.doTransition();
            switch (id) {
                case 0:
                    gradeId = 2;
                    break;
                case 2:
                    gradeId = 3;
                    break;
                case 4:
                    gradeId = 4;
                    break;
                default:
                    GAME.setTextBox(null);
                    GAME.setMenu(null);
                    return;
            }
            int increase = (GAME.getGradeValue(gradeId) / 10) + 1;
            GAME.increaseFriendValue(id, increase);
            GAME.increasePoints(0, id, increase);
            Activity.startActivity(id);
        }
        else {
            GAME.setTextBox(null);
            GAME.setMenu(null);
        }
    }

    private void lookForPlayer(int direction) {
        boolean clearPath = false;
        switch (direction) {
            case 0:
                if (getY() - 1 == GAME.getPlayer().getY() && getX() == GAME.getPlayer().getX()) {
                    spottedPlayer();
                    return;
                }
                else {
                    for (int i = getY() - 1; i > GAME.getPlayer().getY(); i--) {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(i, getX()))) { break; }
                        if (i == GAME.getPlayer().getY() + 1) { clearPath = true; }
                    }
                }
                if (clearPath && GAME.getPlayer().getX() == getX() && Math.abs(getY() - GAME.getPlayer().getY()) < 8) {
                    spottedPlayer();
                }
                break;
            case 1:
                if (getY() + 1 == GAME.getPlayer().getY() && getX() == GAME.getPlayer().getX()) {
                    spottedPlayer();
                    return;
                }
                else {
                    for (int i = getY() + 1; i < GAME.getPlayer().getY(); i++) {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(i, getX()))) { break; }
                        if (i == GAME.getPlayer().getY() - 1) { clearPath = true; }
                    }
                }
                if (clearPath && GAME.getPlayer().getX() == getX() && Math.abs(getY() - GAME.getPlayer().getY()) < 8) {
                    spottedPlayer();
                }
                break;
            case 2:
                if (getX() - 1 == GAME.getPlayer().getX() && getY() == GAME.getPlayer().getY()) {
                    spottedPlayer();
                    return;
                }
                else {
                    for (int i = getX() - 1; i > GAME.getPlayer().getX(); i--) {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(getY(), i))) { break; }
                        if (i == GAME.getPlayer().getX() + 1) { clearPath = true; }
                    }
                }
                if (clearPath && GAME.getPlayer().getY() == getY() && Math.abs(getX() - GAME.getPlayer().getX()) < 8) {
                    spottedPlayer();
                }
                break;
            case 3:
                if (getX() + 1 == GAME.getPlayer().getX() && getY() == GAME.getPlayer().getY()) {
                    spottedPlayer();
                    return;
                }
                else {
                    for (int i = getX() + 1; i < GAME.getPlayer().getX(); i++) {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(getY(), i))) { break; }
                        if (i == GAME.getPlayer().getX() - 1) { clearPath = true; }
                    }
                }
                if (clearPath && GAME.getPlayer().getY() == getY() && Math.abs(getX() - GAME.getPlayer().getX()) < 8) {
                    spottedPlayer();
                }
                break;
        }
    }

    private void walkToPlayer() {
        if (getY() < GAME.getPlayer().getY() - 1) { setDown(true); }
        else if (getY() > GAME.getPlayer().getY() + 1) { setUp(true); }
        else if (getX() < GAME.getPlayer().getX() - 1) { setRight(true); }
        else if (getX() > GAME.getPlayer().getX() + 1) { setLeft(true); }
        move();
    }

    private void spottedPlayer() {
        GAME.setSpotted(true);
        GAME.getPlayer().setSpotted(true);
        setSpotted(true);
        setCtrl(null);
        setEmotion(new Emotion(0));
    }

    private void colourTiles(int direction) {

        switch (direction) {
            case 0:
                for (int i = getY() - 1; i > getY() - 8; i--) {
                    try {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(i, getX())) ||
                                GAME.isDoorTile(GAME.getTileFromMatrix(i, getX()))) { break; }
                        GAME.setBadTile(i, getX());
                    } catch (NullPointerException e) {
                        //
                    }
                }
                break;
            case 1:
                for (int i = getY() + 1; i < getY() + 8; i++) {
                    try {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(i, getX())) ||
                                GAME.isDoorTile(GAME.getTileFromMatrix(i, getX()))) { break; }
                        GAME.setBadTile(i, getX());
                    } catch (NullPointerException e) {
                        //
                    }
                }
                break;
            case 2:
                for (int i = getX() - 1; i > getX() - 8; i--) {
                    try {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(getY(), i)) ||
                                GAME.isDoorTile(GAME.getTileFromMatrix(getY(), i))) { break; }
                        GAME.setBadTile(getY(), i);
                    } catch (NullPointerException e) {
                        //
                    }
                }
                break;
            case 3:
                for (int i = getX() + 1; i < getX() + 8; i++) {
                    try {
                        if (GAME.isCollideTile(GAME.getTileFromMatrix(getY(), i)) ||
                                GAME.isDoorTile(GAME.getTileFromMatrix(getY(), i))) { break; }
                        GAME.setBadTile(getY(), i);
                    } catch (NullPointerException e) {
                        //
                    }
                }
                break;
        }
    }

    public void update() {
        setInPlay(!GAME.isTransition() && GAME.getTextBox() == null && GAME.getMenu() == null && (!GAME.isSpotted() && !isSpotted()) && !GAME.hasLostHeist());
        setMoving(isUp() || isDown() || isLeft() || isRight());
        if (getCtrl() != null && isInPlay()) {
            Action action = getCtrl().action();
            rotate(action.getDirection());
            if (isNotMoving() && action.isMoving()) {
                if (action.getDirection() == 0 && getY() > 0 &&
                        GAME.isObjectNull(getY() - 1, getX()) &&
                        !GAME.isCollideTile(GAME.getTileFromMatrix(getY() - 1, getX())) &&
                        !(GAME.isDoorTile(GAME.getTileFromMatrix(getY() - 1, getX())))){ setUp(true); }

                if (action.getDirection() == 1 && getY() < GAME.getMapMaxY() &&
                        GAME.isObjectNull(getY() + 1, getX()) &&
                        !GAME.isCollideTile(GAME.getTileFromMatrix(getY() + 1, getX())) &&
                        !(GAME.isDoorTile(GAME.getTileFromMatrix(getY() + 1, getX())))){ setDown(true); }

                if (action.getDirection() == 2 && getX() > 0 &&
                        GAME.isObjectNull(getY(), getX() - 1) &&
                        !GAME.isCollideTile(GAME.getTileFromMatrix(getY(), getX() - 1)) &&
                        !(GAME.isDoorTile(GAME.getTileFromMatrix(getY(), getX() - 1)))){ setLeft(true); }

                if (action.getDirection() == 3 && getX() < GAME.getMapMaxX() &&
                        GAME.isObjectNull(getY(), getX() + 1) &&
                        !GAME.isCollideTile(GAME.getTileFromMatrix(getY(), getX() + 1)) &&
                        !(GAME.isDoorTile(GAME.getTileFromMatrix(getY(), getX() + 1)))){ setRight(true); }
            }
            if (hostile) {
                lookForPlayer(action.getDirection());
                colourTiles(action.getDirection());
            }
            move();
        }

        if (getEmotion() != null) { displayEmotion(); }

        if (getCtrl() == null && hostile && isInPlay() && !isSpotted()) {
            switch (defaultDirection) {
                case 0:
                    lookForPlayer(1);
                    colourTiles(1);
                    break;
                case 3:
                    lookForPlayer(3);
                    colourTiles(3);
                    break;
                case 5:
                    lookForPlayer(0);
                    colourTiles(0);
                    break;
                case 8:
                    lookForPlayer(2);
                    colourTiles(2);
                    break;
            }
        }

        if (isSpotted()) {
            setCtrl(null);
            if (getEmotion() == null) {
                walkToPlayer();
                setMoving(isUp() || isDown() || isLeft() || isRight());
                if (isNotMoving() && !GAME.hasLostHeist() && GAME.isSpotted()) { GAME.setTextBox(new TextBox(0, FileReader.getNpcString(35))); }
            }
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getTile().getImg(), getGX() - GAME.getCamera().getGX(), getGY() - GAME.getCamera().getGY(), 32, 32, null);
        if (getEmotion() != null) { getEmotion().paintComponent(g, this); }
    }
}
