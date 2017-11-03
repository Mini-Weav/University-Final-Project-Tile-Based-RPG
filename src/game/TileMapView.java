package game;

import objects.GameObject;
import objects.InteractiveTile;
import utilities.TextBox;
import objects.Tile;
import utilities.TileMapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by lmweav on 23/10/2017.
 */
public class TileMapView extends JComponent implements MouseListener {
    private Game game;
    public int currentId;
    public int maxX, maxY;
    public List<List<Character>> matrix;
    public static TreeMap<Character, Tile> tiles;

    public TileMapView(Game game, TileMap map) {
        this.game = game;

        loadMap(map);
        addMouseListener(this);
    }

    public static List<List<Character>> readMap(File txtFile) throws IOException{
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
            Game.gameMatrix = new ArrayList<>(this.matrix.size());
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
        for (GameObject object : game.objects) {
            if (object.x >= Game.camera.x && object.x <= Game.camera.x + Constants.CAMERA_SIZE_X &&
                    object.y >= Game.camera.y && object.y <= Game.camera.y + Constants.CAMERA_SIZE_Y) {
                object.paintComponent(g); }
        }
        if (Game.transition) { g.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT); }
        if (Game.textBox != null) { Game.textBox.paintComponent(g); }
    }

    public Dimension getPreferredSize() { return Constants.FRAME_SIZE; }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        if (Game.textBox == null) {
            int x = (int)game.player.direction.getX();
            int y = (int)game.player.direction.getY();
            if (tiles.get(matrix.get(y).get(x)) instanceof InteractiveTile) {
                TileMap currentMap = TileMapLoader.tileMaps.get(Game.map.currentId);
                String text = currentMap.interactivePoints.get(new Point(x, y));
                Game.textBox = new TextBox(text);
            }
            else { Game.textBox = new TextBox("There's nothing here.");}
        }
        else { Game.textBox = null; }
    }
}
