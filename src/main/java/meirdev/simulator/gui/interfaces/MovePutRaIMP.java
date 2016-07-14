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
public class MovePutRaIMP implements ActivityPieceINT {

    private int currRaValue;
    private RefType rt;

    public MovePutRaIMP(int secValue, RefType refType) {
        currRaValue = secValue;
        rt = refType;
    }

    @Override
    public void execute() {
        Processor p = LogicalCpu.getInstance().getProcessor();

        byte newRaValue = evaluateAssignedValue(currRaValue);
        assignRaValue(p, newRaValue);
        
    }

    public void assignRaValue(Processor p, byte newRaValue) {
        p.setRa(newRaValue);
    }

    public int getNewRaValue() {
        return currRaValue;
    }

    public void setNewRaValue(int newRaValue) {
        this.currRaValue = newRaValue;
    }

    public MovePutRaIMP(int newRaValue) {
        this.currRaValue = newRaValue;
        rt = RefType.RT_I;
    }

    private GuiSimulator gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }

    protected byte evaluateAssignedValue(int currRaValue) {
        byte reqValue = 0;
        switch (rt) {
            case RT_I:
                reqValue =  (byte)currRaValue;
                break;
            case RT_REF:
                SimulatorJListModel memoryModel = LogicalCpu.getInstance().getMemoryModel();
                reqValue = Integer.valueOf(memoryModel.getIntElementAt(currRaValue, rt)).byteValue();
                break;
            default:
                throw new RuntimeException("No such reference type avilable.");
        }
        return reqValue;
    }
 
}
