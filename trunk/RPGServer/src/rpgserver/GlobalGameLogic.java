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
    NPCEngine cNPCEngine = new NPCEngine();
    GameMap cMapEngine = new GameMap();
    int countdown;
    GAME_STATE state;
    String winner;
            
    
    public void run() {
    	state = GAME_STATE.LOGIN;
        Integer tempID;
        PlayerCharacter tempChar;
        String msg;
        cNPCEngine.loadNPCsFromFile("data/npcs.txt", cDBEngine);
        cNPCEngine.generateRandomCharacters(cDBEngine);

        while (state!=GAME_STATE.INGAME);
        
        forever:	//so that break statement will leave this loop
        while (true) {
            for(Actor actor : cDBEngine.getHashtableKeys()) {
            	if (actor.type == 1 | actor.type == 2) {
            		if (actor.moveto.equals(actor.position)) {
            			cNPCEngine.generateNewPosition(actor.actorID);
            		}
            		
        			if(actor.updatePosition() && actor.type==1){
            			for(Actor otherID : cDBEngine.getHashtableKeys()){
	    					if (otherID.type == 0) {
	        					PlayerCharacter other = (PlayerCharacter)otherID;
	       						if(actor.position.getMinDistance(other.position) < 26)
	       							try{other.out.sendActorMove(actor.actorID, actor.position);}
	       							catch(Exception e) {;}
	    					}
            			}
        			}

            	} 
            	if(actor.type==0){
            		tempChar=(PlayerCharacter)actor;
            		if (!(tempChar.moveto.equals(tempChar.position))) {
            			tempChar.updatePosition();
            			if((tempID=tempChar.checkCollision()) != null) {
            				msg = tempChar.processCollision(tempID);
            				tempChar.out.sendMessage(msg);
            				if (msg.equals(new String("You Win"))) break forever;
            				if (msg.equals(new String("Teleport"))) tempChar.out.sendTeleport(0, tempChar.position);
            				
            				if (cDBEngine.getActorType(tempID) > 16 && cDBEngine.getActorType(tempID) < 26)
            					tempChar.out.sendLastClass(msg.substring(9));
            			}
           				for(Actor otherID : cDBEngine.getHashtableKeys()){
           					if (otherID.type == 0) {
            					PlayerCharacter other = (PlayerCharacter)otherID;
            					if (!(tempChar.equals(other))) {
            						if(tempChar.position.getMinDistance(other.position) < 26)
            							other.out.sendActorMove(tempChar.actorID, tempChar.position);
            					}
            					else tempChar.out.sendActorMove(0, tempChar.position);

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
