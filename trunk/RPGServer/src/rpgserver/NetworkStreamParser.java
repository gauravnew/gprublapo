/*
 * Filename : NetworkStreamParser.java
 * Description : This class parses packets recieved
 * from the client.
 */

package rpgserver;

import java.util.concurrent.*;
import java.util.*;

/**
 * NetworkStreamParser class
 * @author gm
 */
public class NetworkStreamParser {

    //Network Input stream from a client.
    private Scanner netIn;
    //Local Thread access manager.
    private Semaphore semaphore;

    //Constructor.
    public NetworkStreamParser(Scanner in) {

        semaphore = new Semaphore(1,true);
        netIn = in;
        
    }

    //Get next 2 byte packet OPCode from the network stream.
    public int getNextMessageOPCode() {

        try {

            //Enter critical section.
            semaphore.acquire();

            //Is opcode available.
            if (netIn.hasNextShort()) {

                short opcode = 0;
                //Get opcode.
                opcode = netIn.nextShort();

                //Leave critical section.
                semaphore.release();

                return opcode;
            } else {

                //Leave critical section.
                semaphore.release();
                return 0;

            }


        } catch (Exception e) {

            System.out.println("Internal Server Error.");
            //Leave critical section.
            semaphore.release();
            //Return.
            return 0;

        }
        
    }

    
}
