/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.logical.enums.RefType;
import meirdev.simulator.gui.customized.models.SimulatorJListModel;

/**
 *
 * @author MEIRKA
 */
public class MovePullRaIMP implements ActivityPieceINT {

    final private int destAddr;
    final private RefType rt;

    public MovePullRaIMP(int secValue, RefType refType) {
        destAddr = secValue;
        rt = refType;
    }

    @Override
    public void execute() {
        Processor p = LogicalCpu.getInstance().getProcessor();

        int newAssignedValue = p.getRa();
        GuiClickActivitiesIMP activitiesIMP = new GuiClickActivitiesIMP();
        activitiesIMP.updateModel(LogicalCpu.getInstance().getMemoryModel(), destAddr, newAssignedValue);
    }

    public MovePullRaIMP(int newRaValue) {
        this.destAddr = newRaValue;
        rt = RefType.RT_REF;
    }

    private GuiSimulator gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }
}
