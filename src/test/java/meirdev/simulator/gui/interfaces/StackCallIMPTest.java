/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import java.util.Deque;
import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.logical.LogicalCpu;
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
public class StackCallIMPTest {
    
    public StackCallIMPTest() {
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
     * Test of execute method, of class StackCallIMP.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        StackCallIMP instance = null;
        

    }



    /**
     * Test of handleNewOperValue method, of class StackCallIMP.
     */
    @Test
    public void testHandleNewOperValue() {
        System.out.println("handleNewOperValue");
        int newOperValue = 670;
        Byte bExpectedValue = Byte.valueOf("67");
        StackCallIMP instance = new StackCallIMP(67);
        LogicalCpu.getInstance().getProcessor().setPc(bExpectedValue.byteValue());
        instance.handleNewOperValue(newOperValue);
        
        Deque<Byte> stack = LogicalCpu.getInstance().getStack();
        Byte bValue = stack.peek();
        assertEquals(bExpectedValue, bValue);
        
    }

   
}
