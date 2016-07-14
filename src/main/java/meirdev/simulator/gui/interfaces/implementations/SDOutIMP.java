/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces.implementations;

import meirdev.simulator.gui.events.GuiEvent;
import meirdev.simulator.gui.events.implementations.SevenDigitIMP;
import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.gui.interfaces.ActivityPieceINT;

/**
 *
 * @author MEIRKA
 */
public class SDOutIMP extends BaseImp implements ActivityPieceINT {

    private Integer operandValue;
    public SDOutIMP(int secValue) {
        
        operandValue = secValue;
    }

    @Override
    public void execute() {
        
        SevenDigitIMP ge = new SevenDigitIMP(operandValue);
       //ge.setOutputNumber(operandValue);;
        super.getGuiSimulator().getGuiEventList().add(ge);
    }

    
}
