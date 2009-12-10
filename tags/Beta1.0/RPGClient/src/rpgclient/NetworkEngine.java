/*
 * Filename : NetworkEngine.java
 * Description : Controls reading and writing data to the network interface.
 */

 /**
    *   TODO:  Add case to process player health update packets
    *
    */
 
package rpgclient;
import java.net.*;
import java.io.*;

/**
 *
 * @author Gaurav
 */
public class NetworkEngine implements Runnable {

    private NetworkStreamWriter out;
    private NetworkStreamParser in;
    private String ServerIP;
    private Socket sckServer;

    public void setServerIP(String ip) {
        ServerIP = ip;
    }

    public NetworkStreamWriter getNetworkOutput() {
        return out;
    }

	//Function to establish connection
    private boolean connectToServer() {

        try {

            sckServer = new Socket(ServerIP,1234);

        } catch (Exception e) {

            return false;
        
        }
        
        if (sckServer == null) return false;

        return true;
        
    }

    @Override
    public void finalize() {

    }

    @Override
    //Runnable thread
	public void run() {

        if (!connectToServer()) return;

        try {
			//Estabilsh 2 way network communication
            out = new NetworkStreamWriter(new DataOutputStream(sckServer.getOutputStream()));
            in = new NetworkStreamParser(new DataInputStream(sckServer.getInputStream()));
        
            Main.coreLogic.setState(GAME_STATE.LOADING_STATE);

            Main.coreLogic.getLoadingScreen().incBy(20);
			//[C.N.002]
            out.sendLoginPacket(Main.getGameLogic().getMainActorName());

            while(true) {
            	int id;
            	Point2D target;
            	movePkt pk;
                int opcode;

				//read next message and process by opcode
                opcode = in.getNextMessageOPCode();

                if (opcode != 0) {
					//[C.N.011]
					switch(opcode) {
                        //case ping
                        case 'P'*256 + 'G':

                            System.out.println("NETWORK::Ping reply recieved.");

                            break;
						//case Map Image
                        case 'M'*256 + 'I':

                            System.out.println("NETWORK::Downloading Map Image.");
                            in.getMapImage();		//[C.N.013]
                            Main.coreLogic.getLoadingScreen().incBy(50);
                            break;
						//case Map Data
                        case 'M'*256 + 'D':

                            System.out.println("NETWORK::Downloading Map Data.");
                            in.getMapData();		//[C.N.012]
                            Main.coreLogic.getLoadingScreen().incBy(30);
                            Main.coreLogic.setState(GAME_STATE.INGAME_STATE);
                            break;
						//Case Move Actor
                        case 'M'*256 + 'V':
                            pk = in.getActorMove();
                            id = pk.id.intValue();
                            target = pk.pos;
                            System.out.println("NETWORK::Move Packet. " + id + " " + target.getX() + " " + target.getY());
                            try{Main.getGameLogic().getActorEngine().setActorMoveTo(id, target);}
                            catch (Exception e){System.out.println(e);}
                            break;
                        //Case New Actor Data
                        case 'N'*256 + 'A':
                        	Main.getGameLogic().getActorEngine().addActor(in.getNewActorData());
                            break;
                        case 'M'*256 + 'G':
                        	Main.getGameLogic().setMessage(in.getMessage());
                            break;
                        case 'L'*256 + 'C':
                        	Main.getGameLogic().setLastClass(in.getLastClass());
                        	break;
                        case 'G'*256 + 'O':
                        	Main.getGameLogic().state=GAME_STATE.LOGIN_STATE;
                        	Main.getGameLogic().setWinner(in.getGameOver());
                        	break;
                        case 'T'*256 + 'L':
                            pk = in.getActorMove();
                            id = pk.id.intValue();
                            target = pk.pos;
                            System.out.println("NETWORK::Move Packet. " + id + " " + target.getX() + " " + target.getY());
                            try{Main.getGameLogic().getActorEngine().setActorPosition(id, target);}
                            catch (Exception e){System.out.println(e);}
                            break;
                        default:

                            //Unknown packet, disconnect and return.
                            sckServer.close();
                            System.out.println("Server connection closed.");
                            Main.coreLogic.setState(GAME_STATE.LOGIN_STATE);
                            return;
                    }
                }
            }

            } catch (Exception e) {
                System.out.println("Network Error " + e);
                return;
            }

    }

}