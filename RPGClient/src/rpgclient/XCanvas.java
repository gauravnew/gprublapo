/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgclient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Gaurav
 */
public class XCanvas extends Panel {

    private MouseEvent mouse;
    private KeyEvent key;

    public synchronized void setKey(KeyEvent evt) {
        key = evt;
    }

    public synchronized KeyEvent getKey() {
        KeyEvent k = key;
        return k;
    }

    public synchronized void setMouse(MouseEvent evt) {
        mouse = evt;
    }

    public synchronized MouseEvent getMouse() {
        return mouse;
    }

    XCanvas() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                setMouse(e);
            }

        });

         addKeyListener(new KeyAdapter() {

           @Override
           public void keyPressed(KeyEvent ke) {
               setKey(ke);
           }

           @Override
           public void keyReleased(KeyEvent ke) {
               setKey(null);
           }
        });

    }



    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Main.coreLogic.renderLoop(g);
    }
}


