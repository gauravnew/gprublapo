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

/**
 *
 * @author Gaurav
 */
public class GlobalGameLogic implements Runnable {

    GlobalGameDatabase cDBEngine;
    NPCEngine cNPCEngine;
    GameMap cMapEngine;
    AIEngine cAIEngine;
    
    public void run() {

    }
    
}
