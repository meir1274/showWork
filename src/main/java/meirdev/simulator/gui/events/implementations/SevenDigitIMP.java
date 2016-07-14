/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.events.implementations;

import meirdev.simulator.gui.customized.panels.CPanelSevenDigit;
import meirdev.simulator.gui.events.GuiEvent;


/**
 *
 * @author MEIRKA
 */
public class SevenDigitIMP implements GuiEvent{
    private GuiItems gs;
    private Integer outputNumber;

    public SevenDigitIMP(Integer outputNumber) {
        this.outputNumber = outputNumber;
    }
    
    
    
    @Override
    public void updateGui() {
        CPanelSevenDigit sevenDigit = (CPanelSevenDigit) gs.getSevenDigit();
        sevenDigit.setNumber(outputNumber);
    }

    @Override
    public void setGs(GuiItems coreGS) {
        gs = coreGS;
    }

    public Integer getOutputNumber() {
        return outputNumber;
    }

    public void setOutputNumber(Integer outputNumber) {
        this.outputNumber = outputNumber;
    }
    
 }
