/*
 * Filename : PlayerCharacter.java
 * Description : This class stored data about real players.
 */

/**
  *  TODO
  *	Add int health, boolean inBridge, int LastClass, float distFromLastEx, boolean sick, int credits
  *	Add function updateSpeed(), which updates a player character's speed based upon health
  *	Extend Actor's updatePosition function to increase distFromLastClass & distFromLastEx accordingly.
  *	Add function processCollision(Integer id) function which handles collisions with all other objects.
  *		This should update the following accordingly:
  *			health, speed, lastClass, distFromLastEx, sick, credits
  *		Note that interacting with another player character has no repercussions.
  *		Note that a player could be colliding with multiple objects at the same time
  */
 
package rpgserver;

/**
 *
 * @author Gaurav
 */
public class PlayerCharacter extends Actor {

	//datamembers
    ClientHandler client;
	NetworkStreamWriter out;
	
	int[] classesAttended; //array that keeps track of what classes and how many times the classes were attended
    
	int health;
	int lastClass; //last class that the player attended
	int credits; //how many credits the player currently
	
	float distFromLastEx;
    
	boolean inBridge; //if the player is in the bridge circuit;
	boolean sick; //if the player is sick (true if sick, false otherwise)

	//constructor -- initializes the class datamembers
	PlayerCharacter(){
		this.type = 0;
		
		classesAttended = new int[9];
		
		health = 100;
		lastClass = 0;
		credits = 0;
		
		distFromLastEx = (float)0.0;
		
		inBridge = false;
		sick = false;
		
	}
	
	Integer checkCollision(){
		return null;
		
	}
	
	void processCollision(Integer id){
		
	}

    @Override
    public void updatePosition(Actor actor) {
        client.getNetworkOutput().sendActorMove(actor.actorID, actor.moveto);
    }
    


}
