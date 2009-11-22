/*
 * Filename : Main.java
 * Description : Program entrypoint for the Boston
 * University Game Server running on port 1234.
 */

package rpgserver;

import java.net.*;


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

        cDBEngine = new GlobalGameDatabase();

        try {

            //Open a network socket at port 1234 and wait for incoming connections.
            ServerSocket srvr = new ServerSocket(1234);
            System.out.println("Server started. Listening on port 1234");

/**
   *TODO:  
   * 	Modifiy infinite loop to only loop for 30 seconds after the last client connects.
   *	Store the time remaining in  global variable that all clients will have access to.
   *	**ClientHandler should also be updated to push this countdown after the game maps are sent.
   *
   */
            while (true) {
                //Accept incoming connections.
                Socket client = srvr.accept();
                System.out.println("Client connection accepted");
                
                if (client != null) {
                    ClientHandler cHandler = new ClientHandler(client,cDBEngine);
                    int id = cDBEngine.createNewPlayerCharacter(cHandler);
                    cHandler.setActorID(id);
                    
                    (new Thread(cHandler)).start();
                }
                
            }

        } catch (Exception e) {
            
            //A socket error has occoured to print a message.
            System.out.println("Server error.");

        }

    }

}
