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

}
