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
import meirdev.simulator.gui.interfaces.MovePutRaIMP;

/**
 *
 * @author MEIRKA
 */
public class SDInIMP extends MovePutRaIMP implements ActivityPieceINT {

    public SDInIMP(int secValue) {
        super(secValue);
    }

    @Override
    public void execute() {

        Integer val = this.getGs().pullInValue();
        this.setNewRaValue(val);
        super.execute();

    }

}
