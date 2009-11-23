/*
 * Filename : Point2D.java
 * Description : This class is responsible for storing
 * a position on a cartesian plane and performing
 * operations on them.
 */

/**
  *  TODO
  *	Implemnt function moveTo.  (Copy from client side)
  */  

package rpgserver;

/**
 *
 * @author Gaurav
 */
public class Point2D {
    
    private float x;
    private float y;

    public Point2D() {

        x = 0;
        y = 0;

    }
    
    public Point2D(float in_X, float in_Y) {

        x = in_X;
        y = in_Y;

    }
    
    public Point2D(Point2D p) {

        x = p.x;
        y = p.y;

    }

    public float getX() {

        return x;

    }

    public float getY() {

        return y;

    }

    public void setX(float in_x) {

        x = in_x;

    }

    public void setY(float in_Y) {

        y = in_Y;

    }

    public void setPosition(float in_X, float in_Y) {

        x = in_X;
        y = in_Y;

    }

    public boolean equals(Point2D p) {

        return (p.x == x && p.y == y);

    }

    public float getDistance(Point2D p) {

        return (float)Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y));

    }

    public void moveTo(float distance, Point2D towards) {
        //TODO: Implement this function.
        //Scale dx and dy by (distance / getDistance(this,towards))
    }
}
