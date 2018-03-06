package utilities;

import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static game.Game.GAME;

/**
 * 03/11/2017.
 */
public class Menu {
    private static int minimapId;
    private static int tick = 0;
    private static String text;
    private static String friendText;
    private static String gradeText;
    private static Point iconPoint;
    private static BufferedImage img;
    private static BufferedImage mapImg;
    private static BufferedImage iconImg;
    private static BufferedImage friendImg;
    private static BufferedImage gradeImg;
    private static BufferedImage titleImg;

    private static BufferedImage[] imgs;
    private static BufferedImage[] mapImgs;
    private static BufferedImage[] friendImgs;
    private static BufferedImage[] gradeImgs;

    private int currentId;
    private boolean blink;
    private boolean visible;

    public Menu(int id) {
        this.currentId = id;
        friendImg = null;
        gradeImg = null;
        visible = true;
        setUp(id);
    }

    public static void setIconPoint(Point iconPoint) { Menu.iconPoint = iconPoint; }

    private static void setFriendValue(int index) {
        int fp = GAME.getFriendValue(index);
        if (fp == 0) { friendText = FileReader.getMenuString(28); }
        if (fp > 0) { friendText = FileReader.getMenuString(29); }
        if (fp > 10) { friendText = FileReader.getMenuString(30); }
        if (fp > 20) { friendText = FileReader.getMenuString(31); }
    }

    private static void setGradeValue(int index) {
        int gp = GAME.getGradeValue(index);
        if (gp >= 0) { gradeImg = gradeImgs[0]; }
        if (gp > 9) { gradeImg = gradeImgs[1]; }
        if (gp > 19) { gradeImg = gradeImgs[2]; }
        if (gp > 29) { gradeImg = gradeImgs[3]; }
    }

    static BufferedImage getTitleImg() { return titleImg; }

    static BufferedImage getImg(int index) { return imgs[index]; }

    public int getCurrentId() { return currentId; }

    public boolean isVisible() { return visible; }

    public void setVisible(boolean visible) { this.visible = visible; }

    public static void setUp(int id) {
        switch (id) {
            case 0:
                img = imgs[10];
                text = FileReader.getMenuString(0);
                break;
            case 1:
                img = imgs[1];
                loadMapImage(GAME.getMiniMapId());
                text = FileReader.getMenuString(1);
                break;
            case 2:
                img = imgs[2];
                text = FileReader.getMenuString(2);
                break;
            case 3:
            case 15:
                img = imgs[2];
                text = FileReader.getMenuString(3);
                break;
            case 4:
                img = imgs[3];
                text =  FileReader.getMenuString(4) + GAME.getItem(0, 0) +
                        FileReader.getMenuString(5) + GAME.getItem(0, 1) +
                        FileReader.getMenuString(6) + GAME.getItem(1, 0) +
                        FileReader.getMenuString(7) + GAME.getItem(1, 1) +
                        FileReader.getMenuString(8) + GAME.getItem(1, 2) +
                        FileReader.getMenuString(9) + GAME.getItem(1, 3) +
                        FileReader.getMenuString(10) + GAME.getItem(2, 0) +
                        FileReader.getMenuString(11) + GAME.getItem(2, 1) +
                        FileReader.getMenuString(12) + GAME.getItem(2, 2) +
                        FileReader.getMenuString(13) + GAME.getItem(2, 3);
                break;
            case 5:
            case 13:
            case 14:
            case 17:
                img = imgs[1];
                text = FileReader.getMenuString(14);
                break;
            case 6:
                img = imgs[5];
                text =  FileReader.getMenuString(15) + GAME.getItem(2, 0) +
                        FileReader.getMenuString(11) + GAME.getItem(2, 1) +
                        FileReader.getMenuString(12) + GAME.getItem(2, 2) +
                        FileReader.getMenuString(17);
                break;
            case 7:
                img = imgs[5];
                text =  FileReader.getMenuString(16) + GAME.getItem(1, 0) +
                        FileReader.getMenuString(7) + GAME.getItem(1, 1) +
                        FileReader.getMenuString(8) + GAME.getItem(1, 1) +
                        FileReader.getMenuString(17);
                break;
            case 8:
                img = imgs[8];
                text = FileReader.getMenuString(18);
                break;
            case 9:
                img = imgs[0];
                text = FileReader.getMenuString(19);
                break;
            case 10:
                img = imgs[0];
                text = FileReader.getMenuString(20);
                break;
            case 11:
                img = imgs[10];
                text = FileReader.getMenuString(21);
                break;
            case 12:
                img = imgs[9];
                text = FileReader.getMenuString(22);
                break;
            case 16:
                img = imgs[4];
                text =  FileReader.getMenuString(15) + GAME.getItem(2, 0) +
                        FileReader.getMenuString(11) + GAME.getItem(2, 1) +
                        FileReader.getMenuString(12) + GAME.getItem(2, 2) +
                        FileReader.getMenuString(13) + GAME.getItem(2, 3) +
                        FileReader.getMenuString(17);
        }
    }

