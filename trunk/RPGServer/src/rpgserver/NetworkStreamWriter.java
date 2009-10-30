package rpgserver;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gm
 */

import java.io.*;


public class NetworkStreamWriter {
    private PrintWriter netOut;

    public NetworkStreamWriter(PrintWriter out) {

        netOut = out;

    }

    public void sendPingReply() {
        netOut.println("PG");
    }

}
