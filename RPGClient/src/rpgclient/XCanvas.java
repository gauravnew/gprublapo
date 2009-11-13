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

    public MouseEvent mouse;

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
        repaint();
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
