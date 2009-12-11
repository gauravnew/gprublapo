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
import java.lang.System;

/**
 *
 * @author Gaurav
 */
public class GlobalGameLogic implements Runnable {

    public enum GAME_STATE {

        LOGIN, COUNTDOWN, INGAME, GAMEOVER
    }
    GlobalGameDatabase cDBEngine = Main.cDBEngine;
    NPCEngine cNPCEngine = new NPCEngine();
    GameMap cMapEngine = new GameMap();
    long countdown;
    GAME_STATE state;
    String winner;

    public void run() {
        state = GAME_STATE.COUNTDOWN;
        Integer tempID;
        PlayerCharacter tempChar;
        String msg;

        cNPCEngine.loadNPCsFromFile("data/npcs.txt", cDBEngine);  //[S.D.011]
        cNPCEngine.generateRandomCharacters(cDBEngine);				//[S.L.111]

        setCounter(System.currentTimeMillis());		//[S.L.112]

        while (System.currentTimeMillis() < checkCountdown() + 30000) {	//[S.L.113]
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
        state = GAME_STATE.INGAME; //[S.L.031]


        // *** Completed by Jacob Hampton, 3:45 P.M. 12/9/09 ***
/*
        long startTime = java.lang.System.currentTimeMillis();
        
        for(Actor actor : cDBEngine.getHashtableKeys()) { 
        if(actor.type==0){ //if it's a player character
        tempChar=(PlayerCharacter)actor; //cast it as a player character, gain access to more functions
        tempChar.out.sendCountdown(); // This will trigger a compile error until the method "sendCountdown()" exists in the NetworkStreamWriter class.
        }
        }
        
        for(Actor actor : cDBEngine.getHashtableKeys()) { 
        if(actor.type==0){ //if it's a player character
        tempChar=(PlayerCharacter)actor; //cast it as a player character, gain access to more functions
        tempChar.client.sendAllCharacter();
        }
        }
        
        while (true){
        if (java.lang.System.currentTimeMillis() >= (startTime + 10000)){ // Make sure at least 10 seconds have passed
        state = GAME_STATE.INGAME;
        break;
        }
        }
         */
        // ******************************************************

        forever: //so that break statement will leave this loop
        while (true) {	//[S.L.021]
            for (Actor actor : cDBEngine.getHashtableKeys()) {
                if (actor.type == 1 | actor.type == 2) {
                    if (actor.moveto.equals(actor.position)) {
                        cNPCEngine.generateNewPosition(actor.actorID); //[S.L.001]
                    }

                    if (actor.updatePosition() && actor.type == 1) {
                        for (Actor otherID : cDBEngine.getHashtableKeys()) {
                            if (otherID.type == 0) {
                                PlayerCharacter other = (PlayerCharacter) otherID;
                                if (actor.position.getMinDistance(other.position) < 26) {
                                    try {
                                        other.out.sendActorMove(actor.actorID, actor.position); //[S.N.022]
                                    } catch (Exception e) {
                                        ;
                                    }
                                }
                            }
                        }
                    }

                }
                if (actor.type == 0) { //if it's a player character
                    tempChar = (PlayerCharacter) actor; //cast it as a player character, gain access to more functions
                    if (!(tempChar.moveto.equals(tempChar.position))) {
                        tempChar.updatePosition();	//[S.L.062]
                        if ((tempID = tempChar.checkCollision()) != null) {
                            msg = tempChar.processCollision(tempID);
                            tempChar.out.sendMessage(msg);
                            if (msg.equals(new String("You Win"))) {
                                break forever;	//[S.L.041]
                            }
                            if (msg.equals(new String("Teleport"))) {
                                tempChar.out.sendTeleport(0, tempChar.position);
                            }

                            if (cDBEngine.getActorType(tempID) > 16 && cDBEngine.getActorType(tempID) < 26) {
                                tempChar.out.sendLastClass(msg.substring(9));
                            }
                        }
                        for (Actor otherID : cDBEngine.getHashtableKeys()) {
                            if (otherID.type == 0) {
                                PlayerCharacter other = (PlayerCharacter) otherID;
                                if (!(tempChar.equals(other))) {
                                    if (tempChar.position.getMinDistance(other.position) < 26) {
/*                                        other.out.sendActorMove(tempChar.actorID, tempChar.position);  //[S.N.023]
                                        other.out.sendHealth(tempChar.actorID, tempChar.health);  //[S.N.023]
                                        other.out.sendCredits(tempChar.actorID, tempChar.credits);  //[S.N.023]
                                        other.out.sendSpeed(tempChar.actorID, tempChar.speed);  //[S.N.023]
*/										other.out.sendActorInfo(tempChar.actorID, tempChar.speed, tempChar.position, tempChar.health, tempChar.credits);
                                    }
                                } else {
/*                                    tempChar.out.sendActorMove(0, tempChar.position);
                                    // *** Completed by Jacob Hampton, 10:05 P.M. 12/9/09 ***
                                    tempChar.out.sendHealth(0, tempChar.health);
                                    tempChar.out.sendCredits(0, tempChar.credits);
                                    tempChar.out.sendSpeed(0, tempChar.speed);
*/                                    // ******************************************************
									other.out.sendActorInfo(0, tempChar.speed, tempChar.position, tempChar.health, tempChar.credits);
                                }

                            }
                        }
                    }
                }
            }
        }

        for (Actor actor : cDBEngine.getHashtableKeys()) {
            if (actor.type == 0) {
                tempChar = (PlayerCharacter) actor;
                tempChar.out.sendGameOver(winner);
                try {
                    tempChar.client.sckClient.close();	//[S.L.042]
                } catch (IOException e) {
                }
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
    public void setState(GAME_STATE s) {
        state = s;
    }

    public synchronized void setCounter(long t) {
        countdown = t;
    }

    public synchronized long checkCountdown() {
        return countdown;
    }

    public GAME_STATE checkState() {
        return state;
    }

    public boolean checkState(GAME_STATE s) {
        return (state == s);
    }

    public GlobalGameDatabase getGameDB() {
        return cDBEngine;
    }

    public GameMap getGameMap() {
        return cMapEngine;
    }

    public NPCEngine getNPCEngine() {
        return cNPCEngine;
    }

    public String getWinner() {
        return winner;
    }
}
