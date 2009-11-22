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
    public static CoreGameLogic coreLogic = null;
    public static NetworkEngine netEngine = null;
    public static final int width = 800;
    public static final int height = 600;

    public static synchronized XCanvas getCanvas() {
        return canvas;
    }

    public static synchronized CoreGameLogic getGameLogic() {
        return coreLogic;
    }

    public static synchronized NetworkEngine getNetworkEngine() {
        return netEngine;
    }

    public static synchronized void setNetworkEngine(NetworkEngine n) {
        if (netEngine == null)
            netEngine = n;
    }


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
		//[C.G.012]
         while (true)
             canvas.repaint();

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main();
    }

    public static void addPanel(Panel p) {
        canvas.add(p);
        p.repaint();
        canvas.repaint();
    }

    public static void removePanel(Panel p) {
        canvas.remove(p);
        canvas.repaint();
    }

}
