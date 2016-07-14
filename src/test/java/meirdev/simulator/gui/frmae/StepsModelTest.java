/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import meirdev.simulator.logical.cpumembers.ProcessorRegisters;
import meirdev.simulator.logical.StepsModel;
import java.util.LinkedList;
import java.util.List;
import meirdev.simulator.simeasy8.SimpleCmdDetails;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author MEIRKA
 */
@RunWith(PowerMockRunner.class)
public class StepsModelTest {
    @Mock
    ProcessorRegisters mockPr;
    public StepsModelTest() {
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
     * Test of next method, of class StepsModel.
     */
    public void testSingleNextStep2() {
        System.out.println("next");
        StepsModel instance = null;
        int expResult = 2;

        List<SimpleCmdDetails> newList =   new LinkedList<>();
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("", 0);
        SimpleCmdDetails cmdDetails2 = new SimpleCmdDetails("", 1);
        newList.add(cmdDetails);
        newList.add(cmdDetails2);
        cmdDetails.setStartingAddress(2);
        newList.add(cmdDetails);
        byte[] newMemory = {0,0};
        StepsModel model = new StepsModel(mockPr, newList, newMemory);
        model.setStartingIndex(1);
        assertEquals(expResult, model.next());

    }
    /**
     * Test of next method, of class StepsModel.
     */

    public void testJump() {
        System.out.println("next");
        
        int expResult = 5;

        List<SimpleCmdDetails> newList =   new LinkedList<>();
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("JUMP", 0);
        SimpleCmdDetails cmdDetails2 = new SimpleCmdDetails("", 1);
        newList.add(cmdDetails);
        newList.add(cmdDetails2);
        newList.add(cmdDetails2);
        newList.add(cmdDetails2);
        newList.add(cmdDetails2);
        newList.add(cmdDetails);
        byte[] newMemory = {0,5};
        
        StepsModel model = new StepsModel(mockPr, newList, newMemory);
        model.setStartingIndex(0);
        assertEquals(expResult, model.next());

    }


    
}
