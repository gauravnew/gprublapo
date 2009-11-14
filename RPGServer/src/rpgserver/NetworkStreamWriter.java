/*
 * Filename : NetworkStreamWriter.java
 * Description : This class is to send messages
 * from the server to the client. Each possible message
 * has its own function call.
 */

package rpgserver;

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

    //Send ping reply packet to client.
    //[S->C]["PG"] (docs/Network Protocol.txt)
    public synchronized void sendPingReply() {

        try {


            //Write to buffer.
            netOut.writeShort('P'*256+'G');

            //Flush buffer.
            netOut.flush();
            
        } catch (Exception e) {

            //Error
            System.out.println("NetworkStreamWriter : sendPingReply : Server Error.");

        }
    }

    public synchronized void sendMapImage(File f) {
        try {

            int flen = (int)f.length();
            byte [] file = new byte[flen];
            (new DataInputStream(new FileInputStream(f))).readFully(file);
            netOut.writeShort('M' * 256 + 'I');
            netOut.writeInt(flen);
            netOut.write(file);
            netOut.flush();
            
        } catch (Exception e) {
            System.out.println("NetworkStreamWriter : sendMapImage : Server Error.");
        }

    }

    public synchronized void sendMapData(File f) {
        try {

            int flen = (int)f.length();
            byte [] file = new byte[flen];
            (new DataInputStream(new FileInputStream(f))).readFully(file);
            netOut.writeShort('M' * 256 + 'D');
            netOut.writeInt(flen);
            netOut.write(file);
            netOut.flush();

        } catch (Exception e) {
            System.out.println("NetworkStreamWriter : sendMapData : Server Error.");
        }

    }

}
