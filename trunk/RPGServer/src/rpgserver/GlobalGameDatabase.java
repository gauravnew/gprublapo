/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Gaurav
 */
public class GlobalGameDatabase {
    private Hashtable DB;
    Semaphore semaphore;
    Integer nextActorID;

    public GlobalGameDatabase() {
        DB = new Hashtable();
        semaphore = new Semaphore(1, true);
        nextActorID = 0xFFFF;
    }

    public int createNewPlayerCharacter(NetworkStreamWriter out) throws InterruptedException {

        //Enter critical section.
        semaphore.acquire();

        PlayerCharacter p = new PlayerCharacter();
        p.actorID = nextActorID++;
        p.out = out;
        DB.put(p.actorID,p);

        //Leave critical section.
        semaphore.release();

        return p.actorID.intValue();

    }

    //TODO: Implement the following:
    //--------------------------------
    //int createNewNonPlayerCharacter()
    //bool deleteActor(int ActorID)
    //Point2D getActorPosition(int ActorID)
    //float getActorSpeed(int ActorID)
    //int getActorStatusFlags(int ActorID)
    //int getActorType(int ActorID)
    //void setActorPosition(int ActorID, Point2D)
    //void setActorSpeed(int ActorID, float speed)
    //void setActorStatusFlag(int ActorID, int flag, bool state)
    //void setActorType(int ActorID, int type)
    //int [] getAllPlayerCharacters()

}
