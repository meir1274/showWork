/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.events.implementations;

import java.util.List;
import javax.swing.JPanel;
import meirdev.simulator.gui.events.GuiEvent;

/**
 *
 * @author MEIRKA
 */
public interface GuiItems {
    public JPanel getSevenDigit();
    public List<GuiEvent> getGuiEventList();
}
