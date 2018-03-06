package utilities;

import game.Game;
import objects.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 01/11/2017.
 */
public class TextBox {
    private static BufferedImage[] imgs;
    private static BufferedImage[] npcImgs;

    private int id;
    private boolean skip;
    private String text;
    private String npcName;
    private BufferedImage img;
    private BufferedImage npcImg;

    public TextBox(int id, String text) {
        this.id = id;
        setUp(id);
        this.text = text;
    }

    public TextBox(String text, NPC npc, boolean skip) {
        if (npc.getId() >= 10) {
            if (skip) { this.id = 0; }
            else { this.id = 3;}
        }
        else {
            if (skip) { this.id = 2; }
            else { this.id = 4; }
            npcImg = npcImgs[npc.getId()];
            npcName = npc.getName();
        }
        setUp(id);
        this.text = text;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public boolean isSkip() { return skip; }


    public void setUp(int id) {
        switch (id) {
            case 0:
            case 2:
                skip = true;
                img = imgs[0];
                break;
            case 1:
                skip = true;
                img = imgs[1];
                break;
            case 3:
            case 4:
                skip = false;
                img = imgs[0];
                break;
            case 5:
                skip = true;
                img = imgs[2];
                break;
            case 6:
                skip = true;
                img = imgs[3];
                break;
            case 7:
                skip = true;
                img = imgs[4];
                break;
        }
    }

    public static void loadImages() {
        try {
            imgs = new BufferedImage[5];
            imgs[0] = ImageIO.read(new File("resources/textboxes/textbox.png"));
            imgs[1] = ImageIO.read(new File("resources/textboxes/textbox_whiteboard1.png"));
            imgs[2] = ImageIO.read(new File("resources/textboxes/textbox_med.png"));
            imgs[3] = ImageIO.read(new File("resources/textboxes/textbox_whiteboard2.png"));
            imgs[4] = ImageIO.read(new File("resources/textboxes/textbox_whiteboard3.png"));
            npcImgs = new BufferedImage[5];
            npcImgs[0] = ImageIO.read(new File("resources/friendImages/friend1.png"));
            npcImgs[1] = ImageIO.read(new File("resources/friendImages/friend2.png"));
            npcImgs[2] = ImageIO.read(new File("resources/friendImages/friend3.png"));
            npcImgs[3] = ImageIO.read(new File("resources/friendImages/friend4.png"));
            npcImgs[4] = ImageIO.read(new File("resources/friendImages/friend5.png"));
        } catch (IOException e) {
            System.out.println("Cannot find image.");
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        int lineIndex;
        switch (id) {
            case 0:
            case 3:
                g.drawImage(img, 0, Game.getHeight() - 136, img.getWidth() * 2, img.getHeight() * 3, null);
                g.setFont(GameFont.getBigFont());
                lineIndex = 0;
                for (String line : text.split("#")) {
                    g.drawString(line, 24, (Game.getHeight() - 90 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                break;
            case 1:
            case 6:
            case 7:
                g.drawImage(img, 0, 0, img.getWidth() * 2, img.getHeight() * 3, null);
                g.setFont(GameFont.getMedFont());
                lineIndex = 0;
                for (String line : text.split("#")) {
                    g.drawString(line, 24, (26 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                break;
            case 2:
            case 4:
                g.drawImage(img, 0, Game.getHeight() - 136, img.getWidth() * 2, img.getHeight() * 3, null);
                g.drawImage(npcImg, 10, Game.getHeight() - (126 + (int) (npcImg.getHeight() * 2.3)),
                        (int) (npcImg.getWidth() * 2.3), (int) (npcImg.getHeight() * 2.3), null);
                g.setFont(GameFont.getBigFont());
                lineIndex = 0;
                for (String line : text.split("#")) {
                    g.drawString(line, 24, (Game.getHeight() - 90 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                g.setFont(GameFont.getSmallFont());
                int textWidth = g.getFontMetrics(GameFont.getSmallFont()).stringWidth(npcName);
                g.drawString(npcName, 10 + ((int) (npcImg.getWidth() * 1.15)) - (textWidth / 2), Game.getHeight() - 138);
                break;
            case 5:
                g.drawImage(img, 0, Game.getHeight() - 178, img.getWidth() * 2, img.getHeight() * 3, null);
                g.setFont(GameFont.getBigFont());
                lineIndex = 0;
                for (String line : text.split("#")) {
                    g.drawString(line, 24, (Game.getHeight() - 132 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                break;

        }
    }
}
