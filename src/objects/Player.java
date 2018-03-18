package objects;

import controllers.Action;
import controllers.Controller;
import game.Game;
import game.TileMap;
import lessons.LessonTypeC;
import utilities.*;
import utilities.Menu;

import java.awt.*;
import java.util.List;

import static game.Game.GAME;

/**
 * Handles actions available to the player
 */
public class Player extends GameObject {
    public static final List<Tile> TILES = CharacterTileSet.readTileSet("/tilesets/player.png", getKEY());

    private Point direction;
    private int condition;

    /**
     * Class constructor.
     *
     * @param tile the GameObject's tile (for image and key)
     * @param x the starting x co-ordinate
     * @param y the starting y co-ordinate
     * @param ctrl the controller
     */
    public Player(Tile tile, int x, int y, Controller ctrl) {
        super(tile, x, y);
        setUpSprites1(TILES);
        setUpSprites2(TILES);
        setDownSprites1(TILES);
        setDownSprites2(TILES);
        setLeftSprites(TILES);
        setRightSprites(TILES);
        setFlip(true);
        direction = new Point(this.getX(), this.getY() + 1);
        this.setCtrl(ctrl);
    }

    public Point getDirection() { return direction; }

    public int getCondition() { return condition; }
    public void setCondition(int condition) { this.condition = condition; }

    /**
     * Moves the Player graphically and logically.
     */
    public void move() {
        int i;
        if (isUp()) {
            GAME.getCamera().setGY(GAME.getCamera().getGY() - 8);
            setGY(getGY() - 8);
            i = (getGY() % 32) / 8;
            walkAnimation(0, i);
            if (getGY() % 32 == 0 || GAME.isDoorTile(GAME.getTileFromMatrix(getY() - 1, getX()))) {
                GAME.getCamera().setY(GAME.getCamera().getY() - 1);
                setY(getY() - 1);
                setUp(false);
                setFlip(isNotFlip());
                direction.setLocation(getX(), getY() - 1);
            }
        }
        if (isDown()) {
            GAME.getCamera().setGY(GAME.getCamera().getGY() + 8);
            setGY(getGY() + 8);
            i = (getGY() % 32) / 16;
            walkAnimation(1, i);
            if (getGY() % 32 == 0 || GAME.isDoorTile(GAME.getTileFromMatrix(getY() + 1, getX()))) {
                GAME.getCamera().setY(GAME.getCamera().getY() + 1);
                setY(getY() + 1);
                setDown(false);
                setFlip(isNotFlip());
                direction.setLocation(getX(), getY() + 1);

            }
        }
        if (isLeft()) {
            GAME.getCamera().setGX(GAME.getCamera().getGX() - 8);
            setGX(getGX() - 8);
            i = (getGX() % 32) / 8;
            walkAnimation(2, i);
            if (getGX() % 32 == 0) {
                GAME.getCamera().setX(GAME.getCamera().getX() - 1);
                setX(getX() - 1);
                setLeft(false);
                direction.setLocation(getX() - 1, getY());
            }
        }
        if (isRight()) {
            GAME.getCamera().setGX(GAME.getCamera().getGX() + 8);
            setGX(getGX() + 8);
            i = (getGX() % 32)/16;
            walkAnimation(3, i);
            if (getGX() % 32 == 0) {
                GAME.getCamera().setX(GAME.getCamera().getX() + 1);
                setX(getX() + 1);
                setRight(false);
                direction.setLocation(getX() + 1, getY());
            }
        }
    }

    /**
     * Changes the Player's tile image.
     *
     * @param direction used to determine which image is used
     */
    public void rotate(int direction) {
        switch (direction) {
            case 0:
                getTile().setImg(getUpSprite());
                this.direction.setLocation(getX(), getY() - 1);
                break;
            case 1:
                getTile().setImg(getDownSprite());
                this.direction.setLocation(getX(), getY() + 1);
                break;
            case 2:
                getTile().setImg(getLeftSprite());
                this.direction.setLocation(getX() - 1, getY());
                break;
            case 3:
                getTile().setImg(getRightSprite());
                this.direction.setLocation(getX() + 1, getY());
                break;
        }
    }

    /**
     * Determines whether or not the Player can move through a DoorTile.
     */
    private void door() {
        char key = GAME.getTileFromMatrix(getY(), getX());
        if (GAME.getTime() == 10 && key != '$' && key != '^' && key != 'v') {
            if (key == ':' || key == ' ') {
                setY(getY() - 1);
                setGY(getGY() - 8);
                GAME.getCamera().setY(GAME.getCamera().getY() - 1);
                GAME.getCamera().setGY(GAME.getCamera().getGY() - 8);
                if (key == ' ') {
                    GAME.setTextBox(new TextBox(0, FileReader.getMenuString(57)));
                    return;
                }
            }
            else {
                setY(getY() + 1);
                setGY(getGY() + 8);
                GAME.getCamera().setY(GAME.getCamera().getY() + 1);
                GAME.getCamera().setGY(GAME.getCamera().getGY() + 8);
            }
            GAME.setTextBox(new TextBox(0, FileReader.getMenuString(47)));
        }
        else if (key == ':') {
            setY(getY() - 1);
            setGY(getGY() - 8);
            GAME.getCamera().setY(GAME.getCamera().getY() - 1);
            GAME.getCamera().setGY(GAME.getCamera().getGY() - 8);
            if (GAME.getTime() == 2) {
                GAME.setTextBox(new TextBox(3, FileReader.getMenuString(48)));
                GAME.setMenu(new Menu(13));
            }
            else { GAME.setTextBox(new TextBox(0, FileReader.getMenuString(49))); }
        }
        else if (GAME.getTime() != 10 && key == '$') {
            setY(getY() + 1);
            setGY(getGY() + 8);
            GAME.getCamera().setY(GAME.getCamera().getY() + 1);
            GAME.getCamera().setGY(GAME.getCamera().getGY() + 8);
        }
        else { transition(); }
    }

