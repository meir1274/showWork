/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.logical.enums.CompareType;
import meirdev.simulator.logical.enums.RefType;

/**
 *
 * @author meirka
 */
public class MoveDecPutRaImp extends MovePutRaIMP{
    private CompareType compType;


    public MoveDecPutRaImp(int newRaValue) {
        super(newRaValue,RefType.RT_REF);
        compType = CompareType.COMP_TYPE_N;
        
    }
    
    
    private void updateFlags(Processor p, int tempRaValue) {
        p.updateFlags((byte)tempRaValue);
    }
     public void assignRaValue(Processor p, byte newRaValue) {
        int tempRaValue = (p.getRa()-newRaValue)%256;
        updateFlags(p, tempRaValue);
        if (compType == null || compType.equals(CompareType.COMP_TYPE_N)) {
            p.setRa((byte)tempRaValue);
        }
     }  

    public void setCompType(CompareType compType) {
        this.compType = compType;
    }


}
