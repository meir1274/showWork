/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae.utils;

/**
 *
 * @author MEIRKA
 */
public class GuiAdapter {
    private int[] pcMemory;

    public GuiAdapter() {
        pcMemory = new int[256];
    }

    public int[] getPcMemory() {
        return pcMemory;
    }

    public void setPcMemory(int[] pcMemory) {
        this.pcMemory = pcMemory;
    }
    
    
    
    
}
