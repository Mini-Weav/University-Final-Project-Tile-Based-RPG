package utilities;

import game.Constants;
import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by lmweav on 03/11/2017.
 */
public class Menu {
    public int currentId;
    public boolean blink;
    public static Point iconPoint;
    public static int minimapId, tick = 0;
    public static String text, friendText, gradeText;
    public static BufferedImage img, mapImg, iconImg, friendImg, gradeImg;




    public Menu(int id) {
        this.currentId = id;
        friendImg = null;
        gradeImg = null;
        loadImage(id);
    }


    public static void loadImage(int id) {
        try {
            switch (id) {
                case 0:
                    img = ImageIO.read(new File("resources/menus/menu_3row.png"));
                    text = "GRADES\nFRIENDS\nMAP";
                    break;
                case 1:
                    img = ImageIO.read(new File("resources/menus/menu_2row_thin.png"));
                    loadMapImage(Game.map.minimapId);
                    text = "G\n1F";
                    break;
                case 2:
                    img = ImageIO.read(new File("resources/menus/menu_5row.png"));
                    text = "ATHLETE\nCLASSMATE\nNERD\nDELINQUENT\nTUTEE";
                    break;
                case 3:
                    img = ImageIO.read(new File("resources/menus/menu_5row.png"));
                    text = "CHEMISTRY\nICT\nDT\nFOOD TECH\nPE";
                    break;

            }
        }
        catch (IOException e) {
            System.out.println("Cannot find menu image.");
            e.printStackTrace();
        }
    }

    public static void loadMapImage(int id) {
        try {
            iconImg = ImageIO.read(new File("resources/minimaps/minimap_icon.png"));
            minimapId = id;
            switch (id) {
                case 0:
                    mapImg = ImageIO.read(new File("resources/minimaps/minimap1.png"));
                    break;
                case 1:
                    mapImg = ImageIO.read(new File("resources/minimaps/minimap2.png"));
                    break;
            }
        }
        catch (IOException e) {
            System.out.println("Cannot find map image.");
            e.printStackTrace();
        }
    }

    public static void loadFriend(int id) {
        try {
            switch (id) {
                case 0:
                    friendImg = ImageIO.read(new File("resources/friendImages/friend1.png"));
                    break;
                case 1:
                    friendImg = ImageIO.read(new File("resources/friendImages/friend2.png"));
                    break;
                case 2:
                    friendImg = ImageIO.read(new File("resources/friendImages/friend3.png"));
                    break;
                case 3:
                    friendImg = ImageIO.read(new File("resources/friendImages/friend4.png"));
                    break;
                case 4:
                    friendImg = ImageIO.read(new File("resources/friendImages/friend5.png"));
                    break;
            }
        }
        catch (IOException e) {
            System.out.println("Cannot find friend image.");
            e.printStackTrace();
        }
        setFriendValue(id);
    }

    public static void loadGrade(int id) {

        switch (id) {
            case 0:
                gradeText = "Chemistry";
                break;
            case 1:
                gradeText = "ICT";
                break;
            case 2:
                gradeText = "DT";
                break;
            case 3:
                gradeText = "Food Tech";
                break;
            case 4:
                gradeText = "PE";
                break;
        }

        setGradeValue(id);

    }

    public static void setFriendValue(int index) {
        int fp = Game.friendValues.get(index);
        if (fp == 0) { friendText = "Don't know"; }
        if (fp > 0) { friendText = "Acquaintance"; }
        if (fp > 10) { friendText = "Friend"; }
        if (fp > 20) { friendText = "Good friend"; }
    }

    public static void setGradeValue(int index) {
        try {
            int gp = Game.gradeValues.get(index);
            if (gp >= 0) { gradeImg = ImageIO.read(new File("resources/gradeImages/grade_d.png")); }
            if (gp > 9) { gradeImg = ImageIO.read(new File("resources/gradeImages/grade_c.png")); }
            if (gp > 19) { gradeImg = ImageIO.read(new File("resources/gradeImages/grade_b.png")); }
            if (gp > 29) { gradeImg = ImageIO.read(new File("resources/gradeImages/grade_a.png")); }
        }
        catch (IOException e) {
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
        g.setFont(GameFont.font);
        int lineIndex = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, Constants.FRAME_WIDTH - ((img.getWidth() * 2)), 48 + (g.getFontMetrics().getHeight() + 16) * lineIndex);
            lineIndex++;
        }
        switch (currentId) {
            case 0:
                break;
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
