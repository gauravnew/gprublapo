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
	
	private static final int NUM_CLASSES = 9; //number of classes the player has to attend
	
	int[] classesAttended; //array that keeps track of what classes and how many times the classes were attended
    
	int health; // can never be less than 0
	int lastClass; //last class that the player attended
	int credits; //how many credits the player currently
	
	float distFromLastEx; //distance from last exercise and/or eat
    
	boolean inBridge; //if the player is in the bridge circuit;
	boolean sick; //if the player is sick (true if sick, false otherwise)

	//constructor -- initializes the class datamembers
	PlayerCharacter(){
		this.type = 0;
		
		classesAttended = new int[NUM_CLASSES];
		
		health = 100; //starting health
		lastClass = 0;
		credits = 0;
		
		distFromLastEx = (float)0.0; //distance from last exercise and/or eat
		
		inBridge = false;
		sick = false;
		
		//name is set when the login package is sent it
		
	}
	
	Integer checkCollision(){
		//reference a list of charecters
		//every time the character moves, it checks the collision
		return null;
		
	}
	
	void processCollision(Integer id){
		// id of the thing this character is colliding with and change the health and speed accordingly
	}

    @Override
    public void updatePosition(Actor actor) {
        client.getNetworkOutput().sendActorMove(actor.actorID, actor.moveto);
    }
    
    void updateSpeed(){
    	if(this.health<75)
    		this.speed = 0.5f;//normal speed
    	else if (this.health<=75 && this.health>25)
    		this.speed = 0.3f;//slow speed
    	else if (this.health<=25)
    		this.speed = 0.1f;//very slow speed
    	else if (this.health<0) //error check
    		System.out.println("Invalid Health");//health can never be less than 0...
    }

}
