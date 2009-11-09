/*
 * Filename : ClientHandler.java
 * Description : Handles an individual client in
 * an independent thread.
 */

package rpgserver;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

/**
 * ClientHandler class
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

        Scanner in;
        PrintWriter out;

        try {

            sckClient.setKeepAlive(true);
            
            in = new Scanner(new InputStreamReader(sckClient.getInputStream()));

            out = new PrintWriter(sckClient.getOutputStream(), true);

            NetworkStreamParser netIn = new  NetworkStreamParser(in, semaphore);

            NetworkStreamWriter netOut = new NetworkStreamWriter(out);

            System.out.println("Client Connected from: " + sckClient.getInetAddress());

            while (!sckClient.isClosed()) {
                int opcode = 0;
                opcode = netIn.getNextMessageOPCode();

                if (opcode == 0) continue;

                switch(opcode) {

                    case 'P'*255 + 'G':

                        System.out.println("Ping recieved.");
                        netOut.sendPingReply();

                        break;

                    default:

                        return;

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
