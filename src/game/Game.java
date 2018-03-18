package game;

import controllers.Keys;
import lessons.Exam;
import lessons.Lesson;
import objects.*;
import utilities.*;
import utilities.FileReader;
import utilities.Menu;
import utilities.SplashScreen;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Stores game data and runs the application
 */
public class Game implements Serializable {
    public final static Game GAME = new Game();

    private static int height;
    private static int width;
    private static int cameraHeight;
    private static int cameraWidth;

    private static boolean running = true;

    private static String[] timePeriods;
    private static String[] conditions;

    private int time;
    private int day = 1;
    private int daysLeft = 30;
    private int points;
    private int timeBeforeHeist;
    private int examsLeft = 5;
    private int playerX;
    private int playerY;
    private int playerCondition;
    private int mapId;
    private long transitionTime;
    private boolean isTransition;
    private boolean isNewGame;
    private boolean isNewDay;
    private boolean isAfterActivity;
    private boolean givenDrink;
    private boolean isHeist;
    private boolean gotQuestions;
    private boolean isSpotted;
    private boolean isResult;
    private boolean isFinish;
    private boolean hasLostHeist;
    private boolean hasWonHeist;
    private boolean isSuspended;
    private boolean isExams;
    private boolean preExam;
    private boolean emilyCrush;
    private boolean isTitle = true;
    private boolean isDebug;
    private String newDayText;

    private int[] friendValues;
    private int[] gradeValues;
    private int[] examScores;
    private int[] daysSince;
    private int[][] items;

    private transient Player player;
    private transient Keys ctrl;
    private transient TileMapView map;
    private transient Camera camera;
    private transient TitleScreen titleScreen;
    private transient SplashScreen splashScreen;
    private transient TextBox textBox;
    private transient Menu menu;
    private transient StatusMenu statusMenu;
    private transient Lesson lesson;
    private transient Activity activity;
    private transient Exam exam;
    private transient Clip music;
    private transient JFrame frame;

    private transient int[][] badTileMatrix;
    private transient char[][] tileMatrix;
    private transient List<GameObject> objects;
    private transient GameObject[][] objectMatrix;

    /**
     * Class constructor.
     */
    private Game() {
        objects = new ArrayList<>();
        ctrl = new Keys();
        height = Constants.FRAME_HEIGHT;
        width = Constants.FRAME_WIDTH;
        cameraHeight = height / 32;
        cameraWidth = width / 32;
    }

    public static int getHeight() { return height; }
    public static int getWidth() { return width; }

    static int getCameraHeight() { return cameraHeight; }
    static int getCameraWidth() { return cameraWidth; }

    public static void setRunning(boolean running) { Game.running = running; }

    public static String[] getTimePeriods() { return timePeriods; }

