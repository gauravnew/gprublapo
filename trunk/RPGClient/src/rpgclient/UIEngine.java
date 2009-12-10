/*
 * Filename : UIEngine.java
 * Description : Generates the user interface window
 */
package rpgclient;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author Gaurav
 */
public class UIEngine {

    private BufferedImage topBar = null;
    private String playerName = null;
    private int countDown = 0;
    private long lastTime = 0;

    public void setCountDown(int val) {
        countDown = val;
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    UIEngine() {
        try {
            topBar = ImageIO.read(new File("data/BAR.png"));
        } catch (Exception e) {
            System.out.println("UIENGINE::Failed to load.");
        }
    }

    //[C.G.002]
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(90, 60, 112, 20);
        g.drawImage(topBar, 10, 10, null);
        g.setColor(Color.black);
        g.drawString(playerName, 95, 41);
        g.drawString(Main.getGameLogic().getMessage(), 98, 73);
        g.setColor(Color.red);

        if (countDown != 0) {
            g.setFont(new Font("Tahoma", 0, 14));
            g.setColor(Color.gray);
            g.fillRect(0, Main.height - 50, Main.width, 50);
            g.setColor(Color.black);
            g.drawString("The Game will begin in " + countDown + " seconds", 10, Main.height - 32);
        }

    }
}
