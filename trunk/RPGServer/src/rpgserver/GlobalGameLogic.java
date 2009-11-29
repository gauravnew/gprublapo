/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
  *  TODO
  *	Add GAME_STATE enum with types for login, countdown, ingame, gameover
  *	Add int countdown, GAME_STATE state, String winner
  *	Add function setState(GAME_STATE state)
  *	Add function setCounter(int t)
  *	Add function checkCountdown()
  *	Add function checkState()
  *	Add function getGameDB()
  *	Add function getGameMap()
  *	Add function getNPCEngine()
  *	Add function getAIEngine()
  * Add function getWinner
  * In function run(), only check for collisions (aka only run inner loop) when 'actor' 
  *		is a playerCharacter (playerCharacter has a function for this, then use it's 
  *		function to process them as well - update winner and gamestate if necessary).
  *		Do NOT simply update postion to MoveTo, but rather set position based on time 
  *		(use Actor's updatePosition()).  If NPC positon==moveto & it is movable type, 
  *		call NPCEngine's generateNewPosition(Integer ActorID)
  */
 
package rpgserver;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Gaurav
 */
public class GlobalGameLogic implements Runnable {

    GlobalGameDatabase cDBEngine = Main.cDBEngine;
    NPCEngine cNPCEngine;
    GameMap cMapEngine = new GameMap();
    AIEngine cAIEngine;
    
    public void run() {
        while (true) {
            for(Actor actor : cDBEngine.getHashtableKeys()) {
                if (!(actor).moveto.equals((actor).position)) {
                    (actor).position = new Point2D((actor).moveto);
                    for(Actor other : cDBEngine.getHashtableKeys()) {
                        if ((other).type == 0) {
                            ((PlayerCharacter)other).updatePosition(actor);
                        }
                    }
                }
            }
        }
    }
    
}
