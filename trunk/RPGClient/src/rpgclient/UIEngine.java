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

    UIEngine() {
        try {
            topBar = ImageIO.read(new File("data/BAR.png"));
        } catch (Exception e) {
            System.out.println("UIENGINE::Failed to load.");
        }
    }

    public void render(Graphics g) {
        g.drawImage(topBar, 10, 10, null);
    }

}
