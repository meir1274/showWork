/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.logical.cpumembers.Processor;

/**
 *
 * @author MEIRKA
 */
public class RACmdNewValueIMP implements ActivityPieceINT{

    private int currRaValue;
    
    @Override
    public void execute() {
      Processor p =  LogicalCpu.getInstance().getProcessor();
 //     p.setRa((byte)currRaValue);
 //     gs.updateRa((byte)currRaValue);
   
    }

    public int getNewRaValue() {
        return currRaValue;
    }

    public void setNewRaValue(int newRaValue) {
        this.currRaValue = newRaValue;
    }

    public RACmdNewValueIMP(int newRaValue) {
        this.currRaValue = newRaValue;
    }
    
    private GuiSimulator    gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }
    
    
}
