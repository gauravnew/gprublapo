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
            return actors.ceiling(new Actor(actorID,0,"NA"));
        } catch (Exception e) {
            return null;
        }
    }

    public void renderAll(Graphics g, Point2D center) {
        try {
            for (Actor a : actors)
                a.render(g, center);
        } catch (Exception e) {
            return;
        }
    }

}
