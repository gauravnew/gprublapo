package rpgclient;

/*
 * Filename : Actor.java
 * Description : This class stored data about actors.
 * An actor is any interactive element of the game
 * from real players to H1N1 to classrooms.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author Gaurav
 */
public class Actor implements Comparable {

    public Integer actorID;
    public int type;
    public Point2D position;
    public int direction;
    public int frame;
    public Point2D moveto;
    public int status;
    public float speed;
    public long lastmovetime;
    public int health;
    public int credits;
    public String name;

    enum DIRECTION {

        FRONT(0), LEFT(1), BACK(2), RIGHT(3);
        public int value;

        DIRECTION(int x) {
            value = x;
        }
    };
    AnimatedSprite[] sprite;

    //Generic constructor
    public Actor() {

        actorID = 0;
        type = 0;
        position = new Point2D();
        moveto = new Point2D();
        status = 0;
        speed = 0;
        lastmovetime = 0;
        name = "default";
        direction = 0;
        frame = 0;
        health = 0;
        credits = 0;

        sprite = new AnimatedSprite[4];

        sprite[DIRECTION.FRONT.value] = new AnimatedSprite("data/front", 3);
        sprite[DIRECTION.LEFT.value] = new AnimatedSprite("data/left", 3);
        sprite[DIRECTION.BACK.value] = new AnimatedSprite("data/back", 3);
        sprite[DIRECTION.RIGHT.value] = new AnimatedSprite("data/right", 3);

    }

    //Explicit constructor
    public Actor(int ID, int nType, String sName) {

        actorID = ID;
        type = nType;
        position = new Point2D();
        moveto = new Point2D();
        status = 0;
        speed = 0.5f;
        lastmovetime = 0;
        name = sName;
        direction = 0;
        frame = 0;
        health = 0;
        credits = 0;

        sprite = new AnimatedSprite[4];
        if (type == 0) {
            sprite[DIRECTION.FRONT.value] = new AnimatedSprite("data/front", 3);
            sprite[DIRECTION.LEFT.value] = new AnimatedSprite("data/left", 3);
            sprite[DIRECTION.BACK.value] = new AnimatedSprite("data/back", 3);
            sprite[DIRECTION.RIGHT.value] = new AnimatedSprite("data/right", 3);
        } else if (type == 2) {
            sprite[DIRECTION.FRONT.value] = new AnimatedSprite("data/h1n1", 1);
            sprite[DIRECTION.LEFT.value] = new AnimatedSprite("data/h1n1", 1);
            sprite[DIRECTION.BACK.value] = new AnimatedSprite("data/h1n1", 1);
            sprite[DIRECTION.RIGHT.value] = new AnimatedSprite("data/h1n1", 1);

        } else if (type == 3) {
            sprite[DIRECTION.FRONT.value] = new AnimatedSprite("data/manhole", 1);
            sprite[DIRECTION.LEFT.value] = new AnimatedSprite("data/manhole", 1);
            sprite[DIRECTION.BACK.value] = new AnimatedSprite("data/manhole", 1);
            sprite[DIRECTION.RIGHT.value] = new AnimatedSprite("data/manhole", 1);

        } else {
            sprite[DIRECTION.FRONT.value] = new AnimatedSprite("data/h1n1", 1);
            sprite[DIRECTION.LEFT.value] = new AnimatedSprite("data/h1n1", 1);
            sprite[DIRECTION.BACK.value] = new AnimatedSprite("data/h1n1", 1);
            sprite[DIRECTION.RIGHT.value] = new AnimatedSprite("data/h1n1", 1);

        }

    }

    Actor(int ActorID) {
        actorID = ActorID;
    }

    @Override
    public int compareTo(Object a) {
        return ((Actor) a).actorID - actorID;
    }

    @Override
    public boolean equals(Object a) {
        return actorID == ((Actor) a).actorID;
    }

    public boolean isMoving() {
        return lastmovetime != 0;
    }
    //[C.G.021]
    //Function to draw an actor on top of the map

    public void render(Graphics g, Point2D center) {

        float d = position.getDistance(moveto);

        if (lastmovetime == 0 && d != 0) {
            lastmovetime = System.nanoTime();
        }

        long time = System.nanoTime() - lastmovetime;

        if (d == 0) {
            lastmovetime = 0;
        } else {
            direction = position.moveTo(speed * time / 50000000, moveto, direction);
        }

        if (d != 0) {
            lastmovetime = System.nanoTime();
        }

        float x = -(center.getX() - position.getX()) * 16;
        float y = -(center.getY() - position.getY()) * 16;

        x += (Main.width / 2 - 16);
        y += (Main.height / 2 - 16);

        if (type<3){
	        if (lastmovetime == 0) {
	            g.drawImage(sprite[direction].getDefaultFrame(), (int) x, (int) y, null);
	        } else {
	            g.drawImage(sprite[direction].animate(d), (int) x, (int) y, null);
	        }
        }

        if (type == 0) {
            g.setColor(Color.black);
            g.fillRect((int) x, (int) y, 5, 26);
            g.setColor(Color.green);
            g.fillRect((int) x + 1, (int) y + 1, 4, (health / 4));
        }

        if (name != null && (type<1 || type > 3)) {
            g.setColor(Color.black);
            g.drawString(name, (int) x, (int) y + 37);
            if (type==0) g.drawString(Integer.toString(credits), (int)x+24, (int)y+16);
        }

    }
}
