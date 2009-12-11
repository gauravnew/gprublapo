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
import java.awt.image.BufferedImage;

/**
 *
 * @author Gaurav
 */
public class GameMap {

    private BufferedImage mapData = null;	//[S.D.043]
    private int width = 0;
    private int height = 0;

    //Constructor - read map data from file and establish variables.
    GameMap() {
        try {

            mapData = ImageIO.read(new File("data/map_dat.png"));
            width = mapData.getWidth() / 16;
            height = mapData.getHeight() / 16;

        } catch (Exception e) {

            System.out.println("GameMap::Map read error : " + e);

        }

    }

    //Return the type of the point on the map, based upon its color.
    public int getCellType(Point2D p) {
        if (p.getX() < 0 | p.getX() > (width - 1)) {
            return 0;
        }
        if (p.getY() < 0 | p.getY() > (height - 1)) {
            return 0;
        }
        int c = mapData.getRGB((int) (p.getX() * 16 + 8), (int) (p.getY() * 16 + 8));

        switch (c) {
            case 0xFFFFFFFF:
                return 1;	//[S.D.041]
            case 0xFFFF0000:
                return 2;	//[S.D.041]
            default:
                return 0;	//[S.D.042]
        }
    }

    //Generate a random point on the map that is "walkable"
    public Point2D getRandomMapPoint() {

        Point2D p;

        do {

            p = new Point2D((int) (Math.random() * width), (int) (Math.random() * height));

        } while (getCellType(p) != 1);

        return p;

    }

    public Point2D getRandomHorizontal(Point2D p) {
        Point2D d;
        int i=0;
        do {
            d = new Point2D((int) (Math.random() * 20) - 10 + p.getX(), p.getY());
            i++;
        } while (getCellType(d) != 1 && i < 100);
        if(i==100) return p;
        return d;
    }

    public Point2D getRandomVertical(Point2D p) {
        Point2D d;
        int i=0;
        do {
            d = new Point2D(p.getX(), (int) (Math.random() * 20) - 10 + p.getY());
            i++;
        } while (getCellType(d) != 1 && i<100);
        if(i==100) return p;
        return d;
    }
}
