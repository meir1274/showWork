/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.logical.cpumembers;

import meirdev.simulator.gui.frmae.Flag;
import meirdev.simulator.gui.interfaces.ComponnetsPartINT;

/**
 *
 * @author MEIRKA
 */
public class ProcessorRegisters implements ComponnetsPartINT{
    private Flag zFlag  = new Flag(0);
    private Flag cFlag  = new Flag(0);

    public Flag getzFlag() {
        return zFlag;
    }

    public void setzFlag(Flag zFlag) {
        this.zFlag = zFlag;
    }

    public Flag getcFlag() {
        return cFlag;
    }

    public void setcFlag(Flag cFlag) {
        this.cFlag = cFlag;
    }
    
    
    
    public int evaluateEqualization() {
        if (zFlag.getFlagValue()<0){
            throw new RuntimeException("Z flag contains wrong value.");
        } 
        if (zFlag.getFlagValue()>1){
            throw new RuntimeException("Z flag contains wrong value.");
        } 
        if (zFlag.getFlagValue()==1) {
            return 0;
        }else if (cFlag.getFlagValue()==0) {
            return 1;
        }else if (cFlag.getFlagValue()==1) {
            return -1;
        }
        throw new RuntimeException("No w");
    }

    @Override
    public void reset() {
        
        zFlag  = new Flag(0);
        cFlag  = new Flag(0);
    }
    
}
