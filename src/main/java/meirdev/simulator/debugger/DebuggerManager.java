/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;

import static meirdev.simulator.debugger.ActionsManager.SIM_ACTION_PAUSE;

/**
 *
 * @author MEIRKA
 */
public class DebuggerManager {
    private int debuggerStatus = 0;

    public DebuggerManager() {
        debuggerStatus = 0;
    }

    public int getDebuggerStatus() {
        return debuggerStatus;
    }

    public void setDebuggerStatus(Object debuggerStatus) {
        if (debuggerStatus.equals(SIM_ACTION_PAUSE)) {
        this.debuggerStatus = 1;
        }else {
            this.debuggerStatus = 0;
        }
    }

    
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public boolean couldRun() {
        if (status == 1) {
            status = 0;
            return false;
        } else {
            return true;
        }
        
    }
    
    
    
    
    
}
