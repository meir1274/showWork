/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.events;

import meirdev.simulator.gui.events.implementations.GuiItems;

/**
 *
 * @author MEIRKA
 */
public interface  GuiEvent{
    public void updateGui(); 

    public void setGs(GuiItems gs);
}
