/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
  *  TODO
  *	Add GAME_STATE enum with types for login, countdown, ingame, gameover
  *	Add int countdown, GAME_STATE state
  *	Add function setState(GAME_STATE state)
  *	Add function setCounter(int t)
  *	Add function checkCountdown()
  *	Add function checkState()
  *	Add function getGameDB()
  *	Add function getGameMap()
  *	Add function getNPCEngine()
  *	Add function getAIEngine()
  *	Remove function run and do not implement Runnable
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
