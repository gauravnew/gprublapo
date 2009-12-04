/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
  *  TODO
  * Add GAME_STATE enum with types for login, countdown, ingame, gameover
  * Add int countdown, GAME_STATE state, String winner
  * Add function setState(GAME_STATE state)
  * Add function setCounter(int t)
  * Add function checkCountdown()
  * Add function checkState()
  * Add function getGameDB()
  * Add function getGameMap()
  * Add function getNPCEngine()
  * Add function getAIEngine()
  * Add function getWinner
  * In function run(), only check for collisions (aka only run inner loop) when 'actor' 
  *  is a playerCharacter (playerCharacter has a function for this, then use it's 
  *  function to process them as well - update winner and gamestate if necessary).
  *  Do NOT simply update postion to MoveTo, but rather set position based on time 
  *  (use Actor's updatePosition()).  If NPC positon==moveto & it is movable type, 
  *  call NPCEngine's generateNewPosition(Integer ActorID)
  */
 
package rpgserver;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Gaurav
 */
public class GlobalGameLogic implements Runnable {

    public enum GAME_STATE { LOGIN, COUNTDOWN, INGAME, GAMEOVER }
    
    GlobalGameDatabase cDBEngine = Main.cDBEngine;
    NPCEngine cNPCEngine;
    GameMap cMapEngine = new GameMap();
    AIEngine cAIEngine;
    int countdown;
    GAME_STATE state;
    String winner;
            
    
    public void run() {
        Integer tempID;
        PlayerCharacter tempChar;
        String msg;
 forever:	//so that break statement will leave this loop
        while (true) {
            for(Actor actor : cDBEngine.getHashtableKeys()) {
            	if (actor.type < 4 && actor.type > 0) {
            		if (actor.moveto.equals(actor.position)) cNPCEngine.generateNewPosition(actor.actorID);
            		actor.updatePosition();
            	}
            	if(actor.type==0){
            		tempChar=(PlayerCharacter)actor;
            		if (!(tempChar.moveto.equals(tempChar.position))) {
            			tempChar.updatePosition();
            			tempChar.out.sendActorMove(0, tempChar.position);
            			
            			if((tempID=tempChar.checkCollision()) != null) {
            				msg = tempChar.processCollision(tempID);
            				
            				tempChar.out.sendMessage(msg);
            				if (msg.equals(new String("You Win"))) break forever;
            				
            				if (cDBEngine.getActorType(tempID) > 16 && cDBEngine.getActorType(tempID) < 26)
            					tempChar.out.sendLastClass(msg.substring(9));
            				
            				for(Actor otherID : cDBEngine.getHashtableKeys()){
            					if (otherID.type == 0) {
	            					PlayerCharacter other = (PlayerCharacter)otherID;
	            					if (!(tempChar.equals(other))) 
	            						if(tempChar.position.getMinDistance(other.position) < 26)
	            							other.out.sendActorMove(tempChar.actorID, tempChar.position);
            					}
            				}
            			}
            		}
            	}
            }
        }
        
        for(Actor actor : cDBEngine.getHashtableKeys()) {
        	if(actor.type==0){
        		tempChar = (PlayerCharacter)actor;
        		tempChar.out.sendGameOver(winner);
        		try {
					tempChar.client.sckClient.close();
				} catch (IOException e) {}
        	}
        }
        return;
    }
    
/*
 *                if (!(actor).moveto.equals((actor).position)) {
                    (actor).position = new Point2D((actor).moveto);
                    for(Actor other : cDBEngine.getHashtableKeys()) {
                        if ((other).type == 0) {
                            ((PlayerCharacter)other).updatePosition(actor);
                        }
                    }
                } 
 *
 */
    
    
    public void setState(GAME_STATE s){state = s;}
    
    public void setCounter(int t){countdown = t;}
    
    public int checkCountdown(){return countdown;}
    
    public GAME_STATE checkState(){return state;}

    public boolean checkState(GAME_STATE s){return (state==s);}
    
    public GlobalGameDatabase getGameDB(){return cDBEngine;}
    
    public GameMap getGameMap(){return cMapEngine;}
    
    public NPCEngine getNPCEngine(){return cNPCEngine;}
    
    public String getWinner(){return winner;}
    
}