    public static void loadMapImage(int id) {
        try {
            iconImg = ImageIO.read(new File("resources/minimaps/minimap_icon.png"));
            minimapId = id;
            mapImg = mapImgs[id];
        }
        catch (IOException e) {
            System.out.println("Cannot find map image.");
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            minimapId = 0;
            mapImg = mapImgs[0];
        }
    }
    public static void loadFriend(int id) {
        GameAudio.playSfx(GameAudio.sfx_click);
        friendImg = friendImgs[id];
        setFriendValue(id);
    }
    public static void loadGrade(int id) {
        GameAudio.playSfx(GameAudio.sfx_click);
        switch (id) {
            case 0:
                gradeText = FileReader.getMenuString(23);
                break;
            case 1:
                gradeText = FileReader.getMenuString(24);
                break;
            case 2:
                gradeText = FileReader.getMenuString(25);
                break;
            case 3:
                gradeText = FileReader.getMenuString(26);
                break;
            case 4:
                gradeText = FileReader.getMenuString(27);
                break;
        }
        setGradeValue(id);
    }

    public static void loadImages() {
        try {
            imgs = new BufferedImage[11];
            imgs[0] = ImageIO.read(new File("resources/menus/menu_4row.png"));
            imgs[1] = ImageIO.read(new File("resources/menus/menu_2row_thin.png"));
            imgs[2] = ImageIO.read(new File("resources/menus/menu_5row.png"));
            imgs[3] = ImageIO.read(new File("resources/menus/menu_10row_wide.png"));
            imgs[4] = ImageIO.read(new File("resources/menus/menu_5row_wide.png"));
            imgs[5] = ImageIO.read(new File("resources/menus/menu_4row_wide.png"));
            imgs[6] = ImageIO.read(new File("resources/menus/menu_3row.png"));
            imgs[7] = ImageIO.read(new File("resources/menus/menu_3row_wide.png"));
            imgs[8] = ImageIO.read(new File("resources/menus/menu_5row_medium.png"));
            imgs[9] = ImageIO.read(new File("resources/menus/menu_3row(title).png"));
            imgs[10] = ImageIO.read(new File("resources/menus/menu_6row.png"));
            mapImgs = new BufferedImage[2];
            mapImgs[0] = ImageIO.read(new File("resources/minimaps/minimap1.png"));
            mapImgs[1] = ImageIO.read(new File("resources/minimaps/minimap2.png"));
            friendImgs = new BufferedImage[5];
            friendImgs[0] = ImageIO.read(new File("resources/friendImages/friend1.png"));
            friendImgs[1] = ImageIO.read(new File("resources/friendImages/friend2.png"));
            friendImgs[2] = ImageIO.read(new File("resources/friendImages/friend3.png"));
            friendImgs[3] = ImageIO.read(new File("resources/friendImages/friend4.png"));
            friendImgs[4] = ImageIO.read(new File("resources/friendImages/friend5.png"));
            gradeImgs = new BufferedImage[4];
            gradeImgs[0] = ImageIO.read(new File("resources/gradeImages/grade_d.png"));
            gradeImgs[1] = ImageIO.read(new File("resources/gradeImages/grade_c.png"));
            gradeImgs[2] = ImageIO.read(new File("resources/gradeImages/grade_b.png"));
            gradeImgs[3] = ImageIO.read(new File("resources/gradeImages/grade_a.png"));
            titleImg = ImageIO.read(new File("resources/titlescreen.png"));

        } catch (IOException e) {
            System.out.println("Cannot menu find image.");
            e.printStackTrace();
        }
    }

    private void paintSubMenu(Graphics g, int id) {
        BufferedImage subImg;
        String subText;
        if (id == 0) {
            subImg = friendImg;
            subText = friendText;
        }
        else {
            subImg = gradeImg;
            subText = gradeText;
        }
        try {
            int subX = Game.getWidth() - ((int)((img.getWidth() + subImg.getWidth()) * 2.3));
            g.drawImage(subImg, subX, 16, (int) (subImg.getWidth() * 2.3), (int) (subImg.getHeight() * 2.3), null);

            g.setFont(GameFont.getSmallFont());
            int textWidth = g.getFontMetrics(GameFont.getTinyFont()).stringWidth(subText);
            g.drawString(subText, subX + ((int) (subImg.getWidth() * 1.1) - textWidth / 2), 188);
        } catch (NullPointerException e) { /* No image */ }
    }

    public static void hotKeyAccess(int id) {
        if (GAME.getTextBox() == null) {
            GameAudio.playSfx(GameAudio.sfx_menu);
            if (GAME.getMenu() == null) { GAME.setMenu(new Menu(id)); }
            else if (GAME.getMenu().currentId != id) { GAME.setMenu(new Menu(id)); }
            else  { GAME.setMenu(null); }
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, Game.getWidth() - ((img.getWidth() * 2) + 16), 16,
                img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.getBigFont());
        int lineIndex = 0;
        for (String line : text.split("#")) {
            g.drawString(line, Game.getWidth() - ((img.getWidth() * 2)), 48 +
                    (g.getFontMetrics().getHeight() + 16) * lineIndex);
            lineIndex++;
        }
        switch (currentId) {
            case 1:
                tick++;
                if (tick == 5) {
                    blink = !blink;
                    tick = 0;
                }
                int mapX = (Game.getWidth() - (mapImg.getWidth() * 2)) / 2;
                int mapY = (Game.getHeight() - (mapImg.getHeight() * 2)) / 2;
                g.drawImage(mapImg, mapX, mapY, mapImg.getWidth() * 2, mapImg.getHeight() * 2, null);
                if (blink && minimapId == GAME.getMiniMapId()) {
                    g.drawImage(iconImg, mapX + Menu.iconPoint.x, mapY + Menu.iconPoint.y, iconImg.getWidth() * 2,
                            iconImg.getHeight()* 2, null);
                }
                break;
            case 2:
                paintSubMenu(g, 0);
                break;
            case 3:
                paintSubMenu(g, 1);
                break;
        }
    }
}
