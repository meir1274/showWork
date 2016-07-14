/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.logical.enums.CmdType;

/**
 *
 * @author MEIRKA
 */
public class RAIncRaIMP implements ActivityPieceINT {

    public RAIncRaIMP(int currRaValue, CmdType ct) {
        this.currRaValue = currRaValue;
        this.ct = ct;
    }

    private int currRaValue;
    private CmdType ct;

    public int getCurrRaValue() {
        return currRaValue;
    }

    public void setCurrRaValue(int currRaValue) {
        this.currRaValue = currRaValue;
    }

    public CmdType getCt() {
        return ct;
    }

    public void setCt(CmdType ct) {
        this.ct = ct;
    }

    @Override
    public void execute() {
        Processor p = LogicalCpu.getInstance().getProcessor();
        if (CmdType.CT_INC.equals(ct)) {
            currRaValue = 1;
        }
        int newRaValue = (p.getRa()+currRaValue)%256;
        p.setRa((byte)newRaValue);
        gs.updateRa((byte) p.getRa());
    }

    public int getNewRaValue() {
        return currRaValue;
    }

    public void setNewRaValue(int newRaValue) {
        this.currRaValue = newRaValue;
    }

    public RAIncRaIMP(int newRaValue) {
        this.currRaValue = newRaValue;
        ct = CmdType.CT_INC;
    }

    private GuiSimulator gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }

}
