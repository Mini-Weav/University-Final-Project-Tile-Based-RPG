package utilities;

import game.Constants;
import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    public static BufferedImage img, mapImg, iconImg, friendImg, gradeImg;




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
                text = "GRADES\nFRIENDS\nMAP\nITEMS";
                break;
            case 1:
                img = imgs[1];
                loadMapImage(Game.map.minimapId);
                text = "G\n1F";
                break;
            case 2:
                img = imgs[2];
                text = "ATHLETE\nCLASSMATE\nNERD\nDELINQUENT\nTUTEE";
                break;
            case 3:
                img = imgs[2];
                text = "DT\nFOOD TECH\nPE\nCHEMISTRY\nICT";
                break;
            case 4:
                img = imgs[3];
                text =    "ENERGY DRINK ×" + Game.items[0][0] + "\nSTINK BOMB   ×" + Game.items[0][1] +
                        "\nD GRADE CRAFT×" + Game.items[1][0] + "\nC GRADE CRAFT×" + Game.items[1][1] +
                        "\nB GRADE CRAFT×" + Game.items[1][2] + "\nSUPER KEY    ×" + Game.items[1][3] +
                        "\nD GRADE FOOD ×" + Game.items[2][0] + "\nC GRADE FOOD ×" + Game.items[2][1] +
                        "\nB GRADE FOOD ×" + Game.items[2][2] + "\nSUPER CAKE   ×" + Game.items[2][3];
                break;
            case 5:
                img = imgs[1];
                text = "YES\nNO";
                break;
            case 6:
                img = imgs[4];
                text =    "D GRADE FOOD  ×" + Game.items[2][0] + "\nC GRADE FOOD  ×" + Game.items[2][1] +
                        "\nB GRADE FOOD  ×" + Game.items[2][2] + "\nSUPER CAKE    ×" + Game.items[2][3] +
                        "\nNEVER MIND";
                break;
            case 7:
                img = imgs[5];
                text =    "D GRADE CRAFT ×" + Game.items[1][0] + "\nC GRADE CRAFT ×" + Game.items[1][1] +
                        "\nB GRADE CRAFT ×" + Game.items[1][2] + "\nNEVER MIND";
                break;
            case 8:
                img = imgs[0];
                text = "ANSWER\nTHINK\nDRINK\nTOILET";
                break;
            case 9:
                img = imgs[6];
                text = "DO TASK\nDO TASK+\nREREAD";
                break;
            case 10:
                img = imgs[6];
                text = "WARM UP\nSTART\nDRINK";
                break;
            case 11:
                img = imgs[8];
                text = "JOG\nRUN\nSPRINT\nREST\nDRINK";
                break;
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
    }

    public static void loadFriend(int id) {
        friendImg = friendImgs[id];
        setFriendValue(id);
    }

    public static void loadGrade(int id) {
        switch (id) {
            case 0:
                gradeText = "DT";
                break;
            case 1:
                gradeText = "Food Tech";
                break;
            case 2:
                gradeText = "PE";
                break;
            case 3:
                gradeText = "Chemistry";
                break;
            case 4:
                gradeText = "ICT";
                break;
        }

        setGradeValue(id);

    }

    public static void setFriendValue(int index) {
        int fp = Game.friendValues[index];
        if (fp == 0) { friendText = "Don't know"; }
        if (fp > 0) { friendText = "Acquaintance"; }
        if (fp > 10) { friendText = "Friend"; }
        if (fp > 20) { friendText = "Good friend"; }
    }

    public static void setGradeValue(int index) {
        int gp = Game.gradeValues[index];
        if (gp >= 0) { gradeImg = gradeImgs[0]; }
        if (gp > 9) { gradeImg = gradeImgs[1]; }
        if (gp > 19) { gradeImg = gradeImgs[2]; }
        if (gp > 29) { gradeImg = gradeImgs[3]; }
    }

    public static void loadImages() {
        try {
            imgs = new BufferedImage[9];
            imgs[0] = ImageIO.read(new File("resources/menus/menu_4row.png"));
            imgs[1] = ImageIO.read(new File("resources/menus/menu_2row_thin.png"));
            imgs[2] = ImageIO.read(new File("resources/menus/menu_5row.png"));
            imgs[3] = ImageIO.read(new File("resources/menus/menu_10row_wide.png"));
            imgs[4] = ImageIO.read(new File("resources/menus/menu_5row_wide.png"));
            imgs[5] = ImageIO.read(new File("resources/menus/menu_4row_wide.png"));
            imgs[6] = ImageIO.read(new File("resources/menus/menu_3row.png"));
            imgs[7] = ImageIO.read(new File("resources/menus/menu_3row_wide.png"));
            imgs[8] = ImageIO.read(new File("resources/menus/menu_5row_medium.png"));
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

        } catch (IOException e) {
            System.out.println("Cannot find grade image.");
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
            int subX = (Constants.FRAME_WIDTH - (subImg.getWidth() * 4)) / 2;
            g.drawImage(subImg, subX, 16, subImg.getWidth() * 2, subImg.getHeight() * 2, null);

            g.setFont(GameFont.smallFont);
            int textWidth = g.getFontMetrics(GameFont.smallFont).stringWidth(subText);
            g.drawString(subText, subX + (subImg.getWidth() - textWidth / 2), 166);
        } catch (NullPointerException e) {
            //No image
        }
    }

    public static void hotKeyAccess(int id) {
        if (Game.menu == null) { Game.menu = new Menu(id); }
        else if (Game.menu.currentId != id) { Game.menu = new Menu(id); }
        else  { Game.menu = null; }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, Constants.FRAME_WIDTH - ((img.getWidth() * 2) + 16), 16,
                img.getWidth() * 2, img.getHeight() * 2, null);
        g.setFont(GameFont.bigFont);
        int lineIndex = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, Constants.FRAME_WIDTH - ((img.getWidth() * 2)), 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
            lineIndex++;
        }
        switch (currentId) {
            case 1:
                tick++;
                if (tick == 5) {
                    blink = !blink;
                    tick = 0;
                }
                int mapX = (Constants.FRAME_WIDTH - (mapImg.getWidth() * 4)) / 2;
                int mapY = (Constants.FRAME_HEIGHT - (mapImg.getHeight() * 4)) / 2;
                g.drawImage(mapImg, mapX, mapY, mapImg.getWidth() * 4, mapImg.getHeight() * 4, null);
                if (blink && minimapId == Game.map.minimapId) {
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
