/*
 * This class parses packets recieved from the client.
 */

package rpgserver;

import java.io.*;

/**
 *
 * @author gm
 */
public class NetworkStreamParser {

    private BufferedReader netIn;

    public NetworkStreamParser(BufferedReader in) {

        netIn = in;
        
    }

    public char [] getNextMessage() {

        try {

            while (!netIn.ready()) {}

            char opcode[] = new char[2];
            netIn.read(opcode,0,2);

            int op = 255 * opcode[0] + opcode[1];

            switch(op) {

                case 'P'*255 + 'G':
                    
                    return opcode;

                default:

                    System.out.println("Server Error : Unidentified Network OPCODE.");
                    
                    return null;
                    
            }

        } catch (Exception e) {

            System.out.println("Server Error.");

            return null;

        }
        
    }

    
}
