package utilities;

import game.Constants;
import objects.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lmweav on 01/11/2017.
 */
public class TextBox {
    public int id;
    public boolean skip;
    public String text, npcName;
    public static BufferedImage[] imgs, npcImgs;
    public BufferedImage img, npcImg;

    public TextBox(int id, String text) {
        this.id = id;
        setUp(id);
        this.text = text;
    }

    public TextBox(String text, NPC npc, boolean skip) {
        if (skip) { this.id = 2; }
        else { this.id = 4; }
        setUp(id);
        this.text = text;
        npcImg = npcImgs[npc.id];
        npcName = npc.name;
    }

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
        }
    }

    public static void loadImages() {
        try {
            imgs = new BufferedImage[2];
            imgs[0] = ImageIO.read(new File("resources/textboxes/textbox.png"));
            imgs[1] = ImageIO.read(new File("resources/textboxes/textbox_big.png"));
            npcImgs = new BufferedImage[10];
            npcImgs[0] = ImageIO.read(new File("resources/friendImages/friend1.png"));
            npcImgs[1] = ImageIO.read(new File("resources/friendImages/friend2.png"));
            npcImgs[2] = ImageIO.read(new File("resources/friendImages/friend3.png"));
            npcImgs[3] = ImageIO.read(new File("resources/friendImages/friend4.png"));
            npcImgs[4] = ImageIO.read(new File("resources/friendImages/friend5.png"));
            npcImgs[5] = ImageIO.read(new File("resources/friendImages/teacher1.png"));
            npcImgs[6] = ImageIO.read(new File("resources/friendImages/teacher2.png"));
            npcImgs[7] = ImageIO.read(new File("resources/friendImages/teacher3.png"));
            npcImgs[8] = ImageIO.read(new File("resources/friendImages/teacher4.png"));
            npcImgs[9] = ImageIO.read(new File("resources/friendImages/teacher5.png"));

        } catch (IOException e) {
            System.out.println("Cannot find image.");
        }
    }

    public void paintComponent(Graphics g) {
        int lineIndex;
        switch (id) {
            case 0:
            case 3:
                g.drawImage(img, 0, Constants.FRAME_HEIGHT - 136, img.getWidth() * 2, img.getHeight() * 3, null);
                g.setFont(GameFont.bigFont);
                lineIndex = 0;
                for (String line : text.split("#")) {
                    g.drawString(line, 24, (Constants.FRAME_HEIGHT - 90 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                break;
            case 1:
                g.drawImage(img, 0, 0, img.getWidth() * 2, img.getHeight() * 3, null);
                g.setFont(GameFont.medFont);
                lineIndex = 0;
                for (String line : text.split("#")) {
                    g.drawString(line, 24, (26 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                break;
            case 2:
            case 4:
                g.drawImage(img, 0, Constants.FRAME_HEIGHT - 136, img.getWidth() * 2, img.getHeight() * 3, null);
                g.drawImage(npcImg, 10, Constants.FRAME_HEIGHT - (126 + npcImg.getHeight() * 2), npcImg.getWidth() * 2, npcImg.getHeight() * 2, null);
                g.setFont(GameFont.bigFont);
                lineIndex = 0;
                for (String line : text.split("\n")) {
                    g.drawString(line, 24, (Constants.FRAME_HEIGHT - 90 + (g.getFontMetrics().getHeight() + 4) * lineIndex));
                    lineIndex++;
                }
                g.setFont(GameFont.smallFont);
                int textWidth = g.getFontMetrics(GameFont.smallFont).stringWidth(npcName);
                g.drawString(npcName, 10 + (npcImg.getWidth())- (textWidth / 2), Constants.FRAME_HEIGHT - 137);


        }
    }
}
