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
public class Main extends Frame {

    private static XCanvas canvas;
    private static CoreGameLogic coreLogic;
    public static final int width = 800;
    public static final int height = 600;


    public Main() {

        super("Main");

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
        );

        setSize(width,height);
        setTitle("BU Multiplayer Race");
        setResizable(false);
        
        canvas = new XCanvas();
        canvas.setLayout(null);
        add(canvas);
        
        coreLogic = new CoreGameLogic();

        show();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main();
    }

    public static void addPanel(Panel p) {
        canvas.add(p);
    }

    public static void removePanel(Panel p) {
        canvas.remove(p);
    }

    public static void renderLoop(Graphics g) {
        if (coreLogic.state == coreLogic.state.LOGIN_STATE) {
            coreLogic.login.render(g);
        }
        
    }

}
