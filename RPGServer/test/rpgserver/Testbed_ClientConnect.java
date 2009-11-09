package rpgserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * This testbed is for the:
 * 	(Server) Ability to receive requests to join the game from clients
 * 	(Client) Ability to join a game server
 * 
 * Assumption
 * 	The server is live and running
 * 	port number is 1234
 * 
 * 
 */
public class Testbed_ClientConnect {
	
	private static final String HOST = "localhost";
	
	public static void main(String args[]){
		serverConnect();
	}

	private static void serverConnect() {
		
		int port = 1234; 
		//should the client be able to get the port number for the server it wishes to connect to?
		
		
		Socket socket = null;
		
		try{
			System.out.println("Connecting to server...");
			socket = new Socket(HOST,port);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Connected!");
		
	}
		
}
