/*
 * Filename : NetworkStreamWriter.java
 * Description : This class is to send messages
 * from the server to the client. Each possible message
 * has its own function call.
 */
package rpgserver;

/**
 *  TODO:
 *	Add function sendCredits(int credits)
 *	Add function sendHealth(int health)
 */
import java.io.*;
import java.util.concurrent.*;

/**
 *
 * @author gm
 */
public class NetworkStreamWriter {

    //Network Output stream for a client.
    private DataOutputStream netOut;

    //Constructor.
    public NetworkStreamWriter(DataOutputStream out) {

        netOut = out;

    }

    //Send ping reply packet to client.
    //[S->C]["PG"] (docs/Network Protocol.txt)
    public synchronized void sendPingReply() {

        try {


            //Write to buffer.
            netOut.writeShort('P' * 256 + 'G');

            //Flush buffer.
            netOut.flush();

        } catch (Exception e) {

            //Error
            System.out.println("NetworkStreamWriter : sendPingReply : Server Error.");

        }
    }

    //Send data based upon data type
    //[S.N.012]
    public synchronized void sendMapImage(File f) {
        try {

            int flen = (int) f.length();
            byte[] file = new byte[flen];
            (new DataInputStream(new FileInputStream(f))).readFully(file);
            netOut.writeShort('M' * 256 + 'I');
            netOut.writeInt(flen);
            netOut.write(file);
            netOut.flush();

        } catch (Exception e) {
            System.out.println("NetworkStreamWriter : sendMapImage : Server Error.");
        }

    }
    //[S.N.013]

    public synchronized void sendMapData(File f) {
        try {

            int flen = (int) f.length();
            byte[] file = new byte[flen];
            (new DataInputStream(new FileInputStream(f))).readFully(file);
            netOut.writeShort('M' * 256 + 'D');
            netOut.writeInt(flen);
            netOut.write(file);
            netOut.flush();

        } catch (Exception e) {
            System.out.println("NetworkStreamWriter : sendMapData : Server Error.");
        }

    }

    public synchronized void sendActorMove(int actorID, Point2D target) {
        try {

            netOut.writeShort('M' * 256 + 'V');
            netOut.writeInt(actorID);
            netOut.writeFloat(target.getX());
            netOut.writeFloat(target.getY());

            netOut.flush();

        } catch (Exception e) {

            System.out.println("Server Error.");

        }
    }

    public synchronized void sendMessage(String msg) {
        try {
            netOut.writeShort('M' * 256 + 'G');
            netOut.writeUTF(msg);
            netOut.flush();
        } catch (Exception e) {
            System.out.println("Server Error sendMEssage");
        }
    }

    public synchronized void sendLastClass(String msg) {
        try {
            netOut.writeShort('L' * 256 + 'C');
            netOut.writeUTF(msg);
            netOut.flush();
        } catch (Exception e) {
            System.out.println("Server Error sendLastClass");
        }
        //TODO Implement function to write msg with lastclass opcode
    }

    public synchronized void sendGameOver(String winner) {
        try {
            netOut.writeShort('G' * 256 + 'O');
            netOut.writeUTF(winner);
            netOut.flush();
        } catch (Exception e) {
            System.out.println("Server Error sendGameOver");
        }
        //TODO Implement function to write winner with game over opcode
    }

    public synchronized void sendTeleport(int actorID, Point2D target) {
        try {

            netOut.writeShort('T' * 256 + 'L');
            netOut.writeInt(actorID);
            netOut.writeFloat(target.getX());
            netOut.writeFloat(target.getY());

            netOut.flush();

        } catch (Exception e) {

            System.out.println("Server Error.");

        }
    }

    public synchronized void sendNewActorData(int id, int type, float speed, String name, Point2D position) {
        float x = position.getX();
        float y = position.getY();
        try {
            netOut.writeShort('N' * 256 + 'A');
            netOut.writeInt(id);
            netOut.writeInt(type);
            netOut.writeFloat(x);
            netOut.writeFloat(y);
            netOut.writeFloat(speed);
            netOut.writeUTF(name);
            netOut.flush();
        } catch (Exception e) {
            System.out.println("Server Error sendNewActorData " + e);
        }

    }
    
    public synchronized void sendActorInfo(int id, float speed, Point2D position, int health, int credits){
    	float x = position.getX();
    	float y = position.getY();
    	try{
    		netOut.writeShort('A' * 256 + 'I');
    		netOut.writeInt(id);
    		netOut.writeFloat(x);
    		netOut.writeFloat(y);
    		netOut.writeInt(health);
    		netOut.writeInt(credits);
    		netOut.writeFloat(speed);
    		netOut.flush();
    	}
    	catch(Exception e){System.out.println ("Server Error sendActorInfo");}
    }

    public synchronized void sendCountDown(int value) {

        try {

            //Write to buffer.
            netOut.writeShort('C' * 256 + 'D');
            netOut.writeInt(value);

            //Flush buffer.
            netOut.flush();

        } catch (Exception e) {

            //Error
            System.out.println("NetworkStreamWriter : sendCountDown : Server Error.");

        }
    }

    public synchronized void sendCredits(int ID, int value) {

        try {

            //Write to buffer.
            netOut.writeShort('C' * 256 + 'R');
            netOut.writeInt(ID);
            netOut.writeInt(value);

            //Flush buffer.
            netOut.flush();

        } catch (Exception e) {

            //Error
            System.out.println("NetworkStreamWriter : sendCredits : Server Error.");

        }
    }

    public synchronized void sendHealth(int ID, int value) {

        try {

            //Write to buffer.
            netOut.writeShort('H' * 256 + 'L');
            netOut.writeInt(ID);
            netOut.writeInt(value);

            //Flush buffer.
            netOut.flush();

        } catch (Exception e) {

            //Error
            System.out.println("NetworkStreamWriter : sendHealth : Server Error.");

        }
    }

    public synchronized void sendSpeed(int ID, float value) {

        try {

            //Write to buffer.
            netOut.writeShort('S' * 256 + 'P');
            netOut.writeInt(ID);
            netOut.writeFloat(value);

            //Flush buffer.
            netOut.flush();

        } catch (Exception e) {

            //Error
            System.out.println("NetworkStreamWriter : sendSpeed : Server Error.");

        }
    }
}
