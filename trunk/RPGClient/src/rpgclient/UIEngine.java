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
    private String countDown = "00";
    private long lastTime = 0;

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

    private void decrementCount() {
        int val = Integer.valueOf(countDown).intValue();

        if (val > 0) {
            countDown = String.valueOf(val - 1);
            if (countDown.length() < 2)
                countDown = "0" + countDown;
        }
    }
	//[C.G.002]
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(90, 60, 112, 20);
        g.drawImage(topBar, 10, 10, null);
        g.setColor(Color.black);
        g.drawString(playerName, 95, 41);
        g.setColor(Color.red);

        if (!countDown.equals("00")) {

            if (lastTime == 0) {
                lastTime = System.currentTimeMillis();
            }

            long pastTime = System.currentTimeMillis() - lastTime;
            if (pastTime >= 1000) {
                lastTime = System.currentTimeMillis();
                decrementCount();
            }
            g.setFont(new Font("Tahoma",0,400));
            g.setColor(Color.gray);
            g.fillRect(Main.width/2-220, Main.height/2+130, 480, -340);
            g.setColor(Color.black);
            g.drawString(countDown, Main.width/2-200, Main.height/2+110);
        }
        
    }

}
