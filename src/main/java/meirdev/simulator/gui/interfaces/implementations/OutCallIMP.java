/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces.implementations;

import java.util.ArrayDeque;
import java.util.Deque;
import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.gui.frmae.JumpEvent;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.gui.frmae.SimpleJump;
import meirdev.simulator.gui.interfaces.ActivityPieceINT;

/**
 *
 * @author MEIRKA
 */
public class OutCallIMP implements ActivityPieceINT{

    private int operValue;
    
    @Override
    public void execute() {
      handleNewOperValue(operValue);
      JumpEvent je = new SimpleJump((byte)operValue);
      LogicalCpu.getInstance().setJumpEvent(je);
    }

    public int getNewRaValue() {
        return operValue;
    }

    public void handleNewOperValue(int newOperValue) {
        this.operValue = newOperValue;
        Deque<Byte> stack = LogicalCpu.getInstance().getStack();
        int pcValue = LogicalCpu.getInstance().getProcessor().getPc();
        stack.push((byte)pcValue);
        
    }

    public OutCallIMP(int newRaValue) {
        this.operValue = newRaValue;
    }
    
    private GuiSimulator    gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }
    
    
}
