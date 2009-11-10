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
    private PrintWriter netOut;
    //Local thread access manager.
    private Semaphore semaphore;

    //Constructor.
    public NetworkStreamWriter(PrintWriter out) {

        netOut = out;
        semaphore = new Semaphore(1,true);

    }

    //Send ping reply packet to client.
    //[S->C]["PG"] (docs/Network Protocol.txt)
    public void sendPingReply() {

        try {

            //Enter critical section.
            semaphore.acquire();

            //Write to buffer.
            netOut.print("PG");

            //Flush buffer.
            netOut.flush();

            //Leave critical section.
            semaphore.release();
            
        } catch (Exception e) {

            //Error
            System.out.println("Server Error.");
            //Leave critical section.
            semaphore.release();

        }
    }

}
