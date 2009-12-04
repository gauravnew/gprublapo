package rpgclient;

/*
 * Filename : NetworkStreamParser.java
 * Description : This class parses packets recieved
 * from the server.
 */

 /**
  *  TODO:
  *	Add function getHealth()
  *	Add function getGameOverState()
  *	Add function readActorInfo(Integer id)
  */ 
 

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
	//[C.N.013]
    public synchronized void getMapImage() {
        try {

            int len = 0;

            len = netIn.readInt();

            byte [] file = new byte [len];

            netIn.readFully(file);

            DataOutputStream x = (new DataOutputStream(new FileOutputStream(new File("data/map.png"))));
            x.write(file);
            x.close();

        } catch (IOException e) {

            ;

        }
    }
	//[C.N.012]
    public synchronized void getMapData() {

        try {

            int len = 0;

            len = netIn.readInt();

            byte [] file = new byte [len];

            netIn.readFully(file);

            DataOutputStream x = (new DataOutputStream(new FileOutputStream(new File("data/map_dat.png"))));
            x.write(file);
            x.close();

        } catch (IOException e) {

            ;

        }
    }

    public synchronized movePkt getActorMove() {

        try {

            Integer id = new Integer(netIn.readInt());
            float x = netIn.readFloat();
            float y = netIn.readFloat();
            return new movePkt(id, new Point2D (x,y));
        
        } catch (Exception e) {
            System.out.println("GetActorMove " + e);
        	return null;
        }

    }


}

