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
    private Point2D location;

    GameMap() {

        try {

            location = new Point2D(25,20);
            mapImage = ImageIO.read(new File("data/map.png"));
            
        } catch (Exception e) {

            System.out.println("GameMap::Map read error : " + e);
            
        }

    }

    public void setLookAt(Point2D p) {
        location = p;
    }

    public Point2D getLookAt() {
        return location;
    }

    private Point2D getMapCoordinates() {
        int x = (int)(16*location.getX());
        int y = (int)(16*location.getY());
        return new Point2D((-x) + (Main.width/2) - 8, (-y) + (Main.height/2) - 8);
    }

    public void render(Graphics g) {
        if (!hidden) {
            g.setColor(Color.black);
            g.fillRect(0, 0, Main.width, Main.height);
            Point2D dest = getMapCoordinates();
            g.drawImage(mapImage, (int)dest.getX(), (int)dest.getY(), null);
        }
    }
}
