/*
 * Filename : GameMap.java
 * Description : Stores data about the game map.
 */

package rpgserver;

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
    
    private BufferedImage mapData = null;
    private int width = 0;
    private int height = 0;

	//Constructor - read map data from file and establish variables.
    GameMap() {
        try {

            mapData = ImageIO.read(new File("data/map_dat.png"));
            width = mapData.getWidth()/16;
            height = mapData.getHeight()/16;

        } catch (Exception e) {

            System.out.println("GameMap::Map read error : " + e);

        }

    }
	
	//Return the type of the point on the map, based upon its color.
    public int getCellType(Point2D p) {
        int c = mapData.getRGB((int)(p.getX()*16+8), (int)(p.getY()*16+8));

        switch (c) {
            case 0xFFFFFFFF:
                return 1;
            case 0xFF000000:
                return 0;
            default:
                return 0;
        }
    }
	
	//Generate a random point on the map that is "walkable"
    public Point2D getRandomMapPoint() {

        Point2D p;

        do {

            p = new Point2D((int)Math.random()*width, (int)Math.random()*height);

        } while (getCellType(p) != 1);
        
        return p;

    }

}
