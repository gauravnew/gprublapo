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

            PrintWriter out = new PrintWriter(sckClient.getOutputStream(), true);

            NetworkStreamParser netIn = new  NetworkStreamParser(in);

            NetworkStreamWriter netOut = new NetworkStreamWriter(out);


            while (true) {
                
                char[] message = netIn.getNextMessage();

                int op = 255 * message[0] + message[1];

                switch(op) {

                    case 'P'*255 + 'G':

                        netOut.sendPingReply();

                        break;

                    default:

                        break;

                }
            }



        } catch (Exception e) {

            System.out.println("Server Error.");

        }

    }
}
