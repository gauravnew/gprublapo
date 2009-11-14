/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    private String username;

    NetworkEngine() {
        ;
    }

    public void setLogin(String ip, String name) {
        ServerIP = ip;
        username = name;
    }

    public NetworkStreamWriter getNetworkOutput() {
        return out;
    }

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
    public void run() {

        if (!connectToServer()) return;

        try {

            out = new NetworkStreamWriter(new DataOutputStream(sckServer.getOutputStream()));
            in = new NetworkStreamParser(new DataInputStream(sckServer.getInputStream()));
        
            Main.coreLogic.setState(GAME_STATE.LOADING_STATE);

            Main.coreLogic.getLoadingScreen().incBy(20);

            out.sendLoginPacket(username);

            while(true) {

                int opcode;

                opcode = in.getNextMessageOPCode();

                if (opcode != 0) {
                    switch(opcode) {
                        
                        case 'P'*256 + 'G':

                            System.out.println("NETWORK::Ping reply recieved.");

                            break;

                        case 'M'*256 + 'I':

                            System.out.println("NETWORK::Downloading Map Image.");
                            in.getMapImage();
                            Main.coreLogic.getLoadingScreen().incBy(50);
                            break;

                        case 'M'*256 + 'D':

                            System.out.println("NETWORK::Downloading Map Data.");
                            in.getMapData();
                            Main.coreLogic.getLoadingScreen().incBy(30);
                            Main.coreLogic.setState(GAME_STATE.INGAME_STATE);
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
                System.out.println("Network Error");
                return;
            }

    }

}