    /**
     * Loads the map and sets the Player's location based on the DoorTile moved through.
     */
    private void transition() {
        if (GAME.hasQuestions() && GAME.getTime() == 11) { GAME.endHeist(0); }
        GAME.doTransition();
        GameAudio.playSfx(GameAudio.sfx_door);

        Point doorPoint = TileMapLoader.tileMaps.get(GAME.getMapId()).getDoorPoint(new Point(getX(), getY())).getV();

        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.getMapId());
        currentMap.resetNPCs();
        int nextId = currentMap.getDoorPoint(new Point(getX(), getY())).getK();
        GAME.loadMap(TileMapLoader.tileMaps.get(nextId));

        setLocation(doorPoint.x, doorPoint.y);
        direction.setLocation(getX(), getY() - 1);
    }

    public void setLocation(int x, int y) {
        this.setX(x);
        this.setY(y);
        setGX(x * 32);
        setGY(y * 32);
        GAME.getCamera().setX(x - (Game.getWidth() / 64));
        GAME.getCamera().setY(y - (Game.getHeight() / 64));
        GAME.getCamera().setGX(GAME.getCamera().getX() * 32);
        GAME.getCamera().setGY(GAME.getCamera().getY() * 32);
    }

    /**
     * Sets the Player's condition to 0.
     */
    public void sleep() {
        condition = 0;
        GAME.newDayFeedback(2);
    }

    /**
     * Creates a menu for the player to choose a Lesson to study.
     */
    public void study() {
        GAME.setTextBox(null);
        GAME.setMenu(new Menu(15));
    }

    /**
     * Gives player a chance to buff or debuff.
     */
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

    /**
     * Moves the Heist state into the second phase
     */
    public void hack() {
        GameAudio.playSfx(GameAudio.sfx_pcHack);
        GAME.setGotQuestions(true);
        GAME.giveQuestions();
        GAME.setTime(11);
        GAME.setMenu(null);
    }

    /**
     * Determines what actions the Player is doing.
     */
    public void update() {
        setInPlay(!GAME.isTransition() && GAME.getTextBox() == null && GAME.getMenu() == null && !isSpotted());
        setMoving(isUp() || isDown() || isLeft() || isRight());
        Action action = getCtrl().action();

        if (GAME.getLesson() != null) {
            switch (GAME.getTime()) {
                case 3:
                    setLocation(4, 6);
                    rotate(1);
                    break;
                case 4:
                    setLocation(14, 7);
                    rotate(0);
                    break;
                case 5:
                    if (!((LessonTypeC) GAME.getLesson()).isStarted()) {
                        setLocation(24, 16);
                        rotate(1);
                    }
                    else if (GAME.getLesson().isNotFinished()) {
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
        if (GAME.getExam() != null) {
            switch (GAME.getTime()) {
                case 12:
                    setLocation(13, 7);
                    rotate(0);
                    break;
                case 13:
                    setLocation(6, 5);
                    rotate(0);
                    break;
                case 14:
                case 16:
                    setLocation(14, 7);
                    rotate(0);
                    break;
                case 15:
                    setLocation(6, 7);
                    rotate(0);
                    break;
            }
        }
        if (GAME.getActivity() != null) {
            switch (GAME.getActivity().getId()) {
                case 0:
                    if (!GAME.getActivity().isStarted()) {
                        setLocation(24, 16);
                        rotate(1);
                        GAME.getActivity().setStarted(true);
                    }
                    else if (!GAME.isAfterActivity()){ LessonTypeC.movingScript(this); }
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
        if (isNotMoving() && isInPlay() && action.isMoving()) {
            if (action.getDirection() == 0 && getY() > 0 &&
                    GAME.isObjectNull(getY() - 1, getX()) &&
                    !GAME.isCollideTile(GAME.getTileFromMatrix(getY() - 1, getX()))) { setUp(true); }

            if (action.getDirection() == 1 && getY() < GAME.getMapMaxY() &&
                    GAME.isObjectNull(getY() + 1, getX()) &&
                    !GAME.isCollideTile(GAME.getTileFromMatrix(getY() + 1, getX()))) { setDown(true); }

            if (action.getDirection() == 2 && getX() > 0 &&
                    GAME.isObjectNull(getY(), getX() - 1) &&
                    !GAME.isCollideTile(GAME.getTileFromMatrix(getY(), getX() - 1))) { setLeft(true); }

            if (action.getDirection() == 3 && getX() < GAME.getMapMaxX() &&
                    GAME.isObjectNull(getY(), getX() + 1) &&
                    !GAME.isCollideTile(GAME.getTileFromMatrix(getY(), getX() + 1))) { setRight(true); }
        }
        move();
        if (this.isNotMoving() && isInPlay()) {
            if (action.getDirection() >= 0) { rotate(action.getDirection()); }
        }
        if (GAME.isDoorTile(GAME.getTileFromMatrix(getY(), getX()))) { door(); }
        if (getEmotion() != null) { displayEmotion(); }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getTile().getImg(), getGX() - GAME.getCamera().getGX(), getGY() - GAME.getCamera().getGY(),32, 32, null);
        if (getEmotion() != null) { getEmotion().paintComponent(g, this); }
    }
}
