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
public class GameMap {

    private BufferedImage mapImage = null;
    private boolean hidden = false;

    GameMap() {

        try {

            mapImage = ImageIO.read(new File("data/map.png"));
            
        } catch (Exception e) {

            System.out.println("GameMap::Map read error : " + e);
            
        }

    }

    public void render(Graphics g) {
        if (!hidden) {
            g.setColor(Color.black);
            g.fillRect(0, 0, Main.width, Main.height);
            g.drawImage(mapImage, 0, 0, null);
        }
    }
}
