/*
 * Filename : NetworkStreamParser.java
 * Description : This class parses packets recieved
 * from the client.
 */

package rpgserver;

import java.util.concurrent.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 * NetworkStreamParser class
 * @author gm
 */
public class NetworkStreamParser {

    //Network Input stream from a client.
    private DataInputStream netIn;

    //Constructor.
    public NetworkStreamParser(DataInputStream in) {

        netIn = in;
        
    }

    //Get next 2 byte packet OPCode from the network stream.
    public synchronized int getNextMessageOPCode() {

        try {
                //Is opcode available.
                if (netIn.available() >= 2) {

                    short opcode = 0;
                    //Get opcode.
                    opcode = netIn.readShort();

                    return opcode;
                } else {

                    return 0;

                }


        } catch (Exception e) {

            System.out.println("Internal Server Error.");

            //Return.
            return 0;

        }
        
    }

    
}
