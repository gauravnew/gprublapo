/*
 * Filename : NetworkStreamWriter.java
 * Description : This class is to send messages
 * from the server to the client. Each possible message
 * has its own function call.
 */

package rpgserver;

/**
   *  TODO:  
   *	Add functions sendHealth(int health)
   *	Add function sendEndGame(String winner)
   *	Add function sendNewActorData(Integer id)
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
            netOut.writeShort('P'*256+'G');

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

            int flen = (int)f.length();
            byte [] file = new byte[flen];
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

            int flen = (int)f.length();
            byte [] file = new byte[flen];
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

            netOut.writeShort('M'*256 + 'V');
            netOut.writeInt(actorID);
            netOut.writeFloat(target.getX());
            netOut.writeFloat(target.getY());

            netOut.flush();

        } catch(Exception e) {

            System.out.println("Server Error.");

        }
    }
    
    public synchronized void sendMessage(String msg){
    	//TODO Implement function to write msg with message opcode
    }
    
    public synchronized void sendLastClass(String msg){
    	//TODO Implement function to write msg with lastclass opcode
    }

    public synchronized void sendGameOver(String winner) {
    	//TODO Implement function to write winner with game over opcode
    }
    
    public synchronized void sendNewActorData(int id, int type, String name, Point2D position){
    	float x = position.getX();
    	float y = position.getY();
    	//TODO implement remainder of function to write actor info with proper opcode
    }
}
