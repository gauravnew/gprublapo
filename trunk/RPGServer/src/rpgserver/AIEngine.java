/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import java.util.*;
/**
 *
 * @author Jacob
 */
public class AIEngine {

    // This method takes an actor, extracts its current location and "moveto" location (set as its destination) and tries to step the actor towards this goal,
    // while avoiding cells that are not "walkable."
    public void moveTo(Integer actorID, GlobalGameDatabase db, GameMap map){
        Point2D position = db.getActorPosition(actorID);
        Point2D moveto = db.getActorMoveTo(actorID);
        float speed = db.getActorSpeed(actorID);
        
        boolean yDirectionUp; // True if the actor's new position is above the current one; false otherwise.
        boolean xDirectionRight; // True if the actor's new position is to the right of the current one; false otherwise.
        
        if (position.getY() < moveto.getY()) { yDirectionUp = true; }
        else { yDirectionUp = false; }
        if (position.getX() < moveto.getX()) { xDirectionRight = true; }
        else { xDirectionRight = false; }
        
        boolean horizontalMoveReq = true; // Boolean is set to true only if the actor needs to make more steps, horizontally or vertically, to reach its
        boolean verticalMoveReq = true;   // desired location.  Makes sure that the actor is not needlessly moving further away from its goal.
        
        int attempts = 0; // Keeps track of number of consecutive failed attempts to move toward desired location (to ensure the actor does not get stuck).
        
        while (attempts < 6){ // This loop is executed as long the actor has not attempted to move towards its new location and failed 5 times consecutively.
                              // Each iteration through the loop moves the actor one "step" (dependent on movement speed) towards its desired location.
                              // The loop may require changes so that the actor is slowed down somehow and does not move to each location immediately after
                              // the last, i.e., a pause after each individual movement.
            
            // If the actor doesn't need to move any further in either direction (adjusted for actor's speed), set the booleans to false.
            if ((position.getY() > (moveto.getY() - (speed / 2 + 1))) && (position.getY() < (moveto.getY() + (speed / 2 + 1)))){ 
                verticalMoveReq = false;
            }
            if ((position.getX() > (moveto.getX() - (speed / 2 + 1))) && (position.getX() < (moveto.getX() + (speed / 2 + 1)))){
                horizontalMoveReq = false;
            }
            
            // If the actor's position matches its desired position (adjusted for actor's speed), break out of the loop and return.
            if (!verticalMoveReq && !horizontalMoveReq) { return; }
            
            // Otherwise:
            // If the actor does not need to move any further vertically, "moveState" is set to false, and the actor will move left or right.
            // If the actor does not need to move any further horizontally, "moveState" is set to true, and the actor will move up or down.
            // If the actor must move in both directions to reach its goal, generate a random number (either 0 or 1).  
            // Have the actor move up or down if the number is 0, and left or right if the number is 1.
            boolean moveState;
            if (!verticalMoveReq) {
                moveState = false;
            } else if (!horizontalMoveReq) {
                moveState = true;
            } else {
                moveState = ((int)(2 * Math.random()) == 0);
            }
            
            if (moveState){ // Actor will travel vertically.
                if (yDirectionUp){ // Actor will travel upwards.
                    if (map.getCellType(new Point2D((float)position.getX(), (float)(position.getY() - speed))) != 1){ // It should be noted that this condition checks ONLY
                        attempts++;                                                                                   // if the next movement step is not a "walkable" cell.
                    } else {
                        db.setActorPosition(actorID, new Point2D((float)position.getX(), (float)(position.getY() - speed))); // *** See below
                        attempts = 0;
                    }
                } else { // Actor will travel downards.
                    if (map.getCellType(new Point2D((float)position.getX(), (float)(position.getY() + speed))) != 1){
                        attempts++;
                    } else {
                        db.setActorPosition(actorID, new Point2D((float)position.getX(), (float)(position.getY() + speed))); // *** See below
                        attempts = 0;
                    }
                }
            } else { // Actor will travel horizontally.
                if (xDirectionRight){ // Actor will travel to the right.
                    if (map.getCellType(new Point2D((float)position.getX() + speed, (float)(position.getY()))) != 1){
                        attempts++;
                    } else {
                        db.setActorPosition(actorID, new Point2D((float)position.getX() + speed, (float)(position.getY()))); // *** See below
                        attempts = 0;
                    }
                } else{ // Actor will travel to the left.
                    if (map.getCellType(new Point2D((float)position.getX() - speed, (float)(position.getY()))) != 1){
                        attempts++;
                    } else {
                        db.setActorPosition(actorID, new Point2D((float)position.getX() - speed, (float)(position.getY()))); // *** See below
                        attempts = 0;
                    }
                }
            }
            
            // *** The code marked above may need to be changed--as it stands currently, the actors position is simply SET to its current position plus its speed.
            //     Not sure how to do so, but it seems that it might actually require code that animates the actor (walking), so that it does not simply "appear"
            //     in its new location.
        
        }
    }
    
}
