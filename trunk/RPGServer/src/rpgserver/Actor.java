/*
 * Filename : Actor.java
 * Description : This class stored data about actors.
 * An actor is any interactive element of the game
 * from real players to H1N1 to classrooms.
 */

/**
  *  TODO
  *	Create updatePosition() function which updates the actor's position based upon current
  *	position, moveto position and time elapsed.  (See client Actor class render function). 
  *	This should verify that the point it is about to move to is a valid map point.
  */

package rpgserver;

/**
 *
 * @author Gaurav
 */
public class Actor implements Comparable {

    public Integer actorID;
    public int type;
    public Point2D position;
    public Point2D moveto;
    public int status;
    public float speed;
    public long lastmovetime;
    public String name;

    public Actor() {

        actorID = 0;
        type = 0;
        position = new Point2D();
        moveto = new Point2D();
        status = 0;
        speed = 0;
        lastmovetime = 0;
        name = "default";
        
    }

    @Override
    public int compareTo(Object a) {
        return ((Actor)a).actorID - actorID;
    }

    @Override
    public boolean equals(Object a) {
        return actorID == ((Actor)a).actorID;
    }

    public void updatePosition(Actor actor) {
        //this function updates the actors current position based on moveTo position and time elapsed.
        
    	//when was the last time the actor moved?
    	if(actor.lastmovetime == 0){
    		//if this is the first time the actor is moving, the actor will not move at this calculation -- will move next calc.
    		actor.lastmovetime = System.nanoTime(); //if the actor didn't move, the last time it moved is NOW
    	}
    	
    	//distance = time*speed
    	long timeElapsed = actor.lastmovetime - System.nanoTime(); // how much time has passed between the last time actor moved and NOW
    	
    	float dist = timeElapsed*actor.speed; //distance
    	actor.position.moveTo(dist, actor.moveto); //change actor position
    	
    	actor.lastmovetime = System.nanoTime(); //set the last time the actor moved to the system time.
     }
    
}
