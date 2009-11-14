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
    GAME_STATE state;
    LoginScreen login;
    LoadingScreen loading;
    GameMap map;

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
        }
        Main.getCanvas().repaint();
    }

    public void renderLoop(Graphics g) {

        if (checkState(GAME_STATE.LOGIN_STATE)) {
            
            loading = null;
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
            
            map.render(g);
        }

    }

}
