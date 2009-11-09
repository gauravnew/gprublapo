/*
 * Filename : NetworkStreamWriter.java
 * Description : This class is to send messages
 * from the server to the client. Each possible message
 * has its own function call.
 */

package rpgserver;

import java.io.*;

/**
 *
 * @author gm
 */

public class NetworkStreamWriter {
    private PrintWriter netOut;

    public NetworkStreamWriter(PrintWriter out) {

        netOut = out;

    }

    public void sendPingReply() {
        netOut.print("PG");
        netOut.flush();
    }

}
