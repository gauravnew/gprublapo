/*
 * Filename : Main.java
 * Description : Program entrypoint for the Boston
 * University Game Server running on port 1234.
 */

package rpgserver;

import java.net.*;


/**
 * Program Entry Point
 * @author gm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            //Open a network socket at port 1234 and wait for incoming connections.
            ServerSocket srvr = new ServerSocket(1234);
            while (true) {
                //Accept incoming connections.
                Socket client = srvr.accept();
                
                if (client != null) {
                    ClientHandler cHandler = new ClientHandler(client);
                    new Thread(cHandler).start();
                }
                
            }

        } catch (Exception e) {
            
            //A socket error has occoured to print a message.
            System.out.println("Server error.");

        }

    }

}
