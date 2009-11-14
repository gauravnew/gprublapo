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

    //Locals.
    private Socket sckClient;
    private Integer myActorID;
    
    //Globals
    private GlobalGameDatabase cDBEngine;

    //Constructor.
    public ClientHandler(Socket c, GlobalGameDatabase dB) {
        sckClient = c;
        cDBEngine = dB;
    }

    public void setActorID(int actid) {
        myActorID = actid;
    }

    //Class entry-point.
    public void run() {

        //Network input stream.
        DataInputStream in;
        //Network output stream.
        DataOutputStream out;

        try {

            //Set TCP::SO_KEEPALIVE flag to true.
            sckClient.setKeepAlive(true);

            //Get input stream.
            in = new DataInputStream(sckClient.getInputStream());

            //Get output stream.
            out = new DataOutputStream(sckClient.getOutputStream());

            //Instanciate NetworkStreamParser to manage network input.
            NetworkStreamParser netIn = new  NetworkStreamParser(in);

            //Instanciate NetworkStreamWriter to manage network output.
            NetworkStreamWriter netOut = new NetworkStreamWriter(out);

            //A client has been connected.
            System.out.println("Client Connected from: " + sckClient.getInetAddress());

            //Client's loop until connection is closed.
            while (true) {

                //Get OPCode for the next packet from NetworkStreamParser.
                int opcode = 0;
                opcode = netIn.getNextMessageOPCode();

                //If OPCode is zero then no packets have been recieved.
                //(Non-blocking)
                if (opcode != 0)

                //Different tasks to do for different types of packets.
                switch(opcode) {

                    //The ping packet has been recieved.
                    //[C->S]["PG"] (docs/Network Protocol.txt)
                    case 'P'*256 + 'G':

                        System.out.println("NETWORK::Ping recieved.");
                        //Send ping reply packet to client.
                        //[S->C]["PG"] (docs/Network Protocol.txt)
                        netOut.sendPingReply();

                        break;

                    case 'L'*256 + 'G':

                        System.out.println("NETWORK::Login Recieved.");
                        cDBEngine.setActorName(myActorID, netIn.getNamefromLogin());
                        netOut.sendMapImage(new File("data/map.png"));
                        netOut.sendMapData(new File("data/map.txt"));
                        break;

                    default:

                        //Unknown packet, disconnect and return.
                        sckClient.close();
                        System.out.println("Client connection closed.");
                        cDBEngine.deleteActor(myActorID);
                        return;

                }
            }



        } catch (Exception e) {

            //An error has been encountered.
            System.out.println("Error, closing client thread." +  e);
            return;

        }

    }
}
