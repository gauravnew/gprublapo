/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Jacob
 */
public class NPCEngine {
    
    
    //Reads each line individually from a file listing NPCs of the format
    //1234:56:78:Name, where 1234 is the character's type, 56 and 78 are
    //the character's x and y positions respectively, and Name is the
    //character's name, and creates a new NPC using these values.
    //(completed by Jacob Hampton, 11/17, at 12:15 AM)
    public void loadNPCFromFile(String filename, GlobalGameDatabase db) {
        
        int type = 0000;
        int x = 0;
        int y = 0;
        String name = "";
        
        try {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = in.readLine()) != null) {
            type = Integer.parseInt(str.substring(0, 4));
            x = Integer.parseInt(str.substring(5, 7));
            y = Integer.parseInt(str.substring(8, 10));
            Point2D position = new Point2D((float)x, (float)y);
            name = str.substring(11);
            int actorID = db.createNewNonPlayerCharacter(type);
            db.setActorPosition(actorID, position);
            db.setActorName(actorID, name);
        }
        in.close();
        } catch (IOException e) {
        }
        
    }
    
}