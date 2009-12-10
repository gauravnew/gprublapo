/*
 * Filename : CoreGameLogic.java
 * Desciption : Data to represent the game AND
 *				Functions to handle all game events.
 */
/**
 *  TODO
 *	In function setState, do not create self actor, but rather wait to do so until
 *		actor list is transmitted during final countdown.
 */
package rpgclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

enum GAME_STATE {

    LOGIN_STATE, LOADING_STATE, INGAME_STATE
}

/**
 *
 * @author Gaurav
 */
public class CoreGameLogic {

    //Data and data access functions
    String main_actor_name;
    GAME_STATE state;
    LoginScreen login;
    LoadingScreen loading;
    GameMap map;
    UIEngine ui;
    ActorEngine actors;
    String message;
    String lastClass;
    String winner;

    public ActorEngine getActorEngine() {
        return actors;
    }

    public String getMainActorName() {
        return main_actor_name;
    }

    public void setMessage(String s) {
        message = new String(s);
    }

    public String getMessage() {
        return message;
    }

    public void setLastClass(String s) {
        lastClass = new String(s);
    }

    public String getLastClass() {
        return lastClass;
    }

    public void setMainActorName(String name) {
        main_actor_name = name;
    }

    public void setWinner(String s) {
        winner = new String(s);
    }

    public String getWinner() {
        return winner;
    }

    CoreGameLogic() {
        login = new LoginScreen();
        state = GAME_STATE.LOGIN_STATE;
        winner = null;
        message = new String("New game");
    }

    public synchronized LoadingScreen getLoadingScreen() {
        return loading;
    }

    public synchronized boolean checkState(GAME_STATE s) {
        return (state == s);
    }

    //Function to set the state and trigger propper affects when state changes
    public synchronized void setState(GAME_STATE s) {
        state = s;
        if (state == GAME_STATE.LOGIN_STATE && login == null) {
            login = new LoginScreen();
        }
        if (state == GAME_STATE.LOADING_STATE && loading == null) {
            loading = new LoadingScreen();
        }
        if (state == GAME_STATE.INGAME_STATE && map == null) {
            map = new GameMap();
            actors = new ActorEngine();
            ui = new UIEngine();
            ui.setPlayerName(main_actor_name);
            actors.setMainActor(actors.addActor(new Actor(0, 0, getMainActorName())));
            actors.getActor(0).position = new Point2D(30, 20);
            actors.getActor(0).moveto = new Point2D(30, 20);
        }
    }
    //[C.N.021]
    //Function to process keyboard input

    public void processInput() {
        KeyEvent k = Main.getCanvas().getKey();
        if (k == null) {
            return;
        }
        int key = k.getKeyCode();
        Point2D p;
        Actor a = actors.getActor(actors.getMainActor());
        switch (key) {
            case KeyEvent.VK_UP:
                p = new Point2D(a.position);
                p.setY(p.getY() - 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0) {
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);	//[C.N.022]
                }
                break;
            case KeyEvent.VK_DOWN:
                p = new Point2D(a.position);
                p.setY(p.getY() + 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0) {
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);	//[C.N.022]
                }
                break;
            case KeyEvent.VK_LEFT:
                p = new Point2D(a.position);
                p.setX(p.getX() - 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0) {
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);	//[C.N.022]
                }
                break;
            case KeyEvent.VK_RIGHT:
                p = new Point2D(a.position);
                p.setX(p.getX() + 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0) {
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);	//[C.N.022]
                }
                break;

        }
    }

    //Function to render graphics.  Calls render funcion according to current GAME_STATE
    public void renderLoop(Graphics g) {

        if (checkState(GAME_STATE.LOGIN_STATE)) {

            loading = null;
            map = null;
            login.render(g);

        } else if (checkState(state.LOADING_STATE)) {

            if (login != null) {
                login.finalize();
                login = null;
            }

            loading.render(g);

        } else if (checkState(state.INGAME_STATE)) {

            if (login != null) {
                login.finalize();
                login = null;
            }

            map.setLookAt(actors.getActor(actors.getMainActor()).position);
            map.render(g);
            actors.renderAll(g, map.getLookAt());
            ui.render(g);
            processInput();	//[C.N.021]
        }

    }
}

