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

        short opcode = 0;
        //Get opcode.
        opcode = netIn.readShort();

        return opcode;

        } catch (IOException e) {
            
            return -1;

        }
        
    }
	
	//Get data from network stream by data type
	
    public synchronized String getNamefromLogin() {
        byte [] b = new byte[24];
        try {
            netIn.read(b);
        } catch (IOException e) {
            return "Exception";
        }
        
        return new String(b);
    }

    public synchronized Point2D getActorMove() {

        try {

            float x = netIn.readFloat();
            float y = netIn.readFloat();
            return new Point2D (x,y);

        } catch (Exception e) {
            return null;
        }

    }

    
}
