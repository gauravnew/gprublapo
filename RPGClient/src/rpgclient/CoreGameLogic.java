/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    String main_actor_name;
    GAME_STATE state;
    LoginScreen login;
    LoadingScreen loading;
    GameMap map;
    UIEngine ui;
    ActorEngine actors;

    public ActorEngine getActorEngine() {
        return actors;
    }

    public String getMainActorName() {
        return main_actor_name;
    }

    public void setMainActorName(String name) {
        main_actor_name = name;
    }

    CoreGameLogic() {
        login = new LoginScreen();
        state = GAME_STATE.LOGIN_STATE;
    }

    public synchronized LoadingScreen getLoadingScreen() {
        return loading;
    }

    public synchronized boolean checkState(GAME_STATE s) {
        return (state == s);
    }
    
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
            actors.setMainActor(actors.addActor(new Actor(0,0,getMainActorName())));
            actors.getActor(0).position = new Point2D(30,20);
            actors.getActor(0).moveto = new Point2D(30,20);
        }
    }

    public void processInput() {
        KeyEvent k = Main.getCanvas().getKey();
        if (k == null) return;
        int key = k.getKeyCode();
        Point2D p;
        Actor a = actors.getActor(actors.getMainActor());
        switch (key) {
            case KeyEvent.VK_UP:
                p = new Point2D(a.position);
                p.setY(p.getY() - 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0)
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);
                break;
            case KeyEvent.VK_DOWN:
                p = new Point2D(a.position);
                p.setY(p.getY() + 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0)
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);
                break;
            case KeyEvent.VK_LEFT:
                p = new Point2D(a.position);
                p.setX(p.getX() - 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0)
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);
                break;
            case KeyEvent.VK_RIGHT:
                p = new Point2D(a.position);
                p.setX(p.getX() + 1.0f);
                if (!a.isMoving() && map.getCellType(p) != 0)
                    Main.getNetworkEngine().getNetworkOutput().sendActorMove(p);
                break;

        }
    }

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
            processInput();
        }

    }

}

