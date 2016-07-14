/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import meirdev.simulator.logical.StepsModel;

/**
 *
 * @author MEIRKA
 */
public class SimpleJump implements JumpEvent{
    private byte jumpToAddr;

    public SimpleJump(byte jumpToAddr) {
        this.jumpToAddr = jumpToAddr;
    }

    @Override
    public void setGs(GuiSimulator gs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int jumpTo(StepsModel sm) {
        return sm.jumpToAddr(jumpToAddr);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
