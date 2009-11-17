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

    //TODO: Complete? (by Anastasia Vashkevich -- 11/16/09 5PM)
    //This function creates a 'NonPlayerCharacter' object.
    //Sets its actorID to the next available actorID.
    //Puts the 'NonPlayerCharacter' in the HashTable 'DB'
    //Returns the actorID of the new actor.
    public synchronized int createNewNonPlayerCharacter(int type) {

        NonPlayerCharacter np = new NonPlayerCharacter();
        np.actorID = nextActorID++;
        DB.put(np.actorID, np);

        return np.actorID.intValue();
        
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 5PM)
    //This function removes an actor with the
    //given actorID from the HashTable 'DB'
    //return true always.
    public synchronized boolean deleteActor(Integer ActorID) {
    	
    	//if the actor exists in the hashtable
    	if(DB.contains(ActorID)){
    		DB.remove(ActorID); //remove the key and the correspoding value(actor) from the hashtable
    		return true;
    	}
    	
        return false;
        
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the position of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized Point2D getActorPosition(Integer ActorID) {
        
    	Actor a = (Actor) DB.get(ActorID);
    	return a.position; //return the actor's position
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the moveto of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized Point2D getActorMoveTo(Integer ActorID) {
    	
    	Actor a = (Actor) DB.get(ActorID); //get the actor with the ActorID  from the hashtable

    	return a.moveto;
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the speed of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized float getActorSpeed(Integer ActorID) {
    	
    	Actor a = (Actor) DB.get(ActorID); //get the actor with the ActorID  from the hashtable

    	return a.speed;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function returns a status flag of the actor
    //in the HashTable 'DB' which has the given ActorID.
    //basically, it returns the 'status' variable of the
    //actor &(bitwise and) with the argument flag.
    public synchronized int getActorStatusFlags(Integer ActorID, int flag) {
    	Actor a = (Actor) DB.get(ActorID);
        int stat = a.status;
    	return (stat & flag);
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function returns the type of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized int getActorType(Integer ActorID) {
    	Actor a = (Actor) DB.get(ActorID);
    	return a.type;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the position of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorPosition(Integer ActorID, Point2D p) {
    	Actor a = (Actor) DB.get(ActorID);
    	a.position = p;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the moveto of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorMoveTo(Integer ActorID, Point2D p) {
    	Actor a = (Actor) DB.get(ActorID);
    	a.moveto = p;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the speed of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorSpeed(Integer ActorID, float s) {
    	Actor a = (Actor) DB.get(ActorID);
    	a.speed = s;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets a status flag of the actor
    //in the HashTable 'DB' which has the given ActorID.
    //if state is false then set the actor's 'status' to
    //'status' &(bitwise and) with ~(bitwise not)flag.
    //status = status & ~flag
    //If the state is true then set the actor's 'status' to
    //status = status | flag; (bitwise or)
    public synchronized void setActorStatusFlag(Integer ActorID, int flag, boolean state) {
    	Actor a = (Actor) DB.get(ActorID);
    	if(state)
    		a.status = a.status|flag;
    	else
    		a.status = a.status&(~flag);
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the type of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorType(Integer ActorID, int type) {
    	Actor a = (Actor) DB.get(ActorID);
    	a.type = type;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function returns an array of the actorID's of all the
    //actors in the HashTable
    //This is basically an array of all the 'keys' in the hashtable.
    public synchronized int [] getAllPlayerCharacters() {
    	Set keySet = DB.keySet();
    	Object keyArray = keySet.toArray();
    	return (int []) keyArray; 
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //sets the name of the actor with the
    //given actorID.
    public synchronized void setActorName(Integer ActorID, String name) {
    	Actor a = (Actor) DB.get(ActorID);
    	a.name = name;
    }
    
}
