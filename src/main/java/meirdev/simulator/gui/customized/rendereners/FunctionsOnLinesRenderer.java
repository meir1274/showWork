/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.customized.rendereners;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import meirdev.simulator.debugger.Breakpoint;
import static meirdev.simulator.gui.customized.rendereners.FocusedTitleListCellRenderer.changedDataBorder;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class FunctionsOnLinesRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
        List<Breakpoint> tempList = LogicalCpu.getInstance().getDebuggerAdapter().getBreakoutActiveList();
        
        boolean bIsBackout = false;
        Breakpoint b = LogicalCpu.getInstance().getDebuggerAdapter().findBreakpoint(index);
       
        
        String imageName = "default";
        if (b!=null &&  index ==b.getLinkToLine().getStartingAddress()) {
            imageName = "test";
        }
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/" + imageName + ".png"));
        renderer.setIcon(imageIcon);
        return renderer;
    }

}
