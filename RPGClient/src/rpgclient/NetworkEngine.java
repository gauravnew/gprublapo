/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgclient;

/**
 *
 * @author Gaurav
 */
public class NetworkEngine implements Runnable {

    private NetworkStreamWriter out;
    private String ServerIP;

    NetworkEngine() {
        ;
    }

    public void setServerIP(String ip) {
        ServerIP = ip;
    }

    public NetworkStreamWriter getNetworkOutput() {
        return out;
    }

    private boolean connectToServer() {
        return true;
    }

    @Override
    public void run() {

        if (!connectToServer()) return;

        Main.coreLogic.setState(GAME_STATE.LOADING_STATE);

    }

}
