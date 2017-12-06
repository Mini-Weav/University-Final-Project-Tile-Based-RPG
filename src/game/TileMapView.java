package game;

import lessons.LessonTypeA;
import lessons.LessonTypeB;
import lessons.LessonTypeC;
import objects.GameObject;
import objects.InteractiveTile;
import objects.NPC;
import utilities.GameFont;
import utilities.TextBox;
import objects.Tile;
import utilities.Menu;
import utilities.TileMapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by lmweav on 23/10/2017.
 */
public class TileMapView extends JComponent implements MouseListener, MouseMotionListener {
    private Game game;
    public int currentId, minimapId;
    public int maxX, maxY;
    public List<List<Character>> matrix;
    public static TreeMap<Character, Tile> tiles;

    public TileMapView(Game game, TileMap map) {
        this.game = game;

        loadMap(map);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public static List<List<Character>> readMap(File txtFile) throws IOException {
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

    public void loadMap(TileMap map) {
        try {
            this.matrix = readMap(map.txtFile);
            maxX = matrix.get(0).size() - 1;
            maxY = matrix.size() - 1;
            tiles = map.tiles;
            this.currentId = map.id;
            this.minimapId = map.minimapId;
            Game.tileMatrix = new char[matrix.size()][matrix.get(0).size()];
            Game.objectMatrix = new Object[matrix.size()][matrix.get(0).size()];
            Menu.iconPoint = new Point(map.iconPoint);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot read line.");
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        for (int j = -1; j < Constants.CAMERA_SIZE_Y + 2; j++) {
            for (int i = -1; i < Constants.CAMERA_SIZE_X + 2; i++) {
                try {
                    Tile tile = tiles.get(matrix.get(Game.camera.y + j).get(Game.camera.x + i));
                    g.drawImage(tile.img, i * 32 - Game.camera.diffX, j * 32 - Game.camera.diffY, 32, 32, null);
                } catch (IndexOutOfBoundsException e) {
                    Tile tile = tiles.get('#');
                    g.drawImage(tile.img, i * 32 - Game.camera.diffX, j * 32 - Game.camera.diffY, 32, 32, null);
                }
            }
        }
        synchronized (Game.class) {
            for (GameObject object : game.objects) {
                if (object.x >= Game.camera.x - 1 && object.x <= Game.camera.x + Constants.CAMERA_SIZE_X + 1 &&
                        object.y >= Game.camera.y - 1 && object.y <= Game.camera.y + Constants.CAMERA_SIZE_Y + 1) {
                    object.paintComponent(g); }
            }

            if (!Game.isLesson) {
                BufferedImage statusImg = Menu.imgs[6];
                g.drawImage(statusImg, 16, 16, statusImg.getWidth() * 2, statusImg.getHeight() * 2, null);
                g.setFont(GameFont.bigFont);
                int lineIndex = 0;
                String text = Game.timePeriods[Game.time] + "\nPP: " + 0;
                for (String line : text.split("\n")) {
                    g.drawString(line, 26, 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
                    lineIndex++;
                }
            }
            else {
                BufferedImage statusImg;
                int lineIndex;
                String text;
                switch (Game.time) {
                    case 3:
                    case 4:
                        statusImg = Menu.imgs[7];
                        g.drawImage(statusImg, 16, 16, statusImg.getWidth() * 2, statusImg.getHeight() * 2, null);
                        g.setFont(GameFont.bigFont);
                        lineIndex = 0;
                        text = "TASKS:        " + ((LessonTypeB) Game.lesson).tasksLeft +
                             "\nTIME:         " + ((LessonTypeB) Game.lesson).time +
                             "\nREREAD:    " + ((LessonTypeB) Game.lesson).reread;
                        for (String line : text.split("\n")) {
                            g.drawString(line, 26, 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
                            lineIndex++;
                        }
                        break;
                    case 5:
                        statusImg = Menu.imgs[5];
                        g.drawImage(statusImg, 16, 16, statusImg.getWidth() * 2, statusImg.getHeight() * 2, null);
                        g.setFont(GameFont.bigFont);
                        lineIndex = 0;
                        text = "LAPS:       " + ((LessonTypeC) Game.lesson).score +
                             "\nTIME:         " + ((LessonTypeC) Game.lesson).time +
                             "\nDRINKS:       " + Game.items[0][0] +
                             "\nENERGY:       " + ((LessonTypeC) Game.lesson).energy;
                        for (String line : text.split("\n")) {
                            g.drawString(line, 26, 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
                            lineIndex++;
                        }
                        break;
                    case 6:
                    case 7:
                        statusImg = Menu.imgs[5];
                        g.drawImage(statusImg, 16, 16, statusImg.getWidth() * 2, statusImg.getHeight() * 2, null);
                        g.setFont(GameFont.bigFont);
                        lineIndex = 0;
                        text = "QUESTIONS:    " + ((LessonTypeA) Game.lesson).questionsLeft +
                             "\nDRINKS:       " + Game.items[0][0] +
                             "\nATTENTION:    " + ((LessonTypeA) Game.lesson).attentionSpan +
                             "\nCONCENTRATION:" + ((LessonTypeA) Game.lesson).concentration;
                        for (String line : text.split("\n")) {
                            g.drawString(line, 26, 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
                            lineIndex++;
                        }
                        break;
                }

                if (Game.lesson.feedback) { Game.textBox = new TextBox(0, Game.lesson.feedbackText); }
                else {
                    if (Game.lesson.finished) { Game.lesson.finish(); }
                    else { Game.textBox = new TextBox(0, Game.lesson.questionText); }
                }
            }
            if (Game.textBox != null) { Game.textBox.paintComponent(g); }
            if (Game.menu != null) { Game.menu.paintComponent(g); }
            if (Game.transition) { g.fillRect(0, 0, Constants.FRAME_WIDTH * 2, Constants.FRAME_HEIGHT * 2); }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (Game.menu == null) {
                if (Game.textBox == null) {
                    int x = (int)game.player.direction.getX();
                    int y = (int)game.player.direction.getY();
                    if (tiles.get(matrix.get(y).get(x)) instanceof InteractiveTile) {
                        TileMap currentMap = TileMapLoader.tileMaps.get(Game.map.currentId);
                        Game.textBox = currentMap.interactivePoints.get(new Point(x, y));
                    }
                    else if (Game.tileMatrix[y][x] == 'N') {
                        NPC npc = (NPC) Game.objectMatrix[y][x];
                        npc.rotate(game.player);
                        npc.interaction(Game.time);
                    }
                    else { Game.textBox = new TextBox(0, "There's nothing here.");}
                }
                else if (Game.textBox.skip) { Game.textBox = null; }
            }
            else {
                switch (Game.menu.currentId) {
                    case 0:
                        if (curX > 328 && curX < 380 && curY > 96 && curY < 116) { Game.menu = new Menu(1); }
                        if (curX > 328 && curX < 440 && curY > 64 && curY < 80) { Game.menu = new Menu(2); }
                        if (curX > 328 && curX < 428 && curY > 32 && curY < 48) { Game.menu = new Menu(3); }
                        if (curX > 328 && curX < 408 && curY > 128 && curY < 146) { Game.menu = new Menu(4); }
                        return;
                    case 1:
                        if (curX > 404 && curX < 424 && curY > 32 && curY < 52) { Menu.loadMapImage(0); }
                        if (curX > 404 && curX < 436 && curY > 64 && curY < 84) { Menu.loadMapImage(1); }
                        break;
                    case 2:
                        if (curX > 280 && curX < 392 && curY > 32 && curY < 48 ){ Menu.loadFriend(0); }
                        if (curX > 280 && curX < 424 && curY > 64 && curY < 80){ Menu.loadFriend(1); }
                        if (curX > 280 && curX < 344 && curY > 96 && curY < 116){ Menu.loadFriend(2); }
                        if (curX > 280 && curX < 440 && curY > 128 && curY < 144){ Menu.loadFriend(3); }
                        if (curX > 280 && curX < 360 && curY > 160 && curY < 176){ Menu.loadFriend(4); }
                        break;
                    case 3:
                        if (curX > 280 && curX < 312 && curY > 32 && curY < 48 ){ Menu.loadGrade(0); }
                        if (curX > 280 && curX < 424 && curY > 64 && curY < 80){ Menu.loadGrade(1); }
                        if (curX > 280 && curX < 312 && curY > 96 && curY < 116){ Menu.loadGrade(2); }
                        if (curX > 280 && curX < 424 && curY > 128 && curY < 144){ Menu.loadGrade(3); }
                        if (curX > 280 && curX < 328 && curY > 160 && curY < 176){ Menu.loadGrade(4); }
                        break;
                    case 5:
                        int x = (int)game.player.direction.getX();
                        int y = (int)game.player.direction.getY();
                        NPC npc = (NPC) Game.objectMatrix[y][x];
                        if (npc.id < 5) {
                            if (curX > 404 && curX < 452 && curY > 32 && curY < 48) { npc.gift(true); }
                            if (curX > 404 && curX < 438 && curY > 64 && curY < 80) { npc.gift(false); }
                        }
                        else {
                            if (curX > 404 && curX < 452 && curY > 32 && curY < 48) { npc.lesson(true); }
                            if (curX > 404 && curX < 438 && curY > 64 && curY < 80) { npc.lesson(false); }
                        }

                        break;
                    case 6:
                        x = (int)game.player.direction.getX();
                        y = (int)game.player.direction.getY();
                        npc = (NPC) Game.objectMatrix[y][x];
                        if (curX > 196 && curX < 384 && curY > 32 && curY < 48 && Game.items[2][0] > 0) { npc.gift(0); }
                        if (curX > 196 && curX < 384 && curY > 64 && curY < 80 && Game.items[2][1] > 0) { npc.gift(1); }
                        if (curX > 196 && curX < 384 && curY > 96 && curY < 116 && Game.items[2][2] > 0) { npc.gift(2); }
                        if (curX > 196 && curX < 356 && curY > 128 && curY < 144 && Game.items[2][3] > 0) { npc.gift(3); }
                        if (curX > 196 && curX < 356 && curY > 160 && curY < 176) { npc.gift(false); }
                        break;
                    case 7:
                        x = (int)game.player.direction.getX();
                        y = (int)game.player.direction.getY();
                        npc = (NPC) Game.objectMatrix[y][x];
                        if (curX > 196 && curX < 404 && curY > 32 && curY < 48 && Game.items[1][0] > 0) { npc.gift(0); }
                        if (curX > 196 && curX < 404 && curY > 64 && curY < 80 && Game.items[1][1] > 0) { npc.gift(1); }
                        if (curX > 196 && curX < 404 && curY > 96 && curY < 116 && Game.items[1][2] > 0) { npc.gift(2); }
                        if (curX > 196 && curX < 356 && curY > 128 && curY < 144) { npc.gift(false); }
                        break;
                    case 8:
                        if (Game.lesson.feedback) {
                            Game.lesson.feedback = false;
                        }
                        else {
                            if (curX > 328 && curX < 424 && curY > 32 && curY < 48) { Game.lesson.doAction(0); }
                            if (curX > 328 && curX < 408 && curY > 64 && curY < 80) { Game.lesson.doAction(1); }
                            if (curX > 328 && curX < 408 && curY > 96 && curY < 116) { Game.lesson.doAction(2); }
                            if (curX > 328 && curX < 424 && curY > 128 && curY < 146) { Game.lesson.doAction(3); }
                        }
                        break;
                    case 9:
                        if (Game.lesson.feedback) {
                            Game.lesson.feedback = false;
                        }
                        else {
                            if (curX > 328 && curX < 440 && curY > 32 && curY < 48) { Game.lesson.doAction(0); }
                            if (curX > 328 && curX < 456 && curY > 64 && curY < 80) { Game.lesson.doAction(1); }
                            if (curX > 328 && curX < 428 && curY > 96 && curY < 116) { Game.lesson.doAction(2); }
                        }
                        break;
                    case 10:
                        if (Game.lesson.feedback) {
                            Game.lesson.feedback = false;
                        }
                        else {
                            if (curX > 328 && curX < 440 && curY > 32 && curY < 48) { Game.lesson.doAction(0); }
                            if (curX > 328 && curX < 408 && curY > 64 && curY < 80) { Game.lesson.doAction(1); }
                            if (curX > 328 && curX < 408 && curY > 96 && curY < 116) { Game.lesson.doAction(2); }
                        }
                        break;
                    case 11:
                        if (Game.lesson.feedback) {
                            Game.lesson.feedback = false;
                        }
                        else {
                            if (curX > 328 && curX < 376 && curY > 32 && curY < 48) { Game.lesson.doAction(0); }
                            if (curX > 328 && curX < 376 && curY > 64 && curY < 80) { Game.lesson.doAction(1); }
                            if (curX > 328 && curX < 428 && curY > 96 && curY < 116) { Game.lesson.doAction(2); }
                            if (curX > 328 && curX < 392 && curY > 128 && curY < 144) { Game.lesson.doAction(3); }
                            if (curX > 328 && curX < 408 && curY > 160 && curY < 176) { Game.lesson.doAction(4); }
                        }
                        break;
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON3 && Game.textBox == null) {
            if (Game.menu == null || Game.menu.currentId != 0) {
                Game.menu = new Menu(0);
            }
            else  { Game.menu = null; }
        }
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        boolean click = false;
        try {
            int curX = e.getX();
            int curY = e.getY();

            switch (Game.menu.currentId) {
                case 0:
                    click = curX > 328 && curX < 428 && curY > 32 && curY < 48 ||
                            curX > 328 && curX < 440 && curY > 64 && curY < 80 ||
                            curX > 328 && curX < 380 && curY > 96 && curY < 116 ||
                            curX > 328 && curX < 408 && curY > 128 && curY < 146;
                    break;
                case 1:
                    click = curX > 404 && curX < 424 && curY > 32 && curY < 48 ||
                            curX > 404 && curX < 436 && curY > 64 && curY < 80;
                    break;
                case 2:
                    click = curX > 280 && curX < 392 && curY > 32 && curY < 48 ||
                            curX > 280 && curX < 424 && curY > 64 && curY < 80 ||
                            curX > 280 && curX < 344 && curY > 96 && curY < 116 ||
                            curX > 280 && curX < 440 && curY > 128 && curY < 144 ||
                            curX > 280 && curX < 360 && curY > 160 && curY < 176;
                    break;
                case 3:
                    click = curX > 280 && curX < 312 && curY > 32 && curY < 48 ||
                            curX > 280 && curX < 424 && curY > 64 && curY < 80 ||
                            curX > 280 && curX < 312 && curY > 96 && curY < 116 ||
                            curX > 280 && curX < 422 && curY > 128 && curY < 144 ||
                            curX > 280 && curX < 328 && curY > 160 && curY < 176;
                    break;
                case 5:
                    click = curX > 404 && curX < 452 && curY > 32 && curY < 48 ||
                            curX > 404 && curX < 438 && curY > 64 && curY < 80;
                    break;
                case 6:
                    click = curX > 196 && curX < 384 && curY > 32 && curY < 48 && Game.items[2][0] > 0 ||
                            curX > 196 && curX < 384 && curY > 64 && curY < 80 && Game.items[2][1] > 0 ||
                            curX > 196 && curX < 384 && curY > 96 && curY < 116 && Game.items[2][2] > 0 ||
                            curX > 196 && curX < 356 && curY > 128 && curY < 144 && Game.items[2][3] > 0 ||
                            curX > 196 && curX < 356 && curY > 160 && curY < 176;
                    break;
                case 7:
                    click = curX > 196 && curX < 404 && curY > 32 && curY < 48 && Game.items[1][0] > 0 ||
                            curX > 196 && curX < 404 && curY > 64 && curY < 80 && Game.items[1][1] > 0 ||
                            curX > 196 && curX < 404 && curY > 96 && curY < 116 && Game.items[1][2] > 0 ||
                            curX > 196 && curX < 356 && curY > 128 && curY < 144;
                    break;
                case 8:
                    click = (curX > 328 && curX < 424 && curY > 32 && curY < 48 ||
                             curX > 328 && curX < 408 && curY > 64 && curY < 80 ||
                             curX > 328 && curX < 408 && curY > 96 && curY < 116 ||
                             curX > 328 && curX < 424 && curY > 128 && curY < 146) && !Game.lesson.feedback;
                    break;
                case 9:
                    click = (curX > 328 && curX < 440 && curY > 32 && curY < 48 ||
                             curX > 328 && curX < 456 && curY > 64 && curY < 80 ||
                             curX > 328 && curX < 428 && curY > 96 && curY < 116) && !Game.lesson.feedback;
                    break;
                case 10:
                    click =  (curX > 328 && curX < 440 && curY > 32 && curY < 48 ||
                              curX > 328 && curX < 408 && curY > 64 && curY < 80 ||
                              curX > 328 && curX < 408 && curY > 96 && curY < 116) && !Game.lesson.feedback;
                    break;
                case 11:
                    click = (curX > 328 && curX < 376 && curY > 32 && curY < 48 ||
                             curX > 328 && curX < 376 && curY > 64 && curY < 80 ||
                             curX > 328 && curX < 428 && curY > 96 && curY < 116 ||
                             curX > 328 && curX < 392 && curY > 128 && curY < 144 ||
                             curX > 328 && curX < 408 && curY > 160 && curY < 176) && !Game.lesson.feedback;
                    break;
            }


        } catch (NullPointerException e1) {
            //out of frame
        }

        if (click) { this.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        else { this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); }
    }

    public Dimension getPreferredSize() { return Constants.FRAME_SIZE; }

}
