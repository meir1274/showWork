/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class Command implements MemImpactCmdInt{
    private SimpleCmdDetails cmdDetails;
    private String cmdName;
    
    
    public Command(SimpleCmdDetails get) {
        cmdDetails = get;
        cmdName = cmdDetails.getInstructionText();
    }

    @Override
    public int fetchImpactMemory() {
        return 3;
    }
    public int fetchCmdValue(){
        return cmdDetails.getStartingAddress()+1;
    }
    void showPre() {
        
    }

    
    public String getCmdName() {
        return cmdName;
    }

    public SimpleCmdDetails getCmdDetails() {
        return cmdDetails;
    }

    public void setCmdDetails(SimpleCmdDetails cmdDetails) {
        this.cmdDetails = cmdDetails;
    }
    
    
    
}
