/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import meirdev.simulator.gui.interfaces.implementations.StackRetIMP;
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
public class StackRetIMPTest {
    
    public StackRetIMPTest() {
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
     * Test of execute method, of class StackRetIMP.
     */
    @Test
    public void testExecute() {
         System.out.println("handleNewOperValue");
        int newOperValue = 670;
        Byte bExpectedValue = Byte.valueOf("67");
        StackRetIMP instance = new StackRetIMP(67);
        LogicalCpu.getInstance().getProcessor().setPc(bExpectedValue.byteValue());
        Deque<Byte> stack = LogicalCpu.getInstance().getStack();
        stack.clear();
        stack.push(bExpectedValue);
        instance.handleNewOperValue(newOperValue);
        
       
        assertEquals(0, stack.size());
    }

 
}
