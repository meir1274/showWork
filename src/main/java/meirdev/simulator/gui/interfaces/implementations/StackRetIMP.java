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
public class StackRetIMP implements ActivityPieceINT{

    private int operValue;

    public int getOperValue() {
        return operValue;
    }

    public void setOperValue(int operValue) {
        this.operValue = operValue;
    }
    
    @Override
    public void execute() {
      operValue = handleNewOperValue(operValue)+2;
      JumpEvent je = new SimpleJump((byte)operValue);
      LogicalCpu.getInstance().setJumpEvent(je);
    }

    public int getNewRaValue() {
        return operValue;
    }

    public byte handleNewOperValue(int newOperValue) {
        this.operValue = newOperValue;
        Deque<Byte> stack = LogicalCpu.getInstance().getStack();
        int pcValue = LogicalCpu.getInstance().getProcessor().getPc();
        return stack.poll();
        
    }

    public StackRetIMP() {
       
    }

    public StackRetIMP(int operValue) {
        this.operValue = operValue;
    }
    
    private GuiSimulator    gs;

    public GuiSimulator getGs() {
        return gs;
    }

    public void setGs(GuiSimulator gs) {
        this.gs = gs;
    }
    
    
}
