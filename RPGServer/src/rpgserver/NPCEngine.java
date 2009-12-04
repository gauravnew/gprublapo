/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
*/
import java.io.*;

import rpgserver.NonPlayerCharacter.RELATIVE_DIRECTION;

/**
 * TODO
 *	implement generateNewPosition(Integer id) which both generates and sets
 *		a new moveto position for the given id based on direction.
 * @author Jacob
 */
public class NPCEngine {
    
	public NPCEngine() {};
    
    //Reads each line individually from a file listing NPCs of the format
    //type:x:y:Name, where type, x, y are all ints, 3 digits, and name is optional.
    //(completed by Jacob Hampton, 11/17, at 12:15 AM)
	//(modified by Lincoln Waller 12/4
    public void loadNPCsFromFile(String filename, GlobalGameDatabase db) {
        
        int type = 0000;
        int x = 0;
        int y = 0;
        String name = "";
        
        try {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = in.readLine()) != null) {
            type = Integer.parseInt(str.substring(0, 3));
            x = Integer.parseInt(str.substring(4, 7));
            y = Integer.parseInt(str.substring(8, 11));
            Point2D position = new Point2D((float)x, (float)y);
            int actorID = db.createNewNonPlayerCharacter(type, new Point2D(x,y));
            db.setActorPosition(actorID, position);
            if(str.length() > 11) {
            	name = str.substring(12);
            	db.setActorName(actorID, name);
            }
            System.out.println("loaded character: " + type + " at " + x + " " + y);
        }
        in.close();
        } catch (IOException e) {
        	System.out.println("NPC Load error: " + e);
        }
        
    }
    
    public void generateRandomCharacters(GlobalGameDatabase db) {
    	//10 manhole, 10 prof, 5 H1N1
    	int dir = 1;
    	for(int i=0;i<10;i++) 
    		{db.createNewNonPlayerCharacter(1, dir); dir*=-1;}
    	for(int i=0;i<5;i++) 
    		{db.createNewNonPlayerCharacter(2, dir); dir*=-1;}
    	for(int i=0;i<10;i++) 
    		{db.createNewNonPlayerCharacter(3, dir); dir*=-1;}
    	
    }
    
    public void generateNewPosition(Integer id) {
    	GlobalGameDatabase db = Main.cGameLogic.getGameDB();
    	GameMap map = Main.cGameLogic.getGameMap();
    	NonPlayerCharacter moveMe = db.getNonPlayer(id);
    	Point2D newTarget;
    	if(moveMe.nextDirection==RELATIVE_DIRECTION.HORIZONTAL)
			newTarget = map.getRandomHorizontal(moveMe.position);
    	else
			newTarget = map.getRandomVertical(moveMe.position);
    	moveMe.nextDirection.data*=-1;
    	db.setActorMoveTo(id, newTarget);
    }
    
}