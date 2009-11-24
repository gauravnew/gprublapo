/*
 * Filename : PlayerCharacter.java
 * Description : This class stored data about real players.
 */

/**
  *  TODO
  *	Add int health, boolean inBridge, float distFromLastClass, float distFromLastEx, boolean sick, int credits
  *	Add function updateSpeed(), which updates a player character's speed based upon health
  *	Extend Actor's updatePosition function to increase distFromLastClass & distFromLastEx accordingly.
  *	Add function processCollision(Integer id) function which handles collisions with all other objects.
  *		This should update the following accordingly:
  *			health, speed, distFromLastClass, distFromLastEx, sick, credits
  *		Note that interacting with another player character has no repercussions.
  *		Note that a player could be colliding with multiple objects at the same time
  */
 
package rpgserver;

/**
 *
 * @author Gaurav
 */
public class PlayerCharacter extends Actor {

    ClientHandler client;

    @Override
    public void updatePosition(Actor actor) {
        client.getNetworkOutput().sendActorMove(actor.actorID, actor.moveto);
    }

}
