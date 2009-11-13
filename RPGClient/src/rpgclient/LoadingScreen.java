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
public class LoadingScreen {
    private final int MAX = 100;
    private int percentage = 0;
    private boolean hidden = false;

    LoadingScreen() {
        percentage = 0;
    }

    public void hide() {
        hidden = true;
    }
    
    public void show() {
        hidden = false;
    }

    public synchronized void inc() {
        percentage++;
    }

    public synchronized void dec() {
        percentage--;
    }

    public synchronized void incBy(int val) {
        percentage+=val;
    }

    public synchronized void reset() {
        percentage = 0;
    }

    private synchronized float getPercent() {
        return percentage/(float)MAX;
    }
    
    public void render(Graphics g) {
        if (!hidden) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Main.width, Main.height);
            g.setColor(Color.WHITE);
            g.drawRect(100, Main.height - 140, Main.width - 200, 40);
            g.setColor(Color.WHITE);
            g.fillRect(103, Main.height - 137, (int)(getPercent() * (Main.width - 205)), 35);
            g.setColor(Color.orange);
            g.setFont(new Font("Tahoma",0,15));
            g.drawString("Loading..." + (int)(getPercent() * 100) + "%" ,100, Main.height - 150);
        }
    }

}
