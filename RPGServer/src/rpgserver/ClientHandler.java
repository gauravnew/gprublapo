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

        BufferedReader in;
        PrintWriter out;

        try {

            in = new BufferedReader(new InputStreamReader(sckClient.getInputStream()));

            out = new PrintWriter(sckClient.getOutputStream(), true);

            NetworkStreamParser netIn = new  NetworkStreamParser(in);

            NetworkStreamWriter netOut = new NetworkStreamWriter(out);

            System.out.println("Client Connected from: " + sckClient.getInetAddress());

            while (!sckClient.isInputShutdown()) {
                
                char[] message = null;
                message = netIn.getNextMessage();

                if (message == null) return;


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

            System.out.println("Server Error." +  e);
            

        }

        try {

            sckClient.close();
            System.out.println("Client connection closed.");

        } catch (Exception e) {

        }

    }
}
