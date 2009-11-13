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
public class LoginScreen {
    
    private BufferedImage backgoundImage = null;
    private Panel panel = null;
    private final int width = 180;
    private final int height = 150;
    private boolean hidden = false;


    LoginScreen() {
        try {

            panel = new Panel();

            panel.setBackground(Color.gray);
            panel.setLayout(new FlowLayout());
            Main.addPanel(panel);

            panel.add(new Label("Server IP : "));
            panel.add(new TextField("127.0.0.1",16));
            panel.add(new Label("Username : "));
            panel.add(new TextField("Opal",16));
            panel.add(new JButton("Login"));

            panel.setSize(width, height);
            panel.setLocation((Main.width/2) - (width/2), (Main.height/2) - (height/2));
            
            backgoundImage = ImageIO.read(new File("data/login-screen.png"));

        } catch (Exception e) {

            System.out.println("Unable to load background Image.");
            System.exit(101);

        }


    }

    public void hide() {
        panel.hide();
        hidden = true;
    }

    public void show() {
        panel.show();
        hidden = false;
    }

    @Override
    public void finalize() {
        destroy();
    }

    private void destroy() {
        Main.removePanel(panel);
    }

    public void render(Graphics g) {
        if (!hidden) {
            g.drawImage(backgoundImage, 0, 0, null);
        }
    }

}
