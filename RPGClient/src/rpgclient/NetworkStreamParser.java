package rpgclient;

/*
 * Filename : NetworkStreamParser.java
 * Description : This class parses packets recieved
 * from the server.
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

            byte[] file = new byte[len];

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

            byte[] file = new byte[len];

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
            return new movePkt(id, new Point2D(x, y));

        } catch (Exception e) {
            System.out.println("GetActorMove " + e);
            return null;
        }

    }

    public synchronized String getMessage() {
        try {
            return netIn.readUTF();
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized movePkt getHealth() {

        try {
            int id = netIn.readInt();
            int health = netIn.readInt();
            return new movePkt(id, health);

        } catch (IOException e) {

            return null;

        }

    }

    public synchronized movePkt getCredits() {

        try {
            int id = netIn.readInt();
            int credits = netIn.readInt();
            return new movePkt(id, credits);

        } catch (IOException e) {

            return null;

        }

    }

    public synchronized movePkt getSpeed() {

        try {

            int id = netIn.readInt();
            float speed = netIn.readFloat();
            return new movePkt(id, speed);

        } catch (IOException e) {

            return null;

        }

    }

    public synchronized movePkt getActorInfo(){
    	try{
    		return new movePkt(netIn.readInt(), new Point2D(netIn.readFloat(), netIn.readFloat()), netIn.readInt(), netIn.readInt(), netIn.readFloat());
    	}
    	catch (IOException e) {
    		return null;
    	}
    }
    
    public synchronized String getLastClass() {
        try {
            return netIn.readUTF();
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized String getGameOver() {
        try {
            return netIn.readUTF();
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized movePkt getNewActorData() {
        float x, y;
        String name;
        Actor newCharacter = null;
        try {
            int actorID = netIn.readInt();
            int type = netIn.readInt();
            x = netIn.readFloat();
            y = netIn.readFloat();
            Point2D position = new Point2D(x, y);
            Point2D moveto = new Point2D(x, y);
            float speed = netIn.readFloat();
            name = netIn.readUTF();
            // System.out.println(name);
            Main.getGameLogic().getActorEngine().addActor(new Actor(actorID, type, name));
            Main.getGameLogic().getActorEngine().setActorPosition(actorID, position);
            return new movePkt(actorID, position, 100, 0, speed);
        } catch (Exception e) {
            System.out.println("Network Error getNewActorData");
        }



        return null;
    }

    public synchronized int getCountDown() {
        try {

            return netIn.readInt();

        } catch (IOException e) {

            return 0;

        }
    }
}

