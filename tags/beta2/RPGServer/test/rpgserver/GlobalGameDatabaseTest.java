/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpgserver;

import java.util.Set;
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
public class GlobalGameDatabaseTest {

    public GlobalGameDatabaseTest() {
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
     * Test of getHashtableKeys method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetHashtableKeys() {
        System.out.println("getHashtableKeys");
        GlobalGameDatabase instance = new GlobalGameDatabase();
        Set expResult = null;
        Set result = instance.getHashtableKeys();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNewPlayerCharacter method, of class GlobalGameDatabase.
     */
    @Test
    public void testCreateNewPlayerCharacter() {
        System.out.println("createNewPlayerCharacter");
        ClientHandler c = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        int expResult = 0;
        int result = instance.createNewPlayerCharacter(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNewNonPlayerCharacter method, of class GlobalGameDatabase.
     */
    @Test
    public void testCreateNewNonPlayerCharacter_int() {
        System.out.println("createNewNonPlayerCharacter");
        int type = 0;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        int expResult = 0;
        int result = instance.createNewNonPlayerCharacter(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNewNonPlayerCharacter method, of class GlobalGameDatabase.
     */
    @Test
    public void testCreateNewNonPlayerCharacter_int_Point2D() {
        System.out.println("createNewNonPlayerCharacter");
        int type = 0;
        Point2D mapPos = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        int expResult = 0;
        int result = instance.createNewNonPlayerCharacter(type, mapPos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteActor method, of class GlobalGameDatabase.
     */
    @Test
    public void testDeleteActor() {
        System.out.println("deleteActor");
        Integer ActorID = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        boolean expResult = false;
        boolean result = instance.deleteActor(ActorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActorPosition method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetActorPosition() {
        System.out.println("getActorPosition");
        Integer ActorID = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        Point2D expResult = null;
        Point2D result = instance.getActorPosition(ActorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActorMoveTo method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetActorMoveTo() {
        System.out.println("getActorMoveTo");
        Integer ActorID = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        Point2D expResult = null;
        Point2D result = instance.getActorMoveTo(ActorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActorSpeed method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetActorSpeed() {
        System.out.println("getActorSpeed");
        Integer ActorID = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        float expResult = 0.0F;
        float result = instance.getActorSpeed(ActorID);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActorStatusFlags method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetActorStatusFlags() {
        System.out.println("getActorStatusFlags");
        Integer ActorID = null;
        int flag = 0;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        int expResult = 0;
        int result = instance.getActorStatusFlags(ActorID, flag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActorType method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetActorType() {
        System.out.println("getActorType");
        Integer ActorID = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        int expResult = 0;
        int result = instance.getActorType(ActorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActorPosition method, of class GlobalGameDatabase.
     */
    @Test
    public void testSetActorPosition() {
        System.out.println("setActorPosition");
        Integer ActorID = null;
        Point2D p = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        instance.setActorPosition(ActorID, p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActorMoveTo method, of class GlobalGameDatabase.
     */
    @Test
    public void testSetActorMoveTo() {
        System.out.println("setActorMoveTo");
        Integer ActorID = null;
        Point2D p = null;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        instance.setActorMoveTo(ActorID, p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActorSpeed method, of class GlobalGameDatabase.
     */
    @Test
    public void testSetActorSpeed() {
        System.out.println("setActorSpeed");
        Integer ActorID = null;
        float s = 0.0F;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        instance.setActorSpeed(ActorID, s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActorStatusFlag method, of class GlobalGameDatabase.
     */
    @Test
    public void testSetActorStatusFlag() {
        System.out.println("setActorStatusFlag");
        Integer ActorID = null;
        int flag = 0;
        boolean state = false;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        instance.setActorStatusFlag(ActorID, flag, state);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActorType method, of class GlobalGameDatabase.
     */
    @Test
    public void testSetActorType() {
        System.out.println("setActorType");
        Integer ActorID = null;
        int type = 0;
        GlobalGameDatabase instance = new GlobalGameDatabase();
        instance.setActorType(ActorID, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPlayerCharacters method, of class GlobalGameDatabase.
     */
    @Test
    public void testGetAllPlayerCharacters() {
        System.out.println("getAllPlayerCharacters");
        GlobalGameDatabase instance = new GlobalGameDatabase();
        Integer[] expResult = null;
        Integer[] result = instance.getAllPlayerCharacters();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActorName method, of class GlobalGameDatabase.
     */
    @Test
    public void testSetActorName() {
        System.out.println("setActorName");
        Integer ActorID = null;
        String name = "";
        GlobalGameDatabase instance = new GlobalGameDatabase();
        instance.setActorName(ActorID, name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}