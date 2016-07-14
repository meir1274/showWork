/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import meirdev.simulator.gui.interfaces.ActivityPieceINT;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MEIRKA
 */
public class CmdFactoryTest {
    
    public CmdFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
       /**
     * Test of getActivity method, of class ActivityPieceFactory.
     */
    @Test
    public void testAddiActivity() {
        System.out.println("ADDI positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "RAIncRaIMP";
        ActivityPieceINT result = instance.getActivity("ADDI RA,56", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }



    
       /**
     * Test of getActivity method, of class ActivityPieceFactory.
     */
    @Test
    public void testAddActivity() {
        System.out.println("ADD positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "MoveAddPutRaImp";
        ActivityPieceINT result = instance.getActivity("ADD RA,56", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }
    
    /**
     * Test of getActivity method, of class ActivityPieceFactory.
     */
    @Test
    public void testIncActivity() {
        System.out.println("INC positive command.");
        String cmdName = "";
        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "RAIncRaIMP";
        ActivityPieceINT result = instance.getActivity("INC 56", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);

    }
   /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command DEC
     */
    @Test
    public void testDecActivity() {
        System.out.println("INC positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "RADecRaIMP";
        ActivityPieceINT result = instance.getActivity("DEC 56", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }
    
   /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command DEC
     */
    @Test
    public void testSubiActivity() {
        System.out.println("INC positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "RADecRaIMP";
        ActivityPieceINT result = instance.getActivity("SUBI RA,5", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }
       
   /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command CALL
     */
    @Test
    public void testCallActivity() {
        System.out.println("CALL positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "StackCallIMP";
        ActivityPieceINT result = instance.getActivity("CALL 56", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }  
    
      /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command MOVE RA, ...
     */
    @Test
    public void testMovePuttActivity() {
        System.out.println("Move positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "MovePutRaIMP";
        ActivityPieceINT result = instance.getActivity("MOVEI RA,56", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }
    
         /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command MOVE RA, ...
     */
    @Test
    public void testMovePullActivity() {
        System.out.println("Move positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "MovePullRaIMP";
        ActivityPieceINT result = instance.getActivity("MOVE 56,RA", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }
   /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command CALL
     */
    @Test
    public void testRetActivity() {
        System.out.println("CALL positive command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "StackRetIMP";
        ActivityPieceINT result = instance.getActivity("RET", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }     
       /**
     * Test of getActivity method, of class ActivityPieceFactory.
     * Test here the case when input was the command OUT
     */
    @Test
    public void testOutActivity() {
        System.out.println("OUT command.");

        int secValue = 0;
        ActivityPieceFactory instance = new ActivityPieceFactory();
        String expResult = "SDOutIMP";
        ActivityPieceINT result = instance.getActivity("OUT 34", secValue);
        String className = result.getClass().getSimpleName();
        assertEquals(expResult, className);
    }  
}
