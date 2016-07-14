/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;

import java.util.Date;
import java.util.EventObject;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class StepEvent extends EventObject {
    
    private int idIndex;
    
    public StepEvent(Object source) {
        super(source);
    }

    public StepEvent(Object source, int identityHashCode, Date date) {
        super(source);
        this.idIndex = identityHashCode;
                
    }
    
}
