package rpgclient;

/*
 * Filename : NetworkStreamParser.java
 * Description : This class parses packets recieved
 * from the server.
 */

 /**
  *  TODO:
  *	Add function int getHealth()
  * Add function int getCredits()
  * Add function float getSpeed()
  * Add function String getLastClass()
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

    public synchronized String getMessage(){
    	try{
    		return netIn.readUTF();
    	}
    	catch(Exception e){
    		return null;
    	}
    }

    public synchronized String getLastClass(){
    	try{
    		return netIn.readUTF();
    	}
    	catch(Exception e){
    		return null;
    	}    	
    }

    public synchronized String getGameOver(){
    	try{
    		return netIn.readUTF();
    	}
    	catch(Exception e){
    		return null;
    	}
    }
    
    public synchronized Actor getNewActorData() {
    	float x, y;
    	String name;
    	Actor newCharacter = null;
    	try{
    		int actorID = netIn.readInt();
    		int type = netIn.readInt();
    		x = netIn.readFloat();
    		y = netIn.readFloat();
    		Point2D position = new Point2D(x,y);
    		Point2D moveto = new Point2D(x,y);
    		float speed = netIn.readFloat();
    		name = netIn.readUTF();
    		System.out.println(name);
                newCharacter = new Actor(actorID,type,name);
                newCharacter.position = position;
                newCharacter.moveto = moveto;
                newCharacter.speed = speed;
    	}
    	catch (Exception e) { System.out.println("Network Error getNewActorData");}



    	return newCharacter;
    }

    public synchronized int getCountDown() {
        try {
            
            return netIn.readInt();

        } catch (IOException e) {

            return 0;

        }
    }

}

