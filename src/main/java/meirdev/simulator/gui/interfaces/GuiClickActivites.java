/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author MEIRKA
 */
public interface  GuiClickActivites {
    public void updateMemory(JList memoryIndex,JList memoryList, JTextField newMemAddr, JTextField newMemValue);
}
