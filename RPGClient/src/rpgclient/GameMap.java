/*
 * Filename : GameMap.java
 * Description : Stores information regarding the game map
 *  and establishes functions for accesing said information.
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
    private BufferedImage mapData = null;
    private boolean hidden = false;
    private Point2D location;

    GameMap() {
        try {

            location = new Point2D(25,20);
            mapImage = ImageIO.read(new File("data/map.png"));
            mapData = ImageIO.read(new File("data/map_dat.png"));
            
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

    public int getCellType(Point2D p) {
    	if (p.getX()<0 | p.getX() > (Main.width-1)) return 0;
    	if (p.getY()<0 | p.getY() > (Main.height-1)) return 0;
    	int c = mapData.getRGB((int)(p.getX()*16+8), (int)(p.getY()*16+8));

        switch (c) {
            case 0xFFFFFFFF:
                return 1;
            case 0xFFFF0000:
                return 1;
            default:
                return 0;
        }
    }
	//[C.G.001]
    public void render(Graphics g) {
        if (!hidden) {
            g.setColor(Color.black);
            g.fillRect(0, 0, Main.width, Main.height);
            Point2D dest = getMapCoordinates();
            g.drawImage(mapImage, (int)dest.getX(), (int)dest.getY(), null);
        }
    }
}
