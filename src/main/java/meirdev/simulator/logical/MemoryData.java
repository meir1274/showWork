/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.logical;

import meirdev.simulator.logical.enums.ImpactType;
import meirdev.simulator.logical.enums.RefType;
import com.sun.istack.internal.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JList;
import javax.swing.ListModel;
import meirdev.simulator.gui.frmae.ActivityPieceFactory;
import meirdev.simulator.gui.frmae.Command;
import meirdev.simulator.gui.interfaces.RACmdNewValueIMP;
import meirdev.simulator.gui.customized.models.SimulatorJListModel;

/**
 *
 * @author MEIRKA
 */
public class MemoryData {

    private JList<String> memoryContainer;
    private JList<String> cpuContainer;
    private JList<String> cpuMemoryContainer;

    private SimulatorJListModel cpuMemoryModel;

    public MemoryData(JList<String> newMemoryContainer, JList<String> newCpuContainer, JList<String> newCpuMemoryContainer) {
        memoryContainer = newMemoryContainer;
        cpuContainer = newCpuContainer;
        cpuMemoryContainer = newCpuMemoryContainer;

        cpuMemoryModel = (SimulatorJListModel) cpuMemoryContainer.getModel();
    }

    public void showImpactMemory(int reqAddr) {

        int value = pullValueFromMemory(reqAddr);
        Logger.getLogger(MemoryData.class).log(Level.INFO, "showImpactMemory: on index:[" + reqAddr + "], value is [" + value + "]");

        updateMemory(value, ImpactType.IT_IMPACT_ADDRESS);
    }

    protected int pullValueFromMemory(int reqAddr) throws NumberFormatException {
        String reqValue = cpuMemoryModel.getIntElementAt(reqAddr, RefType.RT_I);
        int value = Integer.valueOf(reqValue);
        return value;
    }

    public void showImpactCommand(int index) {
        ListModel temp = cpuContainer.getModel();
        SimulatorJListModel temp2 = (SimulatorJListModel) (temp);
        temp2.updateAnotherCell(index);
    }

    private void updateMemory(int value, ImpactType impactType) {
        ListModel temp = memoryContainer.getModel();
        SimulatorJListModel temp2 = (SimulatorJListModel) (temp);
        temp2.updateAnotherCell(value);
    }

   public  List executeCommand(Command cmd) {
        int valueInAddress = cmd.fetchCmdValue();
        int value = pullValueFromMemory(valueInAddress);

        List queue = new ArrayList();
        ActivityPieceFactory cf = new ActivityPieceFactory();
        queue.add(cf.getActivity(cmd.getCmdName(), value));

        return queue; 

    }
}
