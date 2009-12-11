/*
 * Filename : ActorEngine.java
 * Description : Maintains a list of all actors and
 *   establishes functions to manage them.
 */
package rpgclient;

import java.util.*;
import java.awt.*;
import java.util.concurrent.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author Gaurav
 */
public class ActorEngine {

    ConcurrentSkipListSet<Actor> actors;
    private int mainActorID;

    ActorEngine() {
        actors = new ConcurrentSkipListSet<Actor>();
    }

    public synchronized void setMainActor(int id) {
        mainActorID = id;
    }

    public synchronized int getMainActor() {
        return mainActorID;
    }

    public synchronized int addActor(Actor a) {
        actors.add(a);
        return a.actorID;
    }

    public synchronized int removeActor(Actor a) {
        actors.remove(a);
        return a.actorID;
    }

    public synchronized Actor getActor(int actorID) {
        try {
            Actor a = new Actor(actorID);
            if (actors.floor(a).equals(a)) {
                return actors.floor(a);
            } else {
                Actor newact = new Actor(actorID, 0, "Default");
                addActor(newact);
                return newact;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized void setActorMoveTo(Integer ActorID, Point2D p) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (actors.contains(temp)) {
            a = (Actor) actors.floor(temp);
            a.moveto = p;
        } else {
            a = null;
        }

    }

    public synchronized void setActorPosition(Integer ActorID, Point2D p) {
        Actor temp = new Actor();
        temp.actorID = ActorID;

        Actor a;

        if (actors.contains(temp)) {
            a = (Actor) actors.floor(temp);
            a.position = new Point2D(p);
            a.moveto = new Point2D(p);
        } else {
            a = null;
        }

    }

    public synchronized void setActorInfo(movePkt m) {
        Actor temp = new Actor();
        temp.actorID = m.id;

        Actor a;

        if (actors.contains(temp)) {
            a = (Actor) actors.floor(temp);
            a.moveto = m.pos;
            a.speed = m.float_dat;
            a.health = m.info;
            a.credits = m.info2;
        } else {
            a = null;
        }

    }
    
    public void renderAll(Graphics g, Point2D center) {
        int i;
    	try {
//        	g.drawString("start Actor Render", 400,400);
            for (Actor a : actors) {
//            	i = a.actorID.intValue() % 100;
//            	g.drawString(a.actorID.toString(), (i/10) * 80, (i%10) * 60); 
                a.render(g, center);
            }
//            g.drawString(Integer.toString(actors.size()), 500, 500);
        } catch (Exception e) {
            return;
        }
    }
}
