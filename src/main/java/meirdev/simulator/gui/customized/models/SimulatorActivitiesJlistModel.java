/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.customized.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import meirdev.simulator.debugger.Breakpoint;

/**
 *
 * @author MEIRKA
 */
public class SimulatorActivitiesJlistModel extends AbstractListModel implements ListModel {
    private List anArrayList;

    public SimulatorActivitiesJlistModel(List anArrayList) {
        this.anArrayList = anArrayList;
    }
    
    
    
    @Override
    public Object getElementAt(int index) {
        return anArrayList.get(index);
    }

    
    public void addBreakpoint(Breakpoint b){
        this.fireContentsChanged(this, b.getLinkToLine().getStartingAddress(), b.getLinkToLine().getStartingAddress());
    }

    @Override
    public int getSize() {
        return anArrayList.size();
    }
    
}
