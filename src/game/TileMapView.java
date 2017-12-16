package game;

import objects.GameObject;
import objects.InteractiveTile;
import objects.NPC;
import utilities.TextBox;
import objects.Tile;
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
 * Created by lmweav on 23/10/2017.
 */
public class TileMapView extends JComponent implements MouseListener, MouseMotionListener {

    public int currentId, minimapId;
    public int maxX, maxY;
    public List<List<Character>> matrix;
    public static TreeMap<Character, Tile> tiles;

    public TileMapView(TileMap map) {

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
            GAME.tileMatrix = new char[matrix.size()][matrix.get(0).size()];
            GAME.objectMatrix = new GameObject[matrix.size()][matrix.get(0).size()];
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
        if (GAME.intro) {
            g.fillRect(0, 0, Game.width * 2, Game.height * 2);
            GAME.textBox = new TextBox(0, "Today is my first day at#Brooklands Academy... I#hope it will be okay...");
            GAME.textBox.paintComponent(g);
            return;
        }
        for (int j = -1; j < Game.cameraHeight + 2; j++) {
            for (int i = -1; i < Game.cameraWidth + 2; i++) {
                try {
                    Tile tile = tiles.get(matrix.get(GAME.camera.y + j).get(GAME.camera.x + i));
                    g.drawImage(tile.img, i * 32 - GAME.camera.diffX, j * 32 - GAME.camera.diffY, 32, 32, null);
                } catch (IndexOutOfBoundsException e) {
                    Tile tile = tiles.get('#');
                    g.drawImage(tile.img, i * 32 - GAME.camera.diffX, j * 32 - GAME.camera.diffY, 32, 32, null);
                }
            }
        }
        synchronized (Game.class) {
            for (GameObject object : GAME.objects) {
                if (object.x >= GAME.camera.x - 1 && object.x <= GAME.camera.x + Game.cameraWidth + 1 &&
                        object.y >= GAME.camera.y - 1 && object.y <= GAME.camera.y + Game.cameraHeight + 1) {
                    object.paintComponent(g); }
            }
            GAME.statusMenu.paintComponent(g);
            if (GAME.textBox != null) { GAME.textBox.paintComponent(g); }
            if (GAME.menu != null) { GAME.menu.paintComponent(g); }
            if (GAME.transition) { g.fillRect(0, 0, Game.width * 2, Game.height * 2); }
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
            if (GAME.menu == null) {
                if (GAME.textBox == null) {
                    int x = (int)GAME.player.direction.getX();
                    int y = (int)GAME.player.direction.getY();
                    if (tiles.get(matrix.get(y).get(x)) instanceof InteractiveTile) {
                        TileMap currentMap = TileMapLoader.tileMaps.get(GAME.map.currentId);
                        GAME.textBox = currentMap.interactivePoints.get(new Point(x, y));
                    }
                    else if (GAME.tileMatrix[y][x] == 'N') {
                        NPC npc = (NPC) GAME.objectMatrix[y][x];
                        npc.rotate(GAME.player);
                        npc.interaction(GAME.time);
                    }
                    else { GAME.textBox = new TextBox(0, "There's nothing here.");}
                }
                else if (GAME.textBox.skip) {
                    if (GAME.intro) {
                        GAME.intro = false;
                    }
                    GAME.textBox = null; }
            }
            else {
                switch (GAME.menu.currentId) {
                    case 0:
                        if (curX > Game.width - 152 && curX < Game.width - 100
                                && curY > 96 && curY < 116) { GAME.menu = new Menu(1); }
                        if (curX > Game.width - 152 && curX < Game.width - 40
                                && curY > 64 && curY < 80) { GAME.menu = new Menu(2); }
                        if (curX > Game.width - 152 && curX < Game.width - 52
                                && curY > 32 && curY < 48) { GAME.menu = new Menu(3); }
                        if (curX > Game.width - 152 && curX < Game.width - 72
                                && curY > 128 && curY < 146) { GAME.menu = new Menu(4); }
                        return;
                    case 1:
                        if (curX > Game.width - 76 && curX < Game.width - 56
                                && curY > 32 && curY < 52) { Menu.loadMapImage(0); }
                        if (curX > Game.width - 76 && curX < Game.width - 44
                                && curY > 64 && curY < 84) { Menu.loadMapImage(1); }
                        break;
                    case 2:
                        if (curX > Game.width - 200 && curX < Game.width - 88
                                && curY > 32 && curY < 48 ){ Menu.loadFriend(0); }
                        if (curX > Game.width - 200 && curX < Game.width - 56
                                && curY > 64 && curY < 80){ Menu.loadFriend(1); }
                        if (curX > Game.width - 200 && curX < Game.width - 136
                                && curY > 96 && curY < 116){ Menu.loadFriend(2); }
                        if (curX > Game.width - 200 && curX < Game.width - 40
                                && curY > 128 && curY < 144){ Menu.loadFriend(3); }
                        if (curX > Game.width - 200 && curX < Game.width - 120
                                && curY > 160 && curY < 176){ Menu.loadFriend(4); }
                        break;
                    case 3:
                        if (curX > Game.width - 200 && curX < Game.width - 168
                                && curY > 32 && curY < 48 ){ Menu.loadGrade(0); }
                        if (curX > Game.width - 200 && curX < Game.width - 56
                                && curY > 64 && curY < 80){ Menu.loadGrade(1); }
                        if (curX > Game.width - 200 && curX < Game.width - 168
                                && curY > 96 && curY < 116){ Menu.loadGrade(2); }
                        if (curX > Game.width - 200 && curX < Game.width - 56
                                && curY > 128 && curY < 144){ Menu.loadGrade(3); }
                        if (curX > Game.width - 200 && curX < Game.width - 152
                                && curY > 160 && curY < 176){ Menu.loadGrade(4); }
                        break;
                    case 5:
                        int x = (int)GAME.player.direction.getX();
                        int y = (int)GAME.player.direction.getY();
                        NPC npc = (NPC) GAME.objectMatrix[y][x];
                        if (npc.id < 5) {
                            if (curX > Game.width - 76 && curX < Game.width - 28
                                    && curY > 32 && curY < 48) { npc.gift(true); }
                            if (curX > Game.width - 76 && curX < Game.width - 42
                                    && curY > 64 && curY < 80) { npc.gift(false); }
                        }
                        else {
                            if (curX > Game.width - 76 && curX < Game.width - 28
                                    && curY > 32 && curY < 48) { npc.lesson(true); }
                            if (curX > Game.width - 76 && curX < Game.width - 42
                                    && curY > 64 && curY < 80) { npc.lesson(false); }
                        }

                        break;
                    case 6:
                        x = (int)GAME.player.direction.getX();
                        y = (int)GAME.player.direction.getY();
                        npc = (NPC) GAME.objectMatrix[y][x];
                        if (curX > Game.width - 284 && curX < Game.width - 96
                                && curY > 32 && curY < 48 && GAME.items[2][0] > 0) { npc.gift(0); }
                        if (curX > Game.width - 284 && curX < Game.width - 96
                                && curY > 64 && curY < 80 && GAME.items[2][1] > 0) { npc.gift(1); }
                        if (curX > Game.width - 284 && curX < Game.width - 96
                                && curY > 96 && curY < 116 && GAME.items[2][2] > 0) { npc.gift(2); }
                        if (curX > Game.width - 284 && curX < Game.width - 124
                                && curY > 128 && curY < 144 && GAME.items[2][3] > 0) { npc.gift(3); }
                        if (curX > Game.width - 284 && curX < Game.width - 124
                                && curY > 160 && curY < 176) { npc.gift(false); }
                        break;
                    case 7:
                        x = (int)GAME.player.direction.getX();
                        y = (int)GAME.player.direction.getY();
                        npc = (NPC) GAME.objectMatrix[y][x];
                        if (curX > Game.width - 284 && curX < Game.width - 76
                                && curY > 32 && curY < 48 && GAME.items[1][0] > 0) { npc.gift(0); }
                        if (curX > Game.width - 284 && curX < Game.width - 76
                                && curY > 64 && curY < 80 && GAME.items[1][1] > 0) { npc.gift(1); }
                        if (curX > Game.width - 284 && curX < Game.width - 76
                                && curY > 96 && curY < 116 && GAME.items[1][2] > 0) { npc.gift(2); }
                        if (curX > Game.width - 284 && curX < Game.width - 124
                                && curY > 128 && curY < 144) { npc.gift(false); }
                        break;
                    case 8:
                        if (GAME.lesson.feedback) {
                            GAME.lesson.feedback = false;
                        }
                        else {
                            if (curX > Game.width - 152 && curX < Game.width - 56
                                    && curY > 32 && curY < 48) { GAME.lesson.doAction(0); }
                            if (curX > Game.width - 152 && curX < Game.width - 72
                                    && curY > 64 && curY < 80) { GAME.lesson.doAction(1); }
                            if (curX > Game.width - 152 && curX < Game.width - 72
                                    && curY > 96 && curY < 116) { GAME.lesson.doAction(2); }
                            if (curX > Game.width - 152 && curX < Game.width - 56
                                    && curY > 128 && curY < 146) { GAME.lesson.doAction(3); }
                        }
                        break;
                    case 9:
                        if (GAME.lesson.feedback) {
                            GAME.lesson.feedback = false;
                        }
                        else {
                            if (curX > Game.width - 152 && curX < Game.width - 40
                                    && curY > 32 && curY < 48) { GAME.lesson.doAction(0); }
                            if (curX > Game.width - 152 && curX < Game.width - 24
                                    && curY > 64 && curY < 80) { GAME.lesson.doAction(1); }
                            if (curX > Game.width - 152 && curX < Game.width - 52
                                    && curY > 96 && curY < 116) { GAME.lesson.doAction(2); }
                        }
                        break;
                    case 10:
                        if (GAME.lesson.feedback) {
                            GAME.lesson.feedback = false;
                        }
                        else {
                            if (curX > Game.width - 152 && curX < Game.width - 40
                                    && curY > 32 && curY < 48) { GAME.lesson.doAction(0); }
                            if (curX > Game.width - 152 && curX < Game.width - 72
                                    && curY > 64 && curY < 80) { GAME.lesson.doAction(1); }
                            if (curX > Game.width - 152 && curX < Game.width - 72
                                    && curY > 96 && curY < 116) { GAME.lesson.doAction(2); }
                        }
                        break;
                    case 11:
                        if (GAME.lesson.feedback) {
                            GAME.lesson.feedback = false;
                        }
                        else {
                            if (curX > Game.width - 152 && curX < Game.width - 104
                                    && curY > 32 && curY < 48) { GAME.lesson.doAction(0); }
                            if (curX > Game.width - 152 && curX < Game.width - 104
                                    && curY > 64 && curY < 80) { GAME.lesson.doAction(1); }
                            if (curX > Game.width - 152 && curX < Game.width - 52
                                    && curY > 96 && curY < 116) { GAME.lesson.doAction(2); }
                            if (curX > Game.width - 152 && curX < Game.width - 88
                                    && curY > 128 && curY < 144) { GAME.lesson.doAction(3); }
                            if (curX > Game.width - 152 && curX < Game.width - 72
                                    && curY > 160 && curY < 176) { GAME.lesson.doAction(4); }
                        }
                        break;
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON3 && GAME.textBox == null) {
            if (GAME.menu == null || GAME.menu.currentId != 0) {
                GAME.menu = new Menu(0);
            }
            else  { GAME.menu = null; }
        }
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        boolean click = false;
        try {
            int curX = e.getX();
            int curY = e.getY();

            switch (GAME.menu.currentId) {
                case 0:
                    click = curX > Game.width - 152 && curX < Game.width - 100 && curY > 96 && curY < 116 ||
                            curX > Game.width - 152 && curX < Game.width - 40 && curY > 64 && curY < 80 ||
                            curX > Game.width - 152 && curX < Game.width - 52 && curY > 32 && curY < 48 ||
                            curX > Game.width - 152 && curX < Game.width - 72 && curY > 128 && curY < 146;
                    break;
                case 1:
                    click = curX > Game.width - 76 && curX < Game.width - 56 && curY > 32 && curY < 52 ||
                            curX > Game.width - 76 && curX < Game.width - 44 && curY > 64 && curY < 84;
                    break;
                case 2:
                    click = curX > Game.width - 200 && curX < Game.width - 88 && curY > 32 && curY < 48 ||
                            curX > Game.width - 200 && curX < Game.width - 56 && curY > 64 && curY < 80 ||
                            curX > Game.width - 200 && curX < Game.width - 136 && curY > 96 && curY < 116 ||
                            curX > Game.width - 200 && curX < Game.width - 40 && curY > 128 && curY < 144 ||
                            curX > Game.width - 200 && curX < Game.width - 120 && curY > 160 && curY < 176;
                    break;
                case 3:
                    click = curX > Game.width - 200 && curX < Game.width - 168 && curY > 32 && curY < 48 ||
                            curX > Game.width - 200 && curX < Game.width - 56 && curY > 64 && curY < 80 ||
                            curX > Game.width - 200 && curX < Game.width - 168 && curY > 96 && curY < 116 ||
                            curX > Game.width - 200 && curX < Game.width - 56 && curY > 128 && curY < 144 ||
                            curX > Game.width - 200 && curX < Game.width - 152 && curY > 160 && curY < 176;
                    break;
                case 5:
                    click = curX > Game.width - 76 && curX < Game.width - 28 && curY > 32 && curY < 48 ||
                            curX > Game.width - 76 && curX < Game.width - 42 && curY > 64 && curY < 80;
                    break;
                case 6:
                    click = curX > Game.width - 284 && curX < Game.width - 96 && curY > 32 && curY < 48
                                    && GAME.items[2][0] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 96 && curY > 64 && curY < 80
                                    && GAME.items[2][1] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 96 && curY > 96 && curY < 116
                                    && GAME.items[2][2] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 124 && curY > 128 && curY < 144
                                    && GAME.items[2][3] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 124 && curY > 160 && curY < 176;
                    break;
                case 7:
                    click = curX > Game.width - 284 && curX < Game.width - 76 && curY > 32 && curY < 48
                                    && GAME.items[1][0] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 76 && curY > 64 && curY < 80
                                    && GAME.items[1][1] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 76 && curY > 96 && curY < 116
                                    && GAME.items[1][2] > 0 ||
                            curX > Game.width - 284 && curX < Game.width - 124 && curY > 128 && curY < 144;
                    break;
                case 8:
                    click = (curX > Game.width - 152 && curX < Game.width - 56 && curY > 32 && curY < 48 ||
                             curX > Game.width - 152 && curX < Game.width - 72 && curY > 64 && curY < 80 ||
                             curX > Game.width - 152 && curX < Game.width - 72 && curY > 96 && curY < 116 ||
                             curX > Game.width - 152 && curX < Game.width - 56 && curY > 128 && curY < 146)
                            && !GAME.lesson.feedback;
                    break;
                case 9:
                    click = (curX > Game.width - 152 && curX < Game.width - 40 && curY > 32 && curY < 48 ||
                             curX > Game.width - 152 && curX < Game.width - 24 && curY > 64 && curY < 80 ||
                             curX > Game.width - 152 && curX < Game.width - 52 && curY > 96 && curY < 116)
                            && !GAME.lesson.feedback;
                    break;
                case 10:
                    click = (curX > Game.width - 152 && curX < Game.width - 40 && curY > 32 && curY < 48 ||
                             curX > Game.width - 152 && curX < Game.width - 72 && curY > 64 && curY < 80 ||
                             curX > Game.width - 152 && curX < Game.width - 72 && curY > 96 && curY < 116)
                            && !GAME.lesson.feedback;
                    break;
                case 11:
                    click = (curX > Game.width - 152 && curX < Game.width - 104 && curY > 32 && curY < 48 ||
                             curX > Game.width - 152 && curX < Game.width - 104 && curY > 64 && curY < 80 ||
                             curX > Game.width - 152 && curX < Game.width - 52 && curY > 96 && curY < 116 ||
                             curX > Game.width - 152 && curX < Game.width - 88 && curY > 128 && curY < 144 ||
                             curX > Game.width - 152 && curX < Game.width - 72 && curY > 160 && curY < 176)
                            && !GAME.lesson.feedback;
                    break;
            }


        } catch (NullPointerException e1) {
            //out of frame
        }

        if (click) { this.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        else { this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); }
    }

    public Dimension getPreferredSize() { return new Dimension(Game.width, Game.height); }

}
