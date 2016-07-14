/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import java.util.Iterator;
import meirdev.simulator.logical.StepsModel;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class CmdIterato<T> implements Iterator<T> {
    private StepsModel stepModel;

    public CmdIterato(StepsModel stepModel) {
        this.stepModel = stepModel;
    }
    
    
    
    
    
    
    @Override
    public boolean hasNext() {
        SimpleCmdDetails cmdDetails =  stepModel.getNextCommand();
        if (cmdDetails.getInstructionText().startsWith("STOP")) {
            return false;
        }
        return true;
    }

    @Override
    public T next() {
        Integer nextAddress = stepModel.next();
        return (T)nextAddress;
    }
    
}
