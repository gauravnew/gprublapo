/*
 * Filename : ClientHandler.java
 * Description : Handles an individual client in
 * an independent thread.
 */
/** TODO
 * Modify infinite loop (ln 70) to only allow moves once game has started.
 * Ssend the remaining ("countdown") time to the client.
 * When gamestate changes to countdown, transmit list of actors.
 * Modify opcode switch case for player move packet; move request should just set moveto
 * Before infinite loop loops (ln **?), call updatePosition(), check for collisions, process them
 *  using processCollision (verify return value in case of win -- if so set gamestate to win
 *  transmit gameover win, exit loop, terminate thread), transmit new location & health
 * Transmit game over / loss when gamestate changes to game over.
 * Add datamember PlayerCharacter myCharacter
 * Update setActorID to grab myCharacter from GlobalGameDatabase
 * Transmit other visible players' locations when they move (each loop)
 */
package rpgserver;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

import rpgserver.GlobalGameLogic.GAME_STATE;

/**
 * ClientHandler class
 * @author gm
 */
public class ClientHandler implements Runnable {

    //Locals.
    public Socket sckClient;
    private Integer myActorID;
    private PlayerCharacter myCharacter;
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
        myCharacter = cDBEngine.getPlayer(actid);
    }

    public void sendAllCharacter() {
        //game database
        ConcurrentSkipListSet<Actor> gameDB = Main.cDBEngine.getHashtableKeys();
        //game iterator
        Iterator<Actor> itr = gameDB.iterator();

        //Appropriate characters: other players(type 0), H1N1 (type 1), Manhole(type 3)
        //iterate through all the actors
        while (itr.hasNext()) {
            Actor temp = itr.next();
            if (!temp.equals(myCharacter)) {
                if (temp.type == 0 || temp.type == 1 || temp.type == 3) {
                    netOut.sendNewActorData(temp.actorID.intValue(), temp.type, temp.speed, temp.name, temp.position);
                }
            }
        }
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

            //Instantiate NetworkStreamParser to manage network input.
            netIn = new NetworkStreamParser(in);

            //Instantiate NetworkStreamWriter to manage network output.
            netOut = new NetworkStreamWriter(out);
            cDBEngine.setPlayerOutputStream(myActorID, netOut);

            //A client has been connected.
            //System.out.println("Client Connected from: " + sckClient.getInetAddress());


            //Client's loop until connection is closed.
            while (true) {

                //Get OPCode for the next packet from NetworkStreamParser.
                int opcode = 0;
                opcode = netIn.getNextMessageOPCode();

                //If OPCode is zero then no packets have been recieved.
                //(Non-blocking)
                if (opcode != 0) //Different tasks to do for different types of packets.
                {
                    switch (opcode) {

                        //The ping packet has been recieved.
                        //[C->S]["PG"] (docs/Network Protocol.txt)
                        case 'P' * 256 + 'G':

                            // System.out.println("NETWORK::Ping recieved.");
                            //Send ping reply packet to client.
                            //[S->C]["PG"] (docs/Network Protocol.txt)
                            netOut.sendPingReply();

                            break;

                        case 'L' * 256 + 'G':  //[S.N.011]

                            String name = netIn.getNamefromLogin();
                            // System.out.println("NETWORK::Login Recieved from " + name);
                            cDBEngine.setActorName(myActorID, name);
                            netOut.sendMapImage(new File("data/map.png"));
                            netOut.sendMapData(new File("data/map_dat.png"));

                            //random pt
                            Point2D randPt = Main.cGameLogic.cMapEngine.getRandomMapPoint(); //[S.L.051]
                            netOut.sendTeleport(0, new Point2D(randPt.getX(), randPt.getY()));

                            while (Main.cGameLogic.checkState(GAME_STATE.COUNTDOWN)) {
                                Thread.sleep(50);
                                netOut.sendCountDown((int) (30 - (System.currentTimeMillis() - Main.cGameLogic.countdown) / 1000));
                            }
                            this.sendAllCharacter(); //[S.N.021]

                            break;

                        case 'M' * 256 + 'V':
                            // System.out.println("NETWORK::Player Move" + myActorID.intValue());
                            Point2D p = netIn.getActorMove();
                            if (Main.cGameLogic.cMapEngine.getCellType(p) != 0) {
                                cDBEngine.setActorMoveTo(myActorID, p);  //[S.L.063]
                            }

                            break;

                        default:

                            //Unknown packet, disconnect and return.
                            sckClient.close();
                            // System.out.println("Client connection closed.");
                            cDBEngine.deleteActor(myActorID);
                            return;

                    }
                }
            }



        } catch (Exception e) {

            //An error has been encountered.
            return;

        }

    }
}
