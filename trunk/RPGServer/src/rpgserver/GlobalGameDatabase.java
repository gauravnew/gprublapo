/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgserver;

/**
 * TODO
 *  implement  PlayerCharacter getPlayer(int ActorID)
 */
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Gaurav
 */
public class GlobalGameDatabase implements Iterable {

    //Hashtable to store Actor data.
    private ConcurrentSkipListSet<Actor> DB;
    //Next available actorID.
    Integer nextActorID;	//[S.D.031]

    public GlobalGameDatabase() {
        DB = new ConcurrentSkipListSet<Actor>();
        nextActorID = 0xFFFF;
    }

    @Override
    public synchronized Iterator<Actor> iterator() {
        return DB.iterator();
    }

    //this function get a set of the database's keys
    public synchronized ConcurrentSkipListSet<Actor> getHashtableKeys() {
        return DB;
    }

    //TODO: Complete!
    //This function creates a 'PlayerCharacter' object.
    //Sets its actorID to the next available actorID.
    //Sets the client of the 'PlayerCharacter'
    //Puts the 'PlayerCharacter' in the HashTable 'DB'
    //Returns the actorID of the new actor.
    public synchronized int createNewPlayerCharacter(ClientHandler c) {

        PlayerCharacter p = new PlayerCharacter();
        p.actorID = nextActorID++; //[S.D.032]
        p.client = c;
        p.health = 100;
        DB.add(p);
        //DB.put(p.actorID,p);

        return p.actorID.intValue();

    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/16/09 5PM)
    //This function creates a 'NonPlayerCharacter' object.
    //Sets its actorID to the next available actorID.
    //Puts the 'NonPlayerCharacter' in the HashTable 'DB'
    //Returns the actorID of the new actor.
    public synchronized int createNewNonPlayerCharacter(int type, int direction) {

        NonPlayerCharacter np = new NonPlayerCharacter(type, direction);
        np.actorID = nextActorID++; //[S.D.032]
        DB.add(np);
        //DB.put(np.actorID, np);

        return np.actorID.intValue();

    }

    public synchronized int createNewNonPlayerCharacter(int type, Point2D mapPos) {

        NonPlayerCharacter np = new NonPlayerCharacter(type, mapPos);
        np.actorID = nextActorID++; //[S.D.032]
        DB.add(np);
        //DB.put(np.actorID, np);

        return np.actorID.intValue();

    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 5PM)
    //This function removes an actor with the
    //given actorID from the HashTable 'DB'
    //return true always.
    public synchronized boolean deleteActor(Integer ActorID) {

        Actor temp = new Actor();
        temp.actorID = ActorID;
        //if the actor exists in the hashtable
        //System.out.println("The DB contains the object with the key? " + DB.contains(ActorID));
        if (DB.contains(temp)) {
            //System.out.println("Size before delete: " + DB.size());
            //System.out.println("The DB contains the key " + ActorID);
            DB.remove(temp); //remove the key and the corresponding value(actor) from the hashtable
            //System.out.println("Size after delete: " + DB.size());
            //System.out.println("The DB still contains the key? " + DB.contains(temp));
            return true;
        }

        //System.out.println("The DB contains the key? " + DB.contains(temp));
        //System.out.println("The DB contains the object with the key?? " + DB.contains(temp));
        return false;

    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the position of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized Point2D getActorPosition(Integer ActorID) {

        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
        } else {
            a = null;
        }
        return a.position; //return the actor's position
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the moveto of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized Point2D getActorMoveTo(Integer ActorID) {

        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
        } else {
            a = null;
        }

        return a.moveto;
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the speed of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized float getActorSpeed(Integer ActorID) {

        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
        } else {
            a = null;
        }
        return a.speed;
    }

