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
import meirdev.simulator.logical.enums.CompareType;

/**
 *
 * @author MEIRKA
 */
public class RADecRaIMP implements ActivityPieceINT{

    private int currRaValue;
    private CmdType ct;
    private CompareType compareType;
    
    public RADecRaIMP(int newRaValue) {
        this.currRaValue = newRaValue;
        ct = CmdType.CT_INC;
        compareType = CompareType.COMP_TYPE_N;
    }
 
    public RADecRaIMP(int currRaValue, CmdType newCt) {
        this.currRaValue = currRaValue;
        this.ct = newCt;
        compareType = CompareType.COMP_TYPE_N;
    }
    
    @Override
    public void execute() {
      Processor p = LogicalCpu.getInstance().getProcessor();
        if (CmdType.CT_INC.equals(ct)) {
            currRaValue = 1;
        }
        int newRaValue = (p.getRa()-currRaValue)%256;
        
        if (compareType.equals(CompareType.COMP_TYPE_N))  {
            p.setRa((byte)newRaValue);
            gs.updateRa((byte) p.getRa());
        }
        else {
            p.updateFlags((byte)newRaValue);
        }
    }

    public int getNewRaValue() {
        return currRaValue;
    }

    public void setNewRaValue(int newRaValue) {
        this.currRaValue = newRaValue;
    }

    
    private GuiSimulator    gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }

    public void setCompType(CompareType compareType) {
        this.compareType = compareType;
    }
    
    
}
