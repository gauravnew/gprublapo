/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;

/**
 *
 * @author gm
 */
public class ClientHandler implements Runnable {

    private Socket sckClient;
    public static Semaphore semaphore;


    public ClientHandler(Socket c, Semaphore s) {
        sckClient = c;
        semaphore = s;
    }

    public void run() {
        
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(sckClient.getInputStream()));

            while (true) {

                while (!in.ready()) {}

                char opcode[] = new char[2];
                in.read(opcode,0,2);
                
            }



        } catch (Exception e) {

            System.out.println("Server Error.");

        }

    }
}
