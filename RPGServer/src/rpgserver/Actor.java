/*
 * Filename : Actor.java
 * Description : This class stored data about actors.
 * An actor is any interactive element of the game
 * from real players to H1N1 to classrooms.
 */


package rpgserver;

/**
 *
 * @author Gaurav
 */
public class Actor {

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
    
}
