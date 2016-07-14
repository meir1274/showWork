/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class StepListener implements ActionListener{
    private List<Breakpoint> currBreakpoints;

    public StepListener() {
        currBreakpoints = new ArrayList<>();
    }

    
    
    
    public void addBreakpoints(Breakpoint bp) {
        if (currBreakpoints ==null) {
            currBreakpoints = new ArrayList<>(5);
        }
        
        currBreakpoints.add(bp);

    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        SimpleCmdDetails cmdDetails =  (SimpleCmdDetails) e.getSource();
        
        if (e.getActionCommand().equals("verify_exe_status"))  {
            Breakpoint b = LogicalCpu.getInstance().getDebuggerAdapter().findBreakpoint(cmdDetails.getStartingAddress());
            if (b!=null){
                DebuggerManager dm = LogicalCpu.getInstance().getDebuggerManager();
                dm.setStatus(1);
            }
            return;
        }
       
       for (Breakpoint currBreakpoint : currBreakpoints) {
           SimpleCmdDetails breakpointCmdDetails = currBreakpoint.getLinkToLine();
           System.out.println("breakpointCmdDetails:"+breakpointCmdDetails);
           System.out.println("Input event details:"+cmdDetails);
           if (breakpointCmdDetails.equals(cmdDetails)) {
               
               Object statusType = ActionsManager.SIM_ACTION_PAUSE;
               DebuggerManager dm = LogicalCpu.getInstance().getDebuggerManager();
               dm.setDebuggerStatus(statusType);
           }
       }
    }
    
}