    //TODO: Complete? (by Anastasia Vashkevich -- 11/15/09 6:30PM)
    //This function returns the name of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized String getActorName(Integer ActorID) {

        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
        } else {
            a = null;
        }
        return a.name;
    }
    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function returns a status flag of the actor
    //in the HashTable 'DB' which has the given ActorID.
    //basically, it returns the 'status' variable of the
    //actor &(bitwise and) with the argument flag.

    public synchronized int getActorStatusFlags(Integer ActorID, int flag) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;
        int stat = 0;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            stat = a.status;
        } else {
            a = null;
        }

        return (stat & flag);
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function returns the type of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized int getActorType(Integer ActorID) {

        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
        } else {
            a = null;
        }
        return a.type;
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the position of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorPosition(Integer ActorID, Point2D p) {

        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            a.position = p;
        } else {
            a = null;
        }
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the moveto of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorMoveTo(Integer ActorID, Point2D p) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            a.moveto = p;
        } else {
            a = null;
        }

    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the speed of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorSpeed(Integer ActorID, float s) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            a.speed = s;
        } else {
            a = null;
        }

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
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            if (state) {
                a.status = a.status | flag;
            } else {
                a.status = a.status & (~flag);
            }
        } else {
            a = null;
        }

    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function sets the type of the actor
    //in the HashTable 'DB' which has the given ActorID.
    public synchronized void setActorType(Integer ActorID, int type) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            a.type = type;
        } else {
            a = null;
        }

    }

    public synchronized Actor getActorByType(int type) {
        for (Actor a: DB) {
            if (a.type == type)
                return a;
        }
        return null;
    }

    //TODO: Previously existing keys may be deleted and should not be returned.
    //=========================================================================
    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //This function returns an array of the actorID's of all the
    //actors in the HashTable
    //This is basically an array of all the 'keys' in the hashtable.
    public synchronized Integer[] getAllPlayerCharacters() {
        Integer lastKey = nextActorID - 1; //the last key that was input into the hashtable
        Integer[] keyArray = new Integer[DB.size()]; // the array of keys that are present in the hashtable

        int indx = DB.size() - 1; //size of the hashtable
        //counter that goes through all possible keys that might have went into the hashtable at somepoint
        //and since than might have been deleted
        int counter = nextActorID - 1;

        //this while loop will test the current presence of all possible keys that were in the hashtable at some point
        while (counter >= 0xFFFF) {
            //     System.out.println("Testing if the DB contains key: " + lastKey);
            if (DB.contains(lastKey)) { //if the hashtable has the key...
                keyArray[indx] = lastKey; //assign the key
                //      System.out.println("The DB contains the key: " + lastKey + " that was placed into keyArray[" + indx+"]");
                indx--; //next index going in reverse
            }
            lastKey--; //next key
            counter--; //counter
        }

        return keyArray; //return the array that contains all the current keys present in the hashtable
    }

    //TODO: Complete (by Anastasia Vashkevich -- 11/15/09 10PM)
    //sets the name of the actor with the
    //given actorID.
    public synchronized void setActorName(Integer ActorID, String name) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (DB.contains(temp)) {
            a = (Actor) DB.floor(temp);
            a.name = name;
        } else {
            a = null;
        }

    }

    public synchronized PlayerCharacter getPlayer(Integer id) {
        for (Actor a : this.getHashtableKeys()) {
            if (a.actorID.equals(id)) {
                return (PlayerCharacter) a;
            }
        }
        return null;
    }

    public synchronized NonPlayerCharacter getNonPlayer(Integer id) {
        for (Actor a : this.getHashtableKeys()) {
            if (a.actorID.equals(id)) {
                return (NonPlayerCharacter) a;
            }
        }
        return null;
    }

    public synchronized void setPlayerOutputStream(Integer id, NetworkStreamWriter o) {
        Actor temp = new Actor();
        temp.actorID = id;
        if (DB.contains(temp)) {
            PlayerCharacter a = (PlayerCharacter) DB.floor(temp);
            a.out = o;
        }
    }
}
