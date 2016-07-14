/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.logical;

import java.util.Iterator;
import java.util.List;
import meirdev.simulator.gui.frmae.CmdIterato;
import meirdev.simulator.gui.frmae.Command;

import meirdev.simulator.logical.cpumembers.ProcessorRegisters;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class StepsModel implements Iterable<Integer>{

    private static int startingIndex;
    final private byte[] memory;
    final private List<SimpleCmdDetails> currInstList;
    final private ProcessorRegisters pr;
    private SimpleCmdDetails scd = null;

    
    
    
    public SimpleCmdDetails getScd() {
        if (scd == null) {
            setCurrCommand(currInstList.get(startingIndex));
        }
        return scd;
    }

    public StepsModel(ProcessorRegisters newPr, List<SimpleCmdDetails> newList, byte[] newMemory) {
        currInstList = newList;
        memory = newMemory;
        startingIndex = 0;
        this.pr = newPr;
    }

    /**
     *
     * @return 
     */
    final public int next() {
        String cmdText = null;
        if (startingIndex < 0) {
            ++startingIndex;
            return startingIndex;
        } else {

        }
        cmdText = currInstList.get(startingIndex).getInstructionText();
        setCurrCommand(currInstList.get(startingIndex));
        if (cmdText != null && cmdText.startsWith("J")) {

            int equalValue = pr.evaluateEqualization();
            if (cmdText.startsWith("JLESS") && equalValue == -1) {
                return jmpTo();
            } else if (cmdText.startsWith("JGREATER") && equalValue == 1) {
                return jmpTo();
            } else if (cmdText.startsWith("JEQUAL") && equalValue == 0) {
                return jmpTo();
            } else if (cmdText.startsWith("JUMP")) {
                return jmpTo();
            }
        }
        ++startingIndex;
        SimpleCmdDetails cmdDetails = currInstList.get(startingIndex);
     //   setCurrCommand(cmdDetails);
        return cmdDetails.getStartingAddress();
    }
    
    
    public SimpleCmdDetails getNextCommand() {
        return currInstList.get(startingIndex);
    }


    public int jmpTo() {
        int reqNextAddress = 0;
        SimpleCmdDetails cmdDetails = currInstList.get(startingIndex);
     //    setCurrCommand(cmdDetails);
        reqNextAddress = memory[cmdDetails.getStartingAddress() + 1];
        return jumpToAddr(reqNextAddress);
    }

    public int jumpToAddr(int reqNextAddress) {
        int tempSelectedAddr = Math.min(reqNextAddress, currInstList.size()-1);
        
        int realStartIndex = currInstList.get(tempSelectedAddr).getStartingAddress();
        
        while (realStartIndex > reqNextAddress) {
            --tempSelectedAddr;
            realStartIndex = currInstList.get(tempSelectedAddr).getStartingAddress();
        }
        startingIndex=tempSelectedAddr;
        return reqNextAddress;
    }

    public static int getStartingIndex() {
        return startingIndex;
    }

    public static void setStartingIndex(int startingIndex) {
        StepsModel.startingIndex = startingIndex;
    }

    /**
     *
     * @return
     */
    public Command getCurrCommand() {   
        return new Command(scd);
    }

    private void setCurrCommand(SimpleCmdDetails get) {
        scd = get;
    }

    public boolean hasNext() {
        return !currInstList.get(startingIndex).getInstructionText().startsWith("STOP");
    }

    @Override
    public Iterator<Integer> iterator() {
        return new CmdIterato<Integer>(this);
    }

}
