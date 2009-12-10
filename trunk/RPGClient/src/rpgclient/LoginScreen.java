/*
 * Filename : LoginScreen.java
 * Description : Class that creates the login screen and
 *  then initates the network connection once vaild
 *  information has been provided by the user.
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
    private TextField ip;
    private TextField user;
    private JButton login;

    public String getIP() {
        return ip.getText();
    }

    public String getName() {
        return user.getText();
    }

    LoginScreen() {
        try {

            panel = new Panel();

            panel.setBackground(Color.gray);
            panel.setLayout(new FlowLayout());

            panel.add(new Label("Server IP : "));

            ip = new TextField("127.0.0.1", 16) {

                @Override
                public boolean keyDown(Event evt, int key) {
                    if ((key >= 'a' && key <= 'z') || (key >= 'A' && key <= 'Z')) {
                        return true;
                    } else {
                        return super.keyUp(evt, key);
                    }
                }
            };

            panel.add(ip);

            panel.add(new Label("Username : "));

            user = new TextField("Opal", 16);
            panel.add(user);

            login = new JButton("Login");
            login.addActionListener(new ButtonListener());
            panel.add(login);

            panel.setSize(width, height);
            panel.setLocation((Main.width / 2) - (width / 2), (Main.height / 2) - (height / 2));

            backgoundImage = ImageIO.read(new File("data/login-screen.png"));

            Main.addPanel(panel);

        } catch (Exception e) {

            System.out.println("Unable to load background Image.");
            System.exit(101);

        }


    }

    public void hide() {
        if (hidden == false) {
            panel.hide();
            hidden = true;
        }
    }

    public void show() {
        if (hidden == true) {
            panel.show();
            hidden = false;
        }
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

//Function for when login button is pushed (This is the only button action available)
class ButtonListener implements ActionListener {

    ButtonListener() {
    }
    //[C.N.001]

    public void actionPerformed(ActionEvent e) {
        //if (e.getActionCommand().equals("Login")) {

        NetworkEngine net = new NetworkEngine();
        Main.setNetworkEngine(net);
        Main.coreLogic.setMainActorName(Main.getGameLogic().login.getName());
        net.setServerIP(Main.getGameLogic().login.getIP());
        (new Thread(net)).start();
        //}
    }
}
