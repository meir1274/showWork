/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import meirdev.simulator.gui.customized.models.SimulatorActivitiesJlistModel;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class DebuggerAdapter {

    private List<Breakpoint> breakoutActiveList;

    public DebuggerAdapter() {
    }

    public List<Breakpoint> getBreakoutActiveList() {
        return breakoutActiveList;
    }

    public void setBreakoutActiveList(List<Breakpoint> breakoutActiveList) {
        this.breakoutActiveList = breakoutActiveList;
    }

    public Breakpoint findBreakpoint(int selectedIndex) {
        Breakpoint selectedBreakpoint = null;
        for (Breakpoint breakpoint : breakoutActiveList) {
            if (breakpoint.getLinkToLine().getStartingAddress()==selectedIndex){
                selectedBreakpoint = breakpoint;
                
            }
        }
        return selectedBreakpoint;
    }



    public void addRemoveBreakpoint(JList<String> pcCpuIndex) {

        Breakpoint b = findBreakpoint(pcCpuIndex.getSelectedIndex());
        Breakpoint breakpoint = new Breakpoint();
        if (b == null) {
            SimpleCmdDetails cmdDetails = crtNewBreakpoint(pcCpuIndex.getSelectedIndex(), breakpoint);
            breakpoint.setLinkToLine(cmdDetails);
        }else {
            breakoutActiveList.remove(b);
            breakpoint = b;
        }
        fireBreakpoint(pcCpuIndex, breakpoint);
    }

    public void fireBreakpoint(JList<String> pcCpuIndex, Breakpoint breakpoint) {
        AbstractListModel listModel = (AbstractListModel) pcCpuIndex.getModel();
        if (listModel instanceof SimulatorActivitiesJlistModel) {
            SimulatorActivitiesJlistModel childModel = (SimulatorActivitiesJlistModel) listModel;
            childModel.addBreakpoint(breakpoint);
        }
    }

    public SimpleCmdDetails crtNewBreakpoint(int breakpoitAddress, Breakpoint breakpoint) {
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("TEST", 4);
        cmdDetails.setStartingAddress(breakpoitAddress);
        breakoutActiveList.add(breakpoint);
        return cmdDetails;
    }

}
