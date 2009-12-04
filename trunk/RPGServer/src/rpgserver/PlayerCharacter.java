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

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

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
    float distTrav;
    float distFromLastCollision;
    
	Point2D bridgeStart;
	
	boolean inBridge; //if the player is in the bridge circuit;
	boolean sick; //if the player is sick (true if sick, false otherwise)

	//constructor -- initializes the class datamembers
	PlayerCharacter(){
		super();
		this.type = 0;
		
		classesAttended = new int[NUM_CLASSES];
		
		health = 100; //starting health
		lastClass = 0;
		credits = 0;
		
		distFromLastEx = (float)0.0; //distance from last exercise and/or eat
		distFromLastCollision= (float)0.0;
		distTrav = (float)0.0;
		
		bridgeStart = new Point2D(-1, -1); //initial position of the bridge
		
		inBridge = false;
		sick = false;
		
		//name is set when the login package is sent it
		
	}
	
	Integer checkCollision(){
		//every time the character moves, it checks the collision
		//get the list of all the possible characters
		ConcurrentSkipListSet<Actor> actors = Main.cDBEngine.getHashtableKeys();
		Iterator<Actor> actItr = actors.iterator();
		//for each character in the list
		while(actItr.hasNext()){
			Actor temp = actItr.next();
			//check if my moveto position (or current position?) == to characters (temp) current position
			if(this.moveto.equals(temp.position)){ //if yes
				return temp.actorID; // return the ID of the collided character
			}	
		}
		
		//if by this point we didn't return... return null;
		return null; //return the ID of the character this actor collided with
		
	}
	
	String processCollision(Integer id){
		return new String ("ToBeImplemented" + id.intValue());
		// id of the thing this character is colliding with and change the health and speed accordingly
	}

    @Override
    public void updatePosition(/*Actor actor*/) {
        //distance between the current postition and the moveto postion
    	float dist = this.position.getDistance(this.moveto);
    	this.position.moveTo(dist, this.moveto);
    	return;
   /* 	float d = dist;
    	Point2D proposed = new Point2D();
    	

    	float dX = this.moveto.getX() - this.position.getX();
    	float dY = this.moveto.getY() - this.position.getY();
    	if(d>0) {
    		if(d < dX){
	    		d-=dX;
		    	proposed.setX(this.position.getX() + dX);
	    	}
	    	else {
	    		proposed.setX(this.moveto.getX());
	    		d = 0;
	    	}
    	}
    	if(d>0){
	    	if (d < dY){
	    		d-=dY;
	    		proposed.setY(this.position.getY() + dY);
	    	}
	    	else {
	    		proposed.setY(this.moveto.getY());
	    		d=0;
	    	}
    	}

    	if(Main.cGameLogic.getGameMap().getCellType(proposed) != 0) {
    		this.position.setPosition(proposed.getX(), proposed.getY());
	    	this.distFromLastEx += dist; //add the distance moved to the counter
	    	this.distTrav += dist;
	    	this.distFromLastCollision += dist;
    	}
    */	
    }
/*
 *         //distance between the current postition and the moveto postion
    	float dist = actor.position.getDistance(actor.moveto);
    	 
    	client.getNetworkOutput().sendActorMove(actor.actorID, actor.moveto); //update the position of player
    	
    	this.distFromLastEx += dist; //add the distance moved to the counter
    	if(this.distFromLastEx > 200) // if the distance is far enough
    		this.distFromLastEx = 0; //set the counter to zero
        

 */
    
    
    
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
