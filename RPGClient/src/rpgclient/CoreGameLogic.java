/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgclient;

enum GAME_STATE {
    LOGIN_STATE
}
/**
 *
 * @author Gaurav
 */
public class CoreGameLogic {
    GAME_STATE state;
    LoginScreen login;
    CoreGameLogic() {
        login = new LoginScreen();
        state = GAME_STATE.LOGIN_STATE;
    }

}
