package rpgserver;

import java.util.*;


public class GameDB_test2 {
	
	//this class is a testbed for testing the GlobalGame Database
	public static void main(String[] args){
		
		//create a new game database
		GlobalGameDatabase GameDB = new GlobalGameDatabase();
		
		//creating a new non character player
		int id1 = GameDB.createNewNonPlayerCharacter(1); //first H1N1
		int id2 = GameDB.createNewNonPlayerCharacter(1); //second H1N1
		//test map position
		float testX = (float) 0.5;
		float testY = (float) 0.5;
		Point2D pos = new Point2D(testX, testY);
		int id3 = GameDB.createNewNonPlayerCharacter(5, pos); //Einstein's Bagels
		
		System.out.println("All ids: " + id1 +" "+ id2+ " "+ id3);
		//getting all the keys for the hashtable
		Integer[] k = GameDB.getAllPlayerCharacters();
		System.out.println("Size of the key table: "+ k.length);
		for(int i =0; i<k.length; i++)
			System.out.print(k[i].toString() + " ");
		System.out.println();
		
		
		//setting new position and getting current position
		Integer actorID = k[1];
		Point2D newPos = new Point2D(2,3);
		GameDB.setActorPosition(actorID, newPos);
		
		System.out.println("CHANGING POSITION FOR KEY " + actorID + " ... ");
		
		Point2D curPos = GameDB.getActorPosition(actorID);
		float x = curPos.getX();
		float y = curPos.getY();
		
		System.out.println("New Position of actor with ID " + actorID + " is " + x + ", " + y);

		System.out.println("DELETING KEY " + actorID + " ... ");

		System.out.println("Size of key set before delete " + k.length);
		//deleting actor 2
		boolean deleted = GameDB.deleteActor(actorID);
		System.out.println("Deleted? " + deleted);	
		
		k = GameDB.getAllPlayerCharacters();
		System.out.println("Size of key set after delete " + k.length);
		for(int i =0; i<k.length; i++)
			System.out.print(k[i].toString() + " ");
		System.out.println();
	}
	
}
