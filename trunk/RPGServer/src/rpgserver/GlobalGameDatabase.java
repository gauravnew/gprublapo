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

    //Hashtable to store Actor data.
    private Hashtable DB;
    //Next available actorID.
    Integer nextActorID;

    public GlobalGameDatabase() {
        DB = new Hashtable();
        nextActorID = 0xFFFF;
    }

    //TODO: Complete!
    //This function creates a 'PlayerCharacter' object.
    //Sets its actorID to the next available actorID.
    //Sets the client of the 'PlayerCharacter'
    //Puts the 'PlayerCharacter' in the HashTable 'DB'
    //Returns the actorID of the new actor.
    public synchronized int createNewPlayerCharacter(ClientHandler c) {

        PlayerCharacter p = new PlayerCharacter();
        p.actorID = nextActorID++;
        p.client = c;
        DB.put(p.actorID,p);

        return p.actorID.intValue();

    }

    //TODO:
    //This function creates a 'NonPlayerCharacter' object.
    //Sets its actorID to the next available actorID.
    //Puts the 'NonPlayerCharacter' in the HashTable 'DB'
    //Returns the actorID of the new actor.
    public synchronized int createNewNonPlayerCharacter(int type) {

        return 0;
        
    }

    //TODO:
    //This function removes an actor with the
    //given actorID from the HashTable 'DB'
    //return true always.
    public synchronized boolean deleteActor(Integer ActorID) {

        return false;
        
    }

    //TODO:
    //This function returns the position of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized Point2D getActorPosition(Integer ActorID) {
        return null;
    }

    //TODO:
    //This function returns the moveto of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized Point2D getActorMoveTo(Integer ActorID) {
        return null;
    }

    //TODO:
    //This function returns the speed of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized float getActorSpeed(Integer ActorID) {
        return 0;
    }

    //TODO:
    //This function returns a status flag of the actor
    //in the HashTable 'DB' which has the given ActorID.
    //basically, it returns the 'status' variable of the
    //actor &(bitwise and) with the argument flag.
    public synchronized int getActorStatusFlags(Integer ActorID, int flag) {
        return 0;
    }

    //TODO:
    //This function returns the type of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized float getActorType(Integer ActorID) {
        return 0;
    }

    //TODO:
    //This function sets the position of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorPosition(Integer ActorID, Point2D p) {
        return;
    }

    //TODO:
    //This function sets the moveto of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorMoveTo(Integer ActorID, Point2D p) {
        return;
    }

    //TODO:
    //This function sets the speed of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorSpeed(Integer ActorID, float s) {
        return;
    }

    //TODO:
    //This function sets a status flag of the actor
    //in the HashTable 'DB' which has the given ActorID.
    //if state is false then set the actor's 'status' to
    //'status' &(bitwise and) with ~(bitwise not)flag.
    //status = status & ~flag
    //If the state is true then set the actor's 'status' to
    //status = status | flag; (bitwise or)
    public synchronized void setActorStatusFlag(Integer ActorID, int flag, boolean state) {
        return;
    }

    //TODO:
    //This function sets the type of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorType(Integer ActorID, int type) {
        return;
    }

    //TODO:
    //This function returns an array of the actorID's of all the
    //actors in the HashTable
    //This is basically an array of all the 'keys' in the hashtable.
    public synchronized int [] getAllPlayerCharacters() {
        return null;
    }

    //TODO:
    //sets the name of the actor with the
    //given actorID.
    public synchronized void setActorName(Integer ActorID, String name) {

    }
    
}
