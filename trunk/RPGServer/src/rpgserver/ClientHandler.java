/*
 * Filename : ClientHandler.java
 * Description : Handles an individual client in
 * an independent thread.
 */
/** TODO
   *	Modify infinite loop (ln 70) to only allow moves once game has started.
   *	Ssend the remaining ("countdown") time to the client.
   *	When gamestate changes to countdown, transmit list of actors.
   *	Modify opcode switch case for player move packet; move request should just set moveto
   *	Before infinite loop loops (ln **?), call updatePosition(), check for collisions, process them
   *		using processCollision (verify return value in case of win -- if so set gamestate to win
		transmit gameover win, exit loop, terminate thread), transmit new location & health
	Transmit game over / loss when gamestate changes to game over.
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
    private NetworkStreamParser netIn;
    private NetworkStreamWriter netOut;
    
    //Globals
    private GlobalGameDatabase cDBEngine;

    //Constructor.
    public ClientHandler(Socket c, GlobalGameDatabase dB) {
        sckClient = c;
        cDBEngine = dB;
    }

    public NetworkStreamWriter getNetworkOutput() {
        return netOut;
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
            netIn = new  NetworkStreamParser(in);

            //Instanciate NetworkStreamWriter to manage network output.
            netOut = new NetworkStreamWriter(out);

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

                    case 'L'*256 + 'G':		//[S.N.011]

                        String name = netIn.getNamefromLogin();
                        System.out.println("NETWORK::Login Recieved from " + name);
                        cDBEngine.setActorName(myActorID, name);
                        netOut.sendMapImage(new File("data/map.png"));
                        netOut.sendMapData(new File("data/map_dat.png"));
                        break;

                    case 'M'*256 + 'V':
                        System.out.println("NETWORK::Player Move");
                        Point2D p = netIn.getActorMove();
                        if (Main.cGameLogic.cMapEngine.getCellType(p) != 0)
                            cDBEngine.setActorMoveTo(myActorID, p);
                        
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
