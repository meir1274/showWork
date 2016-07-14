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
import meirdev.simulator.gui.interfaces.utils.GuiFormat;
import meirdev.simulator.gui.customized.models.SimulatorJListModel;

/**
 *
 * @author MEIRKA
 */
public class GuiClickActivitiesIMP implements GuiClickActivites{
  
    final private boolean UPDATE_CELL = true;

    @Override
    public void updateMemory(JList memoryIndex,JList memoryList, JTextField newMemAddr, JTextField newMemValue) {
        //Pull addr to be changed
        int selectedIndex = memoryList.getSelectedIndex();
        int upadateAddr = Integer.parseInt(newMemAddr.getText());
        
        //Pull new value
        int curValue = Integer.valueOf(newMemValue.getText());
        
        //Update data memory - model and display
        byte CurBValue = (byte) curValue;
        
        
        SimulatorJListModel alcbm = (SimulatorJListModel) memoryList.getModel();
        
        updateModel(alcbm, upadateAddr, curValue);
        memoryList.setSelectedIndex(selectedIndex);
        memoryList.updateUI();
    }

    public void updateModel(SimulatorJListModel alcbm, int upadateAddr, int curValue) {
        GuiFormat gf = new GuiFormat();
        String dataDisplay = gf.crtMemoryDisp(curValue);
        alcbm.updateValue(upadateAddr, dataDisplay, Integer.toString(curValue));
                
        if (UPDATE_CELL) {

            alcbm.setLastChangedValue(upadateAddr);
            alcbm.updateAnotherCell(upadateAddr);
        }
    }
}
