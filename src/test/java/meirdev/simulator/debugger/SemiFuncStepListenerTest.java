/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;

import java.awt.event.ActionEvent;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.simeasy8.SimpleCmdDetails;
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
public class SemiFuncStepListenerTest {

    public SemiFuncStepListenerTest() {
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
     * Test of actionPerformed method, of class StepListener.
     */
    @Test
    public void testActionPerformedWithBreakpoint() {
        System.out.println("actionPerformed --> WithBreakpoint");

        int expResult = 1;

        Breakpoint bp = new Breakpoint();
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("MOVE", 2);
        cmdDetails.setStartingAddress(1);
        bp.setLinkToLine(cmdDetails);

        StepListener instance = new StepListener();
        instance.addBreakpoints(bp);

        ActionEvent e = new ActionEvent(cmdDetails, 34, "");
        LogicalCpu.getInstance().getDebuggerManager().setDebuggerStatus(0);
        instance.actionPerformed(e);

        int statusResult = LogicalCpu.getInstance().getDebuggerManager().getDebuggerStatus();
        assertEquals(expResult, statusResult);
    }

    @Test
    public void testActionPerformedWithNoBreakpoint() {
        System.out.println("actionPerformed --> WithNoBreakpoint");

        int expResult = 0;

        Breakpoint bp = new Breakpoint();
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("MOVE", 5);
        cmdDetails.setStartingAddress(1);

        SimpleCmdDetails cmdDetails2 = new SimpleCmdDetails("MOVE", 5);
        cmdDetails2.setStartingAddress(5);

        bp.setLinkToLine(cmdDetails);

        StepListener instance = new StepListener();
        instance.addBreakpoints(bp);

        ActionEvent e = new ActionEvent(cmdDetails2, 34, "");
        LogicalCpu.getInstance().getDebuggerManager().setDebuggerStatus(0);

        instance.actionPerformed(e);

        int statusResult = LogicalCpu.getInstance().getDebuggerManager().getDebuggerStatus();
        assertEquals(expResult, statusResult);
    }

    @Test
    public void testActionPerformedWithSomeAnotherBreakpoint() {
        System.out.println("actionPerformed --> WithAnotherBreakpoint");

        int expResult = 0;

        Breakpoint bp = crtBreakpoint(2);
        Breakpoint b1 = crtBreakpoint(2);
        Breakpoint b2 = crtBreakpoint(3);
        Breakpoint b3 = crtBreakpoint(4);
        
        
        SimpleCmdDetails cmdDetails2 = new SimpleCmdDetails("MOVE", 4);
        cmdDetails2.setStartingAddress(5);

        

        StepListener instance = new StepListener();
        instance.addBreakpoints(bp);
        instance.addBreakpoints(b1);
        instance.addBreakpoints(b2);
        instance.addBreakpoints(b3);
        
        ActionEvent e = new ActionEvent(cmdDetails2, 34, "");

        LogicalCpu.getInstance().getDebuggerManager().setDebuggerStatus(0);

        instance.actionPerformed(e);

        int statusResult = LogicalCpu.getInstance().getDebuggerManager().getDebuggerStatus();
        System.out.println("statusResult = " + statusResult);
        assertEquals(expResult, statusResult);

    }

    
    @Test
    public void testActionPerformedWithSomeAndRealBreakpoint() {
        System.out.println("actionPerformed --> WithSomeAndRealBreakpoint");

        int expResult = 1;

        Breakpoint bp = crtBreakpoint(2);
        Breakpoint b1 = crtBreakpoint(2);
        Breakpoint b2 = crtBreakpoint(3);
        Breakpoint b3 = crtBreakpoint(5);
        
        
        SimpleCmdDetails cmdDetails2 = new SimpleCmdDetails("MOVE", 4);
        cmdDetails2.setStartingAddress(5);

        

        StepListener instance = new StepListener();
        instance.addBreakpoints(bp);
        instance.addBreakpoints(b1);
        instance.addBreakpoints(b2);
        instance.addBreakpoints(b3);
        
        ActionEvent e = new ActionEvent(cmdDetails2, 34, "");

        LogicalCpu.getInstance().getDebuggerManager().setDebuggerStatus(0);

        instance.actionPerformed(e);

        int statusResult = LogicalCpu.getInstance().getDebuggerManager().getDebuggerStatus();
        System.out.println("statusResult = " + statusResult);
        assertEquals(expResult, statusResult);

    }
    
    public Breakpoint crtBreakpoint(int startingPoint) {
        Breakpoint bp = new Breakpoint();
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("MOVE", 5);
        cmdDetails.setStartingAddress(startingPoint);
        bp.setLinkToLine(cmdDetails);
        return bp;
    }

}
