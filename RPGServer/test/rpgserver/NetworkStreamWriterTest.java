/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gaurav
 */
public class NetworkStreamWriterTest {

    public NetworkStreamWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sendPingReply method, of class NetworkStreamWriter.
     */
    @Test
    public void testSendPingReply() {
        System.out.println("sendPingReply");
        NetworkStreamWriter instance = null;
        instance.sendPingReply();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}