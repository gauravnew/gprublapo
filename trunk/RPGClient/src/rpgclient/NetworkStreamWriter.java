package rpgclient;

/*
 * Filename : NetworkStreamWriter.java
 * Description : This class is to send messages
 * from the server to the client. Each possible message
 * has its own function call.
 */

import java.io.*;
import java.util.concurrent.*;

/**
 *
 * @author gm
 */

public class NetworkStreamWriter {

    //Network Output stream for a client.
    private DataOutputStream netOut;

    //Constructor.
    public NetworkStreamWriter(DataOutputStream out) {

        netOut = out;

    }

    //Send login packet.
    //[C->S]["LG"][Name - 24 bytes] (docs/Network Protocol.txt)
    public synchronized void sendLoginPacket(String name) {
        try {
            byte [] b = new byte[24];

            for( int i = 0 ; i < name.getBytes().length ; i ++ ) {
                b[i] = name.getBytes()[i];
            }

            netOut.writeShort('L'*256 + 'G');
            netOut.write(b, 0, 24);
            netOut.flush();

        } catch (Exception e) {
            System.out.println("Server Error.");
        }
    }

    //Send ping reply packet to client.
    //[C->S]["PG"] (docs/Network Protocol.txt)
    public synchronized void sendPing() {

        try {


            //Write to buffer.
            netOut.writeShort('P'*256+'G');

            //Flush buffer.
            netOut.flush();

        } catch (Exception e) {

            //Error
            System.out.println("Server Error.");

        }
    }

    public synchronized void sendActorMove(Point2D target) {
        try {

            netOut.writeShort('M'*256 + 'V');
            netOut.writeFloat(target.getX());
            netOut.writeFloat(target.getY());
            
            netOut.flush();

        } catch(Exception e) {

            System.out.println("Server Error.");

        }
    }


}