    public static String[] getConditions() { return conditions; }

    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public int getDaysLeft() { return daysLeft; }
    public void setDaysLeft(int daysLeft) { this.daysLeft = daysLeft; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    /**
     * Increases the current game score.
     * Applies a formula that gives more points if the friend/grade value is higher and the current game day is lower.
     *
     * @param id the type that triggered the increase (0 = friend, 1 = lesson, 2 = exam, 3 = decrease in friendship)
     * @param index the index of the type that triggered the increase
     * @param increase the pre formula increase in value
     */
    public void increasePoints(int id, int index, int increase) {
        switch(id) {
            case 0:
                points += 2 * (Math.pow((increase * ((friendValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 1:
                points += 2 * (Math.pow((increase * ((gradeValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 2:
                points += 5 * (Math.pow((increase * ((examScores[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
            case 3:
                points -= 2 * (Math.pow((increase * ((friendValues[index] / 10) + 1)), 2) / Math.pow(day, 1 / 3));
                break;
        }
    }

    public int getExamsLeft() { return examsLeft; }
    public void setExamsLeft(int examsLeft) { this.examsLeft = examsLeft; }

    public boolean isTransition() { return isTransition; }

    boolean isNewGame() { return isNewGame; }
    void setNewGame() { isNewGame = false; }

    boolean isNewDay() { return isNewDay; }
    void setNewDay() { isNewDay = false; }

    public boolean isAfterActivity() { return isAfterActivity; }
    void setAfterActivity(boolean afterActivity) { isAfterActivity = afterActivity; }

    public boolean canGetDrink() { return !givenDrink; }
    public void setGivenDrink(boolean givenDrink) { this.givenDrink = givenDrink; }

    boolean isHeist() { return isHeist; }
    void setHeist() { isHeist = false; }

    boolean hasGotAnswers() { return gotQuestions; }
    public void setGotQuestions(boolean gotQuestions) { this.gotQuestions = gotQuestions; }

    public boolean isSpotted() { return isSpotted; }
    public void setSpotted(boolean spotted) { isSpotted = spotted; }

    boolean isResult() { return isResult; }
    void setResult() { isResult = false; }

    void setFinish() { isFinish = true; }

    public boolean hasLostHeist() { return hasLostHeist; }
    void setHasLostHeist() { this.hasLostHeist = false; }

    boolean hasWonHeist() { return hasWonHeist; }
    void setHasWonHeist() { this.hasWonHeist = false; }

    public boolean isSuspended() { return isSuspended; }

    public void setExams(boolean exams) { isExams = exams; }

    boolean isPreExam() { return preExam; }
    void setPreExam(boolean preExam) { this.preExam = preExam; }

    public boolean isEmilyCrush() { return emilyCrush; }
    public void setEmilyCrush(boolean emilyCrush) { this.emilyCrush = emilyCrush; }

    boolean isTitle() { return isTitle; }

    public boolean isDebug() { return isDebug; }
    public void setDebug() {
        menu = null;
        textBox = new TextBox(0, FileReader.getMenuString(65));
        isDebug = true;
        GameAudio.playSfx(GameAudio.sfx_gotAnswers);
    }

    String getNewDayText() { return newDayText; }

    public int getFriendValue(int index) { return friendValues[index]; }
    public void increaseFriendValue(int index, int increase) { friendValues[index] += increase; }

    public int getGradeValue(int index) { return gradeValues[index]; }
    public void increaseGradeValue(int index, int increase) { gradeValues[index] += increase; }

    public void setExamScore(int index, int increase) { examScores[index] += increase; }

    public void resetDaysSince(int index) { daysSince[index] = -1; }

    public int getItem(int type, int index) { return items[type][index]; }

    public void giveEnergyDrink() { items[0][0]++; }
    public void drinkEnergyDrink() { items[0][0]--; }
    public void giveItem(int type, int index) { items[type][index]++; }
    public void takeItem(int type, int index) { items[type][index]--; }
    void giveStinkBomb() { items[0][1] = 1; }
    public void giveQuestions() { items[0][2] = 1; }

    public boolean hasEnergyDrink() { return items[0][0] > 0; }
    public boolean hasItem(int type, int index) { return items[type][index] > 0; }
    public boolean hasCraft() {
        for (int i = 0; i < items[1].length - 1; i++) {
            if (items[1][i] != 0) { return true;}
        }
        return false;
    }
    public boolean hasFood() {
        for (int i = 0; i < items[2].length - 1; i++) {
            if (items[2][i] != 0) { return true;}
        }
        return false;
    }
    private boolean hasSuperKey() { return items[1][3] > 0; }
    public boolean hasLovelyCake() { return items[2][3] > 0; }
    boolean hasStinkBomb() { return items[0][1] > 0; }
    public boolean hasQuestions() { return items[0][2] > 0; }

    public void setBadTile(int j, int i) { badTileMatrix[j][i] = 1; }
    boolean isBadTile(int j, int i) { return badTileMatrix[j][i] == 1; }
    void setBadTileMatrix(int rows, int cols) { badTileMatrix = new int[rows][cols]; }

    boolean isTileNPC(int y, int x) { return tileMatrix[y][x] == '*'; }
    public char getTileFromMatrix(int y, int x) { return tileMatrix[y][x]; }
    void setTileMatrix(int rows, int cols) { tileMatrix = new char[rows][cols]; }

    public Player getPlayer() { return player; }

    public int getMapId() { return map.getCurrentId(); }
    public int getMapMaxX() { return map.getMaxX(); }
    public int getMapMaxY() { return map.getMaxY(); }
    public int getMiniMapId() { return map.getMiniMapId(); }
    public boolean isCollideTile(Character key) { return map.getTiles().get(key).isCollision(); }
    public boolean isDoorTile(Character key) { return map.getTiles().get(key)instanceof DoorTile; }
    public void loadMap(TileMap map) { this.map.loadMap(map);}

    public Camera getCamera() { return camera; }

    public TextBox getTextBox() { return textBox; }
    public void setTextBox(TextBox textBox) { this.textBox = textBox; }

    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }

    public StatusMenu getStatusMenu() { return statusMenu; }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }

    public Activity getActivity() { return activity; }
    void setActivity(Activity activity) { this.activity = activity; }

    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }

    public Clip getMusic() { return music; }
    public void setMusic(Clip music) { this.music = music; }

    private void createFrame() {
        frame = new JFrame("Brooklands Academy");
        frame.getContentPane().add(splashScreen);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    int getNumberOfObjects() { return objects.size(); }
    GameObject getObject(int index) { return objects.get(index); }

    GameObject getObjectFromMatrix(int j, int i) { return objectMatrix[j][i]; }
    public boolean isObjectNull(int j, int i) { return objectMatrix[j][i] == null; }
    void setObjectMatrix(int rows, int cols) { objectMatrix = new GameObject[rows][cols]; }

    /**
     * Updates the logical state of all GameObjects and matrices, and the StatusMenu
     */
    private void update() {
        int rows = map.getMatrixRows();
        int cols = map.getMatrixCols();

        tileMatrix = new char[rows][cols];
        badTileMatrix = new int[rows][cols];
        objectMatrix = new GameObject[rows][cols];

        if (lesson != null) {
            switch (time) {
                case 3:
                case 4:
                    statusMenu.setCurrentId(1);
                    break;
                case 5:
                    statusMenu.setCurrentId(2);
                    break;
                case 6:
                case 7:
                    statusMenu.setCurrentId(3);
                    break;
            }
            StatusMenu.setUp(statusMenu.getCurrentId());
            if (lesson.isFeedback()) {
                menu.setVisible(false);
                textBox = new TextBox(0, GAME.lesson.getFeedbackText());
            } else if (lesson.isRules()) { textBox = lesson.showRules(); }
            else { lesson.checkFinish(); }
        }
        else if (exam != null) {
            statusMenu.setCurrentId(4);
            StatusMenu.setUp(statusMenu.getCurrentId());
            if (exam.isFeedback()) {
                menu.setVisible(false);
                textBox = new TextBox(0, GAME.exam.getFeedbackText());
            } else if (exam.isRules()) { textBox = exam.showRules(); }
            else {
                exam.checkFinish();
                if (exam == null) { return; }
            }
        }
        else {
            statusMenu.setCurrentId(0);
            StatusMenu.setUp(statusMenu.getCurrentId());
        }

        for (int i = 0; i < rows; i++) {
            List<Character> line = map.getMatrixLine(i);
            for (int j = 0; j < cols; j++) {
                tileMatrix[i][j] = line.get(j);
            }
        }
        for (GameObject object : objects) {
            tileMatrix[object.getY()][object.getX()] = object.getTile().getKey();
            objectMatrix[object.getY()][object.getX()]  = object;
        }
        for (GameObject object : objects) {  object.update();  }
        camera.update();
        if (isTransition && System.currentTimeMillis() - transitionTime > 1000 / 5) { isTransition = false; }

        synchronized (Game.class) {
            objects.clear();
            try { objects.addAll(TileMapLoader.tileMaps.get(map.getCurrentId()).getNPCs(time)); }
            catch (NullPointerException e) { /* Do nothing */}
            objects.add(GAME.player);
        }
    }

    /**
     * Updates the air vent tile in the ground floor school hall map.
     * This enables the player to trigger the heist if certain conditions are met.
     */
    void updateAirVent() {
        if (hasStinkBomb() && hasSuperKey() && gradeValues[4] > 29 && !hasQuestions()) {
            TileMapLoader.tileMaps.get(0).putPoint(new Point(7, 5),
                    new TextBox(0, FileReader.getInteractiveString(5) + FileReader.getInteractiveString(36)));
            Tile oldTile = TileMapLoader.tileMaps.get(0).getTile('A');
            Tile newTile = new InteractiveTile(oldTile.getImg(), true, 'A', true);
            TileMapLoader.tileMaps.get(0).putTile('A', newTile);
        }
        else {
            TileMapLoader.tileMaps.get(0).putPoint(new Point(7, 5),
                    new TextBox(0, FileReader.getInteractiveString(5)));
            Tile oldTile = TileMapLoader.tileMaps.get(0).getTile('A');
            Tile newTile = new InteractiveTile(oldTile.getImg(), true, 'A', false);
            TileMapLoader.tileMaps.get(0).putTile('A', newTile);
        }
    }

    public void doTransition() {
        isTransition = true;
        transitionTime = System.currentTimeMillis();
    }

    /**
     * Starts a new game day.
     * Loads the ground floor school hall map and sets the player position to the starting value.
     * Increments and decrements the day and daysLeft fields respectively.
     * If a friend NPC is at a certain friendship level and the haven't done an activity with the player for
     * more than 2 days, their corresponding friend value and the Game's points value is decreased.
     */
    void newDay() {
        time = 0;
        givenDrink = false;
        TileMap nextMap = TileMapLoader.tileMaps.get(0);
        map.loadMap(nextMap);
        player.setLocation(Constants.START_X, Constants.START_Y);
        player.rotate(0);
        day++;
        daysLeft--;
        for (int i = 0; i < 3; i ++) {
            if (friendValues[i * 2] >= 10) {
                daysSince[i]++;
                if (daysSince[i] > 2) {
                    int decrease = daysSince[i] - 1;
                    friendValues[i * 2] -= decrease;
                    increasePoints(3, i *2, decrease);
                }
            }
        }
    }

    /**
     * Loads textual feedback depending on the evebt the player triggered in the bedroom map.
     *
     * @param id the type of event (0 = study, 1 = game, 2 = sleep, 3= start exams)
     *           and possible subtype (subject studied, game outcome)
     */
    public void newDayFeedback(int... id) {
        GameAudio.playSfx(GameAudio.sfx_click);
        menu = null;
        isNewDay = true;
        switch (id[0]) {
            case 0:
                newDayText = FileReader.getNewDayString(id[1]);
                break;
            case 1:
                newDayText = FileReader.getNewDayString(id[1] + 5);
                break;
            case 2:
                newDayText = FileReader.getNewDayString(7);
                break;
            case 3:
                GameAudio.stopMusic();
                newDayText = FileReader.getNewDayString(8) + FileReader.getNewDayString(8 + examsLeft) +
                        FileReader.getNewDayString(14) + FileReader.getNewDayString(14 + examsLeft);
                preExam = true;
                break;
        }
    }

    /**
     * Ends the school day.
     * Loads the bedroom map and sets the player position.
     * Resets the player condition.
     *
     * @param yes whether or not the player triggers the event.
     */
    public void goHome(boolean yes) {
        if (yes) {
            GameAudio.playSfx(GameAudio.sfx_click);
            doTransition();
            objects.clear();
            time = 8;
            TileMap nextMap = TileMapLoader.tileMaps.get(7);
            map.loadMap(nextMap);
            GameAudio.playSfx(GameAudio.sfx_door);
            GameAudio.startMusic(GameAudio.music_bedroom);
            objects.add(GAME.player);
            player.setLocation(6, 2);
            player.rotate(1);
            player.setCondition(0);
            player.setEmotion(null);
            if (daysLeft < 2) { isExams = true; }
        }
        textBox = null;
        menu = null;
    }

    /**
     * Starts the 'Heist' game state if at a suitable game time.
     * Removes the stinkbomb item from the player's inventory.
     */
    public void startHeist() {
        if (time < 2) {
            timeBeforeHeist = time;
            items[0][1] = 0;
            GameAudio.playSfx(GameAudio.sfx_click);
            GameAudio.playSfx(GameAudio.sfx_useStinkBomb);
            GameAudio.stopMusic();
            time = 10;
            isHeist = true;
            textBox = null;
        }
        else { textBox = new TextBox(0, FileReader.getMenuString(51)); }
        menu = null;
    }

    /**
     * Ends the 'Heist' game state.
     * If successful, the player keeps the exam questions item and the current game points are multiplied by 1.5.
     * If not, the player loses the exam questions item and won't be able to trigger 'Activity' game states for the
     * rest of the game.
     *
     * @param outcome the outcome of the heist (0 = win, 1 = lose)
     */
    public void endHeist(int outcome) {
        GameAudio.stopMusic();
        switch (outcome) {
            case 0:
                GameAudio.playSfx(GameAudio.sfx_gotAnswers);
                hasWonHeist = true;
                time = timeBeforeHeist + 1;
                points *= 1.5;
                break;
            case 1:
                items[0][2] = 0;
                isSpotted = false;
                gotQuestions = false;
                hasLostHeist = true;
                isSuspended = true;
                objects.clear();
                time = timeBeforeHeist + 1;
                TileMap nextMap = TileMapLoader.tileMaps.get(1);
                map.loadMap(nextMap);
                objects.add(GAME.player);
                player.setSpotted(false);
                player.setLocation(9, 2);
                player.rotate(1);
                break;
        }
    }

    /**
     * Starts a new game instance.
     * Sets all game values to their default.
     */
    public void newGame() {
        GameAudio.stopMusic();

        assert Player.TILES != null;
        player = new Player(Player.TILES.get(Constants.UP_TILE), Constants.START_X, Constants.START_Y, ctrl);

        items = new int[3][];
        items[0] = new int[3];
        items[1] = new int[4];
        items[2] = new int[4];

        map = new TileMapView(TileMapLoader.tileMaps.get(0));

        camera = new Camera(player.getX() - (cameraWidth / 2), player.getY() - (cameraHeight / 2));

        time = 0;

        friendValues = new int[5];
        gradeValues = new int[5];
        examScores = new int[5];

        daysSince = new int[3];

        day = 1;
        daysLeft = 30;

        points = 0;
        examsLeft = 5;

        isNewGame = true;
        isTitle = false;

        isExams = false;
        isSuspended = false;
        givenDrink = false;
        emilyCrush = false;

    }

    /**
     * Loads a game instance from a file.
     */
    public void load() {
        Game data;

        try {
            FileInputStream saveData = new FileInputStream("sav/game.ser");
            ObjectInputStream in = new ObjectInputStream(saveData);
            data = (Game) in.readObject();
            in.close();
            saveData.close();
        } catch (IOException e) {
            titleScreen.setTextBox(new TextBox(0, FileReader.getMenuString(61)));
            return;
        } catch (ClassNotFoundException e) {
            titleScreen.setTextBox(new TextBox(0, FileReader.getMenuString(62)));
            return;
        }

        assert Player.TILES != null;
        player = new Player(Player.TILES.get(0), data.playerX, data.playerY, ctrl);
        player.setCondition(data.playerCondition);

        items = data.items;
        map = new TileMapView(TileMapLoader.tileMaps.get(data.mapId));
        time = data.time;

        switch (time) {
            case 8:
                GameAudio.startMusic(GameAudio.music_bedroom);
                break;
            case 10:
            case 11:
                GameAudio.startMusic(GameAudio.music_heist);
                break;
            default:
                GameAudio.startMusic(GameAudio.music_school);
                break;
        }

        camera = new Camera(player.getX() - (cameraWidth / 2), player.getY() - (cameraHeight / 2));

        friendValues = data.friendValues;
        gradeValues = data.gradeValues;
        examScores = data.examScores;

        daysSince = data.daysSince;

        day = data.day;
        daysLeft = data.daysLeft;

        points = data.points;
        examsLeft = data.examsLeft;

        isExams = data.isExams;
        isSuspended = data.isSuspended;
        givenDrink = data.givenDrink;
        emilyCrush = data.emilyCrush;

        isNewGame = false;
        isTitle = false;

        isTitle = false;
    }

    /**
     * Saves a game instance to a file.
     */
    void save() {
        GameAudio.playSfx(GameAudio.sfx_click);
        playerX = player.getX();
        playerY = player.getY();
        playerCondition = player.getCondition();
        mapId = map.getCurrentId();
        menu = null;
        try {
            FileOutputStream saveData = new FileOutputStream("sav/game.ser");
            ObjectOutputStream out = new ObjectOutputStream(saveData);
            out.writeObject(this);
            out.close();
            saveData.close();
            GameAudio.playSfx(GameAudio.sfx_save);
            textBox = new TextBox(0, FileReader.getMenuString(58));
        } catch (IOException e) {
            textBox = new TextBox(0, FileReader.getMenuString(59));
            e.printStackTrace();
        }
    }

    /**
     * Quits the game and reloads the TitleScreen.
     */
    void quit() {
        GameAudio.playSfx(GameAudio.sfx_click);
        textBox = new TextBox(3, FileReader.getMenuString(60));
        menu = new Menu(17);
    }
    public void finishGame() {
        GameAudio.startMusic(GameAudio.music_result);
        isResult = true;
        int totalGradePoints = 0, totalFriendPoints = 0, totalExamPoints = 0;
        for (int i : gradeValues) { totalGradePoints += i; }
        for (int i : friendValues) { totalFriendPoints += i; }
        for (int i : examScores) { totalExamPoints += i; }
        textBox = new TextBox(5, FileReader.getResultString(0) + FileReader.getResultString(1) +totalFriendPoints +
                "#" + FileReader.getResultString(2) + totalGradePoints +
                "#" + FileReader.getResultString(3) + totalExamPoints +
                "#" + FileReader.getResultString(4) + (items[0][2] > 0) +
                "#" + FileReader.getResultString(5) + points);
    }

    public static void main(String[] args) throws Exception {
        GAME.splashScreen = new SplashScreen();
        GAME.createFrame();

        CyclicBarrier barrier = new CyclicBarrier(6);
        Thread soundThread = new Thread(() -> GameAudio.loadSounds(barrier));
        Thread fileThread = new Thread(() -> FileReader.readFiles(barrier));
        Thread textboxThread = new Thread(() -> TextBox.loadImages(barrier));
        Thread menuThread = new Thread(() -> Menu.loadImages(barrier));
        Thread fontThread = new Thread(() -> GameFont.loadFont(barrier));

        ExecutorService e = Executors.newFixedThreadPool(6);

        e.submit(soundThread);
        e.submit(fileThread);
        e.submit(textboxThread);
        e.submit(menuThread);
        e.submit(fontThread);

        barrier.await();

        GAME.titleScreen = new TitleScreen();

        timePeriods = FileReader.getTimeStrings();
        conditions = FileReader.getConditionStrings();

        while (true) {
            CyclicBarrier barrier2 = new CyclicBarrier(2);
            Thread npcThread = new Thread(() -> NPCLoader.loadNPCs(barrier2));

            ExecutorService e1 = Executors.newFixedThreadPool(2);

            e1.submit(npcThread);
            barrier2.await();

            barrier2.reset();

            Thread mapThread = new Thread(() -> TileMapLoader.loadMaps(barrier2));

            e1.submit(mapThread);
            barrier2.await();

            GAME.frame.remove(GAME.splashScreen);

            GAME.frame.getContentPane().add(GAME.titleScreen);
            GAME.frame.revalidate();

            GameAudio.startMusic(GameAudio.music_title);

            while (GAME.isTitle) {
                GAME.titleScreen.repaint();
                if (!running) {
                    System.exit(0);
                    return;
                }
            }
            GAME.frame.remove(GAME.titleScreen);

            GAME.statusMenu = new StatusMenu(0);
            GAME.frame.getContentPane().add(GAME.map);
            GAME.frame.addKeyListener(GAME.ctrl);
            GAME.frame.revalidate();

            while (!GAME.isFinish) {
                GAME.update();
                GAME.map.repaint();
                Thread.sleep(70);
            }
            GAME.isFinish = false;
            GAME.isTitle = true;
            GAME.titleScreen = new TitleScreen();

            GAME.frame.removeKeyListener(GAME.ctrl);
            GAME.frame.remove(GAME.map);
            GAME.frame.getContentPane().add(GAME.splashScreen);
            GAME.frame.revalidate();
        }
    }
}
