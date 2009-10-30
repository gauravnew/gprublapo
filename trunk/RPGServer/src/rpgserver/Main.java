/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import java.net.*;
import java.util.concurrent.*;


/**
 *
 * @author gm
 */
public class Main {

    public static Semaphore semaphore;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        semaphore = new Semaphore(1, true);

        try {

            //Open a network socket at port 1234 and wait for incoming connections.
            ServerSocket srvr = new ServerSocket(1234);

            //Accept incoming connections.
            Socket client = srvr.accept();

            ClientHandler cHandler = new ClientHandler(client, semaphore);
            new Thread(cHandler).start();

        } catch (Exception e) {
            
            //A socket error has occoured to print a message.
            System.out.println("Server error.");

        }

    }

}
