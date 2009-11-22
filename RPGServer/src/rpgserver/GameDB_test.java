package rpgserver;

import java.util.*;


public class GameDB_test {
	
	//this class is a testbed for testing the GlobalGame Database
	public static void main(String[] args){
		
		//create a new game database
		GlobalGameDatabase GameDB = new GlobalGameDatabase();
		
		//creating a new non character player
		GameDB.createNewNonPlayerCharacter(1); //first H1N1
		GameDB.createNewNonPlayerCharacter(1); //second H1N1
		//test map position
		float testX = (float) 0.5;
		float testY = (float) 0.5;
		Point2D pos = new Point2D(testX, testY);
		GameDB.createNewNonPlayerCharacter(5, pos); //Einstein's Bagels
		
		//getting all the keys for the hashtable
		Integer[] k = GameDB.getAllPlayerCharacters();
		System.out.println(k[0]+ " " +k[1] + " " + k[2]);
		
		//setting new position and getting current position
		Integer actorID = k[1];
		Point2D newPos = new Point2D(2,3);
		GameDB.setActorPosition(actorID, newPos);
		
		Point2D curPos = GameDB.getActorPosition(actorID);
		float x = curPos.getX();
		float y = curPos.getY();
		
		System.out.println("New Position of actor with ID " + actorID + " is " + x + ", " + y);
		
		//deleting actor 2
		boolean deleted = GameDB.deleteActor(actorID);
		System.out.println("Deleted? " + deleted);	
		
	}
	
}
