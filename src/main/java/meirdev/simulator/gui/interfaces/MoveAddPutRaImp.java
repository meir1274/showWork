/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.logical.enums.RefType;

/**
 *
 * @author meirka
 */
public class MoveAddPutRaImp extends MovePutRaIMP{
    
    public MoveAddPutRaImp(int newRaValue) {
        super(newRaValue,RefType.RT_REF);
        
    }
     public void assignRaValue(Processor p, byte newRaValue) {
        int tempRaValue = (p.getRa()+newRaValue)%256;
        p.setRa((byte)tempRaValue); 

    }  
}
