package utilities;

import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static game.Game.GAME;

/**
 * Created by lmweav on 03/11/2017.
 */
public class Menu {
    public int currentId;
    public boolean blink;
    public static Point iconPoint;
    public static int minimapId, tick = 0;
    public static String text, friendText, gradeText;
    public static BufferedImage[] imgs, mapImgs, friendImgs, gradeImgs;
    public static BufferedImage img, mapImg, iconImg, friendImg, gradeImg, titleImg;




    public Menu(int id) {
        this.currentId = id;
        friendImg = null;
        gradeImg = null;
        setUp(id);
    }


    public static void setUp(int id) {
        switch (id) {
            case 0:
                img = imgs[0];
                text = FileReader.menuStrings[0];
                break;
            case 1:
                img = imgs[1];
                loadMapImage(GAME.map.minimapId);
                text = FileReader.menuStrings[1];
                break;
            case 2:
                img = imgs[2];
                text = FileReader.menuStrings[2];
                break;
            case 3:
            case 15:
                img = imgs[2];
                text = FileReader.menuStrings[3];
                break;
            case 4:
                img = imgs[3];
                text =    FileReader.menuStrings[4] + GAME.items[0][0] + FileReader.menuStrings[5] + GAME.items[0][1] +
                        FileReader.menuStrings[6] + GAME.items[1][0] + FileReader.menuStrings[7] + GAME.items[1][1] +
                        FileReader.menuStrings[8] + GAME.items[1][2] + FileReader.menuStrings[9] + GAME.items[1][3] +
                        FileReader.menuStrings[10] + GAME.items[2][0] + FileReader.menuStrings[11] + GAME.items[2][1] +
                        FileReader.menuStrings[12] + GAME.items[2][2] + FileReader.menuStrings[13] + GAME.items[2][3];
                break;
            case 5:
            case 13:
            case 14:
                img = imgs[1];
                text = FileReader.menuStrings[14];
                break;
            case 6:
                img = imgs[5];
                text =    FileReader.menuStrings[15] + GAME.items[2][0] + FileReader.menuStrings[11] + GAME.items[2][1] +
                        FileReader.menuStrings[12] + GAME.items[2][2] + FileReader.menuStrings[17];
                break;
            case 7:
                img = imgs[5];
                text =    FileReader.menuStrings[16] + GAME.items[1][0] + FileReader.menuStrings[7] + GAME.items[1][1] +
                        FileReader.menuStrings[8] + GAME.items[1][2] + FileReader.menuStrings[17];
                break;
            case 8:
                img = imgs[0];
                text = FileReader.menuStrings[18];
                break;
            case 9:
                img = imgs[6];
                text = FileReader.menuStrings[19];
                break;
            case 10:
                img = imgs[6];
                text = FileReader.menuStrings[20];
                break;
            case 11:
                img = imgs[8];
                text = FileReader.menuStrings[21];
                break;
            case 12:
                img = imgs[9];
                text = FileReader.menuStrings[22];
                break;
            case 16:
                img = imgs[4];
                text =    FileReader.menuStrings[15] + GAME.items[2][0] + FileReader.menuStrings[11] + GAME.items[2][1] +
                        FileReader.menuStrings[12] + GAME.items[2][2] + FileReader.menuStrings[13] + GAME.items[2][3] +
                        FileReader.menuStrings[17];
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
                gradeText = FileReader.menuStrings[23];
                break;
            case 1:
                gradeText = FileReader.menuStrings[24];
                break;
            case 2:
                gradeText = FileReader.menuStrings[25];
                break;
            case 3:
                gradeText = FileReader.menuStrings[26];
                break;
            case 4:
                gradeText = FileReader.menuStrings[27];
                break;
        }

        setGradeValue(id);

    }

    public static void setFriendValue(int index) {
        int fp = GAME.friendValues[index];
        if (fp == 0) { friendText = FileReader.menuStrings[28]; }
        if (fp > 0) { friendText = FileReader.menuStrings[29]; }
        if (fp > 10) { friendText = FileReader.menuStrings[30]; }
        if (fp > 20) { friendText = FileReader.menuStrings[31]; }
    }

    public static void setGradeValue(int index) {
        int gp = GAME.gradeValues[index];
        if (gp >= 0) { gradeImg = gradeImgs[0]; }
        if (gp > 9) { gradeImg = gradeImgs[1]; }
        if (gp > 19) { gradeImg = gradeImgs[2]; }
        if (gp > 29) { gradeImg = gradeImgs[3]; }
    }

    public static void loadImages() {
        try {
            imgs = new BufferedImage[10];
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
            System.out.println("Cannot find image.");
            e.printStackTrace();
        }

    }

    public void paintSubMenu(Graphics g, int id) {
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
            int subX = Game.width - (((img.getWidth() + subImg.getWidth()) * 2) + 24);
            g.drawImage(subImg, subX, 16, subImg.getWidth() * 2, subImg.getHeight() * 2, null);

            g.setFont(GameFont.smallFont);
            int textWidth = g.getFontMetrics(GameFont.smallFont).stringWidth(subText);
            g.drawString(subText, subX + (subImg.getWidth() - textWidth / 2), 166);
        } catch (NullPointerException e) {
            //No image
        }
    }

    public static void hotKeyAccess(int id) {
        if (GAME.textBox == null) {
            GameAudio.playSfx(GameAudio.sfx_menu);
            if (GAME.menu == null) { GAME.menu = new Menu(id); }
            else if (GAME.menu.currentId != id) { GAME.menu = new Menu(id); }
            else  { GAME.menu = null; }
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, Game.width - ((img.getWidth() * 2) + 16), 16,
                img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.bigFont);
        int lineIndex = 0;
        for (String line : text.split("#")) {
            g.drawString(line, Game.width - ((img.getWidth() * 2)), 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
            lineIndex++;
        }
        switch (currentId) {
            case 1:
                tick++;
                if (tick == 5) {
                    blink = !blink;
                    tick = 0;
                }
                int mapX = (Game.width - (mapImg.getWidth() * 4)) / 2;
                int mapY = (Game.height - (mapImg.getHeight() * 4)) / 2;
                g.drawImage(mapImg, mapX, mapY, mapImg.getWidth() * 4, mapImg.getHeight() * 4, null);
                if (blink && minimapId == GAME.map.minimapId) {
                    g.drawImage(iconImg, mapX + Menu.iconPoint.x, mapY + Menu.iconPoint.y, iconImg.getWidth() * 2, iconImg.getHeight()* 2, null);
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
