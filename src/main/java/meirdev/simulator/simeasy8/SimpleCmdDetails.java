/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.simeasy8;

/**
 *
 * @author MEIRKA
 */
public class SimpleCmdDetails {
    private String instructionText;
    private int reqRecords;
    private int startingAddress;

    public SimpleCmdDetails(String instructionText, int reqRecords) {
        this.instructionText = instructionText;
        this.reqRecords = reqRecords;
    }

    public String getInstructionText() {
        return instructionText;
    }

    public void setInstructionText(String instructionText) {
        this.instructionText = instructionText;
    }

    public int getReqRecords() {
        return reqRecords;
    }

    public void setReqRecords(int reqRecords) {
        this.reqRecords = reqRecords;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(int startingAddress) {
        this.startingAddress = startingAddress;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.startingAddress;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleCmdDetails other = (SimpleCmdDetails) obj;
        if (this.startingAddress != other.startingAddress) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SimpleCmdDetails{" + "instructionText=" + instructionText + ", reqRecords=" + reqRecords + ", startingAddress=" + startingAddress + '}';
    }
    
    
    
    
}
