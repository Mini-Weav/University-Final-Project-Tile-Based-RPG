package game;

import lessons.Exam;
import lessons.Lesson;
import objects.*;
import utilities.GameAudio;
import utilities.TextBox;
import utilities.Menu;
import utilities.TileMapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static game.Game.GAME;

/**
 * Renders the game and stores current tile map information
 */
class TileMapView extends JComponent implements MouseListener, MouseMotionListener {
    private int currentId;
    private int miniMapId;
    private int maxX;
    private int maxY;
    private List<List<Character>> matrix;
    private TreeMap<Character, Tile> tiles;

    /**
     * Class constructor.
     *
     * @param map the current tile map in the game
     */
    TileMapView(TileMap map) {
        loadMap(map);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    int getCurrentId() { return currentId; }

    int getMiniMapId() { return miniMapId; }

    int getMaxX() { return maxX; }

    int getMaxY() { return maxY; }

    int getMatrixRows() { return matrix.size(); }
    int getMatrixCols() { return matrix.get(0).size(); }
    List<Character> getMatrixLine(int index) { return matrix.get(index); }

    TreeMap<Character, Tile> getTiles() { return tiles; }

    /**
     * Reads and stores the current TileMap information from its text file
     *
     * @param txtFile the current map's text file
     * @return a 2-dimensional List of Characters that represent's the current map's text file
     * @throws IOException if the map's text file cannot be read or found
     */
    private static List<List<Character>> readMap(File txtFile) throws IOException {
        String line;
        List<List<Character>> matrix = new ArrayList<>();
        FileReader fr = new FileReader(txtFile);
        BufferedReader br = new BufferedReader(fr);

        while ( (line = br.readLine()) != null ) {
            List<Character> row = new ArrayList<>();
            for (char ch : line.toCharArray()) { row.add(ch); }
            matrix.add(row);
        }
        return matrix;
    }

    /**
     * Loads the supplied TileMap into the game.
     * Updates various fields and the game's matrices to represent the new map.
     *
     * @param map the TileMap to be loaded into the game.
     */
    void loadMap(TileMap map) {
        try {
            this.matrix = readMap(map.getTxtFile());
            maxX = matrix.get(0).size() - 1;
            maxY = matrix.size() - 1;
            tiles = map.copyTiles();
            currentId = map.getId();
            miniMapId = map.getMiniMapId();
            GAME.setTileMatrix(matrix.size(), matrix.get(0).size());
            GAME.setBadTileMatrix(matrix.size(), matrix.get(0).size());
            GAME.setObjectMatrix(matrix.size(), matrix.get(0).size());
            Menu.setIconPoint(new Point(map.getIconPoint()));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find map txt file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot read line.");
            e.printStackTrace();
        }
        if (map.getId() == 0) { GAME.updateAirVent(); }
    }

    /**
     * Renders the Tiles and GameObjects within range of the game's camera, as well as any active Menu and TextBox.
     *
     * @param g the graphics
     */
    public void paintComponent(Graphics g) {
        if (paintTransition(g)) { return; }
        for (int j = -1; j < Game.getCameraHeight() + 2; j++) {
            for (int i = -1; i < Game.getCameraWidth() + 2; i++) {
                try {
                    Tile tile = tiles.get(matrix.get(GAME.getCamera().getY() + j).get(GAME.getCamera().getX() + i));
                    g.drawImage(tile.getImg(), i * 32 - GAME.getCamera().getDiffX(),
                            j * 32 - GAME.getCamera().getDiffY(), 32, 32, null);
                    if (GAME.isBadTile(GAME.getCamera().getY() + j, GAME.getCamera().getX() + i)) {
                        g.setColor(new Color(255, 0, 0, 48));
                        g.fillRect(i * 32 - GAME.getCamera().getDiffX(), j * 32 - GAME.getCamera().getDiffY(), 32, 32);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Tile tile = tiles.get('#');
                    g.drawImage(tile.getImg(), i * 32 - GAME.getCamera().getDiffX(),
                            j * 32 - GAME.getCamera().getDiffY(), 32, 32, null);
                } catch (NullPointerException e) { /* Do nothing */ }
            }
        }
        synchronized (Game.class) {
            for (int i = 0; i < GAME.getNumberOfObjects(); i++) {
                GameObject object = GAME.getObject(i);
                if (object.getX() >= GAME.getCamera().getX() - 1 &&
                        object.getX() <= GAME.getCamera().getX() + Game.getCameraWidth() + 1 &&
                        object.getY() >= GAME.getCamera().getY() - 1 &&
                        object.getY() <= GAME.getCamera().getY() + Game.getCameraHeight() + 1) {
                    object.paintComponent(g);
                }
            }
            if (GAME.getStatusMenu() != null) { GAME.getStatusMenu().paintComponent(g); }
            if (GAME.getMenu() != null) {
                if (GAME.getMenu().isVisible()) {
                    GAME.getMenu().paintComponent(g);
                }
            }
            if (GAME.getTextBox() != null) { GAME.getTextBox().paintComponent(g); }
        }
    }

    /**
     * Renders the transition screens between various game states
     *
     * @param g the graphics
     * @return whether or not it is currently a state transition
     */
    private boolean paintTransition(Graphics g) {
        String text = null;
        if (GAME.isNewGame()) { text = utilities.FileReader.getMenuString(54); }
        if (GAME.isNewDay()) { text = GAME.getNewDayText(); }
        if (GAME.isAfterActivity()) { text = GAME.getActivity().getAfterText(); }
        if (GAME.isHeist()) { text = utilities.FileReader.getMenuString(50); }
        if (GAME.hasGotAnswers()) { text = utilities.FileReader.getMenuString(52); }
        if (GAME.hasLostHeist()) { text = utilities.FileReader.getMenuString(55); }
        if (GAME.hasWonHeist()) { text = utilities.FileReader.getMenuString(56); }
        if (text != null) {
            g.fillRect(0, 0, Game.getWidth() * 2, Game.getHeight() * 2);
            GAME.setTextBox(new TextBox(0, text));
            GAME.getTextBox().paintComponent(g);
            return true;
        }
        else if (GAME.isTransition()) {
            g.fillRect(0, 0, Game.getWidth() * 2, Game.getHeight() * 2);
            return true;
        }
        else if (GAME.isResult()) {
            g.fillRect(0, 0, Game.getWidth() * 2, Game.getHeight() * 2);
            GAME.getTextBox().paintComponent(g);
            return true;
        }
        else { return false; }

    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    /**
     * Handles navigating Menus and TextBoxes.
     *
     * @param e the mouse event
     */
    public void mousePressed(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();
        int x = (int) GAME.getPlayer().getDirection().getX();
        int y = (int) GAME.getPlayer().getDirection().getY();
        Tile dirTile = tiles.get(matrix.get(y).get(x));

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (GAME.getMenu() == null) {
                if (!GAME.isTitle()) { GameAudio.playSfx(GameAudio.sfx_click); }
                if (GAME.getTextBox() == null) {
                    if (GAME.getTime() == 11) {
                        GAME.setTextBox(new TextBox(0, utilities.FileReader.getMenuString(53)));
                    }
                    else if (dirTile instanceof InteractiveTile) {
                        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.getMapId());
                        GAME.setTextBox(currentMap.getInteractivePoint(new Point(x, y)));
                        System.out.println(((InteractiveTile) dirTile).isMenu());
                        if ((((InteractiveTile) dirTile).isMenu())) {
                            GAME.setMenu(new Menu(14)); }
                    }
                    else if (GAME.isTileNPC(y, x)) {
                        NPC npc = (NPC) GAME.getObjectFromMatrix(y, x);
                        npc.rotate(GAME.getPlayer());
                        npc.interaction(GAME.getTime());
                    }
                    else { GAME.setTextBox(new TextBox(0, utilities.FileReader.getInteractiveString(37))); }
                }
                else if (GAME.getTextBox().isSkip()) {
                    if (GAME.isNewGame()) {
                        GAME.setNewGame();
                        GameAudio.startMusic(GameAudio.music_school);}
                    else if (GAME.isNewDay() && !GAME.isPreExam()) {
                        if (GAME.getDaysLeft() > 1) {
                            GAME.setNewDay();
                            GAME.newDay();
                            GameAudio.startMusic(GameAudio.music_school);
                        }
                        else {
                            GAME.newDayFeedback(3);
                            GAME.setPreExam(true);
                        }
                    }
                    else if (GAME.isPreExam()) {
                        GAME.setNewDay();
                        GAME.setPreExam(false);
                        Exam.startExam(GAME.getExamsLeft() - 1);
                    }
                    else if (GAME.isAfterActivity()) {
                        GAME.setActivity(null);
                        GAME.setAfterActivity(false);
                        GAME.goHome(true);
                    }
                    else if (GAME.isHeist()) {
                        GAME.setHeist();
                        GameAudio.playSfx(GameAudio.sfx_paAnnouncement);
                        GameAudio.startMusic(GameAudio.music_heist);
                    }
                    else if (GAME.hasGotAnswers()) { GAME.setGotQuestions(false); }
                    else if (GAME.isSpotted()) { GAME.endHeist(1); }
                    else if (GAME.hasLostHeist()) {
                        GAME.setHasLostHeist();
                        GameAudio.startMusic(GameAudio.music_school);
                    }
                    else if (GAME.hasWonHeist()) {
                        GAME.setHasWonHeist();
                        GameAudio.startMusic(GameAudio.music_school);
                    }
                    else if (GAME.getActivity() != null) { GAME.getActivity().finish(); }
                    if (GAME.isResult()) {
                        GAME.setResult();
                        GAME.setFinish();
                    }
                    GAME.setTextBox(null);
                }
            }
            else {
                switch (GAME.getMenu().getCurrentId()) {
                    /* Main Menu */
                    case 0:
                        /* Go to Map Menu */
                        if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 100
                                && curY > 96 && curY < 112) {
                            GAME.setMenu(new Menu(1));
                            GameAudio.playSfx(GameAudio.sfx_click);
                        }
                        /* Go to Friend Menu */
                        if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 40
                                && curY > 64 && curY < 80) {
                            GAME.setMenu(new Menu(2));
                            GameAudio.playSfx(GameAudio.sfx_click);
                        }
                        /* Go to Grade Menu */
                        if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 52
                                && curY > 32 && curY < 48) {
                            GAME.setMenu(new Menu(3));
                            GameAudio.playSfx(GameAudio.sfx_click);
                        }
                        /* Go to Items Menu */
                        if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                && curY > 128 && curY < 144) {
                            GAME.setMenu(new Menu(4));
                            GameAudio.playSfx(GameAudio.sfx_click);
                        }
                        if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 88
                                && curY > 160 && curY < 176) { GAME.save(); }
                        if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 88
                                && curY > 192 && curY < 208) { GAME.quit(); }
                        return;
                    /* Map Menu */
                    case 1:
                        /* Display Ground Floor Map */
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 56
                                && curY > 32 && curY < 48) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            Menu.loadMapImage(0);
                        }
                        /* Display 1F Map */
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 44
                                && curY > 64 && curY < 80) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            Menu.loadMapImage(1); }
                        break;
                    /* Friend Menu */
                    case 2:
                        /* Display Jack info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 88
                                && curY > 32 && curY < 48 ){ Menu.loadFriend(0); }
                        /* Display Emily info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56
                                && curY > 64 && curY < 80){ Menu.loadFriend(1); }
                        /* Display Alexander info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 136
                                && curY > 96 && curY < 112){ Menu.loadFriend(2); }
                        /* Display Nathan info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 40
                                && curY > 128 && curY < 144){ Menu.loadFriend(3); }
                        /* Display Frankie info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 120
                                && curY > 160 && curY < 176){ Menu.loadFriend(4); }
                        break;
                    /* Grade Menu */
                    case 3:
                        /* Display DT grade info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 168
                                && curY > 32 && curY < 48 ){ Menu.loadGrade(0); }
                        /* Display Food Tech grade info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56
                                && curY > 64 && curY < 80){ Menu.loadGrade(1); }
                        /* Display PE grade info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 168
                                && curY > 96 && curY < 112){ Menu.loadGrade(2); }
                        /* Display Chemistry grade info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56
                                && curY > 128 && curY < 144){ Menu.loadGrade(3); }
                        /* Display ICT grade info */
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 152
                                && curY > 160 && curY < 176){ Menu.loadGrade(4); }
                        break;
                    /* NPC interaction Yes/No Menu */
                    case 5:
                        NPC npc = (NPC) GAME.getObjectFromMatrix(y, x);
                        if (npc.getId() < 5) {
                            if (npc.getId() % 2 == 0) {
                                if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                        && curY > 32 && curY < 48) { npc.activity(true); }
                                if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                        && curY > 64 && curY < 80) { npc.activity(false); }
                            }
                            else {
                                if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                        && curY > 32 && curY < 48) { npc.gift(true); }
                                if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                        && curY > 64 && curY < 80) { npc.gift(false); }
                            }
                        }
                        else if (npc.getId() < 10) {
                            if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                    && curY > 32 && curY < 48) { npc.lesson(true); }
                            if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                    && curY > 64 && curY < 80) { npc.lesson(false); }
                        }
                        else {
                            if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                    && curY > 32 && curY < 48) { npc.lunch(true); }
                            if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                    && curY > 64 && curY < 80) { npc.lunch(false); }
                        }

                        break;
                    /* Food Tech Gift Menu */
                    case 6:
                        x = (int) GAME.getPlayer().getDirection().getX();
                        y = (int) GAME.getPlayer().getDirection().getY();
                        npc = (NPC) GAME.getObjectFromMatrix(y, x);
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96
                                && curY > 32 && curY < 48 && GAME.hasItem(2, 0)) { npc.giftTextBox(0); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96
                                && curY > 64 && curY < 80 && GAME.hasItem(2, 1)) { npc.giftTextBox(1); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96
                                && curY > 96 && curY < 112 && GAME.hasItem(2, 2)) { npc.giftTextBox(2); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 124
                                && curY > 128 && curY < 144) { npc.gift(false); }
                        break;
                    /* DT Gift Menu */
                    case 7:
                        x = (int) GAME.getPlayer().getDirection().getX();
                        y = (int) GAME.getPlayer().getDirection().getY();
                        npc = (NPC) GAME.getObjectFromMatrix(y, x);
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 76
                                && curY > 32 && curY < 48 && GAME.hasItem(1, 0)) { npc.giftTextBox(0); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 76
                                && curY > 64 && curY < 80 && GAME.hasItem(1, 1)) { npc.giftTextBox(1); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 76
                                && curY > 96 && curY < 112 && GAME.hasItem(1, 2)) { npc.giftTextBox(2); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 124
                                && curY > 128 && curY < 144) { npc.gift(false); }
                        break;
                    /* Chemistry/ICT Lesson Menu */
                    case 8:
                        Lesson lesson;
                        if (GAME.getLesson() != null) { lesson = GAME.getLesson(); }
                        else { lesson = GAME.getExam(); }
                        if (lesson.isFeedback()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            lesson.setFeedback(false);
                        }
                        else if (lesson.isRules()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            lesson.setRules(false);
                        }
                        else {
                            /* Answer */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 56
                                    && curY > 32 && curY < 48) { lesson.doAction(0); }
                            /* Think */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 64 && curY < 80) { lesson.doAction(1); }
                            /* Drink */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 96 && curY < 112) { lesson.doAction(2); }
                            /* Toilet */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 56
                                    && curY > 128 && curY < 144) { lesson.doAction(3); }
                            /* Rules */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 160 && curY < 179) { lesson.doAction(4); }
                        }
                        break;
                    /* DT/Food Tech Lesson Menu */
                    case 9:
                        if (GAME.getLesson().isFeedback()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.getLesson().setFeedback(false);
                        }
                        else if (GAME.getLesson().isRules()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.getLesson().setRules(false);
                        }
                        else {
                            /* Do Task */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 40
                                    && curY > 32 && curY < 48) { GAME.getLesson().doAction(0); }
                            /* Do Task Meticulously */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 24
                                    && curY > 64 && curY < 80) { GAME.getLesson().doAction(1); }
                            /* Reread Instructions */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 52
                                    && curY > 96 && curY < 112) { GAME.getLesson().doAction(2); }
                            /* Rules */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 128 && curY < 144) { GAME.getLesson().doAction(3); }
                        }
                        break;
                    /* PE Lesson Warm-up Menu */
                    case 10:
                        if (GAME.getLesson().isFeedback()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.getLesson().setFeedback(false);
                        }
                        else if (GAME.getLesson().isRules()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.getLesson().setRules(false);
                        }
                        else {
                            /* Warm Up */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 40
                                    && curY > 32 && curY < 48) { GAME.getLesson().doAction(0); }
                            /* Start Running */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 64 && curY < 80) { GAME.getLesson().doAction(1); }
                            /* Drink */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 96 && curY < 112) { GAME.getLesson().doAction(2); }
                            /* Rules */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 128 && curY < 144) { GAME.getLesson().doAction(3); }
                        }
                        break;
                    /* PE Lesson Menu */
                    case 11:
                        if (GAME.getLesson().isFeedback()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.getLesson().setFeedback(false);
                        }
                        else if (GAME.getLesson().isRules()) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.getLesson().setRules(false);
                        }
                        else {
                            /* Jog */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 104
                                    && curY > 32 && curY < 48) { GAME.getLesson().doAction(0); }
                            /* Run */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 104
                                    && curY > 64 && curY < 80) { GAME.getLesson().doAction(1); }
                            /* Sprint */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 52
                                    && curY > 96 && curY < 112) { GAME.getLesson().doAction(2); }
                            /* Rest */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 88
                                    && curY > 128 && curY < 144) { GAME.getLesson().doAction(3); }
                            /* Drink */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 160 && curY < 176) { GAME.getLesson().doAction(4); }
                            /* Rules */
                            if (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72
                                    && curY > 192 && curY < 208) { GAME.getLesson().doAction(5); }
                        }
                        break;
                    /* Go Home Yes/No Menu */
                    case 13:
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                && curY > 32 && curY < 48) { GAME.goHome(true); }
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                && curY > 64 && curY < 80) { GAME.goHome(false); }
                        break;
                    /* Interactive Tile Yes/No Menu */
                    case 14:
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                && curY > 32 && curY < 48) {
                            switch (GAME.getMapId()) {
                                case 0:
                                    switch (dirTile.getKey()) {
                                        case 'A':
                                            GAME.startHeist();
                                            break;
                                    }
                                    break;
                                case 7:
                                    switch (dirTile.getKey()) {
                                        case 'C':
                                            GAME.getPlayer().study();
                                            GameAudio.playSfx(GameAudio.sfx_click);
                                            break;
                                        case 'G':
                                            GAME.getPlayer().game();
                                            break;
                                        case 'n':
                                        case 'U':
                                            GAME.getPlayer().sleep();
                                            break;
                                        case 'c':
                                            GAME.setMenu(null);
                                            GAME.setTextBox(null);
                                            try {
                                                File manual = new File("resources/manual.pdf");
                                                Desktop.getDesktop().open(manual);
                                            } catch (IOException e1) {
                                                System.out.println("Unable to open manual");
                                            }
                                            break;
                                    }
                                    break;
                                case 8:
                                    switch (dirTile.getKey()) {
                                        case 'C':
                                            GAME.getPlayer().hack();
                                            break;
                                    }
                                    break;
                            }
                        }
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                && curY > 64 && curY < 80) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.setMenu(null);
                            GAME.setTextBox(null);
                        }
                        break;
                    /* Bedroom Study Menu */
                    case 15:
                        int score = (int) ((Math.random() * 2) + 1);
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 168
                                && curY > 32 && curY < 48 ) {
                            GAME.increaseGradeValue(0, score);
                            GAME.newDayFeedback(0, 0);
                        }
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56
                                && curY > 64 && curY < 80) {
                            GAME.increaseGradeValue(1, score);
                            GAME.newDayFeedback(0, 1);
                        }
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 168
                                && curY > 96 && curY < 112) {
                            GAME.increaseGradeValue(2, score);
                            GAME.newDayFeedback(0, 2);
                        }
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56
                                && curY > 128 && curY < 144) {
                            GAME.increaseGradeValue(3, score);
                            GAME.newDayFeedback(0, 3);
                        }
                        if (curX > Game.getWidth() - 200 && curX < Game.getWidth() - 152
                                && curY > 160 && curY < 176) {
                            GAME.increaseGradeValue(4, score);
                            GAME.newDayFeedback(0, 4);
                        }
                        break;
                    /* Lovely Cake Gift Menu */
                    case 16:
                        x = (int) GAME.getPlayer().getDirection().getX();
                        y = (int) GAME.getPlayer().getDirection().getY();
                        npc = (NPC) GAME.getObjectFromMatrix(y, x);
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96
                                && curY > 32 && curY < 48 && GAME.hasItem(2, 0)) { npc.giftTextBox(0); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96
                                && curY > 64 && curY < 80 && GAME.hasItem(2, 1)) { npc.giftTextBox(1); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96
                                && curY > 96 && curY < 116 && GAME.hasItem(2, 2)) { npc.giftTextBox(2); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 108
                                && curY > 128 && curY < 144 && GAME.hasItem(2, 3)) { npc.giftTextBox(3); }
                        if (curX > Game.getWidth() - 284 && curX < Game.getWidth() - 124
                                && curY > 160 && curY < 176) { npc.gift(false); }
                        break;
                    /* Quit Game Yes/No Menu */
                    case 17:
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28
                                && curY > 32 && curY < 48) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.setMenu(null);
                            GAME.setTextBox(null);
                            GAME.setFinish();
                        }
                        if (curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42
                                && curY > 64 && curY < 80) {
                            GameAudio.playSfx(GameAudio.sfx_click);
                            GAME.setMenu(null);
                            GAME.setTextBox(null);
                        }
                        break;
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON3 && GAME.getTextBox() == null) {
            GameAudio.playSfx(GameAudio.sfx_menu);
            if (GAME.getMenu() == null || (GAME.getMenu().getCurrentId() != 0 && GAME.getMenu().getCurrentId() != 15)) {
                GAME.setMenu(new Menu(0));
            }
            else  { GAME.setMenu(null); }
        }
    }

    public void mouseDragged(MouseEvent e) {}

    /**
     * Changes mouse cursor if co-ordinate is interactive.
     *
     * @param e the mouse event
     */
    public void mouseMoved(MouseEvent e) {
        boolean click = false;
        try {
            int curX = e.getX();
            int curY = e.getY();

            switch (GAME.getMenu().getCurrentId()) {
                case 0:
                    click = curX > Game.getWidth() - 152 && curX < Game.getWidth() - 100 && curY > 96 && curY < 112 ||
                            curX > Game.getWidth() - 152 && curX < Game.getWidth() - 40 && curY > 64 && curY < 80 ||
                            curX > Game.getWidth() - 152 && curX < Game.getWidth() - 52 && curY > 32 && curY < 48 ||
                            curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 128 && curY < 144 ||
                            curX > Game.getWidth() - 152 && curX < Game.getWidth() - 88 && curY > 160 && curY < 176 ||
                            curX > Game.getWidth() - 152 && curX < Game.getWidth() - 88 && curY > 192 && curY < 208;
                    break;
                case 1:
                    click = curX > Game.getWidth() - 76 && curX < Game.getWidth() - 56 && curY > 32 && curY < 52 ||
                            curX > Game.getWidth() - 76 && curX < Game.getWidth() - 44 && curY > 64 && curY < 84;
                    break;
                case 2:
                    click = curX > Game.getWidth() - 200 && curX < Game.getWidth() - 88 && curY > 32 && curY < 48 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56 && curY > 64 && curY < 80 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 136 && curY > 96 && curY < 112 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 40 && curY > 128 && curY < 144 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 120 && curY > 160 && curY < 176;
                    break;
                case 3:
                case 15:
                    click = curX > Game.getWidth() - 200 && curX < Game.getWidth() - 168 && curY > 32 && curY < 48 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56 && curY > 64 && curY < 80 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 168 && curY > 96 && curY < 112 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 56 && curY > 128 && curY < 144 ||
                            curX > Game.getWidth() - 200 && curX < Game.getWidth() - 152 && curY > 160 && curY < 176;
                    break;
                case 5:
                case 13:
                case 14:
                case 17:
                    click = curX > Game.getWidth() - 76 && curX < Game.getWidth() - 28 && curY > 32 && curY < 48 ||
                            curX > Game.getWidth() - 76 && curX < Game.getWidth() - 42 && curY > 64 && curY < 80;
                    break;
                case 6:
                    click = curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96 && curY > 32 && curY < 48
                                    && GAME.hasItem(2, 0) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96 && curY > 64 && curY < 80
                                    && GAME.hasItem(2, 1) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96 && curY > 96 && curY < 112
                                    && GAME.hasItem(2, 2) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 124 && curY > 128 && curY < 144;
                    break;
                case 7:
                    click = curX > Game.getWidth() - 284 && curX < Game.getWidth() - 76 && curY > 32 && curY < 48
                                    && GAME.hasItem(1, 0) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 76 && curY > 64 && curY < 80
                                    && GAME.hasItem(1, 1) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 76 && curY > 96 && curY < 112
                                    && GAME.hasItem(1, 2) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 124 && curY > 128 && curY < 144;
                    break;
                case 8:
                    Lesson lesson;
                    if (GAME.getLesson() != null) { lesson = GAME.getLesson(); }
                    else { lesson = GAME.getExam(); }
                    click = (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 56 && curY > 32 && curY < 48 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 64 && curY < 80 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 96 && curY < 112 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 56 && curY > 128 && curY < 144 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 160 && curY < 176)
                            && !lesson.isFeedback() && !lesson.isRules();
                    break;
                case 9:
                    click = (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 40 && curY > 32 && curY < 48 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 24 && curY > 64 && curY < 80 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 52 && curY > 96 && curY < 112 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 128 && curY < 144)
                            && !GAME.getLesson().isFeedback() && !GAME.getLesson().isRules();
                    break;
                case 10:
                    click = (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 40 && curY > 32 && curY < 48 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 64 && curY < 80 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 96 && curY < 112 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 128 && curY < 144)
                            && !GAME.getLesson().isFeedback() && !GAME.getLesson().isRules();
                    break;
                case 11:
                    click = (curX > Game.getWidth() - 152 && curX < Game.getWidth() - 104 && curY > 32 && curY < 48 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 104 && curY > 64 && curY < 80 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 52 && curY > 96 && curY < 112 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 88 && curY > 128 && curY < 144 ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 160 && curY < 176  ||
                             curX > Game.getWidth() - 152 && curX < Game.getWidth() - 72 && curY > 192 && curY < 208)
                            && !GAME.getLesson().isFeedback() && !GAME.getLesson().isRules();
                    break;
                case 16:
                    click = curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96 && curY > 32 && curY < 48
                                    && GAME.hasItem(2, 0) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96 && curY > 64 && curY < 80
                                    && GAME.hasItem(2, 1) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 96 && curY > 96 && curY < 112
                                    && GAME.hasItem(2, 2) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 108 && curY > 128 && curY < 144
                                    && GAME.hasItem(2, 3) ||
                            curX > Game.getWidth() - 284 && curX < Game.getWidth() - 124 && curY > 160 && curY < 176;
                    break;
            }
        } catch (NullPointerException e1) { /* Mouse is out of frame*/ }

        if (click) { this.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        else { this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); }
    }

    public Dimension getPreferredSize() { return new Dimension(Game.getWidth(), Game.getHeight()); }
}
