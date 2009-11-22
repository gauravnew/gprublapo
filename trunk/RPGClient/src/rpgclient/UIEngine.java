/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    }

}
