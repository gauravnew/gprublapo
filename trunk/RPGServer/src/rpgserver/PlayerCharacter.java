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
import java.util.Random;
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

	float distTrav; //distance the characters move
	float distFromLastCollision; //distance the character moved from the last collision;
	
	Point2D bridgeStart;
	
	boolean inBridge; //if the player is in the bridge circuit;
	boolean sick; //if the player is sick (true if sick, false otherwise)

	//constructor -- initializes the class datamembers
	PlayerCharacter(){
		super();
		this.type = 0;
		
		classesAttended = new int[NUM_CLASSES];
		
		health = 100; //starting health
		lastClass = -1;
		credits = 0;
		speed=1;
		distFromLastEx = (float)0.0; //distance from last exercise and/or eat

		distTrav = (float) 0.0;; //distance the characters move
		distFromLastCollision = (float) 0.0; //distance the character moved from the last collision;

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
			if(!(this.actorID.equals(temp.actorID)) && this.position.sameCell(temp.position) && this.distFromLastCollision>=1){ //if yes
				return temp.actorID; // return the ID of the collided character
			}	
		}
		
		//if by this point we didn't return... return null;
		return null; //return the ID of the character this actor collided with
		
	}

	//processing collisions with various objects

	String processCollision(Integer id){

		// id of the thing this character is colliding with and change the health and speed accordingly
		String collisionString = "No collision";
		int actorType = Main.cDBEngine.getActorType(id); //type of the actor I am colliding with
		
		if(actorType == 0) {// if interaction is with another player
			 collisionString = ""; //do nothing
		}
		if(actorType == 1){ // H1N1
			this.sick = true;
			this.speed = 0.1f; // really slow...
			collisionString = "Interacted with H1N1!";
		}
		if(actorType == 2){ // Professors
			this.health -= 5;
			if(this.health<0)
				this.health=0; //health can't be bellow zero
			collisionString = "Interacted with a professor!";
		}
		if(actorType == 3){ // Manholes
			Random randGen = new Random();
			int chance = randGen.nextInt(12);
			if(chance<2){ //sclarite
				this.health +=25; // is there a cap on the amount of health points you can get?
				collisionString = "Interacted with Sclarite!";
			}
			else{ //silberite
				this.health -= 15;
				if(this.health<0)
					this.health=0; //health can't be bellow zero
				this.credits -=2;
				if(this.health<0)
					this.credits=0; //credits can't be bellow zero
				collisionString = "Interacted with Silberite!";
			}
			
		}
		if(actorType >=4 && actorType <= 8){ //Cranberry Farms, Einstein's Bagels, Jamba Juice, Loose Leafs, or Subway
			if(!sick && this.distFromLastEx>200){ //if I am not sick and walked far enough
				this.health += 20;
				this.distFromLastEx = 0; //reset distance counter
				collisionString = "You just Ate and replenished yourself!";
			}
			
		}
		if(actorType >=9 && actorType <= 11){ //Panda Express, Papa John's or Starbucks
			if(!sick && this.distFromLastEx>200){ //if I am not sick and walked far enough
				this.health += 10;
				this.distFromLastEx = 0; //reset distance counter
				collisionString = "You just Ate and replenished yourself!";
			}
		}
		if(actorType >= 12 && actorType <= 13){ //Dunkin Donuts or Store 24
			if(!sick && this.distFromLastEx>200){ //if I am not sick and walked far enough
				this.health += 5;
				this.distFromLastEx = 0; //reset distance counter
				collisionString = "You just Ate and replenished yourself!";
			}
		}
		if(actorType == 14){ //FitRec
			if(!sick && this.distFromLastEx>200){ //if I am not sick and walked far enough
				this.health += 15;
				this.distFromLastEx = 0; //reset distance counter
				collisionString = "You just went to FitRec!";
			}
		}
		if(actorType == 15){ //Student Health Services
			this.sick = false; //im cured!
			collisionString = "You just went to Student Health Services!";
		}
		if(actorType >= 16 && actorType <=24){ //Classrooms
			if(!sick){ //if i am not sick...
				int currClass = actorType - 16; // current class
				if(currClass != this.lastClass) //if this is not the last class attended
					this.classesAttended[currClass] ++;
				if(this.classesAttended[currClass] == 2) //if attended this class twice
					this.credits += 4;
			}
		}
		if(actorType == 25){ //Bridge circuit
			if(!this.sick){ //if im not sick
				//enter the bridge circuit
				collisionString = "Bridge Circuit";
				if(!this.inBridge){ // inBridge = false
					this.inBridge = true;
					this.bridgeStart = this.position;
					this.speed = 1.5f; // Very very fast!
					 
				}
				//exit the bridge circuit
				if(this.inBridge) { //if in bridge circuit
					if(!this.bridgeStart.sameCell(this.position) && this.distFromLastEx>200 ){ // and exit not where enter and walked far enough
						this.health +=25;
						this.distFromLastEx = 0; //reset 
					}
					this.inBridge = false; //exit the bridge
					updateSpeed(); //return speed to normal
				}
				// reduce speed down to correct amount
			}
		}
		
		if(actorType==27){ //Teleport
			float x = Float.parseFloat(Main.cDBEngine.getActorName(id).substring(0,3));
			float y = Float.parseFloat(Main.cDBEngine.getActorName(id).substring(4));
			this.position.setPosition(x,y);
			this.moveto.setPosition(x,y);
			collisionString = "Teleport";
		}
		
		this.distFromLastCollision = 0;
		updateSpeed();
		return collisionString;
	
	}

    @Override
    public boolean updatePosition(/*Actor actor*/) {
//    	this.position.setPosition(this.moveto.getX(), this.moveto.getY());
//    	return true;
    	//distance between the current postition and the moveto postion

//    	super.updatePosition(/*actor*/);

    	float dist = this.position.getDistance(this.moveto);
/*    	this.position.moveTo(dist, this.moveto);
    	
    	this.distFromLastEx += dist; //add the distance moved to the counter
    	this.distFromLastCollision += dist;
    	this.distTrav += dist;
    	
    	return;
*/  
   	 	float d = dist;
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
	    	return true;
    	}
    	return false;
    }

    /*
     *         //distance between the current postition and the moveto postion
>>>>>>> .r104
    	float dist = actor.position.getDistance(actor.moveto);
    	
    	this.distFromLastEx += dist; //add the distance moved to the counter
    	this.distFromLastCollision += dist;
    	this.distTrav += dist;
        

 */
    
    void updateSpeed(){
    	if(sick){
    		this.speed = 0.1f;
    	}else if(inBridge && !sick){ //if Im health and in Bridge circuit
    		this.speed = 1.5f;
    	}else{
	    	if(this.health>125)
	    		this.speed = 0.8f; //Zippy speed!
	    	if(this.health<=125 && this.health>75)
	    		this.speed = 0.5f;//normal speed
	    	else if (this.health<=75 && this.health>25)
	    		this.speed = 0.3f;//slow speed
	    	else if (this.health<=25)
	    		this.speed = 0.1f;//very slow speed
	    	else if (this.health<0) //error check
	    		System.out.println("Invalid Health");//health can never be less than 0...
    	}
    }

}
