/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces.implementations;

import meirdev.simulator.gui.frmae.GuiSimulator;
import meirdev.simulator.gui.interfaces.ActivityPieceINT;

/**
 *
 * @author MEIRKA
 */
public abstract class  BaseImp implements ActivityPieceINT {
    private GuiSimulator guiSimulator;
    @Override
    public void setGs(GuiSimulator gs) {
        guiSimulator = gs;
    }

    public GuiSimulator getGuiSimulator() {
        return guiSimulator;
    }
    
}
