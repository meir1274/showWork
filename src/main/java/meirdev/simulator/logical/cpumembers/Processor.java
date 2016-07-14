/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.logical.cpumembers;

import meirdev.simulator.gui.frmae.Flag;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.gui.interfaces.ComponnetsPartINT;

/**
 *
 * @author MEIRKA
 */
public class Processor implements ComponnetsPartINT{
    private byte ra;
    private byte pc;
    private byte sp;

    public Processor() {
        ra = pc = sp = 0;
    }

    
    
    
    public byte getRa() {
        return ra;
    }

    public void setRa(byte ra) {
        
        updateFlags(ra);
        
        this.ra = ra;
    }

    public void updateFlags(byte ra1) {
        if (ra1 == 0) {
            LogicalCpu.getInstance().getPr().setzFlag(new Flag(1));
        } else {
            LogicalCpu.getInstance().getPr().setzFlag(new Flag(0));
        }
        if (ra1 < 0) {
            LogicalCpu.getInstance().getPr().setcFlag(new Flag(0));
        } else {
            LogicalCpu.getInstance().getPr().setcFlag(new Flag(1));
        }
    }

    public byte getPc() {
        return pc;
    }

    public void setPc(byte pc) {
        this.pc = pc;
    }

    public byte getSp() {
        return sp;
    }

    public void setSp(byte sp) {
        this.sp = sp;
    }
    
    public void incRa() {
        ++ra;
    }

    @Override
    public void reset() {
        pc = sp = ra = 0;
    }

    public void decRa() {
         --ra;
    }
    
    
}
