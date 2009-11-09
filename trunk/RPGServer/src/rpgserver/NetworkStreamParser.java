/*
 * Filename : NetworkStreamParser.java
 * Description : This class parses packets recieved
 * from the client.
 */

package rpgserver;

import java.io.*;
import java.util.concurrent.*;
import java.util.*;

/**
 * NetworkStreamParser class
 * @author gm
 */
public class NetworkStreamParser {

    private Scanner netIn;
    private Semaphore semaphore;

    public NetworkStreamParser(Scanner in, Semaphore s) {

        semaphore = s;
        netIn = in;
        
    }

    public int getNextMessageOPCode() {

        try {

            if (netIn.hasNextShort()) {

                short opcode = 0;
                opcode = netIn.nextShort();

                return opcode;
            } else {
                return 0;
            }

        } catch (Exception e) {

            System.out.println("Internal Server Error.");

            return 0;

        }
        
    }

    
}
