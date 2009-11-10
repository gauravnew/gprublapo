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

    //The socket that this client is using.
    private Socket sckClient;

    //Constructor.
    public ClientHandler(Socket c) {
        sckClient = c;
    }

    //Class entry-point.
    public void run() {

        //Network input stream.
        Scanner in;
        //Network output stream.
        PrintWriter out;

        try {

            //Set TCP::SO_KEEPALIVE flag to true.
            sckClient.setKeepAlive(true);

            //Get input stream.
            in = new Scanner(new InputStreamReader(sckClient.getInputStream()));

            //Get output stream.
            out = new PrintWriter(sckClient.getOutputStream(), true);

            //Instanciate NetworkStreamParser to manage network input.
            NetworkStreamParser netIn = new  NetworkStreamParser(in);

            //Instanciate NetworkStreamWriter to manage network output.
            NetworkStreamWriter netOut = new NetworkStreamWriter(out);

            //A client has been connected.
            System.out.println("Client Connected from: " + sckClient.getInetAddress());

            //Client's loop until connection is closed.
            while (!sckClient.isClosed()) {

                //Get OPCode for the next packet from NetworkStreamParser.
                int opcode = 0;
                opcode = netIn.getNextMessageOPCode();

                //If OPCode is zero then no packets have been recieved.
                //(Non-blocking)
                if (opcode == 0) continue;

                //Different tasks to do for different types of packets.
                switch(opcode) {

                    //The ping packet has been recieved.
                    //[C->S]["PG"] (docs/Network Protocol.txt)
                    case 'P'*255 + 'G':

                        System.out.println("Ping recieved.");
                        //Send ping reply packet to client.
                        //[S->C]["PG"] (docs/Network Protocol.txt)
                        netOut.sendPingReply();

                        break;

                    default:

                        //Unknown packet, disconnect and return.
                        sckClient.close();
                        System.out.println("Client connection closed.");
                        return;

                }
            }



        } catch (Exception e) {

            //An error has been encountered.
            System.out.println("Server Error." +  e);

        }

        try {

            //Exit Thread.
            sckClient.close();
            System.out.println("Client connection closed.");

        } catch (Exception e) {
            return;
        }

    }
}
