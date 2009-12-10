package rpgclient;
/*
 * Filename : Point2D.java
 * Description : This class is responsible for storing
 * a position on a cartesian plane and performing
 * operations on them.
 */

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

        if (x != p.x) {
            return Math.abs(p.x - x);
        }
        if (y != p.y) {
            return Math.abs(p.y - y);
        }
        return 0;

    }

    public int moveTo(float distance, Point2D towards, int direction) {
        float k = distance / getDistance(towards);
        if (k >= 1) {
            x = towards.x;
            y = towards.y;
            return direction;
        } else {
            x += (towards.x - x) * k;
            y += (towards.y - y) * k;
            if (x > towards.x) {
                return 1;
            } else if (x < towards.x) {
                return 3;
            } else if (y < towards.y) {
                return 0;
            } else if (y > towards.y) {
                return 2;
            }
        }
        return direction;
    }
}

