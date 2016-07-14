/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.simeasy8;

import meirdev.cpu.simeasy8.processor.OperationCode;

/**
 *
 * @author MEIRKA
 */
public class compiledLine {
    private AssemblerLine assemblerLine;
    private CpuAddress address;
    private CpuAddress secAddress;
    private OperationCode oc;
    private int reqWords;

    public compiledLine(AssemblerLine assemblerLine) {
        this.assemblerLine = assemblerLine;
    }

    
    
    
    
    
    public AssemblerLine getAssemblerLine() {
        return assemblerLine;
    }

    public void setAssemblerLine(AssemblerLine assemblerLine) {
        this.assemblerLine = assemblerLine;
    }

    public CpuAddress getAddress() {
        return address;
    }

    public void setAddress(CpuAddress address) {
        this.address = address;
    }

    public CpuAddress getSecAddress() {
        return secAddress;
    }

    public void setSecAddress(CpuAddress secAddress) {
        this.secAddress = secAddress;
    }

    public OperationCode getOc() {
        return oc;
    }

    public void setOc(OperationCode oc) {
        this.oc = oc;
    }

    public int getReqWords() {
        return reqWords;
    }

    public void setReqWords(int reqWords) {
        this.reqWords = reqWords;
    }
    
    
    
    
    
}
