/*
 * Filename : AnimatedSprite.java
 * Description : Maintain data for animated sprites
 */

package rpgclient;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author Gaurav
 */
public class AnimatedSprite {
    BufferedImage [] imageList;
    float current_frame = 0;
    int num_frames = 0;

    AnimatedSprite(String sprite_name, int frame_count) {
        imageList = new BufferedImage[frame_count];
        for (int i = 1 ; i <= frame_count ; i ++ ) {
            try {
                imageList[i-1] = (ImageIO.read(new File(sprite_name + i + ".png")));
                num_frames = frame_count;
            } catch (Exception e) {
                System.out.println("ANIMATED_SPRITE::Failed to load images.");
            }
        }
    }

    public BufferedImage getDefaultFrame() {
        return imageList[0];
    }

    public BufferedImage animate(float distance) {
        return imageList [((int)(num_frames*distance)) % num_frames];
    }
    
}
