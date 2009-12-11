/*
 * Filename : Main.java
 * Description : Program entrypoint for the Boston
 * University Game Server running on port 1234.
 */
/**
 * TODO:  
 *  Modifiy infinite loop (ln 41) to only loop for 20 seconds after the last client connects,
then initiate 10 second countdown, set game state to countdown, do not acept more clients
 * Wait for all other threads to terminate before exiting/restarting
 *
 */
package rpgserver;

import java.net.*;

import rpgserver.GlobalGameLogic.GAME_STATE;

import com.sun.jmx.snmp.tasks.ThreadService;

/**
 * Program Entry Point
 * @author gm
 */
public class Main {

    static GlobalGameDatabase cDBEngine;
    static GlobalGameLogic cGameLogic;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int port;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 1234;
        }
        cDBEngine = new GlobalGameDatabase();
        cGameLogic = new GlobalGameLogic();
        cGameLogic.setState(GAME_STATE.LOGIN);

        Thread logic = new Thread(cGameLogic);
        logic.start();

        try {

            //Open a network socket at port 1234 and wait for incoming connections.
            ServerSocket srvr = new ServerSocket(port);
            //System.out.println("Server started. Listening on port " + port);

            while (cGameLogic.state == cGameLogic.state.LOGIN) {
                //Accept incoming connections.
                srvr.setSoTimeout(20000);		//[S.N.031]
                Socket client = srvr.accept();  //[S.N.001]
                //System.out.println("Client connection accepted");
//                if (cGameLogic.state != cGameLogic.state.LOGIN) {
//                    continue;
//                }

                if (client != null) {
                    cGameLogic.setCounter(System.currentTimeMillis());	//[S.L.114]
                    ClientHandler cHandler = new ClientHandler(client, cDBEngine); //[S.N.002]
                    int id = cDBEngine.createNewPlayerCharacter(cHandler);
                    cHandler.setActorID(id);

                    (new Thread(cHandler)).start();
                }

            }

        } catch (Exception e) {
            //A socket error has occoured to print a message.
            //Unless it's a timeout! (Assue this is the case)
        }
        //******Start the game here*****
        try {
			logic.join();
		} catch (InterruptedException e) {
			;
		}

    }
}
